package com.bluezhang.windowmanagertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class DialogTestActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog nihao = builder.setTitle("你好").setIcon(R.mipmap.ic_launcher).create();
        nihao.show();

    }

    public void btnfis(View view) {
        Intent intent = new Intent(this, SystemFloatActivity.class);
        startActivity(intent);
    }
}
