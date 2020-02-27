package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.Thread;

//This class controls when to release balls via the solenoid gate
public class BallRelease extends Component
{
    //Solenoid to dump balls
    DoubleSolenoid openGate;

    //Solenoid Vaues so I don't have to use the whole long name every time :D
    DoubleSolenoid.Value reverse = DoubleSolenoid.Value.kReverse;
    DoubleSolenoid.Value forward = DoubleSolenoid.Value.kForward;
    DoubleSolenoid.Value off = DoubleSolenoid.Value.kOff;

    //distance sensor
    private DistanceSensor distanceSensor;

    //Pointer to central robot object
    Robot myRobot;

    //Constructor
    public BallRelease(Robot inRobot)
    {
        //sets gate solenoid
        openGate = new DoubleSolenoid(RobotMap.COMPRESSOR_PCM, RobotMap.DUMP_BALL_RELEASE, RobotMap.RETRACT_BALL_RELEASE);
        activateDumpSolenoid(reverse);

        //Creates distance sensor here
        distanceSensor = new DistanceSensor();

        //Actually insert the pointer to the central robot object
        myRobot = inRobot;
    }

    public void update()
    {
        //when x is pressed, dump balls
        if(RobotMap.manipController.getRawButton(XboxMap.X)==true)
        {
            activateDumpSolenoid(forward);
        }

        //when b is pressed, put gate back up
        if(RobotMap.manipController.getRawButton(XboxMap.B)==true)
        {
            activateDumpSolenoid(reverse);
        }

        //If A is pressed (auto)
        if(RobotMap.driveController.getRawButton(XboxMap.A)==true)
        {
            if(distanceSensor.checkDistance() < 270 && distanceSensor.checkDistance() != 0)
            {
                activateDumpSolenoid(forward);
                myRobot.setAutoDoneTrue();
            }
        }
    }

    public void autoUpdate()
    {
        //Dumps if within 300 distance
        if(distanceSensor.checkDistance() < 300 && myRobot.getAutoDrive())
        {
            activateDumpSolenoid(forward);
            myRobot.setAutoDoneTrue();
        }

        //closes the ball tray after doing autonomous
        if(distanceSensor.checkDistance() > 900 && myRobot.getAutoDrive())
        {
            activateDumpSolenoid(reverse);
        }
    }
    
    //set everything to nothing
    public void disable()
    {
         openGate = null;
    }

    public void teleOpInit()
    {
        activateDumpSolenoid(reverse);
    }


    //Puts up or down the solenoid controlling the gate
    public void activateDumpSolenoid(DoubleSolenoid.Value value)
    {
        openGate.set(value);
    }
}