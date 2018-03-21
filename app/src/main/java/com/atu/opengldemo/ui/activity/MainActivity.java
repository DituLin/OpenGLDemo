package com.atu.opengldemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atu.opengldemo.R;
import com.atu.opengldemo.base.BaseActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity{

    @OnClick({
            R.id.btn_base,
            R.id.btn_sphere,
            R.id.btn_solar,
            R.id.btn_light

    })
    void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_base:
                intent.setClass(this,OpenGLOneActivity.class);
                break;
            case R.id.btn_solar:
                intent.setClass(this,SolarSystemActivity.class);
                break;
            case R.id.btn_sphere:
                intent.setClass(this,SphereActivity.class);
                break;
            case R.id.btn_light:
                intent.setClass(this,LightingActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }



}
