package com.crejaud.jrejaud.munchkincompanion;

/**
 * Created by creja_000 on 7/22/2014.
 */
public class Player {

    private String myName;
    private int myBase;
    private int myBonus;
    private int myTotal;

    public Player(String name) {
        myName = name;
        myBase = 1;
        myBonus = 0;
        myTotal = myBase + myBonus;
    }

    public String getName() {
        return myName;
    }


    public int getBase() {
        return myBase;
    }

    public void setBase(int base) { myBase = base; }

    public int getBonus() {
        return myBonus;
    }

    public void setBonus(int bonus) { myBonus = bonus; }

    public int getTotal() { return myTotal; }

    public void setTotal(int total) { myTotal = total; }

}
