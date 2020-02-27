package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;


//This class controls the system, including the motor, to control the climbing done at the end of the game
public class Climb extends Component
{
    //Motor to control winch that moves scisssor lift up and down
    private CANSparkMax hookLift;
    
     //Solenoid to switch from driving to climbing
     DoubleSolenoid driveClimbSwitch;

     //Solenoid values for different states
     DoubleSolenoid.Value climbing = DoubleSolenoid.Value.kReverse;
     DoubleSolenoid.Value driving = DoubleSolenoid.Value.kForward;

     //Multiplier to slow down the motor and reverse its direction
     private final double MULTIPLIER = -.4;

     //Encoder for climb motor (checks position of scissor lift)
     CANEncoder climbEncoder;

     //Variables to store joystick position, button position, and encoder position (makes code more readable)
     double leftManipJoystickPos;
     boolean overridePressed;
     double climbEncoderPos;

    //Constructor
    public Climb()
    {
        //Motor to extend the drawer-slide with the hook
        hookLift =  new CANSparkMax(RobotMap.HOOK_LIFT_MOTOR, MotorType.kBrushless);

        //Shifts the drive train into controlling the winches to pull ourselves up (set to driving mode to begin with)
        driveClimbSwitch = new DoubleSolenoid(RobotMap.COMPRESSOR_PCM, RobotMap.CLIMB_SWITCH_DRIVING, RobotMap.CLIMB_SWITCH_CLIMBING);
        driveClimbSwitch.set(driving);

        //Create the climb encoder and set it to zero
        climbEncoder = new CANEncoder(hookLift);
        climbEncoder.setPosition(0);
    }

    public void update()
    {
        //Left joystick on manipulator controls when to put the hook up
        leftManipJoystickPos = RobotMap.manipController.getRawAxis(XboxMap.LEFT_JOY_VERT);
        climbEncoderPos = climbEncoder.getPosition();
        overridePressed = RobotMap.manipController.getRawButton(XboxMap.LB);

        //RB on the drive controller switches to climbing mode
        if(RobotMap.driveController.getRawButton(XboxMap.RB))
        {
            driveClimbSwitch.set(climbing);
        }

        //LB on the drive controller switches to driving mode
        if(RobotMap.driveController.getRawButton(XboxMap.LB))
        {
            driveClimbSwitch.set(driving);
        }

        if (leftManipJoystickPos < -.2 && (climbEncoderPos < 270 || overridePressed) )
        { hookLift.set(leftManipJoystickPos*MULTIPLIER); }

        else if(leftManipJoystickPos > .2 && (climbEncoderPos > 1.66 || overridePressed) )
        { hookLift.set(leftManipJoystickPos*MULTIPLIER); }

        else
        { hookLift.set(0); }

        //Set the encoder value to zero when y is pressed
        if(RobotMap.driveController.getRawButton(XboxMap.Y))
        { climbEncoder.setPosition(0); }
    }

    public void autoUpdate(){}

    //Set the encoder value back to zero and set to driving mode
    public void disable()
    {
        hookLift.set(0);
        driveClimbSwitch.set(driving);
    }

    //Sets the switch to driving by default
    public void teleOpInit()
    {
        driveClimbSwitch.set(driving);
    }
}