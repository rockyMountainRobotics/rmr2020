//This class will allow us to shift from high gear to low gear for driving the robot.
package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shifter extends Component
{
    //This solenoid is the one that actually changes gears.
    DoubleSolenoid shifter;
    boolean current = true;
    boolean past = false;

    Robot myRobot;

    DoubleSolenoid.Value low = DoubleSolenoid.Value.kReverse;
    DoubleSolenoid.Value high = DoubleSolenoid.Value.kForward;

    //Constructor
    public Shifter(Robot inRobot)
    {
        shifter = new DoubleSolenoid(RobotMap.COMPRESSOR_PCM, RobotMap.SHIFTER_HIGH, RobotMap.SHIFTER_LOW);
        myRobot = inRobot;
    }

    public void update()
    {
        current = RobotMap.driveController.getRawButton(XboxMap.B);

        if(current == true && past == false)
        {
            if(shifter.get() == high)
            {
                shifter.set(low);
            }
            else
            {
                shifter.set(high);
            }
        }
        SmartDashboard.putBoolean("Gear", shifter.get().equals(DoubleSolenoid.Value.kForward));

        if(RobotMap.driveController.getRawButton(XboxMap.RB))
        {
            shifter.set(low);
        }
    }

    @Override
    public void autoUpdate()
    {

    }

    @Override
    public void disable()
    {

    }

    public void teleOpInit()
    {
        
    }
    

}
