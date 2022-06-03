package com.example.pwdmanager;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pwdmanager.Dao.itemsDao;
import com.example.pwdmanager.uitls.StrUitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public String base64encoder(String str){
        String EncodeStr = null;
        EncodeStr = java.util.Base64.getEncoder().encodeToString(str.getBytes());
        return EncodeStr;
    }

    public String base64decoder(String str){
        byte[] decodeBytes = java.util.Base64.getDecoder().decode(str);
        String DecodeStr = new String(decodeBytes);
        return DecodeStr;
    }

    // 为了让软件在升级之后，数据库部分可以同步得到升级，因此在设计数据库改动或数据改动的大
    // 版本升级的时候需要重新设计这个部分，如果是小的版本改动的话，需要通过该部分对软件的版
    // 本标识符进行升级。

    protected void UpdateVersionState(){

        // Version1.0 --> Version1.1 先调用SharedPreference查看
        // 需要先判断储存在SharedPreference里的版本号，根据版本号进行选择
        // 由于上一个版本的SharedPreference里面没有version信息
        // 因此通过判断SharedPreference里面是否含有version信息
        // 来判断是 1.0 版本还是 1.1 版本
        // 用来储存版本信息的键 "version"
        String spName = "login_state_sp";


        if (CheckisSharedPreferencesContains(spName,"version")){
            // 存在version信息，需要先判断版本信息是否为 1.1 如果不是需要提出警告并终止程序
            String VersionTemp = null;
            VersionTemp = GetStringSharedPreferencesContains(spName,"version");
            if (GetStringSharedPreferencesContains(spName,"version").equals("1.1")){
                // 如果版本号是 1.1
                showToast("数据库检查无误");
            } else {
                // 如果版本不是 1.1
                // 只有 1.1 及之后的才有 version 因此这里只可能是 1.1 版本之后的版本
                // 需要提示用户软件不可以降级使用，并在1.5秒后退出应用
                showToast("请勿降级使用软件");
                DelayEndActivity(1500);
            }
        } else {
            /*
            * 不存在 version 信息，说明软件是用 1.0 版本升级上来的
            * 版本的软件相较于 1.0 版本增加了数据的加密功能，使用Base64
            * 进行加密，不再是明文储存了，因此需要对数据库储存的数据
            * 进行升级以达到软件可以继续使用的效果
            *
            * 需要将原先所有的数据一条一条读取出来之后再写入进去
            *
            * */

            itemsDao myDAO;
            // 原始数据的列表
            List<Map<String,Object>> OriginalData = new ArrayList<>();
            // 原始数据项
            Map<String,Object> OriginalDataItem;
            // 转换完成的数据列表
            List<Map<String,Object>> ConvertData = new ArrayList<>();
            // 转换完成的数据项
            Map<String,Object> ConvertDataItem;
            // 先读取所有的数据并添加到表中
            myDAO = new itemsDao(mContext);
            Cursor cursor = myDAO.allQuery();
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                // 除id之外的全部数据进行base64编码
                String appname = base64encoder(cursor.getString(1));
                String username = base64encoder(cursor.getString(2));
                String passwd = base64encoder(cursor.getString(3));
                myDAO.updateInfo(id,appname,username,passwd);
            }
            SaveStringToSharedPreferences(spName, "version", "1.1");
            showToast("完成数据库升级");
        }
    }

}
