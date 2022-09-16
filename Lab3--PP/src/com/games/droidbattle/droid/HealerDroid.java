package com.games.droidbattle.droid;

public class HealerDroid extends BaseDroid {
    private final int HealPower = 20;
    public HealerDroid(String name) {
        super(name,"Healer");
        this.hitChance = 80;
        this.health = 110;
        this.damage = 20;
    }

    @Override
    public BaseDroid copyDroid() {
        return new HealerDroid(name);
    }

    @Override
    public int ability() {
        return this.HealPower;
    }
}
