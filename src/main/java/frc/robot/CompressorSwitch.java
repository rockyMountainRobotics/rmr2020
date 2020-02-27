package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;

public class CompressorSwitch extends Component
{
    //Objects for the compressor switch on the robot and 
    DigitalInput compressorInput = new DigitalInput(RobotMap.COMPRESSOR_SWITCH);
    Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PCM);

    public void update()
    {
        if(!compressorInput.get())
        {
            compressor.setClosedLoopControl(true);
            compressor.start();
        }
        else 
        {
            compressor.stop();
        }
    }

    public void autoUpdate() { update(); }

    public void disable() { compressor.stop(); }

    public void teleOpInit() {}
}