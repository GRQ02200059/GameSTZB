package com.itgowo.gamestzb.Entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.itgowo.gamestzb.UserManager;

import java.util.Date;

public class UserInfo {
    public static final int HERO5 = 5;
    public static final int HERO4 = 4;
    public static final int HERO3 = 3;
    public static final int HERO2 = 2;
    public static final int HERO1 = 1;
    public static final int LOGIN_TYPE_WEIXIN = 1;
    public static final int LOGIN_TYPE_QQ = 2;
    public static final int LOGIN_TYPE_AUTO = 0;
    private String name;
    private String uuid;
    private String pwd;
    private String head;
    private String nickname;
    private String phone;
    private String imei;
    private transient int id;
    private transient Date lastlogin;
    private transient Integer seed1;
    private transient Integer seed2;
    private transient Integer seed3;
    private transient Integer seed4;
    private transient Integer seed5;
    private int logintype;
    private Object logininfo;

    @JSONField(serialize = false)
    public int getSeedCount() {
        return seed1 + seed2 + seed3 + seed4 + seed5;
    }

    public Integer getSeed3() {
        return seed3;
    }

    public Integer getSeed1() {
        return seed1;
    }

    public Integer getSeed2() {
        return seed2;
    }

    public UserInfo setSeed1(int mSeed1) {
        seed1 = mSeed1;
        return this;
    }

    public int getLogintype() {
        return logintype;
    }

    public UserInfo setLogintype(int mLogintype) {
        logintype = mLogintype;
        return this;
    }

    public Object getLogininfo() {
        return logininfo;
    }

    public UserInfo setLogininfo(Object mLogininfo) {
        logininfo = mLogininfo;
        return this;
    }

    public UserInfo setSeed2(int mSeed2) {
        seed2 = mSeed2;
        return this;
    }

    public UserInfo setSeed3(int mSeed3) {
        seed3 = mSeed3;
        return this;
    }

    public Integer getSeed4() {
        return seed4;
    }

    public UserInfo setSeed4(int mSeed4) {
        seed4 = mSeed4;
        return this;
    }

    public Integer getSeed5() {
        return seed5;
    }

    public UserInfo setSeed5(int mSeed5) {
        seed5 = mSeed5;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserInfo setPhone(String mPhone) {
        phone = mPhone;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public UserInfo setImei(String mImei) {
        imei = mImei;
        return this;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public UserInfo setLastlogin(Date mLastlogin) {
        lastlogin = mLastlogin;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String mName) {
        name = mName;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public UserInfo setUuid(String mUuid) {
        uuid = mUuid;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public UserInfo setPwd(String mPwd) {
        pwd = mPwd;
        return this;
    }

    public String getHead() {
        return head;
    }

    public UserInfo setHead(String mHead) {
        head = mHead;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserInfo setNickname(String mNickname) {
        nickname = mNickname;
        return this;
    }

    public int getId() {
        return id;
    }

    public UserInfo setId(int mId) {
        id = mId;
        return this;
    }

    public static UserInfo covertUserInfoFormQQ(String json) {

        QQLoginEntity entity = JSON.parseObject(json, QQLoginEntity.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setHead(entity.getFigureurl_qq_2()).setNickname(entity.getNickname()).setUuid(UserManager.mTencent.getOpenId())
                .setLogintype(UserInfo.LOGIN_TYPE_QQ).setLogininfo(json);
        return userInfo;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
