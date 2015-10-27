package com.bluezhang.windowmanagertest;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 系统级别的悬浮窗
 */
public class SystemFloatActivity extends AppCompatActivity implements View.OnClickListener {

    public WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
    private static WindowManager wm;
    private static ImageView imageView;
    float ax, ay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_float);
        //使用系统级别的windowmanager
        wm = (WindowManager) getApplicationContext().getSystemService(
                WINDOW_SERVICE
        );
        //2.1使用进程ApplaycationContext创建的UI控件 避免Activity退出就没了
        imageView = new ImageView(getApplicationContext());
        //设置ImageView的触摸监听
        //建议使用内名内部类设置触摸的监听listener
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float lastX, lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                //使用美两个点之间的聚类增量移动控件
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        ret = true;
//                        if (BuildConfig.DEBUG) Log.d("SystemFloatActivity", "Action DOWN触发");

                        imageView.setImageResource(R.mipmap.ic_luncher);

                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if (BuildConfig.DEBUG) Log.d("SystemFloatActivity", "Action MOVE触发");
                        //在移动的时候获取屏幕上的事件的点击位置进行增量的计算。设置控件位置
                        float cx = event.getRawX();
                        float cy = event.getRawY();
                        //计算增量
                        float ccx = cx - lastX;
                        float ccy = cy - lastY;
                        //设置坐标
                        LP.x += ccx;
                        LP.y += ccy;
                        ax = LP.x;
                        ay = LP.y;
                        //更新
                        wm.updateViewLayout(imageView, LP);
                        //记录当前的坐标 ，在下次移动的时候使用
                        lastY = cy;
                        lastX = cx;
                        isMove = false;
                        break;
                    case MotionEvent.ACTION_UP:
//                        if (BuildConfig.DEBUG) Log.d("SystemFloatActivity", "Action UP触发");
                        //设置坐标
                        imageView.setImageResource(R.mipmap.ic_launcher);
                        if(isMove) {
                            Intent intent = new Intent(SystemFloatActivity.this, SystemFloatActivity.class);
                            startActivity(intent);
                        }

                        if (
                                !isMove && ax > 60 &&
                                        ay > 60 &&
                                        ax < getResources().getDisplayMetrics().widthPixels-100 &&
                                        ay < getResources().getDisplayMetrics().heightPixels-100
                                )
                        {
                            new MoveThread().start();
                        }


                        break;
                }
                return ret;

            }
        });

        imageView.setImageResource(R.mipmap.ic_launcher);
        //2.2 设置系统级别悬浮窗的参数保证悬浮窗悬浮在手机的桌面上
        //系统级别需要指定type的级别
        //对于应用程序使用的系统类型;
        //     TYPE_SYSTEM_ALERT
        //     TYPE_SYSTEM_OVERLAY 只是悬浮在系统上
        //说明 使用系统级别的悬浮窗必须指定权限
        LP.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        LP.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        LP.gravity = Gravity.LEFT | Gravity.TOP;
        LP.x = 300;
        LP.y = 300;


        LP.width = 50;
        LP.height = 50;
        LP.format = PixelFormat.TRANSPARENT;

        imageView.setOnClickListener(this);
        // 2.addView()
        wm.addView(imageView, LP);


    }

    public void btnCl(View view) {
        Intent intent = new Intent(this, WindowManagerActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "点你妹呀！", Toast.LENGTH_SHORT).show();
    }

    Handler handler = new Handler();

    private int m;//通过改变m的值让图片有加速的效果
    private boolean isMove = true; //判断当前时候是点击/拖动

    class MoveThread extends Thread {
        @Override
        public void run() {
            while (true) {
                m++;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (LP.y >= 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageResource(R.mipmap.ic_luncher);
                            LP.y -= m;
                            //更新
                            wm.updateViewLayout(imageView, LP);
                        }
                    }, 50);
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            m = 0;
                            LP.x = (int) ax;
                            LP.y = (int) ay;
                            wm.updateViewLayout(imageView, LP);
                            imageView.setImageResource(R.mipmap.ic_launcher);
                            Thread.currentThread().interrupted();
                        }
                    });
                    break;
                }
            }
            isMove = true;
//
//
//            for (int i = 0; i < 300; i++) {
//                m = i;
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(LP.y>=0) {
//                            LP.y -= m;
//                            //更新
//                            wm.updateViewLayout(imageView, LP);
//                        }else {
//                            Toast.makeText(SystemFloatActivity.this, "小伙 到顶了！", Toast.LENGTH_SHORT).show();
//                            LP.x = (int) ax;
//                            LP.y= (int) ay;
//                            wm.updateViewLayout(imageView, LP);
//
//                        }
//
//
//                    }
//                });
//        }
//
//
//
        }
    }
}
