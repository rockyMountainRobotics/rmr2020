package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class RobotMap
{
    //Drive Motors
	public final static int RIGHT_FRONT_MOTOR = 7;
  	public final static int LEFT_FRONT_MOTOR = 8;
	public final static int RIGHT_BACK_MOTOR = 6;
    public final static int LEFT_BACK_MOTOR = 9;

    //Manip Motors
    public final static int MANIP_BELT_MOTOR = 10;
    public final static int MANIP_PICKUP_MOTOR = 11;
    public final static int BALL_RELEASE_MOTOR = 4;
    public final static int HOOK_LIFT_MOTOR = 12;

    //Solenoids
    public final static int DUMP_BALL_RELEASE = 0;
    public final static int RETRACT_BALL_RELEASE = 1;
    public final static int SHIFTER_LOW = 4;
    public final static int SHIFTER_HIGH = 5;
    public final static int INTAKE_PISTON_EXTEND = 3;
    public final static int INTAKE_PISTON_RETRACT = 2;
    public final static int CLIMB_SWITCH_DRIVING = 7;
    public final static int CLIMB_SWITCH_CLIMBING = 6;

    //Controllers
    public static XboxController driveController = new XboxController(0);
    public static XboxController manipController = new XboxController(1);

    //Compressor
    public final static int COMPRESSOR_PCM = 1;
    public final static int COMPRESSOR_SWITCH = 0;

    //Autonomous switch
    public final static int AUTONOMOUS_SWITCH_1 = 1;
    public final static int AUTONOMOUS_SWITCH_2 = 2;

    //Sensors
    public final static int DISTANCE_SENSOR = 0;
}