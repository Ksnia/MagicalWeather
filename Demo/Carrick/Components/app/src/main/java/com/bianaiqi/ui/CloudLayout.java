package com.bianaiqi.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bianaiqi.components.R;
import com.bianaiqi.util.AnimUtils;

/**
 * Project：Components
 * Description：
 * Created by gsoft2-3 on 16-5-24
 *
 * @version ${VERSION}
 */
public class CloudLayout extends FrameLayout {

    private ImageView mCloud1;
    private ImageView mCloud2;
    private ImageView mCloud3;
    private ImageView mCloud4;
    private ImageView mInvertedcloud;


    public CloudLayout(Context context) {
        super(context);
        onInit();
    }

    public CloudLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    private void onInit() {
        View rootView = inflate(getContext(), R.layout.cloud_layout, this);

        mCloud1 = (ImageView) findViewById(R.id.cloud_img1);
        mCloud2 = (ImageView) findViewById(R.id.cloud_img2);
        mCloud3 = (ImageView) findViewById(R.id.cloud_img3);
        mCloud4 = (ImageView) findViewById(R.id.cloud_img4);
        mInvertedcloud = (ImageView) findViewById(R.id.inverted_cloud_img);

        doCloudAnimation();
    }

    public void doCloudAnimation() {
        Animator anim_cloud1 = AnimUtils.createCloudAnimator(mCloud1, -16f);
        Animator anim_cloud2 = AnimUtils.createCloudAnimator(mCloud2, -12f);
        Animator anim_cloud3 = AnimUtils.createCloudAnimator(mCloud3, 12f);
        Animator anim_cloud4 = AnimUtils.createCloudAnimator(mCloud4, 12f);
        Animator anim_inverted_cloud = AnimUtils.createCloudAnimator(mInvertedcloud, -12f);

        AnimatorSet s = new AnimatorSet();
        s.playTogether(anim_cloud1, anim_cloud2, anim_cloud3, anim_cloud4, anim_inverted_cloud);
        s.setStartDelay(AnimUtils.ANIMATION_START_DELAY_DURATION);
        s.start();
    }
}
