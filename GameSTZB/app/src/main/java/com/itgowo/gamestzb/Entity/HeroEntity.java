package com.itgowo.gamestzb.Entity;

public class HeroEntity {
        /**
         * contory : 群
         * name : 吕布
         * siege : 9
         * speed : 77
         * methodDetail : {"id":200012,"name":"辕门射戟","icon":"http://res.stzb.netease.com/gw/15v1/data/jineng/tactics_02.png"}
         * cost : 3.5
         * type : 弓
         * quality : 5
         * id : 100479
         * icon : http://res.stzb.netease.com/gw/15v1/data/small/card_100479.jpg
         */

        private String contory;
        private String name;
        private int siege;
        private int speed;
        private MethodDetailBean methodDetail;
        private double cost;
        private String type;
        private int quality;
        private int id;
        private String icon;

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

        public MethodDetailBean getMethodDetail() {
            return methodDetail;
        }

        public void setMethodDetail(MethodDetailBean methodDetail) {
            this.methodDetail = methodDetail;
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

        public static class MethodDetailBean {
            /**
             * id : 200012
             * name : 辕门射戟
             * icon : http://res.stzb.netease.com/gw/15v1/data/jineng/tactics_02.png
             */

            private int id;
            private String name;
            private String icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
}
