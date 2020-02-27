package frc.robot;

//Imports
import edu.wpi.first.wpilibj.GenericHID.Hand;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;

//Intake
public class Intake extends Component
{
    //Motors
    private CANSparkMax beltMotor; //Controls conveyor belt to move balls up
    private CANSparkMax pickUpMotor; //Controls vectored intake
    
    //Solenoid for piston that drops the vector intake
    private DoubleSolenoid intakePiston;

    //set the deadzone as a constant
    private final double DEADZONE = .3;

    //I don't know whether we need this
    int numBalls;

    //These are used by the intake motors
    double stick1;
    double stick2;

    //Set these so we don't have to worry about it later
    DoubleSolenoid.Value retract = DoubleSolenoid.Value.kReverse;
    DoubleSolenoid.Value extend = DoubleSolenoid.Value.kForward;
    DoubleSolenoid.Value off = DoubleSolenoid.Value.kOff;

    public Intake()
    {
        //initialize the belt and pick up motors
        beltMotor = new CANSparkMax(RobotMap.MANIP_BELT_MOTOR, MotorType.kBrushless);
        pickUpMotor = new CANSparkMax(RobotMap.MANIP_PICKUP_MOTOR, MotorType.kBrushless);
        beltMotor.setInverted(true);
        numBalls = 0;

        intakePiston = new DoubleSolenoid(RobotMap.COMPRESSOR_PCM, RobotMap.INTAKE_PISTON_EXTEND,RobotMap.INTAKE_PISTON_RETRACT);
        intakePiston.set(retract);
    }

    public void update()
    {
        //set the stick values - they're linked to the triggers
        stick1 = RobotMap.manipController.getTriggerAxis(Hand.kLeft);
        stick2 = RobotMap.manipController.getTriggerAxis(Hand.kRight);

        //if you press the left trigger, the vectored intake will run
        if(stick1>DEADZONE)
        {
            pickUpMotor.set((stick1*.25));
        }

        //otherwise, if you press the right trigger, the vectored intake will run backwards
        else if(stick2>DEADZONE)
        {
            pickUpMotor.set((-stick2*.25));
        }

        //otherwise, it sets it back to 0
        else
        {
            pickUpMotor.set(0);
        }

        //if you press the left trigger, the conveyor belt will run
        if(stick1>DEADZONE)
        {
            beltMotor.set( (stick1*.4));
        }

        //otherwise, if you press the right trigger, the conveyor belt will run backwards
        else if(stick2>DEADZONE)
        {
            beltMotor.set( (-stick2*.4));
        }

        //otherwise, it sets it back to 0
        else
        {
            beltMotor.set(0);
        }

        //if you press a, the vectored intake flips one way
        if(RobotMap.manipController.getRawButton(XboxMap.A)==true)
        {
            intakePiston.set(extend);
        }

        //if you press y, the vectored intake flips the other way
        if(RobotMap.manipController.getRawButton(XboxMap.Y)==true)
        {
            intakePiston.set(retract);
        }

        //When we enter climb mode, the intake will come up
        if(RobotMap.driveController.getRawButton(XboxMap.RB))
        {
            intakePiston.set(retract);
        }
    }

    public void autoUpdate()
    {
        //do nothing here - we could theoretically run update but there's honestly no point as far as I can see
    }

    public void disable()
    {   
        intakePiston.set(extend);
        beltMotor.set(0);
        pickUpMotor.set(0);
    }

    public void teleOpInit()
    {
        intakePiston.set(extend);
    }
}
