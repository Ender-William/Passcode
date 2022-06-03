# Version 1.1 更新 2022-06-04

增加了加密功能，原有的软件数据应该是可以完全升级上来的，这里的加密功能使用的是`java`的`base64`方法进行加密处理

原有的`SharedPreference`里面添加了`version`信息的说明

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="flag">used</string>
    <string name="pwd">test</string>
    <string name="user">root</string>
    <string name="version">1.1</string>
</map>
```



# 简介

***Passcode***是一个离线式的密码管理软件，第一次使用的时候，会在本机自动注册用户名或密码，这个用户名和密码需要牢记，因为在这一版本账号和密码不可更改。

登录软件的用户名和密码通过***SharedPreference***功能保存在本机，保存的数据条目则是通过***Sqlite***数据库保存在本机上。

# 功能

增删改查数据，没错，就这么简单。

# 补充

目前软件是使用明文保存数据，希望有能力的开发者可以对软件的安全性能进行提升。

对于在软件中按钮上的字母解释：

- “+”：添加条目
- R：刷新页面
- C：更改登录软件的用户名和密码（当前该功能未实现）
- L：锁定界面
- B：返回
- E：编辑
- D：删除
- S：保存

如果想进行开发，需要使用**Java 11**及以上版本，需要使用 **Android API 30**

# 免责说明

软件中出现的**icon**均来自互联网，本项目仅作为学习使用，若要进行商用开发或作为推广软件使用，请自行更换掉icon图标。

其中的**app_icon.png**来自**Apple Inc**，**icon.png**来自阿里旗下的**iconfont**。

如果用于安卓作业提交，请充分理解本项目的全部代码，学习是给自己学的。

