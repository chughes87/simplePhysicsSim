/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplephysicssim;

/**
 *
 * @author charleshughes
 */


public class Particle implements IParticle
{
    Particle()
    {
	m_coordinates[0] = 0;
	m_coordinates[1] = 0;
	m_velocity[0] = 0;
	m_velocity[1] = 0;
    }
    Particle(double x, double y, double vx, double vy)
    {
	m_coordinates[0] = x;
	m_coordinates[1] = y;
	m_velocity[0]    = vx;
	m_velocity[1]    = vy;
    }
    public Pair getCoord()
    {
	return new Pair(m_coordinates[0],m_coordinates[1]);
    }
    public void setCoord(double i, double j)
    {
        m_coordinates[0] = i;
        m_coordinates[1] = j;
    }
    public void setVelocity(double i, double j)
    {
        m_velocity[0] = i;
        m_velocity[1] = j;
    }
    public Pair getVelocity()
    {
    	return new Pair(m_velocity[0],m_velocity[1]);
    }

    public void setXVelocity(double x)
    {
	m_velocity[0] = x;
    }

    public void setYVelocity(double y)
    {
    	m_velocity[1] = y;
    }
    
    public void setMass(double mass)
    {
    	m_mass = mass;
    }
    public double getMass()
    {
    	return m_mass;
    }
    public void printData()
    {
        System.out.println("coordinates: ("+m_coordinates[0]+","+m_coordinates[1]+")");
        System.out.println("velocity: ("+m_velocity[0]+","+m_velocity[1]+")");
    }
    private double m_coordinates[] = new double[2];//particle coordinates
    private double m_velocity[] = new double[2];//particle velocity
    private double m_mass;//particle's mass}
}
