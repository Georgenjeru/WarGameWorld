package com.challenge.model;


import com.challenge.Tank;

public class Soldier {
    private boolean alive;
    private Gun gun;
    private String militaryId;

    private Tank tank;

    public Soldier(String militaryId) {
        this.militaryId = militaryId;
        this.alive = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean gunHasBullets() {
        if (this.gun.getBullets() > 0)
            return true;
        else
            return false;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public void shoot() {
        System.out.println(this.militaryId + " SHOOTING THE BULLETS");
        this.gun.shootBullets();
    }
    public void changeShootingMode() {
        this.gun.changeShootingMode();
    }

    public void shot() {
        this.alive = false;
        System.out.println(this.militaryId + " ENEMY IS SHOT DEAD");
    }
    public void setTank(Tank tank){
        this.tank =tank;
    }
    public void shell(){
        System.out.println(this.militaryId + "'Releasing SHells");
        this.tank.shootShells();
    }
}
