package com.example.pwdmanager;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pwdmanager.Dao.itemsDao;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowItemDetailActivity extends BaseActivity {

    private itemsDao myDAO;
    private List<Map<String,Object>> MyListData = new ArrayList<>();
    private Map<String,Object> DataItem;
    private TextView AppNameTV;
    private TextView UserNameTV;
    private TextView PasswdTV;
    private Button BackBtn;
    private Button EditBtn;
    private Button DelBtn;

    @Override
    protected int initLayout() {
        return R.layout.activity_show_item_detail;
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

        BackBtn = findViewById(R.id.BackBtn);
        EditBtn = findViewById(R.id.EditBtn);
        DelBtn = findViewById(R.id.DelnBtn);

        AppNameTV = findViewById(R.id.ItemDetailAppName);
        UserNameTV = findViewById(R.id.ItemDetailUserName);
        PasswdTV = findViewById(R.id.ItemDetailPasswd);

        //展示数据
        //传送的数据坐标是从 1 开始计数，而非从 0 开始计数
        //因此 RawPosition 用于保存从 1 开始的计数位置
        String RawPosition = getStringFromNavigate();
        //position 是原始的，数据在 List 中的位置
        int position = (Integer.valueOf(getStringFromNavigate()).intValue() -1) ;
        //showToast(String.valueOf(position));

        //从 Sqlite 加载数据
        myDAO = new itemsDao(this);
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

        Map<String,Object> MapTemp;
        //找出目标位置，这里使用的是从 0 开始计数的 position
        MapTemp = MyListData.get(position);

        AppNameTV.setText(MapTemp.get("appname").toString());
        UserNameTV.setText(MapTemp.get("username").toString());
        PasswdTV.setText(MapTemp.get("passwd").toString());



        //返回按键
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateWithAnimation(ContentActivity.class);
                DelayEndActivity(500);
            }
        });

        //编辑条目
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateWithStringAnimation(EditItemActivity.class,RawPosition); //将ID号传递过去
                DelayEndActivity(600);
            }
        });

        //删除按键
        DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> TempData;
                TempData = MyListData.get(position);
                String TempID = TempData.get("id").toString();
                myDAO.deleteInfo(TempID);
                showToast("删除成功！");
                NavigateWithAnimation(ContentActivity.class);
                DelayEndActivity(500);
            }
        });

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