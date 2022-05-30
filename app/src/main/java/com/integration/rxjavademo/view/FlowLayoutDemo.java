package com.integration.rxjavademo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/4/1 - 21:31
 * @Description com.integration.rxjavademo.view
 */
public class FlowLayoutDemo extends ViewGroup {
    private static final String TAG = "FlowLayout";

    private int mVerticalSpacing = dp2pd(8);

    private List<List<View>> mAllLines = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();

    private int mHorizontalSpacing = dp2pd(16);


    public static int dp2pd(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    //创建view对象
    public FlowLayoutDemo(Context context) {
        super(context);
    }

    //XML文件创建view
    public FlowLayoutDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //主题style
    public FlowLayoutDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //自定义属性
    private FlowLayoutDemo(Context context, AttributeSet attrs,
                           int defStyleAttr, int defStyleRes, int verticalSpacing) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mVerticalSpacing = verticalSpacing;
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: widthMeasureSpec=" + widthMeasureSpec + ";heightMeasureSpec=" + heightMeasureSpec);
        //初始化集合对象，清空数据
        //

        //先度量子view
        int childCount = getChildCount();
        //测量子view与父布局的边距
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();

        //ViewGroup解析的父布局给子view的宽度
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        //ViewGroup解析的父布局给子view的高度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure: selfWidth=" + selfWidth + ";selfHeight= " + selfHeight);

        //一行里的view数组
        List<View> list = new ArrayList<>();
        //没行已经使用的宽度
        int lineWidthUsed = 0;
        int lineHeight = 0;
        //ViewGroup measure过程中，子view要求父布局ViewGroup的宽度
        int parentNeedWidth = 0;
        //ViewGroup measure过程中，子view要求父ViewGroup的高
        int parentNeedHeight = 0;


        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams childLayoutParams = child.getLayoutParams();
            //将LayoutParams 转换为 MeasureSpec
            int childMeasureSpecWidth = getChildMeasureSpec(widthMeasureSpec,
                    paddingLeft + paddingRight, childLayoutParams.width);
            int childMeasureSpecHeight = getChildMeasureSpec(heightMeasureSpec,
                    paddingBottom + paddingTop, childLayoutParams.height);
            child.measure(childMeasureSpecWidth, childMeasureSpecHeight);
            //计算子view的宽高size
            int measuredHeight = child.getMeasuredHeight();
            int measuredWidth = child.getMeasuredWidth();

            //判断是否需要换行的条件,当新的view增加的宽度超过 本行自身的 宽度，就换行
            if (lineWidthUsed + measuredWidth + mHorizontalSpacing > selfWidth) {

                //确定换行，就可以判断当前行所需的宽和高了，此时需要记录下来
                // 父ViewGroup行高取各行高的和
                parentNeedHeight = parentNeedHeight + lineHeight + mVerticalSpacing;
                //父布局行宽取最大值
                parentNeedWidth = Math.max(parentNeedWidth, lineWidthUsed + mHorizontalSpacing);


                //当需要换行是，将集合清空，将行宽和行高清零
                list = new ArrayList<>();
                lineHeight = 0;
                lineWidthUsed = 0;

            }

            //view是分行layout的，所以记录每一行有哪些view 这样方便layout布局
            list.add(child);
            //每一行都有自己的宽高
            lineWidthUsed = lineWidthUsed + measuredWidth + mHorizontalSpacing;

            lineHeight = Math.max(lineHeight, measuredHeight);

            //处理最后一行

        }

        //根据子view的度量结果，重新度量自己的ViewGroup
        // 作为一个ViewGroup，它自己也是一个view，它的大小也用根据父布局给它提供的宽高来度量
        // 先获取自身的mode属性
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int hieghtMode = MeasureSpec.getMode(heightMeasureSpec);
        //根据mode类型，判断使用自身的宽度，还是父布局的宽度
        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeedWidth;
        int realHeight = (hieghtMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeedHeight;
        //再度量父viewgroup 自己，吧自己的宽高参数传入函数
        setMeasuredDimension(realWidth, realHeight);
    }

    /**
     * 布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lineCount = mAllLines.size();

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        for (int i = 0; i < lineCount; i++) {

            List<View> viewList = mAllLines.get(i);
            for (int j = 0; j < viewList.size(); j++) {
                //
                View view = viewList.get(j);
                int left = paddingLeft;
                int top = paddingTop;
                //
                int right = left + getMeasuredWidth();
                int bottom = top + getMeasuredHeight();
                //
                view.layout(left, top, right, bottom);
                //
                paddingLeft = right + mHorizontalSpacing;


            }
        }
    }
}