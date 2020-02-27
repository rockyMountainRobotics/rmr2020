package frc.robot;

import com.playingwithfusion.TimeOfFlight;

public class DistanceSensor
{
    //create a distance sensor object and a variable to store distance
    private static TimeOfFlight distanceSensor;
    double distance;

    public DistanceSensor()
    {
        //instanciate distanceSensor and set distance to zero
        distanceSensor = new TimeOfFlight(RobotMap.DISTANCE_SENSOR); 
        distance = 0;
    }

    public double checkDistance()
    {
        //Returns distance sensor's range
        double numToDivide = 0;
        double sumDist = 0;

        //Return the average of 100 distance sensor values
        for(int i = 0; i < 100; i++)
        {
            distance=distanceSensor.getRange();

            if(distance>100)
            {
                sumDist += distance;
                numToDivide++;
            }
        }

        distance = sumDist/numToDivide;
        //System.out.println(distance);

        //filters out 0 values
        if(distance != 0.0)
        {
            return distance;
        }
        
        return 1500;
    }
}