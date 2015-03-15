package com.cuyazilim.lighter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class LightActivity extends Activity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback {

    Camera mCamera;
    SurfaceView mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mPreview = (SurfaceView) findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCamera = Camera.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
        Log.d("CAMERA", "Destroy");
    }

    public void onCancelClick(View v) {
        finish();
    }

    public void onSnapClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.10.14.14/accccc.php");
                    // URL url = new URL("http://vk.com/video_ext.php?oid=199336083&id=164763203&hash=a83f27eb6a7465b5&hd=3");
                    HttpURLConnection con = (HttpURLConnection) url
                            .openConnection();
                    BufferedReader asd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Thread.sleep(1500);
                    url = new URL("http://10.10.14.14/kapaaaaa.php");
                    // URL url = new URL("http://vk.com/video_ext.php?oid=199336083&id=164763203&hash=a83f27eb6a7465b5&hd=3");
                    con = (HttpURLConnection) url
                            .openConnection();
                    asd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Thread.sleep(500);
                    url = new URL("http://10.10.14.14/accccc.php");
                    // URL url = new URL("http://vk.com/video_ext.php?oid=199336083&id=164763203&hash=a83f27eb6a7465b5&hd=3");
                    con = (HttpURLConnection) url
                            .openConnection();
                    asd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    mCamera.takePicture(LightActivity.this, null, null, LightActivity.this);
                    Thread.sleep(700);
                    url = new URL("http://10.10.14.14/kapaaaaa.php");
                    // URL url = new URL("http://vk.com/video_ext.php?oid=199336083&id=164763203&hash=a83f27eb6a7465b5&hd=3");
                    con = (HttpURLConnection) url
                            .openConnection();
              //      BufferedReader asd = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    asd = new BufferedReader(new InputStreamReader(con.getInputStream()));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

    }

    @Override
    public void onShutter() {
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        //Here, we chose internal storage
     /*   NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.BigPictureStyle notiStyle = new
                NotificationCompat.BigPictureStyle();
        notiStyle.setBigContentTitle("Big Picture Expanded");
        notiStyle.setSummaryText("Nice big picture.");
        notiStyle.bigPicture(BitmapFactory.decodeByteArray(data, 0, data.length));
        Notification myNotification= new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeByteArray(data, 0, data.length))
                .setContentTitle("Normal Notification")
                .setContentText("This is an example of a Normal Style.").build();

        notificationManager.notify("Picture Taken",0,myNotification);*/
        final RelativeLayout r=(RelativeLayout) findViewById(R.id.rella);
        r.setVisibility(View.VISIBLE);
        ImageView im= (ImageView) findViewById(R.id.imageView);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap cbmp =BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap bmsp = Bitmap.createBitmap(cbmp, 0, 0,
                cbmp.getWidth(), cbmp.getHeight(),
                matrix, true);
        im.setImageBitmap(bmsp);
       Button iptal= (Button) findViewById(R.id.button2);

        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.setVisibility(View.GONE);
            }
        });
        final Bitmap bmp = bmsp;
        Calendar a=Calendar.getInstance();
        final String name= a.get(Calendar.HOUR_OF_DAY)+"_"+ a.get(Calendar.MINUTE)+"_"+a.get(Calendar.SECOND);
        Button kyt= (Button) findViewById(R.id.button);
        kyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Lighter";
                File dir = new File(file_path);
                if(!dir.exists())
                    dir.mkdirs();
                File file = new File(dir, name+ ".png");
                FileOutputStream fOut = new FileOutputStream(file);

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/png");

                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    share.putExtra(Intent.EXTRA_TEXT, "My Image");

                    startActivity(Intent.createChooser(share, "Share Image"));
                bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    /*    try {
            FileOutputStream out = openFileOutput("picture.jpg", Activity.MODE_PRIVATE);
            out.write(data);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        camera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width, selected.height);
        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW", "surfaceDestroyed");
    }
}