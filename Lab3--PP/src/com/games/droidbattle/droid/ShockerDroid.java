package com.games.droidbattle.droid;

public class ShockerDroid extends BaseDroid {
    private final int StunPower = 2;
    public ShockerDroid(String name) {
        super(name,"Shocker");
        this.chanceShot = 85;
        this.health = 100;
        this.damage = 25;
    }

    @Override
    public BaseDroid copyDroid() {
        return new ShockerDroid(name);
    }

    public int ability(){
        return StunPower;
    };
}
