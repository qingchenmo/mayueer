package com.jlkf.ego.zxing;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.activity.ProductQuickSelectActivity;
import com.jlkf.ego.utils.SelectPhotoUtils;
import com.jlkf.ego.zxing.camera.CameraManager;
import com.jlkf.ego.zxing.decoding.CaptureActivityHandler;
import com.jlkf.ego.zxing.decoding.InactivityTimer;
import com.jlkf.ego.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends BaseActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private Button button_photo;
    private Camera camera;
    private Camera.Parameters parameters;
    //二维码选择
    private final int REQ_CODE_SELECT_IMG_KITKAT = 3001;
    private final int REQ_CODE_SELECT_IMG = 300;
    private boolean isOpen;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private MySensorListener mMySensorListener;
    private TextView mTv_power;
    private ImageView mIv_power;
    private boolean isVisibity = true;

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_capture);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.scan_erweima);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(backClick);
        LinearLayout lin_power = (LinearLayout) findViewById(R.id.ll_power);
        mIv_power = (ImageView) findViewById(R.id.iv_power);
        mTv_power = (TextView) findViewById(R.id.tv_power);
        lin_power.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                camera = CameraManager.get().getCamera();
                parameters = camera.getParameters();
                if (isOpen) {
                    isOpen = false;
                    mTv_power.setText(R.string.open_light);
                    mIv_power.setImageResource(R.drawable.icon_light_closed);
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                } else {
                    isOpen = true;
                    mTv_power.setText(R.string.close_light);
                    mIv_power.setImageResource(R.drawable.icon_light_open);
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameters);
                }

            }
        });
        button_photo = (Button) findViewById(R.id.button_photo);
        button_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//使用以上这种模式，并添加以上两句
                    startActivityForResult(intent, REQ_CODE_SELECT_IMG_KITKAT);
                } else {
                    intent.setAction(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQ_CODE_SELECT_IMG);
                }
            }
        });

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        initSensor();

    }

    private Point getBestCameraResolution(Camera.Parameters parameters, Point screenResolution) {
        float tmp = 0f;
        float mindiff = 100f;
        float x_d_y = (float) screenResolution.x / (float) screenResolution.y;
        Camera.Size best = null;
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size s : supportedPreviewSizes) {
            tmp = Math.abs(((float) s.height / (float) s.width) - x_d_y);
            if (tmp < mindiff) {
                mindiff = tmp;
                best = s;
            }
        }
        return new Point(best.width, best.height);
    }


    private Camera.Size getBestPreviewSize(int width, int height) {
        Camera.Size result = null;
        final Camera.Parameters p = camera.getParameters();
        //特别注意此处需要规定rate的比是大的比小的，不然有可能出现rate = height/width，但是后面遍历的时候，current_rate = width/height,所以我们限定都为大的比小的。
        float rate = (float) Math.max(width, height) / (float) Math.min(width, height);
        float tmp_diff;
        float min_diff = -1f;
        for (Camera.Size size : p.getSupportedPreviewSizes()) {
            float current_rate = (float) Math.max(size.width, size.height) / (float) Math.min(size.width, size.height);
            tmp_diff = Math.abs(current_rate - rate);
            if (min_diff < 0) {
                min_diff = tmp_diff;
                result = size;
            }
            if (tmp_diff < min_diff) {
                min_diff = tmp_diff;
                result = size;
            }
        }
        return result;
    }


    /**
     * 光线传感器
     */
    private void initSensor() {
        //获取SensorManager对象
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取Sensor对象
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(mMySensorListener = new MySensorListener(), mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public class MySensorListener implements SensorEventListener {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        public void onSensorChanged(SensorEvent event) {
            //获取精度
            float acc = event.accuracy;
            //获取光线强度
            float lux = event.values[0];
            if (lux < 10) {
                getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        camera = CameraManager.get().getCamera();
                        parameters = camera.getParameters();
                        isOpen = true;
                        mTv_power.setText(R.string.close_light);
                        mIv_power.setImageResource(R.drawable.icon_light_open);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        mSensorManager.unregisterListener(mMySensorListener);
                    }
                });
            }

        }

    }

    OnClickListener backClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        BaseActivity.finish(MipcaActivityCapture.this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        final SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        /*getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                camera = CameraManager.get().getCamera();
                parameters = camera.getParameters();
//                Point point = getBestCameraResolution(parameters, new Point());
                Camera.Size size = getBestPreviewSize(surfaceView.getWidth(), surfaceView.getHeight());
                parameters.setPreviewSize(size.width, size.height - 500);
                camera.startPreview();
            }
        });*/


        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isVisibity) {
//                            ToastUti.show(getString(R.string.scan_fail));
                        }
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisibity = false;
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        if (mSensorManager != null && mMySensorListener != null) {
            mSensorManager.unregisterListener(mMySensorListener);
        }
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        viewfinderView.setStop(true);

        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        Log.e("dcg", "扫描二维码结果：" + resultString);
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "识别二维码失败!", Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("result", resultString);
//            bundle.putParcelable("bitmap", barcode);
            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_OK, resultIntent);
            Intent intent = new Intent(this, ProductQuickSelectActivity.class);
            intent.putExtra("productName", resultString);
            intent.putExtra("codeBars", resultString);
            startActivity(intent);
        }
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {

        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_SELECT_IMG: // 选择图片 4.4以下
                    if (intent != null) {
                        String path = SelectPhotoUtils.getSelectPath(this, intent);
                        Result result = saveCurrentImage(path);
                        if (null != result) {
                            Intent resultIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("result", result.toString());
                            bundle.putString("path", path);
                            resultIntent.putExtras(bundle);
                            this.setResult(RESULT_OK, resultIntent);
                            Log.e("dcg", "扫描结果为：" + result.toString() + "---图片的路径:" + path);
                            finish();
                        } else {
                            Toast.makeText(MipcaActivityCapture.this, "无法识别", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case REQ_CODE_SELECT_IMG_KITKAT://选择图片 4.4以上
                    String path = SelectPhotoUtils.getSelectPath(this, intent);
                    Result result = saveCurrentImage(path);
                    if (null != result) {
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("result", result.toString());
                        bundle.putString("path", path);
                        resultIntent.putExtras(bundle);
                        this.setResult(RESULT_OK, resultIntent);
                        Log.e("dcg", "扫描结果为：" + result.toString() + "---图片的路径:" + path);
                        finish();
                    } else {
                        Toast.makeText(MipcaActivityCapture.this, "无法识别", Toast.LENGTH_LONG).show();
                    }

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 解析二维码
     *
     * @param path
     */
    private Result saveCurrentImage(final String path) {
        return parseQRcodeBitmap(path);
    }

    //解析二维码图片,返回结果封装在Result对象中
    private Result parseQRcodeBitmap(String bitmapPath) {
        //解析转换类型UTF-8  
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)  
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你  
        options.inJustDecodeBounds = true;
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了  
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素  
        /**
         options.outHeight = 400;
         options.outWidth = 400;
         options.inJustDecodeBounds = false;
         bitmap = BitmapFactory.decodeFile(bitmapPath, options);
         */
        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性  
        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0  
        }
        /**
         * 辅助节约内存设置 
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888 
         * options.inPurgeable = true;  
         * options.inInputShareable = true;  
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象  
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片  
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象  
        QRCodeReader reader = new QRCodeReader();
        //开始解析  
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            // TODO: handle exception  
        }

        return result;
    }
}