package com;
import robocode.*;

/**
 * Roomba - a robot by (Chris)
 */

public class Roomba extends AdvancedRobot
{
	public void run() 
	{
		double vertical = getBattleFieldHeight()-80;
		double horizontal = getBattleFieldWidth()-80;
		
		setTurnGunRight(3960);		

		turnLeftRadians(getHeadingRadians());
		
		ahead(vertical - getY() + 40);
		
		turnLeft(90);
		
		ahead(getX() - 40);

		turnLeft(90);
			
		while(true)
		{
			setTurnGunRight(3960);
			ahead(vertical-50);
			setTurnLeft(90);
			ahead(horizontal-50);
			setTurnLeft(90);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) 
	{
		fire(2);
	}

	public void onHitByBullet(HitByBulletEvent e) 
	{
		setTurnLeft(90);
	}

	public void onHitRobot(HitRobotEvent e) 
	{
		back(100);
	}
}
