package com.bianaiqi.ui;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bianaiqi.components.R;

/**
 * Created by Carrick on 2016/7/6.
 */

public class SunDayLayout extends FrameLayout {

    private View rootView;
    private ImageView mHalo;
    private ImageView mShip;
    private AnimatorSet mSunAnimator;

    public SunDayLayout(Context context){
        super(context);
        onViewInit();
    }

    public SunDayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onViewInit();
    }

    private void onViewInit(){
        rootView = inflate(getContext(), R.layout.sunday_layout, this);

        mHalo = (ImageView) findViewById(R.id.halo);
        mShip = (ImageView) findViewById(R.id.ship);

        mSunAnimator = createSunDayAnimator();
        doSunAnimation();
    }

    public void doSunAnimation(){
        if(null != mSunAnimator){
            mSunAnimator.start();
        }
    }

    private AnimatorSet createSunDayAnimator(){
        AnimatorSet haloAnimator = createHaloAnimator();
        Animator shipAnimator = createShipAnimator();

        AnimatorSet sunAnimator = new AnimatorSet();
        sunAnimator.setStartDelay(3000);
        sunAnimator.playTogether(haloAnimator,shipAnimator);
        return sunAnimator;
    }

    private AnimatorSet createHaloAnimator(){
        final ValueAnimator animHaloFadeIn = ObjectAnimator.ofFloat(mHalo, "alpha",0,1);
        animHaloFadeIn.setDuration(4000);
        animHaloFadeIn.setInterpolator(new DecelerateInterpolator());

        final ValueAnimator animHaloForwardSwing = ObjectAnimator.ofFloat(mHalo, "rotation",0,10);
        animHaloForwardSwing.setDuration(4000);
        animHaloForwardSwing.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animHaloForward = new AnimatorSet();
        animHaloForward.playTogether(animHaloFadeIn,animHaloForwardSwing);

        final ValueAnimator animHaloPause = ObjectAnimator.ofFloat(mHalo, "alpha",1.0f,0.8f);
        animHaloPause.setInterpolator(new LinearInterpolator());
        animHaloPause.setDuration(2000);

        final ValueAnimator animHaloFadeOut = ObjectAnimator.ofFloat(mHalo, "alpha",0.8f,0.0f);
        animHaloFadeOut.setDuration(4000);
        animHaloFadeOut.setInterpolator(new LinearInterpolator());

        final ValueAnimator animHaloBackwardSwing = ObjectAnimator.ofFloat(mHalo, "rotation",10,3);
        animHaloBackwardSwing.setDuration(4000);
        animHaloBackwardSwing.setInterpolator(new LinearInterpolator());

        AnimatorSet animHaloBackward = new AnimatorSet();
        animHaloBackward.playTogether(animHaloFadeOut,animHaloBackwardSwing);

        AnimatorSet animHalo = new AnimatorSet();
        animHalo.play(animHaloBackward).after(animHaloPause).after(animHaloForward);
        return animHalo;
    }

    private Animator createShipAnimator() {
        final ValueAnimator animShip = ObjectAnimator.ofFloat(mShip, "translationX",0,-360);
        animShip.setInterpolator(new DecelerateInterpolator());
        animShip.setDuration(9000);
        animShip.setStartDelay(1000);
        return animShip;
    }
}
