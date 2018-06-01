package com.itgowo.gamestzb.Entity;

import android.content.Context;

import com.itgowo.gamestzb.Base.BaseApp;

import java.io.File;

public class HeroEntity {
    /**
     * contory : 群
     * name : 吕布
     * siege : 9
     * distance : 77
     * methodDetail : {"id":200012,"name":"辕门射戟","icon":"http://res.stzb.netease.com/gw/15v1/data/jineng/tactics_02.png"}
     * cost : 3.5
     * type : 弓
     * quality : 5
     * id : 100479
     * icon : http://res.stzb.netease.com/gw/15v1/data/small/card_100479.jpg
     * src : https://mgame-f.netease.com/forum/201509/30/172410ttnvuwqeu8ae4v4h.jpg
     * url : /thread-967815-1-1.html
     */

    private String contory;
    private String name;
    private Integer distance;
    private Double cost;
    private String type;
    private Integer quality;
    private Integer id;
    private String icon;

    public String getHeroFilePath() {
        return new File(BaseApp.app.getDir("hero", Context.MODE_PRIVATE), getFileName()).getAbsolutePath();
    }

    public String getFileName() {
        return String.format("hero_%s.jpg", id);
    }

    public String getContory() {
        return contory;
    }

    public HeroEntity setContory(String contory) {
        this.contory = contory;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getId() {
        return id;
    }

    public HeroEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "HeroEntity{" +
                "contory='" + contory + '\'' +
                ", name='" + name + '\'' +
                ", distance=" + distance +
                ", cost=" + cost +
                ", type='" + type + '\'' +
                ", quality=" + quality +
                ", id=" + id +
                ", icon='" + icon + '\'' +
                '}';
    }
}
