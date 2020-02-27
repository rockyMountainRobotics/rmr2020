/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;


/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  //create a component array 
  Component[] parts = new Component[10];
  int numParts;
  boolean autoDone = false;
  boolean autoDrive = false;
  boolean inClimbMode = false;
  DigitalInput autonomousInput1 = new DigitalInput(RobotMap.AUTONOMOUS_SWITCH_1);
  DigitalInput autonomousInput2 = new DigitalInput(RobotMap.AUTONOMOUS_SWITCH_2);


  @Override
  public void robotInit()
  {
    autoDone = false;
    autoDrive = false;
    inClimbMode = false;
    
    numParts = 0;
    //Initialize a Drive Component
    parts[numParts] = new Drive(this);
    numParts++;
    parts[numParts] = new CompressorSwitch();
    numParts++;
    parts[numParts] = new BallRelease(this);
    numParts++;
    parts[numParts] =  new Intake();
    numParts++;
    parts[numParts] = new Shifter(this);
    numParts++;
    parts[numParts] = new Climb();
    numParts++;

   // camera = CameraServer.getInstance().startAutomaticCapture();
  }


  @Override
  public void teleopInit()
  {
    for(int i = 0; i < numParts; i++)
    {
      parts[i].teleOpInit();
    }
  }

  @Override
  public void teleopPeriodic(){
    //System.out.println("hello");
    //Update the components


    for(int i = 0; i < numParts; i++)
    {
      parts[i].update();
    }
    
  }

  @Override
  public void autonomousPeriodic()
  {
    for(int i = 0; i < numParts; i++)
    {
      //if(autonomousInput1.get())
      parts[i].autoUpdate();
      
    }
  }
  public void setAutoDriveTrue()
  {
    autoDrive = true;
  }

  public boolean getAutoDrive()
  {
    return autoDrive;
  }

  public void setAutoDoneTrue()
  {
    autoDone = true;
  }

  public boolean getAutoDone()
  {
    return autoDone;
  }


  public void setInClimbMode()
  {
    inClimbMode = true;
  }

  public boolean getInClimbMode()
  {
    return inClimbMode;
  }
}

