package com.example.pwdmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pwdmanager.Dao.itemsDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditItemActivity extends BaseActivity {

    private itemsDao myDAO;

    private List<Map<String,Object>> MyListData = new ArrayList<>();
    private Map<String,Object> DataItem;

    private Button SaveBtn;
    private Button BackBtn;

    private EditText AppNameET;
    private EditText UserNameET;
    private EditText PasswdET;

    private String AppNameTemp;
    private String UserNameTemp;
    private String PasswdTemp;

    @Override
    protected int initLayout() {
        return R.layout.activity_edit_item;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        //绑定控件
        SaveBtn = findViewById(R.id.AddBtn);
        BackBtn = findViewById(R.id.CanceldBtn);

        AppNameET = findViewById(R.id.AddAppNameET);
        UserNameET = findViewById(R.id.AddUserNameET);
        PasswdET = findViewById(R.id.AddPasswdET);


        //先判断是编辑旧条目还是添加新条目
        String flagID = getStringFromNavigate();
        //获取Intent传值，如果是编辑旧条目的话，会传递过来一个ID值
        //传递过来的是从 1 开始计数的 List 的位置，需要做 -1 操作才是原始的位置
        //如果是添加新条目，则没有ID值，这个字符串 为"AddItem"

        myDAO = new itemsDao(this);

        //从ContentActivity跳转过来的，会传递一个字符串叫做 "AddItem"
        if (flagID.equals("AddItem")){
            //添加新条目
            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppNameTemp = base64encoder(AppNameET.getText().toString());
                    UserNameTemp = base64encoder(UserNameET.getText().toString());
                    PasswdTemp = base64encoder(PasswdET.getText().toString());
                    myDAO.insertInfo(AppNameTemp, UserNameTemp, PasswdTemp);
                    NavigateWithAnimation(ContentActivity.class);
                    DelayEndActivity(600);
                }
            });

            //返回到ContentActivity
            BackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavigateWithAnimation(ContentActivity.class);
                    DelayEndActivity(600);
                }
            });
        }else{
            //编辑旧条目
            //先加载所有数据，我个人认为就算密码再多也不会有上万个吧...
            //就算有那么多，这种人也不会用这个APP吧...
            Cursor cursor = myDAO.allQuery();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String appname = cursor.getString(1);
                //int age=cursor.getInt(2);
                String username = cursor.getString(2);
                String passwd = cursor.getString(3);
                DataItem = new HashMap<String, Object>();
                DataItem.put("id", id);
                DataItem.put("appname", appname);
                DataItem.put("username", username);
                DataItem.put("passwd", passwd);
                MyListData.add(DataItem);
            }
            // -1 操作获得到要编辑的数据在List中的真正位置
            int position = (Integer.valueOf(flagID).intValue() -1) ;
            Map<String,Object> MapTemp;
            //读取
            MapTemp = MyListData.get(position);

            // TempID 要用于 Update 数据时寻找数据库中保存的ID的值
            // Sqlite 在 Android 上有一个Bug，ID序列无法自增补全
            // 因此在删掉部分数据的时候，ID会出现中端，也就是
            // ID 和 数据在List中的位置无法保持 +1 的状态
            // 因此需要将数据条目中的真正的ID读取出来
            String TempID = MapTemp.get("id").toString();
            AppNameET.setText(base64decoder(MapTemp.get("appname").toString()));
            UserNameET.setText(base64decoder(MapTemp.get("username").toString()));
            PasswdET.setText(base64decoder(MapTemp.get("passwd").toString()));

            //按下保存键，进行数据的插入
            SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppNameTemp = base64encoder(AppNameET.getText().toString());
                    UserNameTemp = base64encoder(UserNameET.getText().toString());
                    PasswdTemp = base64encoder(PasswdET.getText().toString());
                    myDAO.updateInfo(TempID,AppNameTemp,UserNameTemp,PasswdTemp);
                    NavigateWithStringAnimation(ShowItemDetailActivity.class,flagID);
                    DelayEndActivity(600);
                }
            });

            //返回到ShowItemDetailActivity
            BackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavigateWithStringAnimation(ShowItemDetailActivity.class,flagID);
                    DelayEndActivity(600);
                }
            });

        }


    }

    //监听系统返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {// 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        // 这里处理逻辑代码，大家注意：该方法仅适用于2.0或更新版的sdk
        NavigateWithAnimation(ContentActivity.class);
        DelayEndActivity(500);
    };

}