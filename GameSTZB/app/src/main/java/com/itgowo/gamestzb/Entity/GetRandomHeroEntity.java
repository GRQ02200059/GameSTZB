package com.itgowo.gamestzb.Entity;

import java.util.List;

public class GetRandomHeroEntity {
    private long game_money;
    private List<HeroEntity> herolist;

    public long getGame_money() {
        return game_money;
    }

    public GetRandomHeroEntity setGame_money(long game_money) {
        this.game_money = game_money;
        return this;
    }

    public List<HeroEntity> getHerolist() {
        return herolist;
    }

    public GetRandomHeroEntity setHerolist(List<HeroEntity> herolist) {
        this.herolist = herolist;
        return this;
    }
}
