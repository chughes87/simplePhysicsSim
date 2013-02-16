/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplephysicssim;

/**
 *
 * @author charleshughes
 */
public interface IParticle 
{
    Pair getCoord();
    void setCoord(double i, double j);
    void setVelocity(double i, double j);
    Pair getVelocity();
    void setXVelocity(double x);
    void setYVelocity(double y);
    void setMass(double mass);
    double getMass();
    void printData();
}
