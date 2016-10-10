
package com.brooks.bezierdemo.demo1;

import com.brooks.bezierdemo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author: lisongda
 * @date: 16/9/26.
 * @description:
 */

public class TipBubble extends FrameLayout {

    private Paint paint;
    private Paint textPaint;
    private Path path;

    // 默认圆半径
    private float default_radius = 20f;
    private float radius = default_radius;

    private float startX = 100f;
    private float startY = 100f;

    private float anchorX = 200f;
    private float anchorY = 200f;

    private float x;
    private float y;

    private boolean isTouching;
    private boolean isAnimRunning;
    private ImageView tips;
    private ImageView bomb;

    private String numberText;
    //圆半径阈值
    private float threshold4radius;
    private TypedArray typedArray;

    private int numberColor;
    private int bubbleColor;
    private float numberSize;

    public TipBubble(Context context) {
        super(context);
        init();
    }

    public TipBubble(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TipBubble);
        init();
    }

    public TipBubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TipBubble);
        init();
    }

    private void init() {
        int number = typedArray.getInteger(R.styleable.TipBubble_number, 0);
        default_radius = typedArray.getFloat(R.styleable.TipBubble_radius, 20f);
        threshold4radius = typedArray.getFloat(R.styleable.TipBubble_threshold4radius, 9f);
        numberColor = typedArray.getColor(R.styleable.TipBubble_number_color, Color.WHITE);
        bubbleColor = typedArray.getColor(R.styleable.TipBubble_bubble_color, Color.RED);
        numberSize = typedArray.getDimension(R.styleable.TipBubble_number_size, 20);
        if (number > 99) {
            numberText = "99+";
        } else if (number <= 0) {
            numberText = "";
        } else {
            numberText = number + "";
        }
        threshold4radius = 9;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(bubbleColor);

        textPaint = new Paint();
        textPaint.setColor(numberColor);
        textPaint.setStrokeWidth(2);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
        //文本水平居中
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        // Paint.FontMetricsInt metricsInt = textPaint.getFontMetricsInt();

        textPaint.setTextSize(numberSize);
        path = new Path();

        LayoutParams params =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        bomb = new ImageView(getContext());
        bomb.setLayoutParams(params);
        bomb.setImageResource(R.drawable.anim_bmob);
        bomb.setVisibility(INVISIBLE);

        tips = new ImageView(getContext());
        tips.setLayoutParams(params);
        tips.setImageResource(R.drawable.skin_tips_new);
        tips.setVisibility(INVISIBLE);
        addView(bomb);
        addView(tips);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        bomb.setX(startX - bomb.getWidth() / 2);
        bomb.setY(startY - bomb.getHeight() / 2);

        tips.setX(startX - tips.getWidth() / 2);
        tips.setY(startY - tips.getHeight() / 2);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint.FontMetrics textMetrics = textPaint.getFontMetrics();
        if (!isAnimRunning && !isTouching) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
            canvas.drawCircle(startX, startY, default_radius, paint);
            canvas.drawText(numberText, startX, startY - (textMetrics.bottom + textMetrics.top) / 2, textPaint);
        } else if (isAnimRunning) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        } else {
            calu();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
            canvas.drawCircle(startX, startY, radius, paint);
            canvas.drawCircle(x, y, default_radius, paint);
            canvas.drawPath(path, paint);
            canvas.drawText(numberText, x, y - (textMetrics.bottom + textMetrics.top) / 2, textPaint);
        }
        super.onDraw(canvas);
    }

    private void calu() {
        float distance = (float) Math.sqrt(Math.pow((startX - x), 2) + Math.pow((startY - y), 2));
        radius = default_radius - distance / 15;
        if (radius < threshold4radius) {
            isAnimRunning = true;
            bomb.setVisibility(VISIBLE);
            ((AnimationDrawable) bomb.getDrawable()).stop();
            ((AnimationDrawable) bomb.getDrawable()).start();
            tips.setVisibility(GONE);
        }
        double alpha = Math.atan((y - startY) / (x - startX));
        float offset4sx = (float) (radius * Math.sin(alpha));
        float offset4sy = (float) (radius * Math.cos(alpha));
        float offset4x = (float) (default_radius * Math.sin(alpha));
        float offset4y = (float) (default_radius * Math.cos(alpha));
        float x1 = startX - offset4sx;
        float y1 = startY + offset4sy;
        float x2 = x - offset4x;
        float y2 = y + offset4y;
        float x3 = x + offset4x;
        float y3 = y - offset4y;
        float x4 = startX + offset4sx;
        float y4 = startY - offset4sy;
        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);

        path.lineTo(x1, y1);
        tips.setX(x - tips.getWidth() / 2);
        tips.setY(y - tips.getHeight() / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect rect = new Rect();
            int[] location = new int[2];
            tips.getDrawingRect(rect);
            tips.getLocationOnScreen(location);
            rect.left = location[0];
            rect.top = location[1];
            rect.right = location[0] + rect.right;
            rect.bottom = location[1] + rect.bottom;
            if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                isTouching = true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            isTouching = false;
            tips.setX(startX - tips.getWidth() / 2);
            tips.setY(startY - tips.getHeight() / 2);
        }
        invalidate();
        if (isAnimRunning) {
            return super.onTouchEvent(event);
        }
        x = event.getX();
        y = event.getY();
        anchorX = (startX + x) / 2;
        anchorY = (startY + y) / 2;
        return true;
    }

    public String getNumberText() {
        return numberText;
    }

    public void setNumberText(String numberText) {
        this.numberText = numberText;
        invalidate();
    }

    public float getDefault_radius() {
        return default_radius;
    }

    public void setDefault_radius(float default_radius) {
        this.default_radius = default_radius;
        invalidate();
    }

    public float getThreshold4radius() {
        return threshold4radius;
    }

    public void setThreshold4radius(float threshold4radius) {
        this.threshold4radius = threshold4radius;
        invalidate();
    }

    public int getNumberColor() {
        return numberColor;
    }

    public void setNumberColor(int numberColor) {
        this.numberColor = numberColor;
        invalidate();
    }

    public int getBubbleColor() {
        return bubbleColor;
    }

    public void setBubbleColor(int bubbleColor) {
        this.bubbleColor = bubbleColor;
        invalidate();
    }

    public float getNumberSize() {
        return numberSize;
    }

    public void setNumberSize(float numberSize) {
        this.numberSize = numberSize;
        invalidate();
    }
}
