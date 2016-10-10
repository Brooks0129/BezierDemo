
package com.brooks.bezierdemo.demo4;

import java.util.Random;

import com.brooks.bezierdemo.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author: lisongda
 * @date: 16/10/9.
 * @description:
 */

public class HeartLayout extends RelativeLayout {

    private Drawable blue;
    private Drawable green;
    private Drawable yellow;
    Drawable[] hearts;
    private int intrinsicHeight;
    private int mWidth;
    private int intrinsicWidth;
    private int mHeight;
    private LayoutParams params;
    private Random random = new Random();
    private ImageView iv;

    public HeartLayout(Context context) {
        super(context);
        init();
    }

    public HeartLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        hearts = new Drawable[3];
        blue = getResources().getDrawable(R.drawable.blue_heart);
        green = getResources().getDrawable(R.drawable.green_heart);
        yellow = getResources().getDrawable(R.drawable.yellow_heart);
        hearts[0] = blue;
        hearts[1] = green;
        hearts[2] = yellow;
        //获取图片实际宽高
        intrinsicHeight = blue.getIntrinsicHeight();
        intrinsicWidth = blue.getIntrinsicWidth();
        params = new RelativeLayout.LayoutParams(intrinsicWidth, intrinsicWidth);
        params.addRule(CENTER_HORIZONTAL, TRUE);
        params.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public void addHeart() {
        final ImageView iv =  new ImageView(getContext());
        iv.setImageDrawable(hearts[new Random().nextInt(3)]);
        iv.setLayoutParams(params);
        addView(iv);

        AnimatorSet set = getAnimator(iv);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(iv);
            }
        });
        set.start();
    }

    //构造属性动画
    private AnimatorSet getAnimator(ImageView iv) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 0.3f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(iv);

        ValueAnimator bezierAnim = getBezierAnim(iv);
        AnimatorSet bezierSet = new AnimatorSet();
        bezierSet.playSequentially(enter, bezierAnim);
       // bezierSet.setInterpolator(new AccelerateInterpolator(3));
        //bezierSet.setDuration(3000);
        bezierSet.setTarget(iv);
        return bezierSet;
    }

    private ValueAnimator getBezierAnim(final ImageView iv) {
        PointF pointF2 = getPointF(2);
        PointF pointF1 = getPointF(1);
        PointF pointF0 = new PointF((mWidth - intrinsicWidth) / 2, mHeight - intrinsicHeight);
        PointF pointF3 = new PointF(random.nextInt(mWidth), 0);
        BezierEvaluator evaluator = new BezierEvaluator(pointF1, pointF2);
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointF0, pointF3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                iv.setX(point.x);
                iv.setY(point.y);
                iv.setAlpha(1 - animation.getAnimatedFraction());
            }
        });
        animator.setDuration(3000);
        animator.setTarget(iv);
        return animator;
    }

    private PointF getPointF(int i) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth);
        if (i == 2) {
            pointF.y = random.nextInt(mHeight / 2);
        } else {
            pointF.y = random.nextInt(mHeight / 2) + mHeight / 2;
        }
        return pointF;

    }
}
