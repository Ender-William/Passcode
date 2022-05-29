package com.example.pwdmanager;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(initLayout());
        initView();
        initData();

    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    //展示Toast提示
    public void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    //没有效果的跳转
    public void NavigateWithNoAnimation(Class cls){
        Intent intent = new Intent(mContext,cls);
        startActivity(intent);
    }
    //带有效果的跳转
    public void NavigateWithAnimation(Class cls){
        Intent intent = new Intent(mContext,cls);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
    }

    public void NavigateWithStringAnimation(Class cls, String str){
        Intent intent = new Intent(mContext,cls);
        intent.putExtra("arg",str);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
    }

    public void NavigateWithStringNoAnimation(Class cls, String str){
        Intent intent = new Intent(mContext,cls);
        intent.putExtra("arg",str);
        startActivity(intent);
    }

    public String getStringFromNavigate(){
        Intent getStr = getIntent();
        String strTemp = getStr.getStringExtra("arg");
        return strTemp;
    }

    /**
     * 在SharedPreference中插入字符串
     * @param spName String 数据库名称
     * @param key String 键名
     * @param val String 字符串值
     */
    protected void SaveStringToSharedPreferences(String spName, String key, String val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,val);
        editor.commit();
    }

    /**
     * 在SharedPreference中插入整数
     * @param spName String 数据库名称
     * @param key String 键名
     * @param val Int 整数值
     */
    protected void SaveIntToSharedPreferences(String spName, String key, int val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,val);
        editor.commit();
    }

    /**
     * 在SharedPreference中插入浮点数
     * @param spName String 数据库名称
     * @param key String 键名
     * @param val FLOAT 浮点值
     */
    protected void SaveFloatToSharedPreferences(String spName, String key, float val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key,val);
        editor.commit();
    }

    /**
     * 在SharedPreference中插入布尔值
     * @param spName String 数据库名称
     * @param key String 键名
     * @param val boolean 布尔值
     */
    protected void SaveBooleanToSharedPreferences(String spName, String key, boolean val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,val);
        editor.commit();
    }

    protected boolean CheckisSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        boolean isContains = sharedPreferences.contains(key);
        return isContains;
    }

    protected String GetStringSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        String val = sharedPreferences.getString(key,null);
        return val;
    }

    protected int GetIntSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        int val = sharedPreferences.getInt(key,0);
        return val;
    }

    protected void DelayEndActivity(int sed) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        },sed);
    }

    protected void Activityreload() {
        //刷新当前Activity
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
