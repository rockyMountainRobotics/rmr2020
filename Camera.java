//programming the Limelight i guess

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Camera extends Component
{
    //This just creates the objects for the network table and its entries - see comment at bottom for definitions
    NetworkTable table;
    NetworkTableEntry tx,ty,ta,thor,tvert;
    double x,y,a,hor,vert,aspectRatio;

    public Camera()
    {
        //This instanciates the network table and its entries
        table=NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        thor = table.getEntry("thor");
        tvert = table.getEntry("tvert");
    }

    public void update()
    {
        //this just updates x, y, and a
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        a = ta.getDouble(0.0);

        //idk if there's a special NetworkTables object for aspect ratio so this just calculates it
        hor = thor.getDouble(0.0);
        vert = tvert.getDouble(0.0);
        aspectRatio = hor/vert;

        //this puts x, y, a, and the aspect ratio onto SmartDashboard
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightA", a);
        SmartDashboard.putNumber("AspectRatio", aspectRatio);
    }

    public void autoUpdate()
    {
        //this just updates x, y, and a
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        a = ta.getDouble(0.0);

        //idk if there's a special NetworkTables object for aspect ratio so this just calculates it
        hor = thor.getDouble(0.0);
        vert = tvert.getDouble(0.0);
        aspectRatio = hor/vert;

        //this puts x, y, a, and the aspect ratio onto SmartDashboard
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightA", a);
        SmartDashboard.putNumber("AspectRatio", aspectRatio);
    }

    public void disable()
    {

    }
}

//So we basically need code to line up the crosshair with the target (to be used for aiming)
/*helpful values:
tv is if we have targets (0 or 1)
tx is the horizontal offset in degrees
ty is the vertical offset in degrees
ta is the target area as a percentage*/

