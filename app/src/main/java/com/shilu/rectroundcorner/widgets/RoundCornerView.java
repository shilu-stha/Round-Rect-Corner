package com.shilu.rectroundcorner.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shilu.rectroundcorner.R;

import timber.log.Timber;

/**
 * Custom rounded corner view. Create round view with either color filled rectangle or
 * simply bordered only.
 */
public class RoundCornerView extends RelativeLayout {
    private static final int STROKE_WIDTH = 5;
    private static final int RADIUS = 10;
    private static final int STROKE_ONLY = 1;
    private static final int FILL = 0;

    private int cornerRadius;
    private int strokeType;
    private int strokeSize;
    private int color;
    private int minWidth = 900;
    private int minHeight = 1000;
    private int maxWidth;
    private int maxHeight;

    private GradientDrawable gradientDrawable;
    private Rect rect;


    public RoundCornerView(Context context) {
        this(context, null);
    }

    public RoundCornerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerView);
            strokeType = typedArray.getInt(R.styleable.RoundCornerView_rc_brush_type, STROKE_ONLY);
            color = typedArray.getColor(R.styleable.RoundCornerView_rc_color, Color.DKGRAY);
            strokeSize = typedArray.getDimensionPixelSize(R.styleable.RoundCornerView_rc_stroke_size, STROKE_WIDTH);
            cornerRadius = typedArray.getDimensionPixelSize(R.styleable.RoundCornerView_rc_corner_radius, RADIUS);

            typedArray.recycle();
        }
        setUpView();
    }

    private void setUpView() {
        rect = new Rect();
        gradientDrawable = new GradientDrawable();

        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(cornerRadius);

        // set the shape to be filled or only bordered
        switch (strokeType) {
            case STROKE_ONLY:
                gradientDrawable.setStroke(strokeSize, color);
                break;
            case FILL:
                gradientDrawable.setColor(color);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
        gradientDrawable.setBounds(rect);
//        if (h == 0) {
//            findDimensions();
//        }

        Timber.d("OnsizeChanged w: %d", w);
        Timber.d("OnsizeChanged h: %d", h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Timber.d("--------------------------------");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Timber.d("onMeasure width: %d ", widthMeasureSpec);
        Timber.d("onMeasure height: %d ", heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        int specMode = MeasureSpec.getMode(heightMeasureSpec);

        Timber.d("Height spec Size: %d", specSize);
        Timber.d("Height spec Mode: %d", specMode);
        Timber.d("******************************");
//        setMeasuredDimension(800, 800);

    }

    public void findDimensions() {
        if (getWidth() == 0) {
            maxWidth = minWidth;
        } else {
            if (getWidth() < minWidth) {
                maxWidth = minWidth;
            } else {
                maxWidth = getWidth();
            }
        }

        if (getHeight() == 0) {
        } else {
            if (getHeight() < minHeight) {
                maxHeight = minHeight;
            } else {
                maxHeight = getHeight();
            }
        }

        rect.set(getPaddingLeft(), getPaddingTop(), maxWidth - getPaddingRight(), maxHeight - getPaddingBottom());
        gradientDrawable.setBounds(rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        gradientDrawable.draw(canvas);
    }
}
