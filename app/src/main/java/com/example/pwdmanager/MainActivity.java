package com.example.pwdmanager;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pwdmanager.Dao.itemsDao;

public class MainActivity extends BaseActivity {

    private itemsDao myDAO;
    private Button LoginBtn;
    private EditText UserNameET;
    private EditText PwdET;
    private String UserNameStr;
    private String PwdStr;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //强制使用竖屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();

        //绑定控件
        LoginBtn = findViewById(R.id.LoginBtn);
        UserNameET = findViewById(R.id.LoginUserNameET);
        PwdET = findViewById(R.id.LoginPwdET);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进行登录事项处理
                Login_Process();
            }
        });
    }

    protected void Login_Process() {

        /*
         * 该模块用于处理登录时的相关事项，判断是否跳转至内容页面
         * 第一次登录的时候，通过方式SharedPreference的flag标识来判断是否为第一次使用
         * 如果是第一次使用：自动注册输入的用户名和密码
         * 如果是使用过了，通过获取SharedPreference中保存的信息，进行对比，来完成登录
         */

        //获取用户名和密码
        UserNameStr = UserNameET.getText().toString();
        PwdStr = PwdET.getText().toString();

        //确定数据库的名称
        String spName = "login_state_sp";
        //showToast("2");

        //读取SharedPreference中是否有flag标签，有代表并非第一次安装使用，没有代表第一次安装使用
        if (CheckisSharedPreferencesContains("login_state_sp", "flag")) {
            //已经注册过，并非第一次使用
            //showToast("3");
            String UserNameTempStr;
            String PwdTempStr;
            //获取用户名和密码
            UserNameTempStr = GetStringSharedPreferencesContains(spName, "user");
            PwdTempStr = GetStringSharedPreferencesContains(spName, "pwd");
            //进行比对
            if (UserNameStr.equals(UserNameTempStr) && PwdStr.equals(PwdTempStr)) {
                //用户名和密码都正确
                NavigateWithAnimation(ContentActivity.class);
                DelayEndActivity(1000);
            } else {
                //用户名或密码有一项错误
                //showToast(UserNameTempStr+PwdTempStr);
                showToast("用户名或密码有误");
            }
        } else {
            //showToast("1");
            //第一次使用
            //需要先判断用户输入的密码或字符串是否为空
            if (UserNameStr.length() == 0 || PwdStr.length() == 0) {
                //如果用户名或密码为空
                showToast("用户名或密码不得为空");
            } else {
                //如果用户名或密码不为空
                myDAO = new itemsDao(this);
                //if (myDAO.getRecordsNumber() == 0) {
                //数据库内为空，没有内容，添加两条示例
                myDAO.insertInfo("This is your app name", "this is your username", "your password");
                myDAO.insertInfo("Microsoft Outlook", "xxxxxx@xxx.outlook", "your password");
                //Activityreload(); //刷新当前Activity用于加载Activity中ListView的数据

                //保存使用状态
                SaveStringToSharedPreferences(spName, "flag", "used");
                //保存账号名
                SaveStringToSharedPreferences(spName, "user", UserNameStr);
                //保存密码
                SaveStringToSharedPreferences(spName, "pwd", PwdStr);
                //跳转页面
                NavigateWithAnimation(ContentActivity.class);
                DelayEndActivity(1000);
            }
        }
    }
}