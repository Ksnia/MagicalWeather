package com.bianaiqi.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bianaiqi.components.R;

/**
 * Created by Carrick on 2016/7/12.
 */
public class FogLayout extends WeatherLayout {

    private final int FOG_DISAPPEAR_DURATION = 2000;
    private final int FOG_WAITTING_DURATION = 1000;
    private final int FOG_REAPPEAR_DURATION = 200;

    private AnimatorSet mFogAnimator;
    private ImageView mMountainBack;
    private ImageView mMountainFront;

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

    public FogLayout(Context context) {
        super(context);
        onViewInit();
    }

    public FogLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onViewInit();
    }

    public FogLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        onViewInit();
    }

    @Override
    public void startAnimation() {
        if(null != mFogAnimator && !getAnimationState()){
            mFogAnimator.start();
        }
    }

    @Override
    public void stopAnimation() {
        if(null != mFogAnimator){
            mFogAnimator.cancel();
        }
    }

    private void onViewInit() {
        View rootView = inflate(getContext(), R.layout.fog_layout, this);

        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
        showWeatherIcon();
        mWeatherIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        mMountainFront = (ImageView) findViewById(R.id.fog_mountain_front);
        mMountainBack = (ImageView) findViewById(R.id.fog_mountain_back);

        mFogAnimator = createFogAnimator();
    }

    private AnimatorSet createFogAnimator(){
        Animator anim_mountain2 = createFog2Animator(mMountainFront, 0.2f,0.5f);
        Animator anim_mountain1 = createFog1Animator(mMountainBack, 0.3f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(listener);
        animatorSet.playTogether(anim_mountain1, anim_mountain2);
        animatorSet.setStartDelay(FOG_WAITTING_DURATION);
        return animatorSet;
    }

    private Animator createFog1Animator(View v, float alpha) {
        AnimatorSet animator = new AnimatorSet();
        ObjectAnimator anim_disappear = createAlphaAnimator(v,alpha);
        anim_disappear.setDuration(FOG_DISAPPEAR_DURATION);
        ObjectAnimator anim_reappear = createAlphaAnimator(v,1f);
        anim_reappear.setStartDelay(FOG_DISAPPEAR_DURATION);
        anim_reappear.setDuration(FOG_DISAPPEAR_DURATION);
        animator.play(anim_reappear).after(anim_disappear);
        return animator;
    }

    /**
     *
     * @param v
     * @param alpha1
     * @return
     * 0-2       消失 0.8
     * 2-2.2     出现 0.8-0.5
     * 2.2-2.4   消失 0.5-0.8
     * 2.4-2.6   出现 0.8-0.5
     * 2.6-2.8   消失 0.5-0.8
     * 2.8-5     出现 1
     */
    private Animator createFog2Animator(View v, float alpha1, float alpha2) {
        AnimatorSet animator = new AnimatorSet();
        ObjectAnimator anim_disappear = createAlphaAnimator(v,alpha1);
        anim_disappear.setDuration(FOG_DISAPPEAR_DURATION);

        ObjectAnimator anim1 = createAlphaAnimator(v,alpha1,alpha2);
        anim_disappear.setDuration(FOG_DISAPPEAR_DURATION);


        ObjectAnimator anim2 = createAlphaAnimator(v,alpha2,alpha1);
        anim_disappear.setDuration(FOG_DISAPPEAR_DURATION);

        ObjectAnimator anim_reappear = createAlphaAnimator(v,1f);
        anim_reappear.setDuration(FOG_DISAPPEAR_DURATION);
        animator.playSequentially(anim_disappear,
                anim1, anim2,
                anim_reappear);

        return animator;
    }

    private ObjectAnimator createAlphaAnimator(View v, float alpha){
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", alpha);
        return animator;
    }

    private ObjectAnimator createAlphaAnimator(View v, float alpha1, float alpha2){
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", alpha1, alpha2);
        return animator;
    }
}