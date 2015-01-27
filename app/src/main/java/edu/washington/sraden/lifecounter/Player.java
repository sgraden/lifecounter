package edu.washington.sraden.lifecounter;

/**
 * Created by steven on 1/26/15.
 */
public class Player {
    private int life;

    public Player() {
        this.life = 20; //Intialize with 20 life
    }

    public void setLife(int n) {
        this.life = n;
    }

    public void changeLife(int n) {
        this.life += n;
    }
}
