/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simplephysicssim;

/**
 *
 * @author charleshughes
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class engine implements Runnable, MouseListener//, KeyListener
{
    private Particle temp = new Particle();
    ArrayList<Particle> pList = new ArrayList();
    ArrayList<Particle> tempList = new ArrayList();
    private window win = new window();
    int width = win.getWidth();
    int height = win.getHeight();
 
    engine(){
	    win.addController(this);      
	    randInit(100,win.getWidth(),win.getHeight());
	    }

    public void randInit(int count, int width, int height)
    {
         Random rand = new Random();

        //populate particle array
        for(int i = 0; i<count; i++)
        {
            Particle P = new Particle();
            P.setCoord(rand.nextInt(width),rand.nextInt(height));
            P.setVelocity(rand.nextInt(1),rand.nextInt(1));
            P.setMass(5);//rand.nextInt(10));//set mass value
            pList.add(P);
            System.out.println("particle " + i + " is " + P.getMass() + " kg.");
        }
  	
    }

    public void newParticle(int x1, int y1, int x2, int y2)
    {
        int vx = x2 - x1;
        int vy = y2 - y1;
        Particle p = new Particle(x1, y1, vx, vy);
        pList.add(p);
    }	

    public void addParticle(Particle p)
    {
	    pList.add(p);
    }	
//    private int pointCount = 50;

//    ArrayList<Particle> pList = new ArrayList();

//    engine()
//    {
//    }
//
//    public void run()
//    {
//        win.draw(pList); //draw initial scene
//        pause(2000); //let gui come up
//
//        //particle motion calculation loop
	public void run()
	{

	    while(true)
	    {
		    tempList = pList;
             for(int i = 0; i< tempList.size(); i++)
            {
                //particle to analyze gravitational forces on
                Particle A = tempList.get(i);
		
                //wall collision detection
                if(A.getXCoord() + A.getXVelocity() < 0 ||
		   A.getXCoord() + A.getXVelocity() > width)
		{
                    A.setXVelocity(-A.getXVelocity());
		}
                if(A.getYCoord() + A.getYVelocity() < 0 ||
		   A.getYCoord() + A.getYVelocity() > height)
		{
                    A.setYVelocity(-A.getYVelocity());
		}

                A.setCoord( A.getXCoord() + A.getXVelocity(), 
			    A.getYCoord() + A.getYVelocity());

                //friction
                //if(A.getMass()>1)
                {
                    int dirX = 1;
                    int dirY = 1;
                    double friction = 0.001;
                    if(A.getXVelocity()>0){
                        dirX = -1;
		    }else if(A.getXVelocity()==0){
                        dirX = 0;
		    }if(A.getYVelocity()>0){
                        dirY = -1;
		    }else if(A.getYVelocity()==0){
                        dirY = 0;
		    }
                    A.setVelocity(A.getXVelocity() + dirX*friction*Math.pow(A.getXVelocity(),2), 
				  A.getYVelocity() + dirY*friction*Math.pow(A.getYVelocity(),2));
                }

                //check other particles for gravity calculations
		if(true)//A.getVelocity().first+A.getVelocity().second<1)
		{
			for(int j = 0; j<tempList.size(); j++)
			{
			//particle to compare A with
				Particle B = pList.get(j);
				if(Math.pow((A.getCoord().first-B.getCoord().first),2)!=0 && Math.pow((A.getCoord().second-B.getCoord().second),2)!=0)
				{
					//calculate gravity forces
					double d = Math.sqrt(Math.pow((A.getCoord().second-B.getCoord().second),2)+Math.pow((A.getCoord().first-B.getCoord().first),2));
					double F = 2*(1/Math.pow(d,2))*10*(B.getMass()/A.getMass());
					double ratioX = Math.abs((A.getCoord().first-B.getCoord().first)/d);
					double ratioY = Math.abs((A.getCoord().second-B.getCoord().second)/d);

					//apply gravity rules
					if(A.getCoord().first-B.getCoord().first>0)
					{
						A.setXVelocity(A.getXVelocity() - ratioX*F);
					}
					else if(A.getCoord().first-B.getCoord().first<0)
					{
						A.setXVelocity(A.getXVelocity() + ratioX*F);
					}
					if(A.getCoord().second-B.getCoord().second>0)
					{
						A.setYVelocity(A.getYVelocity() - ratioY*F);
					}
					else if(A.getCoord().second-B.getCoord().second<0)
					{
						A.setYVelocity(A.getYVelocity() + ratioY*F);
					}

					//collision detection
					if(d < 3)
					{
						//inelastic collision
						A.setMass(A.getMass() + B.getMass());
						//delete the other
						tempList.remove(j);

							//elastic collision
					}
				}
			}
		}
            }
	     pList = tempList;
            //pause(10);
            win.draw(pList);
	    }
    }
    
    public void pause(int time)
    {
        try{ 
        	Thread.sleep(time);
	}catch(InterruptedException e) {
	}
    }

    private void mouseDragged(MouseEvent me)
    {
        //Point point = new Point(me.getX(),me.getY());
    }

    public void mouseClicked(MouseEvent me)
    {
        //MPxy = new Point(me.getX(),me.getY());
        if(me.getButton() == 3)
        {
            win.clear();
            win.updatePicture();
        }
	else
	{
		temp.setCoord(me.getX(), me.getY());
		temp.setVelocity(0, 0);
	}
    }


    public void mousePressed(MouseEvent me)
    {
        if(me.getButton() == 1)
        {
		temp.setCoord(me.getX(), me.getY());
        }
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void mouseReleased(MouseEvent me)
    {
        if(me.getButton() == 1)
        {
		temp.setVelocity(temp.getXCoord() - me.getX(), 
				 temp.getYCoord() - me.getY());
		temp.setMass(5);
        }
        //ask the user for particle mass
//        System.out.print("mass? ");
//	try {
//		temp.setMass(reader.read());//.readLine().intValue());
//	} catch (IOException ex) {
//		Logger.getLogger(engine.class.getName()).log(Level.SEVERE, null, ex);
//	}
	addParticle(temp);
        //eng.newParticle(v.x1,v.y1,v.x2,v.y2);
    }


    public void mouseMoved(MouseEvent me){}
    public void mouseEntered(MouseEvent mouseevent){}
    public void mouseExited(MouseEvent mouseevent){}
}
