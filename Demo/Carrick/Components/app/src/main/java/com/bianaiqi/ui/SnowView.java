package com.bianaiqi.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.bianaiqi.components.R;
import com.bianaiqi.util.RandomGenerator;

/**
 * Created by Carrick on 2016/7/11.
 */
public class SnowView extends View {

    private SnowFlake[] mSnowFlakes;
    //control snow level:light, moderate, heavy
    public final int LIGHT_SNOW_LEVEL = 0;
    public final int MODERATE_SNOW_LEVEL = 1;
    public final int HEAVY_SNOW_LEVEL = 2;
    public int mSnowLevel = HEAVY_SNOW_LEVEL;

    private final float[][] FLAKE_SIZE = new float[][]{{2f, 4f}, {3f, 6f}, {2f, 6f}};
    //control the speed
    private final float[][] FLAKE_INCREMENT = new float[][]{{4, 8}, {4, 6}, {5, 10}};
    private final float ANGLE_RANGE = 0.1f;
    private final float HALF_ANGLE_RANGE = ANGLE_RANGE/2f;
    private final float HALF_PI = (float) (Math.PI/2f);
    private final float ANGLE_SEED = 25f;
    private final float ANGLE_DIVISOR = 10000f;

    private final int[] NUM_OF_FLAKES = new int[]{40, 100, 200};
    private final int DRAW_DELAY_INTERVAL = 5;


    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initSnow(int width, int height){
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.flakeColor_two));
        paint.setStyle(Paint.Style.FILL);
        mSnowFlakes = new SnowFlake[NUM_OF_FLAKES[mSnowLevel]];
        for(int i = 0; i < NUM_OF_FLAKES[mSnowLevel]; i ++){
            mSnowFlakes[i] = createSnowFlake(width,height,paint);
        }
    }

    private SnowFlake createSnowFlake(int width, int height, Paint paint){
        RandomGenerator randomGenerator = new RandomGenerator();
        int x = randomGenerator.getRandom(width);
        int y = randomGenerator.getRandom(height);
        Point position = new Point(x,y);
        int snowLevel = mSnowLevel;
        float increment = randomGenerator.getRandom(FLAKE_INCREMENT[snowLevel][0], FLAKE_INCREMENT[snowLevel][1]);
        float flakeSize = randomGenerator.getRandom(FLAKE_SIZE[snowLevel][0], FLAKE_SIZE[snowLevel][1]);
        float angle = randomGenerator.getRandom(ANGLE_SEED)/ANGLE_SEED * ANGLE_RANGE + HALF_PI - HALF_ANGLE_RANGE;

        return new SnowFlake(randomGenerator, position, increment, paint, flakeSize, angle, snowLevel);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSnow(w,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null != mSnowFlakes){
            for(SnowFlake flake : mSnowFlakes){
                flake.Draw(canvas);
            }
        }
        getHandler().postDelayed(mRunnable,DRAW_DELAY_INTERVAL);
    }

    protected class SnowFlake {
        private final RandomGenerator mRandomGenerator;
        private final Point mPosition;
        private float mFlakeSize;
        private float mFlakeIncrement;
        private float mAngle;
        private final Paint mPaint;

        public SnowFlake(RandomGenerator randomGenerator, Point point, float flakeIncrement, Paint paint, float flakeSize, float angle, int snowLevel) {
            mRandomGenerator = randomGenerator;
            mPosition = point;
            mFlakeIncrement = flakeIncrement;
            mPaint = paint;
            mFlakeSize = flakeSize;
            mAngle = angle;
            mSnowLevel = snowLevel;
        }

        public void Draw(Canvas canvas) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            drawCircle(width, height, canvas);
        }

        private void drawCircle(int width, int height, Canvas canvas) {
            double x = mPosition.x + mFlakeIncrement * Math.cos(mAngle);
            double y = mPosition.y + mFlakeIncrement * Math.sin(mAngle);
            mAngle += mRandomGenerator.getRandom(-ANGLE_SEED, ANGLE_SEED) / ANGLE_DIVISOR;
            mPosition.set((int) x, (int) y);
            canvas.drawCircle(mPosition.x, mPosition.y, mFlakeSize, mPaint);
            if (!isInside(width, height)) {
                reset(width, height);
            }
        }

        private boolean isInside(int width, int height) {
            boolean inWidth = mPosition.x < width;
            boolean inHeight = mPosition.y < height * 0.85 && (mPosition.y + mFlakeSize) < height * 0.85;
            return inWidth && inHeight;
        }

        private void reset(int width, int height) {
            mPosition.x = mRandomGenerator.getRandom(width);
            mPosition.y = 0;
            mAngle = mRandomGenerator.getRandom(ANGLE_SEED) / ANGLE_SEED * ANGLE_RANGE + HALF_PI - HALF_ANGLE_RANGE;
        }
    }
}