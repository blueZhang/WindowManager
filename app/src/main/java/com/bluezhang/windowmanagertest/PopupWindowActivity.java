package com.bluezhang.windowmanagertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;


/**
 * popupWindow
 * 是activity级别的悬浮窗，封装了属性的设置，直接使用就可以，
 * 能够和制定的控件结合在一起 例如Spanner 可以
 * 和Spanner 挨在一起不用计算x y 坐标；
 * <p/>
 * window 包裹了控件的一个组建，用于控制UI显示位置。方式。效果；
 */
public class PopupWindowActivity extends AppCompatActivity {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
    }

    /**
     * PopupWindow 实例
     *
     * @param view
     */
    public void btnPopupWindow(View view) {
        ImageView imageView = new ImageView(this);

        if (popupWindow == null) {
            imageView.setImageResource(R.mipmap.ic_luncher);

            popupWindow = new PopupWindow(imageView, 200, 200);

            popupWindow.showAsDropDown(view);

        }
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
//            popupWindow.showAsDropDown(view,0,0);
            popupWindow.showAtLocation(
                    view, //哪个资源进行定位
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,//那个位置为基准
                    0,
                    0
            );
        }
    }

    public void btngo(View view) {
        Intent intent = new Intent(this, DialogTestActivity.class);
        startActivity(intent);

    }
}
