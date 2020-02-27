//generic component class
package frc.robot;

public abstract class Component
{
    public abstract void update();
    public abstract void autoUpdate();
    public abstract void disable();
    public abstract void teleOpInit();
}