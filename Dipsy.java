package edu.ufl.cise.cs1.robots;
import robocode.*;

import java.awt.*;
import java.io.Serializable;

public class Dipsy extends TeamRobot
{

    public void run () {
        setBulletColor(Color.red);
        setRadarColor(Color.blue);
        setBodyColor(Color.green);


        while (true) {
            ahead(50);
            turnRight(360);
            scan();
        }
    }

    public void onScannedRobot (ScannedRobotEvent e) {

        if (e.getName().contains("LaaLaa") || e.getName().contains("TinkyWinky") || e.getName().contains("Po")) {
            turnLeft(90);
            ahead(50);

        }
        if (e.getVelocity() == 0 || e.getVelocity() < .03) {
            turnLeft(e.getBearing());
            ahead(e.getDistance());
            setAhead(50);
            //setFire(Rules.MAX_BULLET_POWER);
            execute();
        }
        else {
            turnLeft(e.getBearing());
            ahead(e.getDistance());
            setAhead(15);
            setFire(Rules.MAX_BULLET_POWER);
            execute();
        }
    }
    public void onHitRobot (HitRobotEvent e) {

        if (e.getName().contains("LaaLaa") || e.getName().contains("TinkyWinky") || e.getName().contains("Po")) {
            turnLeft(90);
            ahead(50);
        }
        else {
            ahead(50);
            if(getGunHeat() == 0) {
                setFire(12);
                execute();

            }
        }
    }

    public void onHitByBullet (HitByBulletEvent e){
        turnRight(e.getBearing());
        setFire(Rules.MAX_BULLET_POWER);
        execute();

    }

    public void onHitWall (HitWallEvent e) {
        turnRight(90 - e.getBearing());
        ahead(300);

    }


    public void onBulletHit(BulletHitEvent e)
    {
        if (e.getName().contains("LaaLaa") || e.getName().contains("TinkyWinky") || e.getName().contains("Po")) {
            turnLeft(90);
            ahead(50);
        }
        else if(getGunHeat() == 0) {
            setFire(Rules.MAX_BULLET_POWER);
            execute();
        }

    }


    //communication strategy
    public void broadcastMessage (Serializable e) throws IndexOutOfBoundsException
    {

    }
    public void onMessageRecieved (MessageEvent e) {
        Serializable message = e.getMessage();
        System.out.println(message + "got em!");
        Point x = (Point) e.getMessage();

    }

    public void goTo (Point x, Point y) {

    }



}
