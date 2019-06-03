package com.example.allamoda;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class CameraActivity extends Activity implements View.OnClickListener {

    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private Camera camera;
    private boolean inPreview = false;
    ImageView image;
    Bitmap bmp, itembmp;
    static Bitmap mutableBitmap;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    File imageFileName = null;
    File imageFileFolder = null;
    private MediaScannerConnection msConn;
    Display d;
    int screenhgt, screenwdh;
    ProgressDialog dialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        image = (ImageView) findViewById(R.id.camera_preview);
        preview = (SurfaceView) findViewById(R.id.surface);
        image.setAlpha(0.5f);


        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        previewHolder.setFixedSize(getWindow().getWindowManager()
                .getDefaultDisplay().getWidth(), getWindow().getWindowManager()
                .getDefaultDisplay().getHeight());


    }


    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera = null;
        inPreview = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();
        camera.setDisplayOrientation(0);
        setCameraDisplayOrientation(this,
                Camera.CameraInfo.CAMERA_FACING_BACK, camera);
    }



    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size: parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return (result);
    }

    private void setCameraDisplayOrientation(Activity activity, int cameraId,
                                             android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }






    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
            } catch (Throwable t) {
                Log.e("surfaceCallback",
                        "Exception in setPreviewDisplay()", t);
                Toast.makeText(CameraActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {



            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = getBestPreviewSize(width, height,
                    parameters);

            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);
                camera.startPreview();
                inPreview = true;
            }
        }


        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };


    Camera.PictureCallback photoCallback = new Camera.PictureCallback() {
        public void onPictureTaken(final byte[] data, final Camera camera) {
            dialog = ProgressDialog.show(CameraActivity.this, "", "Saving Photo");
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {}
                    onPictureTake(data, camera);
                }
            }.start();
        }
    };



    public void onPictureTake(byte[] data, Camera camera) {

        bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
        savePhoto(mutableBitmap);
        dialog.dismiss();
    }



    class SavePhotoTask extends AsyncTask< byte[], String, String > {@Override
    protected String doInBackground(byte[]...jpeg) {
        File photo = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
        if (photo.exists()) {
            photo.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(photo.getPath());
            fos.write(jpeg[0]);
            fos.close();
        } catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
        }
        return (null);
    }
    }


    public void savePhoto(Bitmap bmp) {
        imageFileFolder = new File(Environment.getExternalStorageDirectory(), "Rotate");
        imageFileFolder.mkdir();
        FileOutputStream out = null;
        Calendar c = Calendar.getInstance();
        String date = fromInt(c.get(Calendar.MONTH)) + fromInt(c.get(Calendar.DAY_OF_MONTH)) + fromInt(c.get(Calendar.YEAR)) + fromInt(c.get(Calendar.HOUR_OF_DAY)) + fromInt(c.get(Calendar.MINUTE)) + fromInt(c.get(Calendar.SECOND));
        imageFileName = new File(imageFileFolder, date.toString() + ".jpg");
        try {
            out = new FileOutputStream(imageFileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            scanPhoto(imageFileName.toString());
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String fromInt(int val) {
        return String.valueOf(val);
    }

    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(CameraActivity.this, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);
                Log.i("scanPhoto", "connection established");
            }
            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();
                Log.i("ScanPhoto", "scan completed");
            }
        });
        msConn.connect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBack() {
        Log.e("onBack :", "yes");
        camera.takePicture(null, null, photoCallback);
        inPreview = false;
    }

    @Override
    public void onClick(View v) {

    }

}