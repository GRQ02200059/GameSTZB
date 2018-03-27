package com.itgowo.gamestzb;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lujianchao on 2018/3/23.
 */

public class Entity implements Comparable<Entity> {
    /**
     * cn_name : 吕布
     * src : https://mgame-f.netease.com/forum/201509/30/172410ttnvuwqeu8ae4v4h.jpg
     * url : /thread-967815-1-1.html
     * lv : 0
     * type : 0
     */

    private String cn_name;
    /**
     * 图片地址
     */
    private String src;
    /**
     * 跳转url
     */
    private String url;
    /**
     * 国家
     * lv=0  汗
     * lv=1  魏
     * lv=2  蜀
     * lv=3  吴
     * lv=4  群
     */
    private int lv;
    /**
     * type =0  5星
     * type =1  4星
     * type =2  3星
     */
    private int type;
    public String getCountry(){
        switch (lv){
            case 0:
                return "汗";
            case 1:
                return "魏";
            case 2:
                return "蜀";
            case 3:
                return "吴";
            case 4:
                return "群";
        }
        return "天堂";
    }
    public String getCn_name() {
        return cn_name;
    }

    public String getEncodeName() throws UnsupportedEncodingException {
        return URLEncoder.encode(getCn_name(), "utf-8");
    }

    public String getFileName() {
        return getCn_name() + getLevel() + ".jpg";
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLv() {
        return lv;
    }

    public int getLevel() {
        if (type == 0){
            return 5;}
        if (type == 1){
            return 4;}
        return 3;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull Entity o) {
        return type - o.type;
    }
}
