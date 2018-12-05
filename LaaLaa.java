package edu.ufl.cise.cs1.robots;
import robocode.*;
import robocode.Robot;
import robocode.Rules;
import robocode.TeamRobot;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

public class LaaLaa extends TeamRobot
{
    Robot target = new Robot();
    //asdlfklksjfd salkfjalsdjflasdfklajfoijs
    public void run() {

        setBulletColor(Color.red);
        setRadarColor(Color.blue);
        setBodyColor(Color.green);

     /*try {
         broadcastMessage("hey");
     } catch (IOException ignored) {

     }*/

        while (true) {
            scan();
            ahead(90);
            //http://www.cs.xu.edu/csci170/02s/robocode/javadoc/
            setAdjustGunForRobotTurn(true);
            turnGunRight(360);
            back(90);
            turnGunLeft(360);
            if(getGunHeat() == 0) {
                setFire(Rules.MAX_BULLET_POWER);
                execute();
            }


            // how many opponents are left
            int x = getOthers();
        }


    }



    public void onHitWall(HitWallEvent h) {
     /*back(50);
     turnGunLeft(180);
     turnLeft(180);
     scan();*/

        turnLeft(h.getBearing());
        //turnGunLeft(90);
        ahead(10);
        scan();
    }

   /*public void onBulletMissed(BulletMissedEvent e) {
       turnLeft(10);
   }*/

    public void onHitRobot(HitRobotEvent h) {
        if (h.getName().equals("edu.ufl.cise.cs1.robots.Dipsy*") || h.getName().equals("edu.ufl.cise.cs1.robots.TinkyWinky*") || h.getName().equals("edu.ufl.cise.cs1.robots.Po*")) {
            back(100);
            turnRight(90);
        }
       /*else if(h.isMyFault())
       {
           ahead(200);
           if(getGunHeat() == 0) {
               setFire(Rules.MAX_BULLET_POWER);
               execute();
           }
       }*/

        if (getGunHeat() == 0) {
            if (h.getEnergy() > 10) {
                setFire(Rules.MAX_BULLET_POWER);
            } else if (h.getEnergy() > 5) {
                setFire(2);
            } else if (h.getEnergy() > 1) {
                setFire(1);
            } else {
                setFire(.5);
            }

            ahead(100);
        }

        else if (h.getBearing() < 30 || h.getBearing() > -30) {
            //back(100);
            //turnRight(90);
            ahead(200);
            if(getGunHeat() == 0) {
                setFire(Rules.MAX_BULLET_POWER);
                execute();
            }
        }

       /*turnRight(h.getBearing());
       if (getGunHeat() == 0) {
           fire(Rules.MAX_BULLET_POWER);
       } else {
          turnLeft(90);
          ahead(100);
       }*/
    }

    public void onHitByBullet(HitByBulletEvent e) {
       /*double bulletPower = e.getPower();
       if(robocode.Rules.getBulletDamage(e.getBullet().getPower()) == ((3 * bulletPower)))
       //robocode dodging bullets
       //use sweeping sin waves to dodge bullets
       {
           //double distance = Math.random();
           turnRight(90);
           ahead(100);
       }
       turnLeft(90);
       back(100);*/

        turnRight(e.getHeading() - 90);
        //if (getGunHeat() == 0) {
        setFire(Rules.MAX_BULLET_POWER);
        execute();
        //} else {
        //   turnLeft(90);
        //  ahead(100);
        //}
    }

 /* public void broadcastMessage(Serializable m) throws IndexOutOfBoundsException {
      String[] g = getTeammates();
      for (int i = 0; i < g.length; i++) {
          sendMessage(g[i], m);
      }
  }*/

    public void onScannedRobot(ScannedRobotEvent eee)
    {
        double distance = eee.getDistance();

       /*if (eee.getVelocity() < .1) {
           setTurnGunRight(eee.getBearing());
           setFire(Rules.MAX_BULLET_POWER);
           setAhead(distance + 10);
           execute();
       }*/
        //if (distance < 200) {
        //broadcastMessage("wus popping");
        try {broadcastMessage(eee.getName());}catch (IOException ignored) {}
        if (eee.getName().equals("edu.ufl.cise.cs1.robots.Dipsy*") || eee.getName().equals("edu.ufl.cise.cs1.robots.TinkyWinky*") || eee.getName().equals("edu.ufl.cise.cs1.robots.Po*"))
        {
            turnRight(45);
            ahead(10);
        } else if (getGunHeat() == 0)
        {
            setFire(Rules.MAX_BULLET_POWER);
            //setMaxVelocity(Rules.MAX_VELOCITY);
            setAhead(distance + 100);
            execute();
            scan();
        }

        else {
            setAhead(distance + 100);
            execute();
            scan();
        }
    }

       /*} else if (distance < 300) {

           if (eee.getName().equals("edu.ufl.cise.cs1.robots.Dipsy*") || eee.getName().equals("edu.ufl.cise.cs1.robots.TinkyWinky*") || eee.getName().equals("edu.ufl.cise.cs1.robots.Po*")) {

               turnRight(30);
               ahead(10);
           } else {
               //fire(Rules.MAX_BULLET_POWER);
               //fire(2.5);
               setFire(Rules.MAX_BULLET_POWER);
               setAhead(distance + 10);
               execute();
           }

       } else if (distance < 400) {

           if (eee.getName().equals("edu.ufl.cise.cs1.robots.Dipsy*") || eee.getName().equals("edu.ufl.cise.cs1.robots.TinkyWinky*") || eee.getName().equals("edu.ufl.cise.cs1.robots.Po*")) {
               turnRight(30);
               ahead(10);
           } else {
               //fire(2);
               setFire(Rules.MAX_BULLET_POWER);
               setAhead(distance + 10);
               execute();

           }
       } else{
           if (eee.getName().equals("edu.ufl.cise.cs1.robots.Dipsy*") || eee.getName().equals("edu.ufl.cise.cs1.robots.TinkyWinky*") || eee.getName().equals("edu.ufl.cise.cs1.robots.Po*")) {
               setFire(Rules.MAX_BULLET_POWER);
               setAhead(distance + 10);
               execute();
           } else {
               //fire(1);
               setFire(Rules.MAX_BULLET_POWER);
               setAhead(distance + 10);
               execute();
           }

       }*/


 /* public void sendMessage(String name, Serializable message) throws IndexOutOfBoundsException {
      //if (target.getName().equals(name)) {
      MessageEvent m = new MessageEvent("LaaLaa", message);
      System.out.println(m.getMessage());
      //}
      //System.out.println("dasjsa");
  }*/
}


