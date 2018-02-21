package com.example.tshivang.n_able_2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraView extends Activity implements SurfaceHolder.Callback, OnClickListener{
    private static final String TAG = "CameraTest";
    Camera mCamera;
    boolean mPreviewRunning = false;

    @SuppressWarnings("deprecation")
    public void onCreate(Bundle icicle){
        super.onCreate(icicle);
        Log.e(TAG, "onCreate");

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_view);
        ImageView img = (ImageView) findViewById(R.id.blankImage);

        if(CaptureCameraImage.isBlack)
            img.setBackgroundResource(android.R.color.black);
        else
            img.setBackgroundResource(android.R.color.white);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        mSurfaceView.setOnClickListener(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }


    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            if (data != null){
                //Intent mIntent = new Intent();
                //mIntent.putExtra("image",imageData);

                mCamera.stopPreview();
                mPreviewRunning = false;
                mCamera.release();

                try{
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    Bitmap bitmap= BitmapFactory.decodeByteArray(data, 0, data.length,opts);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int newWidth = 500;
                    int newHeight = 500;

                    // calculate the scale - in this case = 0.4f
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;

                    // create a matrix for the manipulation
                    Matrix matrix = new Matrix();
                    // resize the bit map
                    matrix.postScale(scaleWidth, scaleHeight);
                    // rotate the Bitmap
                    matrix.postRotate(-90);
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            width, height, matrix, true);
                    MainActivity.image.setImageBitmap(resizedBitmap);
                    File file=new File(Environment.getExternalStorageDirectory()+"/DCIM/NABLE");
                    if(!file.isDirectory()){
                        file.mkdir();
                    }

                    file=new File(Environment.getExternalStorageDirectory()+"/DCIM/NABLE","Sample"+".jpg");
                    //MainActivity mainActivity = new MainActivity();
                   // mainActivity.speakOut();

                    try
                    {
                        FileOutputStream fileOutputStream=new FileOutputStream(file);
                        System.err.println("DONE");
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);

                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                    catch(Exception exception)
                    {
                        exception.printStackTrace();
                    }


                }catch(Exception e){
                    e.printStackTrace();
                }

                //StoreByteImage(mContext, imageData, 50,"ImageName");
                //setResult(FOTO_MODE, mIntent);
                setResult(585);
                finish();
            }
        }
    };

    protected void onResume(){
        Log.e(TAG, "onResume");
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    protected void onStop(){
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @TargetApi(9)
    public void surfaceCreated(SurfaceHolder holder){
        Log.e(TAG, "surfaceCreated");
        mCamera = Camera.open(CaptureCameraImage.cameraID);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.e(TAG, "surfaceChanged");

        // XXX stopPreview() will crash if preview is not running
        if (mPreviewRunning){
            mCamera.stopPreview();
        }

        Camera.Parameters p = mCamera.getParameters();
       // p.setPreviewSize(300, 300);
        List<Camera.Size> allSizes = p.getSupportedPictureSizes();
        Camera.Size size = allSizes.get(0); // get top size
        for (int i = 0; i < allSizes.size(); i++) {
            if (allSizes.get(i).width > size.width)
                size = allSizes.get(i);
        }
//set max Picture Size
        p.setPictureSize(size.width, size.height);

        if(CaptureCameraImage.cameraID == 0){
            String stringFlashMode = p.getFlashMode();
            if (stringFlashMode.equals("torch"))
                p.setFlashMode("on"); // Light is set off, flash is set to normal 'on' mode
            else
                p.setFlashMode("torch");
        }

        mCamera.setParameters(p);
        try{
            mCamera.setPreviewDisplay(holder);
        }catch (Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mCamera.startPreview();
        mPreviewRunning = true;
        mCamera.takePicture(null, mPictureCallback, mPictureCallback);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed");
        //mCamera.stopPreview();
        //mPreviewRunning = false;
        //mCamera.release();
    }

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    public void onClick(View v) {
        // TODO Auto-generated method stub
        mCamera.takePicture(null, mPictureCallback, mPictureCallback);
    }

}