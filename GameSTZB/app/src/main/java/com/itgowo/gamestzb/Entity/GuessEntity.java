package com.itgowo.gamestzb.Entity;

import java.util.List;

public class GuessEntity {
    /**
     * id : 100575
     * option : [{"contory":"吴","name":"全琮"},{"contory":"魏","name":"典韦"},{"contory":"汉","name":"陶谦"},{"contory":"汉","name":"黄祖"}]
     */

    private int id;
    private List<HeroEntity> option;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<HeroEntity> getOption() {
        return option;
    }

    public void setOption(List<HeroEntity> option) {
        this.option = option;
    }
}
