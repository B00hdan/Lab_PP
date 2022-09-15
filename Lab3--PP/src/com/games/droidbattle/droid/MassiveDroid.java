package com.games.droidbattle.droid;

public class MassiveDroid extends BaseDroid {
    public MassiveDroid(String name) {
        super(name,"Massive");
        this.chanceShot = 100;
        this.health = 150;
        this.damage = 45;
    }

    @Override
    public BaseDroid copyDroid() {
        return new MassiveDroid(name);
    }

    @Override
    public int ability() {
        return 0;
    }

}
