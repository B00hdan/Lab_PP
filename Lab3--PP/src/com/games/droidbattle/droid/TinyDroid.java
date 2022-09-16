package com.games.droidbattle.droid;

public class TinyDroid extends BaseDroid {
    public TinyDroid(String name) {
        super(name,"Tiny");
        this.hitChance = 75;
        this.health = 85;
        this.damage = 15;
    }

    @Override
    public BaseDroid copyDroid() {
        return new TinyDroid(name);
    }

    public int ability(){
        return this.damage*2;
    };
}
