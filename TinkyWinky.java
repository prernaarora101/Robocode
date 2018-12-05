package edu.ufl.cise.cs1.robots;

import com.sun.javafx.css.Rule;
import robocode.*;
import robocode.control.events.TurnStartedEvent;
import robocode.util.Utils;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class TinkyWinky extends TeamRobot
{
    private String targetName = null;

    public void run()
    {
        //setAdjustGunForRobotTurn(true);
        //setAdjustRadarForGunTurn(true);
        //setScanColor(Color.blue);
        setBodyColor(Color.GREEN);
        setBulletColor(Color.RED);
        setRadarColor(Color.BLUE);
        setScanColor(Color.PINK);

        while (true)
        {
            setTurnRadarRight(Double.POSITIVE_INFINITY);
            //setAdjustRadarForGunTurn(true);
            setAdjustGunForRobotTurn(false);
            setBack(100);
            turnLeft(360);
            if (getTime() % 10 == 0)
            {
                double distance = Math.random() * 3000;
                setTurnRight(distance);
                ahead(distance);
                execute();
            }
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e)
    {
        targetName = e.getName();
        int x = Integer.MIN_VALUE;
        int y = Integer.MIN_VALUE;
        double angle = Math.toRadians((getHeading() + e.getBearing())% 360);
        x= (int)(getX() + Math.sin(angle) * e.getDistance());
        y = (int)(getY() +Math.cos(angle) * e.getDistance());

        try {
            broadcastMessage(e);
        }catch(IOException ignored)
        {
        }

        //getStatusEvents();
       /*if(e.getName().contains("Failbot"))
       {
           ahead(e.getDistance() - 50);
           fire(Rules.MAX_BULLET_POWER);
       }
       else
       {
           doNothing();
       }*/
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());


        setAhead(100 * Math.sin(getTime() / 4));
        //oscillating from robowiki
        execute();

        double distance = e.getDistance();
        if (e.getName().equals("edu.ufl.cise.cs1.robots.Po*") || e.getName().equals("edu.ufl.cise.cs1.robots.LaaLaa*")
                || e.getName().equals("edu.ufl.cise.cs1.robots.Dispsy*")) {
            //back(180);
            turnRight(180);
            ahead(100);
        }
        else if (distance < 200 && getGunHeat() == 0)
        {
            setTurnRight(90 + e.getBearing());
            setFire(Rules.MAX_BULLET_POWER);
            execute();
        }
        else if (distance < 500 && getGunHeat() == 0)
        {
            setTurnRight(90 + e.getBearing());
            setFire(2);
            execute();
        }
        else if (distance < 800 && getGunHeat() == 0)
        {
            setTurnRight(90 + e.getBearing());
            setFire(1.5);
            execute();
        }
        else {
            if (getGunHeat() == 0)
            {
                setTurnRight(90 + e.getBearing());
                setFire(.5);
                execute();
            }
        }
    }

    //when bullet has been fired abort, evade bullet also start firing after 300 ticks
    public void onBulletHit(BulletHitEvent e)
    {
        if (getGunHeat() == 0)
        {
            ahead(200);
            //changed from 100
            fire(2);
        }
    }

    public void onHitByBullet(HitByBulletEvent e)
    {
        double energy = getEnergy();
        if (e.getName().equals("edu.ufl.cise.cs1.robots.Po*") || e.getName().equals("edu.ufl.cise.cs1.robots.LaaLaa*") || e.getName().equals("edu.ufl.cise.cs1.robots.Dispsy*")) {
            //back(180);
            //turnRight(180);
            //ahead(100);
            doNothing();
        }
        else {
            double bearing = e.getBearing();
            //turnRight(e.getBearing());
            if (energy < 50)
            {
                turnRight(bearing);
                back(100);
                //fire(1);
            }
            else {
                setTurnRight(bearing);
                setFire(Rules.MAX_BULLET_POWER);
                execute();
                //changed from turning 180 and no fire
            }
            //cite
            //changed turn right from 360, -bearing, and ahead where it says back
        }
    }

    public void onHitWall(HitWallEvent e)
    {
        double bearing = e.getBearing();
        setTurnRight(-bearing);
        setAhead(200);
        execute();
    }

    public void onHitRobot(HitRobotEvent e)
    {
        //ahead(40);
        if (e.getName().equals("edu.ufl.cise.cs1.robots.Po*") || e.getName().equals("edu.ufl.cise.cs1.robots.LaaLaa*") || e.getName().equals("edu.ufl.cise.cs1.robots.Dispsy*")) {
            doNothing();
        }
        else {
            turnRight(e.getBearing());
            setAhead(40);
            execute();
            if (e.getEnergy() > 15 && getGunHeat() == 0)
            {
                setFire(Rules.MAX_BULLET_POWER);
                execute();
            }
            else if (e.getEnergy() > 10 && getGunHeat() == 0)
            {
                setFire(2);
                execute();
            }
            else if (e.getEnergy() > 6 && getGunHeat() == 0)
            {
                setFire(1.5);
                execute();
            }
            else if (e.getEnergy() > 2 && getGunHeat() == 0)
            {
                setFire(.5);
                execute();
            }
            else {
                setFire(.1);
                execute();
            }
            //add ramming qualities
           /*if (getGunHeat() == 0) {
               fire(Rules.MAX_BULLET_POWER);
           }*/
            //setTurnRadarRight(e.getBearing());
            setBack(40);
            setAhead(80);
            execute();
        }
    }

    public void onMessageReceived(MessageEvent e)
    {
        //out.println(event.getSender() + " sent me: " + event.getMessage());
        Serializable string = e.getMessage();
        fire(Rules.MAX_BULLET_POWER);
        System.out.println(string + "\nhey back");
    }

    public void onRobotDeath(RobotDeathEvent e)
    {
        if(e.getName().equals(targetName))
        {
            targetName = null;
        }
    }
}
//random movement and predictive targeting
//iterate through array of distance
//use sin at some point
//array.sort
