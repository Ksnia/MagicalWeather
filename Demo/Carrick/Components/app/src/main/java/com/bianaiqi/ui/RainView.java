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
public class RainView extends View {
    //public final boolean isFlashDay = true;
    private final float[][] DROP_LENGTH = new float[][]{{40f, 60f}, {30f, 50f}, {60f, 80f}};
    //control the speed
    private final float[][] DROP_INCREMENT = new float[][]{{12, 14}, {14, 16}, {16, 21}};
    private final float DROP_WIDTH_RANGE_LOWER = 0.5F;
    private final float DROP_WIDTH_RANGE_UPPER = 2F;

    private final int[] NUM_OF_DROP = new int[]{8, 40, 100};
    private final int DROP_LEVEL = 2;
    private final int DRAW_DELAY_INTERVAL = 5;
    private RainDrop[] mRainDrops;

    public RainView(Context context) {
        super(context);
    }

    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initRain(int width, int height){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.dropCorlor));
        paint.setStyle(Paint.Style.FILL);
        mRainDrops = new RainDrop[NUM_OF_DROP[DROP_LEVEL]];
        for(int i = 0; i < NUM_OF_DROP[DROP_LEVEL]; i ++){
            mRainDrops[i] = createRainDrop(width,height,paint);
        }
    }

    public RainDrop createRainDrop(int width, int height, Paint paint){
        RandomGenerator randomGenerator = new RandomGenerator();
        int x = randomGenerator.getRandom(width);
        int y = randomGenerator.getRandom(height);
        Point position = new Point(x,y);
        float increment = randomGenerator.getRandom(DROP_INCREMENT[DROP_LEVEL][0], DROP_INCREMENT[DROP_LEVEL][1]);
        float dropLength = randomGenerator.getRandom(DROP_LENGTH[DROP_LEVEL][0], DROP_LENGTH[DROP_LEVEL][1]);
        float dropWidth = randomGenerator.getRandom(DROP_WIDTH_RANGE_LOWER, DROP_WIDTH_RANGE_UPPER);
        return new RainDrop(randomGenerator, position, increment, paint, dropLength, dropWidth);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w != oldw || h != oldh){
            initRain(w,h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(RainDrop d : mRainDrops){
            d.Draw(canvas);
        }
        getHandler().postDelayed(mRunnable,DRAW_DELAY_INTERVAL);
    }

    protected class RainDrop {
        private final RandomGenerator mRandomGenerator;
        private final Point mPosition;
        private final Paint mPaint;
        private final float mDropIncrement;
        private float mDropWidth;
        private float mDropLength;

        public RainDrop(RandomGenerator randomGenerator, Point point, float dropIncrement, Paint paint, float dropLength, float dropWidth) {
            mRandomGenerator = randomGenerator;
            mPosition = point;
            mDropIncrement = dropIncrement;
            mPaint = paint;
            mDropLength = dropLength;
            mDropWidth = dropWidth;
        }

        public void Draw(Canvas canvas){
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            drawLine(width, height, canvas);
        }

        private void drawLine(int width, int height, Canvas canvas){
            if(!isInside(width, height)){
                reset(width,height);
            }
            mPaint.setStrokeWidth(mDropWidth);
            canvas.drawLine(mPosition.x, mPosition.y, mPosition.x + mDropWidth * 3, mPosition.y + mDropLength, mPaint);
            double x = mPosition.x;
            double y = mPosition.y + mDropIncrement;
            mPosition.set((int)x, (int)y);
        }

        private boolean isInside(int width, int height){
            boolean inWidth = mPosition.x < width;
            boolean inHeight = mPosition.y < height*0.85 && (mPosition.y + mDropLength) < height*0.85;
            return inWidth && inHeight;
        }

        private void reset(int width, int height){
            mPosition.x = mRandomGenerator.getRandom(width);
            mPosition.y = mRandomGenerator.getRandom(height/6);
        }
    }
}