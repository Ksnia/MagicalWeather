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

    private final int FOG_WAITTING_DURATION = 2000;

    private final int FRONT_DISAPPEAR_DURATION = 2500;
    private final int FRONT_INDISTINCT_FADE_IN_DELAY_RUTATION = 1500;
    private final int FRONT_INDISTINCT_FADE_IN_DURATION = 2000;
    private final int FRONT_INDISTINCT_FADE_OUT_DELAY_RUTATION = 2000;
    private final int FRONT_INDISTINCT_FADE_OUT_DURATION = 2000;
    private final int FRONT_APPEAR_DELAY_RUTATION = 2000;
    private final int FRONT_APPEAR_DURATION = 2500;

    private final int BACK_DISAPPEAR_DURATION = 2500;
    private final int BACK_INDISTINCT_FADE_IN_DELAY_RUTATION = 1500;
    private final int BACK_INDISTINCT_FADE_IN_DURATION = 2000;
    private final int BACK_INDISTINCT_FADE_OUT_DELAY_RUTATION = 2000;
    private final int BACK_INDISTINCT_FADE_OUT_DURATION = 2000;
    private final int BACK_APPEAR_DELAY_RUTATION = 2000;
    private final int BACK_APPEAR_DURATION = 2000;

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
        if (null != mFogAnimator && !getAnimationState()) {
            mFogAnimator.start();
        }
    }

    @Override
    public void stopAnimation() {
        if (null != mFogAnimator) {
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
        startAnimation();
    }

    private AnimatorSet createFogAnimator() {
        AnimatorSet anim_mountainBack = createBackMountainAnimator(0.2f, 0.6f);
        AnimatorSet anim_mountainFront = createFrontMountainAnimator(0.3f, 0.7f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(listener);
        animatorSet.playTogether(anim_mountainBack, anim_mountainFront);
        animatorSet.setStartDelay(FOG_WAITTING_DURATION);
        return animatorSet;
    }

    private AnimatorSet createBackMountainAnimator(float alpha1, float alpha2) {
        AnimatorSet animator = new AnimatorSet();
        ObjectAnimator anim_disappear = createAlphaAnimator(mMountainBack, alpha1);
        anim_disappear.setDuration(BACK_DISAPPEAR_DURATION);

        ObjectAnimator anim_indistinct_fadein = createAlphaAnimator(mMountainBack, alpha1, alpha2);
        anim_indistinct_fadein.setDuration(BACK_INDISTINCT_FADE_IN_DURATION);
        anim_indistinct_fadein.setStartDelay(BACK_INDISTINCT_FADE_IN_DELAY_RUTATION);

        ObjectAnimator anim_indistinct_fadeout = createAlphaAnimator(mMountainBack, alpha2, alpha1);
        anim_indistinct_fadeout.setDuration(BACK_INDISTINCT_FADE_OUT_DURATION);
        anim_indistinct_fadeout.setStartDelay(BACK_INDISTINCT_FADE_OUT_DELAY_RUTATION);

        ObjectAnimator anim_reappear = createAlphaAnimator(mMountainBack, 1f);
        anim_reappear.setDuration(BACK_APPEAR_DURATION);
        anim_indistinct_fadeout.setStartDelay(BACK_APPEAR_DELAY_RUTATION);

        animator.playSequentially(anim_disappear,
                anim_indistinct_fadein,
                anim_indistinct_fadeout,
                anim_reappear);

        return animator;
    }

    private AnimatorSet createFrontMountainAnimator(float alpha1, float alpha2) {
        AnimatorSet animator = new AnimatorSet();
        ObjectAnimator anim_disappear = createAlphaAnimator(mMountainFront, alpha1);
        anim_disappear.setDuration(FRONT_DISAPPEAR_DURATION);

        ObjectAnimator anim_indistinct_fadein = createAlphaAnimator(mMountainFront, alpha1, alpha2);
        anim_indistinct_fadein.setDuration(FRONT_INDISTINCT_FADE_IN_DURATION);
        anim_indistinct_fadein.setStartDelay(FRONT_INDISTINCT_FADE_IN_DELAY_RUTATION);

        ObjectAnimator anim_indistinct_fadeout = createAlphaAnimator(mMountainFront, alpha2, alpha1);
        anim_indistinct_fadeout.setDuration(FRONT_INDISTINCT_FADE_OUT_DURATION);
        anim_indistinct_fadeout.setStartDelay(FRONT_INDISTINCT_FADE_OUT_DELAY_RUTATION);

        ObjectAnimator anim_reappear = createAlphaAnimator(mMountainFront, 1f);
        anim_reappear.setDuration(FRONT_APPEAR_DURATION);
        anim_indistinct_fadeout.setStartDelay(FRONT_APPEAR_DELAY_RUTATION);

        animator.playSequentially(anim_disappear,
                anim_indistinct_fadein,
                anim_indistinct_fadeout,
                anim_reappear);

        return animator;
    }

    private ObjectAnimator createAlphaAnimator(View v, float alpha) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", alpha);
        return animator;
    }

    private ObjectAnimator createAlphaAnimator(View v, float alpha1, float alpha2) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", alpha1, alpha2);
        return animator;
    }
}