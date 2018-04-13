package com.itgowo.gamestzb.Entity;

public class HeroEntity {
    /**
     * contory : 群
     * name : 吕布
     * siege : 9
     * speed : 77
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
    private int siege;
    private int speed;
    private double cost;
    private String type;
    private int quality;
    private int id;
    private String icon;
    private String src;
    private String url;
    private int skillId;

    public int getSkillId() {
        return skillId;
    }

    public HeroEntity setSkillId(int skillId) {
        this.skillId = skillId;
        return this;
    }

    public String getSrc() {
        return src;
    }

    public HeroEntity setSrc(String src) {
        this.src = src;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HeroEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getContory() {
        return contory;
    }

    public void setContory(String contory) {
        this.contory = contory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSiege() {
        return siege;
    }

    public void setSiege(int siege) {
        this.siege = siege;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }



    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", siege=" + siege +
                ", speed=" + speed +
                ", cost=" + cost +
                ", type='" + type + '\'' +
                ", quality=" + quality +
                ", id=" + id +
                ", icon='" + icon + '\'' +
                ", src='" + src + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
