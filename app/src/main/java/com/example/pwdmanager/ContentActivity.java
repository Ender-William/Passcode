package com.example.pwdmanager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.pwdmanager.Dao.itemsDao;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ContentActivity extends BaseActivity {

    private itemsDao myDAO;
    private List<Map<String,Object>> MyListData = new ArrayList<>();  //用于保存多个数据项
    private Map<String,Object> DataItem;  //用于保存每一条数据项

    private Button AddItemBtn;
    private Button RefreshBtn;
    private Button ChangePwdBtn;
    private Button LockActivityBtn;

    private String IDTemp;

    @Override
    protected int initLayout() {
        return R.layout.activity_content;
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
        AddItemBtn = findViewById(R.id.AddItemBtn);
        RefreshBtn = findViewById(R.id.ScanBtn);
        ChangePwdBtn = findViewById(R.id.ChangePwdBtn);
        LockActivityBtn = findViewById(R.id.LockBtn);

        //从 Sqlite 中加载数据
        myDAO = new itemsDao(this);
        Cursor cursor = myDAO.allQuery();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String appname=cursor.getString(1);
            //int age=cursor.getInt(2);
            String username=cursor.getString(2);
            String passwd=cursor.getString(3);
            DataItem=new HashMap<String,Object>();
            DataItem.put("id", id);
            DataItem.put("appname", appname);
            DataItem.put("username", username);
            DataItem.put("passwd",passwd);
            MyListData.add(DataItem);

        ListView lv = findViewById(R.id.MyMesgListView);
        MyAdapter adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    showToast("当前下表位置"+(position+1));
                //临时传送的数据坐标是从 1 开始计数，而非从 0 开始计数，因此需要在原有坐标上 +1 操作
                String TranTemp = String.valueOf(position + 1);
                NavigateWithStringAnimation(ShowItemDetailActivity.class,TranTemp);
                DelayEndActivity(500);
                }
            });
        }

        //添加item
        AddItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateWithStringAnimation(EditItemActivity.class,"AddItem");
                DelayEndActivity(600);
            }
        });

        //刷新Activity
        RefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activityreload();
            }
        });

        //锁定Activity
        LockActivityBtn.setOnClickListener(new View.OnClickListener() {
            //Stop activity after 100ms
            @Override
            public void onClick(View view) {
                NavigateWithAnimation(MainActivity.class);
                DelayEndActivity(600);
            }
        });

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyListData.size();
        }

        //用来返回指定position的那一个item
        @Override
        public Object getItem(int position) {
            return MyListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {


            View newview = View.inflate(ContentActivity.this,R.layout.content_item,null);
//            TextView id = newview.findViewById(R.id.MyMsgId);
            TextView appname = newview.findViewById(R.id.MyMesgLVAppName);
            TextView username = newview.findViewById(R.id.MyMesgLVUserName);
            TextView passwd = newview.findViewById(R.id.MyMesgLVPasswd);
            ImageView iv = newview.findViewById(R.id.MyMesgLVImg);

            Map<String,Object> MapTemp;
            MapTemp = MyListData.get(position);

//            id.setText(MapTemp.get("id").toString());
            appname.setText(MapTemp.get("appname").toString());
            username.setText(MapTemp.get("username").toString());
            passwd.setText(MapTemp.get("passwd").toString());
            iv.setBackgroundResource(R.drawable.app_icon);

            return newview;
        }
    }

}