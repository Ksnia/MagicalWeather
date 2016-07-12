package com.bianaiqi.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bianaiqi.components.R;

/**
 * Created by Carrick on 2016/7/11.
 */
public class LightningView extends SurfaceView implements SurfaceHolder.Callback{

    private Context mContext;
    private Resources mRes;
    private SurfaceHolder mRippleSurfaceHolder;
    private DrawThread mThread;

    private final int ANIMATION_INTERVAL = 60;
    private final int IMAGE_COUNT = 25;
    private int index = 0;

    private final int[] mResId = {R.drawable.flash_a0, R.drawable.flash_a2, R.drawable.flash_a4,
            R.drawable.flash_a6, R.drawable.flash_a8, R.drawable.flash_a10,
            R.drawable.flash_a12, R.drawable.flash_a14, R.drawable.flash_a16,
            R.drawable.flash_a18, R.drawable.flash_a20, R.drawable.flash_b0,
            R.drawable.flash_b1, R.drawable.flash_b2, R.drawable.flash_b3,
            R.drawable.flash_b4, R.drawable.flash_b5, R.drawable.flash_b6,
            R.drawable.flash_b13, R.drawable.flash_b14, R.drawable.flash_b16,
            R.drawable.flash_b18, R.drawable.flash_b19, R.drawable.flash_b20,
            R.drawable.flash_b21};

    public LightningView(Context context) {
        super(context);
        mContext = context;
        this.setZOrderOnTop(true);
        mRippleSurfaceHolder = this.getHolder();
        mRippleSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mRippleSurfaceHolder.addCallback(this);
    }

    public LightningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.setZOrderOnTop(true);
        mRippleSurfaceHolder = this.getHolder();
        mRippleSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mRippleSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        onInit();
        if(null == mThread){
            mThread = new DrawThread();
        }
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(null != mThread){
            mThread.exit();
            mThread = null;
        }
        onDeInit();
    }

    private void onInit(){
        mRes = mContext.getResources();
    }

    private void onDeInit(){
        index = 0;
    }

    private void drawLightning(){
        try {
            Canvas canvas = mRippleSurfaceHolder.lockCanvas();
            Paint paint = new Paint();

        /* Clear mCanvas */
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawPaint(paint);

        /* Draw Image mCanvas */
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            Drawable drawable = mRes.getDrawable(mResId[index]);
            Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
            canvas.drawBitmap(bmp, 0, 0, paint);

            if (mRippleSurfaceHolder.getSurface().isValid()) {
                mRippleSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        } catch (Exception e){

        }
    }

    class DrawThread extends Thread {
        private boolean done = false;
        public DrawThread() {
            super();
            this.done = false;
        }

        public void exit(){
            done = true;
        }

        @Override
        public void run() {
            super.run();
            while (!done){
                drawLightning();
                index = index + 1;

                if(IMAGE_COUNT == index){
                    index = 0;
                }

                try {
                    Thread.sleep(ANIMATION_INTERVAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
