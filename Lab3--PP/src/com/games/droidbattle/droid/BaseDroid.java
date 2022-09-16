package com.games.droidbattle.droid;

import java.util.Random;

abstract public class BaseDroid {
    protected final String name;
    protected final String type;
    protected int damage;
    protected int health;
    protected int hitChance;
    protected int stun = 0;

    public BaseDroid(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }
    public abstract int ability();
    public int getHit(int damage) {
        Random random = new Random();
        int actualDamage = 0;
        if (random.nextInt(hitChance) <= hitChance) {
            actualDamage = random.nextInt(damage);
            this.health -= actualDamage;
            if (health < 0) {
                health = 0;
            }
        }
        return actualDamage;
    }
    public int getHeal(int heal){
        Random random = new Random();
        int actualHeal = 0;
        if(this.health != 0){
            actualHeal = random.nextInt(heal);
            this.health += actualHeal;
        }
        return actualHeal;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getStun() {
        return stun;
    }
    public int setStun(int stun) {
        this.stun = stun;
        return this.stun;
    }
    public void addHealth(int health) {
        this.health += health;
    }
    public void setHitChance(int hitChance) {
        this.hitChance = hitChance;
    }
    public int getHitChance() {
        return hitChance;
    }
    abstract public BaseDroid copyDroid();
    public final boolean isStun() {
        return stun > 0;
    }
    public final boolean isAlive() {
        return health > 0;
    }
    @Override
    public final String toString() {
        return "Name:" + name + " Power = " + health;
    }
}
