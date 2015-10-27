package com.radomar.customprogressbar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radomar on 23.10.2015.
 */
public class MyCustomView extends View implements ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener {

    private static final int DESIRE_WIDTH = 400;
    private static final int DESIRE_HEIGHT = 400;

    private List<Rectangle> rectangles = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private Paint mPaint;

    private List<Animator> mAnimationList = new ArrayList<>();
    AnimatorSet mSet = new AnimatorSet();

    private int mSideSize;

    private boolean isAnimationStarted = false;

    public MyCustomView(Context context) {
        super(context);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);

        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.baseColor));
        mPaint.setStrokeWidth(5f);
        mPaint.setAntiAlias(true);

        mSet.addListener(this);
//init points
        for (int i = 0; i < 5; i++) {
            rectangles.add(new Rectangle());
        }

//init lines
        lines.add(new Line(rectangles.get(0), rectangles.get(1)));
        lines.add(new Line(rectangles.get(1), rectangles.get(2)));
        lines.add(new Line(rectangles.get(2), rectangles.get(3)));
        lines.add(new Line(rectangles.get(3), rectangles.get(0)));
        lines.add(new Line(rectangles.get(0), rectangles.get(2)));
        lines.add(new Line(rectangles.get(1), rectangles.get(3)));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int pointSize = mSideSize / 10;
        int rightSide = mSideSize - pointSize;

        Rectangle r = rectangles.get(0);
        r.init(0, 0, pointSize);

        r = rectangles.get(1);
        r.init(rightSide, 0, pointSize);

        r = rectangles.get(2);
        r.init(rightSide, rightSide, pointSize);

        r = rectangles.get(3);
        r.init(0, rightSide, pointSize);

        r = rectangles.get(4);
        int beginPoint = mSideSize / 2 - pointSize * 3 / 2;
        r.init(beginPoint, beginPoint, pointSize * 3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

//Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(DESIRE_WIDTH, widthSize);
        } else {
            width = DESIRE_WIDTH;
        }

//Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(DESIRE_HEIGHT, heightSize);
        } else {
            height = DESIRE_HEIGHT;
        }

        mSideSize = Math.min(width, height);

//SetMeasureDimension
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//Draw points
        for (Rectangle r: rectangles) {
            mPaint.setAlpha(r.opacity);
            canvas.drawRect(r.x, r.y, r.x + r.sideSize, r.y + r.sideSize, mPaint);
        }

//Draw lines
        for (Line line: lines) {
            mPaint.setAlpha(255);
            canvas.drawLine(line.firstPoint.x + line.firstPoint.halfSideSize,
                            line.firstPoint.y + line.firstPoint.halfSideSize,
                            line.secondPoint.x + line.secondPoint.halfSideSize,
                            line.secondPoint.y + line.secondPoint.halfSideSize,
                            mPaint);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

    public void initAnimationList() {
        ObjectAnimator animator;
        mAnimationList.clear();
        int center = (mSideSize - rectangles.get(0).sideSize) / 2;
        int rightBottom = mSideSize - rectangles.get(0).sideSize;
//blink big rectangle
        Rectangle r = rectangles.get(4);
        mAnimationList.add(ObjectAnimator.ofInt(r, Rectangle.ALPHA, 255, 0, 255, 0, 255, 0).setDuration(3000));

//first point animation
        r = rectangles.get(2);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, rightBottom, center);
        animator.setStartDelay(3000);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, rightBottom, center);
        animator.setStartDelay(3300);
        mAnimationList.add(animator);


//second point animation
        r = rectangles.get(3);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, 0, center);
        animator.setStartDelay(3100);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, rightBottom, center);
        animator.setStartDelay(3400);
        mAnimationList.add(animator);

//third point animation
        r = rectangles.get(0);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, 0, center);
        animator.setStartDelay(3200);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, 0, center);
        animator.setStartDelay(3500);
        mAnimationList.add(animator);
//fourth point animation
        r = rectangles.get(1);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, rightBottom, center);
        animator.setStartDelay(3300);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, 0, center);
        animator.setStartDelay(3600);
        mAnimationList.add(animator);

//blink small rectangles
        animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0).setDuration(500);
        animator.setStartDelay(3900);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(5);
        mAnimationList.add(animator);

//reverse animation
        r = rectangles.get(2);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, center, rightBottom);
        animator.setStartDelay(6900);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, center, rightBottom);
        animator.setStartDelay(7200);
        mAnimationList.add(animator);

        r = rectangles.get(3);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, center, rightBottom);
        animator.setStartDelay(7000);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, center, 0);
        animator.setStartDelay(7300);
        mAnimationList.add(animator);


        r = rectangles.get(0);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, center, 0);
        animator.setStartDelay(7100);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, center, 0);
        animator.setStartDelay(7400);
        mAnimationList.add(animator);


        r = rectangles.get(1);
        animator = ObjectAnimator.ofInt(r, Rectangle.Y, center, 0);
        animator.setStartDelay(7200);
        mAnimationList.add(animator);
        animator = ObjectAnimator.ofInt(r, Rectangle.X, center, rightBottom);
        animator.setStartDelay(7500);
        mAnimationList.add(animator);

    }

    private void setListener() {
        for (Animator a: mAnimationList) {
            ((ObjectAnimator)a).addUpdateListener(this);
        }
    }

    private void createAndStartAnimationSet() {
        mSet.playTogether(mAnimationList);
        mSet.start();
    }

    public void startAnimation() {
        if (!isAnimationStarted) {
            isAnimationStarted = true;
            initAnimationList();
            setListener();
            createAndStartAnimationSet();
        }
    }

    public void stopAnimation() {
        if (isAnimationStarted) {
            isAnimationStarted = false;
            mSet.end();
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (isAnimationStarted) {
            initAnimationList();
            setListener();
            createAndStartAnimationSet();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
