package com.games.droidbattle.arena;

import com.games.droidbattle.droid.BaseDroid;

import java.util.List;

public class Factory extends City {

    @Override
    public List<BaseDroid> setArena(List<BaseDroid> list) {
        for (BaseDroid droid : list){
            droid.addHealth(20);
        }
        return list;
    }
}
