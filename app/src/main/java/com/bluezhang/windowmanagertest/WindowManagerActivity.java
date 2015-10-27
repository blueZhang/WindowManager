package com.bluezhang.windowmanagertest;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 悬浮窗的演示 类似360
 * format :用于控制悬浮窗 显示内容中透明的部分应该怎么显示
 * 关于悬浮窗的泄露 ：使用activity getWindowManager获取的windowManager 在添加悬浮窗的时候是
 * Actiivty的级别的默认这种悬浮窗，是无法单独在手机上存在的；
 * 悬浮窗在添加的时候如果没有指定 LayputParams的type的时候，那么默认悬浮窗也是activity级别的。
 * 当activity推出的时候那么这个级别的悬浮窗必须经过remove的操作：
 * 使用windowManager的removeView可以删除释放悬浮窗；
 */
public class WindowManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);
        //悬浮窗的显示方式 使用windowManager
        WindowManager windowManager = getWindowManager();


        //2.1 准备addView的参数 View的对象 作为悬浮窗整体
        imageView = new ImageView(this);

        imageView.setImageResource(R.mipmap.ic_luncher);
        //2.2 准备addView的参数2 windowManager。LayoutParams
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        //设置悬浮窗的位置 左上角到右下角
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.x = 100; //相对于grivate而言  位置的偏移
        lp.y = 100;

        //设置ImageView的宽度和高度
//        lp.width =WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = 100;
        lp.height = 100;

        //注意事项 ：悬浮窗 通过AddView之后如果可以获取焦点
        //那么下层的activity将会接受不到任何事件 包括返回键
        //设置flags 包含FLAG_NOT_FOCUSABLE 就能解决这个问题
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置没有内容的区域透明的
        lp.format = PixelFormat.TRANSPARENT;

        imageView.setOnClickListener(this);


        //2.添加悬浮窗 ：windowManager addView（View,LayoutParams）
        windowManager.addView(imageView, lp);
    }

    @Override
    protected void onDestroy() {
        WindowManager windowManager = getWindowManager();
        //释放悬浮窗
        windowManager.removeViewImmediate(imageView);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "可以点击！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnToa(View view) {
        Toast.makeText(this, "按钮可以点击", Toast.LENGTH_SHORT).show();
    }
}
