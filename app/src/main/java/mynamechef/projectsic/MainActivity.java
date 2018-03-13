package mynamechef.projectsic;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    public static Button upload;
    public static Button download;
    public static Button uploadApp;
    public static PDFView pdfview;

    static DropboxAPI<AndroidAuthSession> dropboxAPI;
    private static final String APP_KEY = "uli22ywt0ycnpil";
    private static final String APP_SECRET = "m7ben3wso7vmes7";
    private static final String ACCESSTOKEN = "5wKAV011pZAAAAAAAAAAcbtN8QDnISQNco0kl_88zkA4WdQGXSYwxLiC1v4xAoVR";
    private DropboxAPI.UploadRequest request;

    public static String path = getAssets().toString()  ;//Environment.getDataDirectory().getAbsolutePath() + "/Downloads/";
    public static File Dir = new File(path);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload = (Button)findViewById(R.id.upload);
        download = (Button) findViewById(R.id.download);
        uploadApp = (Button)findViewById(R.id.uploadapp);
        PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("Augustine--Confessions.pdf").load();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadToDropboxFromFilemanager("ProjectSIC_Test_Folder/UploadFileFromFileManager");
            }
        });

        uploadApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadToDropboxFromSelectedApp("ProjectSIC_Test_Folder/UploadFileFromSelectedApp");
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadFromDropboxFromPath(path + "downloadFileFromDropbox", "ProjectSIC_Test_Folder/week2class1s.pdf");
            }
        });



        AndroidAuthSession session = buildSession();
        dropboxAPI = new DropboxAPI<AndroidAuthSession>(session);

        Dir.mkdirs();
    }


    private AndroidAuthSession buildSession()
    {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        session.setOAuth2AccessToken(ACCESSTOKEN);
        return session;
    }
    static final int UploadFromSelectApp = 9501;
    static final int UploadFromFilemanager = 9502;
    public static String DropboxUploadPathFrom = "";
    public static String DropboxUploadName = "";
    public static String DropboxDownloadPathFrom = "";
    public static String DropboxDownloadPathTo = "";

    private void UploadToDropboxFromPath (String uploadPathFrom, String uploadPathTo)
    {
        Toast.makeText(getApplicationContext(), "Upload file ...", Toast.LENGTH_SHORT).show();
        final String uploadPathF = uploadPathFrom;
        final String uploadPathT = uploadPathTo;
        Thread th = new Thread(new Runnable()
        {
            public void run()
            {
                File tmpFile = null;
                try
                {
                    tmpFile = new File(uploadPathF);
                }
                catch (Exception e) {e.printStackTrace();}
                FileInputStream fis = null;
                try
                {
                    fis = new FileInputStream(tmpFile);
                }
                catch (FileNotFoundException e) {e.printStackTrace();}
                try
                {
                    dropboxAPI.putFileOverwrite(uploadPathT, fis, tmpFile.length(), null);
                }
                catch (Exception e) {}
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File successfully uploaded.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        th.start();
    }


    private void UploadToDropboxFromSelectedApp (String uploadName)
    {
        DropboxUploadName = uploadName;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Upload from ..."), UploadFromSelectApp);
    }

    private void UploadToDropboxFromFilemanager (String uploadName)
    {
        DropboxUploadName = uploadName;
        Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        intent.putExtra("CONTENT_TYPE", "*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, UploadFromFilemanager);
    }

    private void DownloadFromDropboxFromPath (String downloadPathTo, String downloadPathFrom)
    {
        DropboxDownloadPathTo = downloadPathTo;
        DropboxDownloadPathFrom = downloadPathFrom;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Download file ...", Toast.LENGTH_SHORT).show();
                Thread th = new Thread(new Runnable() {
                    public void run() {
                        File file = new File(DropboxDownloadPathTo + DropboxDownloadPathFrom.substring(DropboxDownloadPathFrom.lastIndexOf('.')));
                        if (file.exists()) file.delete();
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            dropboxAPI.getFile(DropboxDownloadPathFrom, null, outputStream, null);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "File successfully downloaded.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                th.start();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == UploadFromFilemanager)
        {
            final Uri currFileURI = intent.getData();
            final String pathFrom = currFileURI.getPath();
            Toast.makeText(getApplicationContext(), "Upload file ...", Toast.LENGTH_SHORT).show();
            Thread th = new Thread(new Runnable()
            {
                public void run()
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            UploadToDropboxFromPath(pathFrom, "/ProjectSIC_Test_Folder/" + DropboxUploadName + pathFrom.substring(pathFrom.lastIndexOf('.')));
                            Toast.makeText(getApplicationContext(), "File successfully uploaded.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            th.start();
        }
        if (requestCode == UploadFromSelectApp)
        {
            Toast.makeText(getApplicationContext(), "Upload file ...", Toast.LENGTH_SHORT).show();
            final Uri uri = intent.getData();

            DropboxUploadPathFrom = getPath(getApplicationContext(), uri);
            if(DropboxUploadPathFrom == null) {
                DropboxUploadPathFrom = uri.getPath();
            }
            Thread th = new Thread(new Runnable(){
                public void run() {
                    try
                    {
                        final File file = new File(DropboxUploadPathFrom);
                        InputStream inputStream = getContentResolver().openInputStream(uri);

                        dropboxAPI.putFile("/ProjectSIC_Test_Folder/" + DropboxUploadName + file.getName().substring(file.getName().lastIndexOf("."),
                                file.getName().length()), inputStream, file.length(), null, new ProgressListener(){
                            @Override
                            public long progressInterval() {return 100;}
                            @Override
                            public void onProgress(long arg0, long arg1){}
                        });
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(getApplicationContext(), "File successfully uploaded.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {e.printStackTrace();}
                }
            });
            th.start();
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public String getPath(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA, MediaStore.Video.Media.DATA, MediaStore.Audio.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            if(s!=null) {
                cursor.close();
                return s;
            }
        }
        catch(Exception e){}
        try {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            if(s!=null) {
                cursor.close();
                return s;
            }
        }
        catch(Exception e){}
        try {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            String s = cursor.getString(column_index);
            cursor.close();
            return s;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
