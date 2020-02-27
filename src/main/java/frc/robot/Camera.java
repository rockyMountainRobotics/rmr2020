//Returning useful information/calculations from Limelight data
package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Camera
{
    //gets value for "turn" to go into ArcadeDrive in autonomous
    public static double getTurnValue()
    {
        //Get the tx value (how far the target is from the center (difference in crosshairs))
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
        
        //Initialize two multiplier values, plus a minimum motor movement value
        final double MULTIPLIER1 = 0.05;
        final double MULTIPLIER2 = .6;
        final double MINIMUM_MOTOR_MOVEMENT = 0.1;

        //initialize the lining up values to the minimum motor value to overcome friction.
        double lineUpLeft = MINIMUM_MOTOR_MOVEMENT;
        double lineUpRight = MINIMUM_MOTOR_MOVEMENT;

        //Create a double for the turn value (to be returned)
        double turnValue;

        //If the limelight's crosshair is too far to the left of the target, add value to the left motors so it turns right, and vice versa
        if(tx > 2) lineUpLeft += tx * MULTIPLIER1;
        else if (tx < -2) lineUpRight += -tx * MULTIPLIER1;
        
        //Set turnvalue to the actual value to be used in ArcadeDrive
        turnValue = (lineUpLeft-lineUpRight)*MULTIPLIER2;

        //Fixes if the absolute value of the turn value is more than 1
        if(turnValue > 1) turnValue = 1;
        if(turnValue < -1) turnValue = -1;

        //Return turnValue
        return turnValue;  
    }
}