package com.bianaiqi.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bianaiqi.components.R;

/**
 * Created by Carrick on 2016/7/7.
 */
public class SunNightLayout extends WeatherLayout {

    private View rootView;
    private ImageView mShootingStar1;
    private ImageView mShootingStar2;
    private ImageView mShootingStar3;
    private AnimatorSet mSunNightAnimator;

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

    public SunNightLayout(Context context){
        super(context);
        onViewInit();
    }

    public SunNightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onViewInit();
    }

    @Override
    public void startAnimation() {
        if(null != mSunNightAnimator && !getAnimationState()){
            mSunNightAnimator.start();
        }
    }

    @Override
    public void stopAnimation() {
        if(null != mSunNightAnimator){
            mSunNightAnimator.cancel();
        }
    }

    private void onViewInit(){
        rootView = inflate(getContext(), R.layout.sunnight_layout, this);

        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        showWeatherIcon();
        mWeatherIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        mShootingStar1 = (ImageView) findViewById(R.id.shooting_star_1);
        mShootingStar2 = (ImageView) findViewById(R.id.shooting_star_2);
        mShootingStar3 = (ImageView) findViewById(R.id.shooting_star_3);

        mSunNightAnimator = createSunNightAnimator();
        startAnimation();
    }

    private AnimatorSet createSunNightAnimator(){
        AnimatorSet animatorSet = new AnimatorSet();

        Animator animStar1 = createShootingStar1Animator();
        Animator animStar2 = createShootingStar2Animator();
        Animator animStar3 = createShootingStar3Animator();

        animatorSet.addListener(listener);
        animatorSet.setStartDelay(5000);
        animatorSet.playSequentially(animStar1,animStar2,animStar3);
        return animatorSet;
    }

    private Animator createShootingStar1Animator() {
        AnimatorSet animatorSet = new AnimatorSet();

        final ValueAnimator animtranslationX = ObjectAnimator.ofFloat(mShootingStar1, "translationX",0,1010);
        animtranslationX.setDuration(2000);
        final ValueAnimator animtranslationY = ObjectAnimator.ofFloat(mShootingStar1, "translationY",0,580);
        animtranslationY.setDuration(2000);
        animatorSet.playTogether(animtranslationX,animtranslationY);
        return animatorSet;
    }

    private Animator createShootingStar2Animator() {
        AnimatorSet animatorSet = new AnimatorSet();

        final ValueAnimator animtranslationX = ObjectAnimator.ofFloat(mShootingStar2, "translationX",0,932);
        animtranslationX.setDuration(1200);
        final ValueAnimator animtranslationY = ObjectAnimator.ofFloat(mShootingStar2, "translationY",0,536);
        animtranslationY.setDuration(1200);
        animatorSet.playTogether(animtranslationX,animtranslationY);
        animatorSet.setStartDelay(400);
        return animatorSet;
    }

    private Animator createShootingStar3Animator() {
        AnimatorSet animatorSet = new AnimatorSet();

        final ValueAnimator animtranslationX = ObjectAnimator.ofFloat(mShootingStar3, "translationX",0,923);
        animtranslationX.setDuration(1500);
        final ValueAnimator animtranslationY = ObjectAnimator.ofFloat(mShootingStar3, "translationY",0,531);
        animtranslationY.setDuration(1500);
        animatorSet.playTogether(animtranslationX,animtranslationY);
        animatorSet.setStartDelay(200);
        return animatorSet;
    }
}