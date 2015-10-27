package com.bluezhang.windowmanagertest;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;

/**
 * Activity和Window 的测试  window 的属性
 * window的作用：
 * 一个activity就有一个window
 * window就是activity显示内同的管理；用于管理显示的内容；window内部使用decorView
 * 来描述Ui的最顶级的容器，就是一个Framelayout的子类；
 * 所有activity中Ui相关的方法，都是window来执行的，调用内部的decorView来完成
 * window内部维护了当前的焦点；如果window失去焦点那么activity将不可以交互；window同时是
 * 事件传递的入口，由activity将事件传递给window ，window将事件传递给decroView 一直传递下去
 */
public class MainActivity extends FragmentActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setBackgroundDrawableResource(R.mipmap.ic_launcher);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        //获取activity自身的显示的顶级，window对象
        Window window = getWindow();
        //这个才是真的，androidUi中一个activity的顶级容器；
        //.getDecorView();才是真正的最顶级的View
        View view = window.getDecorView();
        FrameLayout.LayoutParams viewPar = new FrameLayout.LayoutParams(500, 500);
        view.setLayoutParams(viewPar);

        SeekBar bar = (SeekBar) view.findViewById(R.id.btn_light);
        bar.setOnSeekBarChangeListener(this);


        ViewGroup decorView = (ViewGroup) view;
        //decorView.setBackgroundResource(R.mipmap.ic_launcher);
        View v = decorView.getChildAt(0);
        if (v instanceof LinearLayout) {
            if (BuildConfig.DEBUG) Log.d("MainActivity", "出现了LinearLayout");
            ImageView child = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(300, 10);
            child.setBackgroundResource(R.mipmap.ic_launcher);
            //childParams.gravity = Gravity.TOP|Gravity.CENTER_HORIZONTAL;
            child.setLayoutParams(childParams);
            ((ViewGroup) v).addView(child);

        }

        Button button = new Button(getApplicationContext());
        button.setText("button");
        FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(
                200,
                150
        );
        layoutParams1.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        button.setLayoutParams(layoutParams1);
        decorView.addView(button);


        //获取当前谁获得了焦点
        View focus = window.getCurrentFocus();

        //获取当前window的layoutParams的属性
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        //设置当前的参数的gravity = 左上；
        layoutParams.gravity = Gravity.CENTER;
        //Animation animation = (Animation) getResources().getAnimation(R.anim.translate);

//        //设置x坐标，只有grivate设置left，right之后才起作用
//        layoutParams.x = 100;
//        //设置y的偏移只有gravity设置了top bottom之后才起作用
//        layoutParams.y = 300;
        //只有设置了宽度和高度之后x、y才起作用
        layoutParams.width = 500;
        layoutParams.height = 500;

        layoutParams.format = PixelFormat.TRANSPARENT;
        // layoutParams.alpha = 0.5f;

        //更新属性
        window.setAttributes(layoutParams);


        //----------------------------------------------------------
        Button bb = new Button(getApplicationContext());
        WindowManager wmManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        /**
         *以下都是WindowManager.LayoutParams的相关属性
         * 具体用途请参考SDK文档
         */
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;   //这里是关键，你也可以试试2003
        wmParams.format = 1;
        /**
         *这里的flags也很关键
         *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
         *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
         */
        wmParams.flags = 40;
        wmParams.width = 40;
        wmParams.height = 40;
        wmParams.gravity = Gravity.TOP;

        wmManager.addView(bb, wmParams);  //创建View


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        Window win = getWindow();
        switch (id) {
            case R.id.btn_light:
                WindowManager.LayoutParams attributes = win.getAttributes();
                attributes.screenBrightness = (float) (progress * 0.01);
                win.setAttributes(attributes);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
