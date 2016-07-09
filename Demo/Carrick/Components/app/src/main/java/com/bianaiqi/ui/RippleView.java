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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bianaiqi.components.R;

/**
 * Created by Carrick on 2016/7/9.
 */
public class RippleView extends SurfaceView implements SurfaceHolder.Callback{

    private Context mContext;
    private Resources mRes;
    private SurfaceHolder mRippleSurfaceHolder;
    private DrawThread mThread;

    private final int ANIMATION_INTERVAL = 50;
    private final int IMAGE_COUNT = 13;
    private int mHeight;
    private int mWidth;
    private int index = 0;
    private int[] mResId = {R.drawable.ripple0,R.drawable.ripple1,R.drawable.ripple2,R.drawable.ripple3,
            R.drawable.ripple4,R.drawable.ripple5,R.drawable.ripple6,R.drawable.ripple7,
            R.drawable.ripple8,R.drawable.ripple9,R.drawable.ripple10,R.drawable.ripple11,
            R.drawable.ripple12};

    public RippleView(Context context) {
        super(context);
        Log.d("Carrick", "RippleView Construct1 .... ");
        mContext = context;
        this.setZOrderOnTop(true);
        mRippleSurfaceHolder = this.getHolder();
        mRippleSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mRippleSurfaceHolder.addCallback(this);

    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("Carrick", "RippleView Construct2 .... ");
        mContext = context;
        this.setZOrderOnTop(true);
        mRippleSurfaceHolder = this.getHolder();
        mRippleSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mRippleSurfaceHolder.addCallback(this);
    }

    private void onInit(){
        mRes = mContext.getResources();
        DisplayMetrics dm = mRes.getDisplayMetrics();
        mHeight = dm.heightPixels;
        mWidth = dm.widthPixels;
    }

    private void onDeInit(){
        index = 0;
    }

    private void drawRipple(){
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
            float top = mHeight - bmp.getHeight() + 18;
            canvas.drawBitmap(bmp, 0, top, paint);

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
                drawRipple();
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

}
