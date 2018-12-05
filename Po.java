package edu.ufl.cise.cs1.robots;

import robocode.*;

import java.awt.*;
import java.io.Serializable;

//dont run into teammates, scan (locking?) better, shoot better

//po hello

//random movement and predictive targeting

public class Po extends TeamRobot
{
    public void run()
    {
        setBulletColor(Color.red);
        setRadarColor(Color.blue);
        setBodyColor(Color.green);
        setRadarColor(Color.red);

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        //setAdjustRadarForRobotTurn(true);

        setTurnRadarRight(360);

        while (true)
        {
            setTurnRadarRight(360);
            if (getTime() % 10 == 0)
            {
                setTurnRight(90);
            }
            //turnRadarRight(45);

            ahead(100);
            execute();


            //turnGunRight(45);
            //turnRadarRight(45);
            //scan();


            //setTurnRight(enemy.getBearing() + 90);

        }



    }

    public void onMessageRecieved(MessageEvent e)
    {
        Serializable message = e.getMessage();
        System.out.println(e);


    }

    //public void onHitBullet()


    public void onScannedRobot(ScannedRobotEvent e)
    {
        if (e.getName().equals("edu.ufl.cise.cs1.robots.TinkyWinky*") || e.getName().equals("edu.ufl.cise.cs1.robots.LaaLaa*") || e.getName().equals("edu.ufl.cise.cs1.robots.Dipsy*"))
        {
            doNothing();
        }
        else
        {
            setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
            if (getGunHeat() == 0 || e.getDistance() < 600)
            {
                setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
                setFire(1200 / (e.getDistance()));
            }

            execute();
        }

    }

    public void onHitWall(HitWallEvent e)
    {
        //goTo(0,0);
        back(20);
        turnRight(90);

    }

    public void onHitByBullet(HitByBulletEvent e)
    {
        setTurnRadarRight(e.getBearing());
        setTurnRight(90);
        setAhead(800);
        execute();

    }

    public void onHitRobot(HitRobotEvent e) // change to is EQUALS team mate
    {
        //turnRight(90);
        if (!e.getName().equals("edu.ufl.cise.cs1.robots.TinkyWinky*") || !e.getName().equals("edu.ufl.cise.cs1.robots.LaaLaa*") || !e.getName().equals("edu.ufl.cise.cs1.robots.Dipsy*"))
        {
            ahead(800);
            if (getGunHeat() == 0)
            {
                fire(3);
            }
            //turnRight(90);
        }
        else
        {
            turnRight(30);
            ahead(20);
        }
    }

    private void goTo(int x, int y) {
        double a;
        setTurnRightRadians(Math.tan(
                a = Math.atan2(x -= (int) getX(), y -= (int) getY())
                        - getHeadingRadians()));
        setAhead(Math.hypot(x, y) * Math.cos(a));
    }


    // hide in corner until someone scans robot then we all attack?
    //someone rams enemy?
    // learn how to aim at target

}
