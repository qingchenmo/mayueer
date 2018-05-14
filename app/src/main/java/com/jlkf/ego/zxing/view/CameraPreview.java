package com.jlkf.ego.zxing.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * @autor zcs
 * @time 2017/9/8
 * @describe
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";
    private final SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        //下面一行适用于Android3.0之前的设备适配，一般可以省略
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //制定相机图像的绘制区域为这个SurfaceView，并且启动相机的预览
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //摧毁时释放相机的资源，如果留空的话则需要在activity里释放camera
        mCamera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        //当SurfaceView尺寸变化时（包括设备横屏竖屏改变时时），需要重新设定相关参数
        if (mHolder.getSurface() == null) {
            //检查SurfaceView是否存在
            return;
        }

        //改变设置前先关闭相机
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用最佳比例配置重启相机
        try {
            mCamera.setPreviewDisplay(mHolder);
            final Camera.Parameters parameters = mCamera.getParameters();
            final Camera.Size size = getBestPreviewSize(w, h);
            parameters.setPreviewSize(size.width, size.height);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


    private Camera.Size getBestPreviewSize(int width, int height) {
        //在下面叙述private Camera.Size getBestPreviewSize(int width, int height) {
        Camera.Size result = null;
        final Camera.Parameters p = mCamera.getParameters();
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
}
