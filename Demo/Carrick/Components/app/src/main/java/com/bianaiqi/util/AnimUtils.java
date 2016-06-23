package com.bianaiqi.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Project：Components
 * Description：
 * Created by gsoft2-3 on 16-5-24
 *
 * @version ${VERSION}
 */
public class AnimUtils {

    public static final int ANIMATION_START_DELAY_DURATION = 3000;
    private final static int CLOUD_MOVE_DURATION = 1000;
    private final static int CLOUD_PAUSE_DURATION = 1700;
    private final static int CLOUD_BACK_DURATION = 1250;


    public static ObjectAnimator createTranslationYAnimator(View v, float translationY) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", translationY);
        return animator;
    }

    public static Animator createCloudAnimator(View v, float translationY) {
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
