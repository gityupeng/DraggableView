package com.view.drag.yu.dragview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView mDragView;

    private int mPortraitLastX; //移动时变动的x轴坐标
    private int mPortraitLastY; //移动时变动的y轴坐标
    private float mOriginalX; //头像view X轴原始坐标
    private float mOriginalY; //头像view Y轴原始坐标


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDragView = (ImageView) findViewById(R.id.drag_view);

        mDragView.setOnTouchListener(this); //设置onTouch事件
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: //手指按下屏幕事件
                mPortraitLastX = (int) event.getRawX();
                mPortraitLastY = (int) event.getRawY();
                mOriginalX = v.getX();
                mOriginalY = v.getY();
                break;
            case MotionEvent.ACTION_MOVE: //view滑动事件
                int dx = (int) event.getRawX() - mPortraitLastX;
                int dy = (int) event.getRawY() - mPortraitLastY;
                mDragView.setX(v.getX() + dx);
                mDragView.setY(v.getY() + dy);
                mPortraitLastX = (int) event.getRawX();
                mPortraitLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP: //手指离开屏幕事件
                AnimatorSet animatorSet = new AnimatorSet();//组合动画
                ObjectAnimator animatorX = ObjectAnimator.ofFloat(mDragView, "X", v.getX(), mOriginalX);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(mDragView, "Y", v.getY(), mOriginalY);
                animatorSet.setDuration(250);
                animatorSet.play(animatorX).with(animatorY);
                animatorSet.start();
                break;
        }
        return true;
    }
}
