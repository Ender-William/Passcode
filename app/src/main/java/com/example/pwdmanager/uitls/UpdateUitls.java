package com.example.pwdmanager.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.pwdmanager.Dao.itemsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateUitls{
    // 存放各版本的升级处理程序，这个程序没有别的作用


//    protected void UpdateVersionState(){
//
//        // Version1.0 --> Version1.1 先调用SharedPreference查看
//        // 需要先判断储存在SharedPreference里的版本号，根据版本号进行选择
//        // 由于上一个版本的SharedPreference里面没有version信息
//        // 因此通过判断SharedPreference里面是否含有version信息
//        // 来判断是 1.0 版本还是 1.1 版本
//        // 用来储存版本信息的键 "version"
//        String spName = "login_state_sp";
//
//
//        if (CheckisSharedPreferencesContains(spName,"version")){
//            // 存在version信息，需要先判断版本信息是否为 1.1 如果不是需要提出警告并终止程序
//            String VersionTemp = null;
//            VersionTemp = GetStringSharedPreferencesContains(spName,"version");
//            if (GetStringSharedPreferencesContains(spName,"version").equals("1.1")){
//                // 如果版本号是 1.1
//                showToast("数据库检查无误");
//            } else {
//                // 如果版本不是 1.1
//                // 只有 1.1 及之后的才有 version 因此这里只可能是 1.1 版本之后的版本
//                // 需要提示用户软件不可以降级使用，并在1.5秒后退出应用
//                showToast("请勿降级使用软件");
//                DelayEndActivity(1500);
//            }
//        } else {
//            /*
//             * 不存在 version 信息，说明软件是用 1.0 版本升级上来的
//             * 版本的软件相较于 1.0 版本增加了数据的加密功能，使用Base64
//             * 进行加密，不再是明文储存了，因此需要对数据库储存的数据
//             * 进行升级以达到软件可以继续使用的效果
//             *
//             * 需要将原先所有的数据一条一条读取出来之后再写入进去
//             *
//             * */
//
//            itemsDao myDAO;
//            // 原始数据的列表
//            List<Map<String,Object>> OriginalData = new ArrayList<>();
//            // 原始数据项
//            Map<String,Object> OriginalDataItem;
//            // 转换完成的数据列表
//            List<Map<String,Object>> ConvertData = new ArrayList<>();
//            // 转换完成的数据项
//            Map<String,Object> ConvertDataItem;
//            // 先读取所有的数据并添加到表中
//            myDAO = new itemsDao(mContext);
//            Cursor cursor = myDAO.allQuery();
//            while (cursor.moveToNext()) {
//                String id = cursor.getString(0);
//                // 除id之外的全部数据进行base64编码
//                String appname = base64encoder(cursor.getString(1));
//                String username = base64encoder(cursor.getString(2));
//                String passwd = base64encoder(cursor.getString(3));
//                myDAO.updateInfo(id,appname,username,passwd);
//            }
//            SaveStringToSharedPreferences(spName, "version", "1.1");
//            showToast("完成数据库升级");
//        }
//    }


}
