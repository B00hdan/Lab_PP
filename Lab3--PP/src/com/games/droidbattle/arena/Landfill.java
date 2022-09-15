package com.games.droidbattle.arena;

import com.games.droidbattle.droid.BaseDroid;

import java.util.List;

public class Landfill extends City {

    @Override
    public List<BaseDroid> setArena(List<BaseDroid> list){
        for (BaseDroid droid : list){
            droid.setChanceShot(droid.getChanceShot() - 10);
        }
        return list;
    }
}
