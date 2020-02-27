package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.*;

public class Drive extends Component
{
  //Motors
  private WPI_TalonFX leftFront;
  private WPI_TalonFX rightFront;
  private WPI_TalonFX leftBack;
  private WPI_TalonFX rightBack;

  //Combines the motors into left and right "sides" of the robot, then into a "robot" that can use ArcadeDrive
  private SpeedControllerGroup leftDrive;
  private SpeedControllerGroup rightDrive;
  private DifferentialDrive m_myRobot;

  //Values for arcade drive, plus their multipliers
  private double forward = 0.0;
  private double turn = 0.0;
  final double FORWARD_MULTIPLIER = 1.0;
  final double TURN_MULTIPLIER = .8;

  //Constant of proportionality between joystick number (-1 to 1)
  final double JOYSTICK_TO_ENCODER = 213.9; //is average between constant for left motors and right motors

  //Encoders for each motor
  TalonFXSensorCollection rfsensor;
  TalonFXSensorCollection lfsensor;
  TalonFXSensorCollection rbsensor;
  TalonFXSensorCollection lbsensor;

  //Variables and objects for autonomous specifically
  Robot myRobot;
  DistanceSensor distanceSensor;
  double turnValue;
  boolean autoDone;
  int wait;
  double distance;
  double encoderPos;


  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //CONSTRUCTOR
  public Drive(Robot inRobot) {

    //Initialize motors using the ports that are in RobotMap
    leftFront = new WPI_TalonFX(RobotMap.LEFT_FRONT_MOTOR);
    rightFront = new WPI_TalonFX(RobotMap.RIGHT_FRONT_MOTOR);
    leftBack = new WPI_TalonFX(RobotMap.LEFT_BACK_MOTOR);
    rightBack = new WPI_TalonFX(RobotMap.RIGHT_BACK_MOTOR);

    //Set all the motors to coast mode to begin with
    leftFront.setNeutralMode(NeutralMode.Coast);
    rightFront.setNeutralMode(NeutralMode.Coast);
    leftBack.setNeutralMode(NeutralMode.Coast);
    rightBack.setNeutralMode(NeutralMode.Coast);

    //Initialize encoders
    rfsensor = new TalonFXSensorCollection(((BaseTalon)rightFront));
    lfsensor = new TalonFXSensorCollection(((BaseTalon)leftFront));
    rbsensor = new TalonFXSensorCollection(((BaseTalon)rightBack));
    lbsensor = new TalonFXSensorCollection(((BaseTalon)leftBack));

    //Initialize speed controllers and differential drive
    leftDrive = new SpeedControllerGroup(leftFront, leftBack);
    rightDrive = new SpeedControllerGroup(rightFront, rightBack);
    m_myRobot = new DifferentialDrive(leftDrive, rightDrive);

    //Initialize all of the autonomous-specific variables
    myRobot = inRobot;
    distanceSensor = new DistanceSensor();
    turnValue = 0;
    autoDone = false;
    wait = 0;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //UPDATE
  public void update() {
    
    forward = 0;
    turn = 0;

    //TODO: fix this nonsensical garbage
    //If the left joystick value is past .1, set the forward value to it
    if(Math.abs(RobotMap.driveController.getY(Hand.kLeft))>.1)
    {
      forward = RobotMap.driveController.getY(Hand.kLeft);
    }
    //If the right joystick value is past .2, set the forward value to .7
    if(Math.abs(RobotMap.driveController.getY(Hand.kRight))>.2)
    {
      if(RobotMap.driveController.getY(Hand.kRight) > 0)
        {forward = .5*RobotMap.driveController.getY(Hand.kRight);}
      if(RobotMap.driveController.getY(Hand.kRight) < 0)
        {forward = .5*RobotMap.driveController.getY(Hand.kRight);}
    }


    //If the left joystick value is past .1, set the turn value to it
    if(Math.abs(RobotMap.driveController.getX(Hand.kLeft))>.1)
    {
      turn = RobotMap.driveController.getX(Hand.kLeft);
    }
    //If the right joystick value is past .2, set the turn value to .6
    if(Math.abs(RobotMap.driveController.getX(Hand.kRight))>.2)
    {
      if(RobotMap.driveController.getX(Hand.kRight) > 0)
        {turn = .5*RobotMap.driveController.getX(Hand.kRight);}
      if(RobotMap.driveController.getX(Hand.kRight) < 0)
        {turn = .5*RobotMap.driveController.getX(Hand.kRight);}
    }


    m_myRobot.arcadeDrive(FORWARD_MULTIPLIER*(forward * Math.abs(forward)), TURN_MULTIPLIER*(turn * Math.abs(turn)));

    //When we go into climb mode, set drive motors to brake mode and all the encoders to zero
    if(RobotMap.driveController.getRawButton(XboxMap.RB))
    {
      leftFront.setNeutralMode(NeutralMode.Brake);
      rightFront.setNeutralMode(NeutralMode.Brake);
      leftBack.setNeutralMode(NeutralMode.Brake);
      rightBack.setNeutralMode(NeutralMode.Brake);
      lfsensor.setIntegratedSensorPosition(0.0,0);
      rfsensor.setIntegratedSensorPosition(0.0,0);
      lbsensor.setIntegratedSensorPosition(0.0,0);
      rbsensor.setIntegratedSensorPosition(0.0,0);
    }

    //If we switch back into drive mode, set the drive motors back to coast mode
    if(RobotMap.driveController.getRawButton(XboxMap.LB))
    {
      leftFront.setNeutralMode(NeutralMode.Coast);
      rightFront.setNeutralMode(NeutralMode.Coast);
      leftBack.setNeutralMode(NeutralMode.Coast);
      rightBack.setNeutralMode(NeutralMode.Coast);
    }

    //If we press A
    if(RobotMap.driveController.getRawButton(XboxMap.A))
    {
      if(distanceSensor.checkDistance() > 300)
      {
        turnValue = Camera.getTurnValue();
        m_myRobot.arcadeDrive(-.55, turnValue);
      }

    }
  }
   
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //AUTONOMOUS
    public void autoUpdate() {

       distance = distanceSensor.checkDistance();
       autoDone = myRobot.getAutoDone();
       encoderPos = lfsensor.getIntegratedSensorPosition();
       System.out.println(encoderPos);

       if(distance > 1200 || distance < 150 && !autoDone);
       {
        turnValue = Camera.getTurnValue();
        m_myRobot.arcadeDrive(-.55, turnValue);
       }
       if(distance > 280 && distance <= 1200 && !autoDone)
       {
         turnValue = Camera.getTurnValue();
         m_myRobot.arcadeDrive(-.45, turnValue);
       }
       if(distance > 200 && distance <= 280 && !autoDone)
       {
         turnValue = Camera.getTurnValue();
         m_myRobot.arcadeDrive(-.4, turnValue);
         myRobot.setAutoDriveTrue();
       }

       if(autoDone)
       {
         if(wait > 100)
         {
           m_myRobot.arcadeDrive(.5, 0);
         }
         wait++;
       }
    }

    public void disable() 
    {
      wait = 0;
      autoDone = false;
    }

    public void teleOpInit() {}
}