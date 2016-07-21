package com.bianaiqi.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bianaiqi.components.R;

/**
 * Project：Components
 * Description：
 * Created by gsoft2-3 on 16-5-24
 *
 * @version ${VERSION}
 */
public class CloudLayout extends WeatherLayout {

    private final int ANIMATION_START_DELAY_DURATION = 2000;
    private final int CLOUD_MOVE_DURATION = 1500;
    private final int CLOUD_PAUSE_DURATION = 2000;
    private final int CLOUD_BACK_DURATION = 1750;

    private View rootView;
    private ImageView mCloud1;
    private ImageView mCloud2;
    private ImageView mCloud3;
    private ImageView mCloud4;
    private ImageView mInvertedcloud;
    private AnimatorSet mCloudAnimator;

    private Animator.AnimatorListener listener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
            // TODO Auto-generated method stub
            setAnimationState(true);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            // TODO Auto-generated method stub
            setAnimationState(false);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // TODO Auto-generated method stub
            setAnimationState(false);
        }
    };

    public CloudLayout(Context context) {
        super(context);
        onInit();
    }

    public CloudLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    @Override
    public void setAnimationState(boolean state) {

    }

    @Override
    public void startAnimation() {
        if(null != mCloudAnimator && !getAnimationState()){
            mCloudAnimator.start();
        }
    }

    @Override
    public void stopAnimation() {
        if(null != mCloudAnimator){
            mCloudAnimator.cancel();
        }
    }

    private void onInit() {
        rootView = inflate(getContext(), R.layout.cloud_layout, this);

        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        showWeatherIcon();
        mWeatherIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        mCloud1 = (ImageView) findViewById(R.id.cloud_img1);
        mCloud2 = (ImageView) findViewById(R.id.cloud_img2);
        mCloud3 = (ImageView) findViewById(R.id.cloud_img3);
        mCloud4 = (ImageView) findViewById(R.id.cloud_img4);
        mInvertedcloud = (ImageView) findViewById(R.id.inverted_cloud_img);

        mCloudAnimator = createCloudAnimator();
        startAnimation();
    }

    public AnimatorSet createCloudAnimator() {
        Animator anim_cloud1 = createCloudAnimator(mCloud1, -15f);
        Animator anim_cloud2 = createCloudAnimator(mCloud2, -8f);
        Animator anim_cloud3 = createCloudAnimator(mCloud3, 10f);
        Animator anim_cloud4 = createCloudAnimator(mCloud4, 15f);
        Animator anim_inverted_cloud = createCloudAnimator(mInvertedcloud, -12f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(listener);
        animatorSet.playTogether(anim_cloud1, anim_cloud2, anim_cloud3, anim_cloud4, anim_inverted_cloud);
        animatorSet.setStartDelay(ANIMATION_START_DELAY_DURATION);
        return animatorSet;
    }

    private ObjectAnimator createTranslationYAnimator(View v, float translationY) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", translationY);
        return animator;
    }

    private Animator createCloudAnimator(View v, float translationY) {
        AnimatorSet animator = new AnimatorSet();

        ObjectAnimator anim_move = createTranslationYAnimator(v, translationY);
        anim_move.setDuration(CLOUD_MOVE_DURATION);
        ObjectAnimator anim_back = createTranslationYAnimator(v, 0f);
        anim_back.setDuration(CLOUD_BACK_DURATION);
        anim_back.setStartDelay(CLOUD_PAUSE_DURATION);

        animator.play(anim_back).after(anim_move);
        return animator;
    }
}
