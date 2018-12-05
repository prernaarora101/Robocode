package edu.ufl.cise.cs1.robots;
import robocode.*;

import java.awt.*;
import java.io.Serializable;

public class Dipsy extends TeamRobot
{
    //Hello I need to clean my keyboard guys pls help
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
     // logic based on message and get rid of the scanning
    public void onMessageRecieved ( ScannedRobotEvent e) {
        
        //Serializable message = e.getMessage();
        //System.out.println(message + " got em!");
       // Point x = (Point) e.getMessage();

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

    private void goTo(int x, int y) {
        double a;
        setTurnRightRadians(Math.tan(
                a = Math.atan2(x -= (int) getX(), y -= (int) getY())
                        - getHeadingRadians()));
        setAhead(Math.hypot(x, y) * Math.cos(a));

    }





}
