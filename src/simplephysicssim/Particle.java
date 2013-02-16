/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplephysicssim;

/**
 *
 * @author charleshughes
 */


public class Particle// implements IParticle
{
    Particle()
    {
	m_coordinates.first = 0;
	m_coordinates.second = 0;
	m_velocity.first = 0;
	m_velocity.second = 0;
	for(int i = 0; i<16; i++){
		tail[i] = new Pair(-1.0,-1.0);
	}
    }
    Particle(double x, double y, double vx, double vy)
    {
	m_coordinates.first = x;
	m_coordinates.second = y;
	m_velocity.first    = vx;
	m_velocity.second    = vy;
    }
    public Pair getCoord()
    {
	return new Pair(m_coordinates.first,m_coordinates.second);
    }
    public void setCoord(double i, double j)
    {
	for(int x = 0; x<16; x++){
		if(x==0){
			tail[x] = m_coordinates;
		}else{
			tail[x] = tail[x-1];
		}
	}
        m_coordinates.first = i;
        m_coordinates.second = j;
    }
    public void setVelocity(double i, double j)
    {
        m_velocity.first = i;
        m_velocity.second = j;
    }

    public void setXVelocity(double x)
    {
	m_velocity.first = x;
    }

    public void setYVelocity(double y)
    {
    	m_velocity.second = y;
    }
    
    public double getXVelocity()
    {
	    return m_velocity.first;
    }
    
    public double getYVelocity()
    {
	    return m_velocity.second;
    }
    
    public double getXCoord()
    {
	    return m_coordinates.first;
    }
    
    public double getYCoord()
    {
	    return m_coordinates.second;
    }
    
    public void setMass(double mass)
    {
    	m_mass = mass;
    }
    public double getMass()
    {
    	return m_mass;
    }
    public Pair getTail(int i){
	    return tail[i];
    }
    public void printData()
    {
        System.out.println("coordinates: ("+m_coordinates.first+","+m_coordinates.second+")");
        System.out.println("velocity: ("+m_velocity.first+","+m_velocity.second+")");
    }
    private Pair m_coordinates = new Pair();//particle coordinates
    private Pair m_velocity = new Pair();//particle velocity
    private double m_mass;//particle's mass}
    private Pair[] tail = new Pair[16];
}
