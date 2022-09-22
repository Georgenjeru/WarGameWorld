package com.challenge.model;

import com.challenge.Tank;

import java.util.*;

public class Army {
    LinkedList<String> Soldier = new LinkedList<String>();

    public void setSoldier(LinkedList<String> soldiers) {
        Soldier = soldiers;
    }
    private Soldier[] soldiers;

    LinkedList<Gun> Guns = new LinkedList<>();

    public LinkedList<String> getSoldier() {
        return Soldier;
    }

    public LinkedList<Gun> getGuns() {
        return Guns;
    }

    public void setGuns(LinkedList<Gun> guns) {
        Guns = guns;
    }

    public LinkedList<com.challenge.Tank> getTank() {
        return Tank;
    }

    public void setGun(LinkedList<Gun> guns) {
        Guns = guns;
    }
    //private Gun[] guns;
    LinkedList<Tank> Tank = new LinkedList<>();
    public void setTank(LinkedList<Tank> tanks) {
        Tank = tanks;
    }

    private Tank[] tanks;

    private static final int maxGuns = 400;
    private static final int maxShells = 5;

    public Army() {
        //Iterator<String> i = Gun.iterator();

        for (int k = 0; k < maxGuns; k++) {
            int rand = new Random().nextInt(20);
            if (rand % 3 == 0)
                Guns.add(new Ak47());
            else if (rand % 2 == 0)
                Guns.add(new Drugnov());
            else if (rand % 5 == 0)
                Guns.add(new Minimi());
            else
                Guns.add(new Mp3sd5());
        }
    }

    public Soldier[] getSoldiers() {
        return soldiers;
    }

    private void assignGuns() {
        for (int k = 0; k < soldiers.length; k ++) {
            int gunIndex = new Random().nextInt(maxGuns - 1);
            soldiers[k].setGun(Guns.get(gunIndex));
        }
    }
    public void setSoldiers(Soldier[] soldiers) {
        this.soldiers = soldiers;
        this.assignGuns();
    }
    private void assignTanks() {
        int a;
        for (a = 0; a < soldiers.length; a++) ;
        int tankIndex = new Random().nextInt();
        soldiers[a].setTank(tanks[tankIndex]);
    }

    /*public Gun[] getGuns() {
        return Guns;
    }*/

    /*public void set Guns(Gun[] guns) {
        this.Guns = guns;
    }*/
    public Tank[] getTanks(){
        return tanks;
    }
    public void setTanks(Tank[] tanks) {
        this.tanks = tanks;
    }

}
