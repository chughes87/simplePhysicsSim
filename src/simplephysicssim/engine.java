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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class engine implements Runnable, MouseListener
{
    ArrayList<Particle> pList = new ArrayList();
    private window win = new window();
    int width = win.getWidth();
    int height = win.getHeight();

    public void randInit(int count, int width, int height)
    {
         Random rand = new Random();

        //populate particle array
        for(int i = 0; i<count; i++)
        {
            Particle P = new Particle();
            P.setCoord(rand.nextInt(width),rand.nextInt(height));
            P.setVelocity(rand.nextInt(1),rand.nextInt(1));
            P.setMass(rand.nextInt(10));//set mass value
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
    private Particle temp;
//    private int pointCount = 50;

//    ArrayList<Particle> pList = new ArrayList();

    engine()
    {
    }

    public void run()
    {
        win.draw(pList); //draw initial scene
        pause(2000); //let gui come up

        //particle motion calculation loop
        while(true)
        {
            //check for new particles
//            if(win.v.x2!=-1)
//            {
////                System.out.println("get");
//                Particle p = new Particle(win.v.x1,win.v.y1,win.v.vx,win.v.vy);
//                p.setMass(5);//win.v.getMass();
//                pList.add(p);
////                System.out.println("plist size:"+pList.size());
//                win.v.x2=-1;
//            }

            for(int i = 0; i<pList.size(); i++)
            {
                //particle to analyze gravitational forces on
                Particle A = pList.get(i);

                //law of conservation of motion
                if(A.getMass()>0)
		{
                    A.setCoord( A.getCoord().first+A.getVelocity().first*0.1, 
			    	A.getCoord().second+A.getVelocity().second*0.1);
		}

                //friction
                if(A.getMass()>10)
                {
                    int dirX = 1;
                    int dirY = 1;
                    double friction = 0.00001;
                    if(A.getVelocity().first>0)
                        dirX = -1;
                    else if(A.getVelocity().first==0)
                        dirX = 0;
                    if(A.getVelocity().second>0)
                        dirY = -1;
                    else if(A.getVelocity().second==0)
                        dirY = 0;
                    A.setVelocity(A.getVelocity().first+dirX*friction*Math.pow(A.getMass(),2), A.getVelocity().second+dirY*friction*Math.pow(A.getMass(),2));
                }

                //check other particles for gravity calculations
		if(A.getVelocity().first+A.getVelocity().second<1)
		{
			for(int j = 0; j<pList.size(); j++)
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
					double newX;
					double newY;
					if(A.getCoord().first-B.getCoord().first>0)
					{
						A.setXVelocity(A.getVelocity().first - ratioX*F);
					}
					else if(A.getCoord().first-B.getCoord().first<0)
					{
						A.setXVelocity(A.getVelocity().first + ratioX*F);
					}
					if(A.getCoord().second-B.getCoord().second>0)
					{
						A.setYVelocity(A.getVelocity().second - ratioY*F);
					}
					else if(A.getCoord().second-B.getCoord().second<0)
					{
						A.setYVelocity(A.getVelocity().second + ratioY*F);
					}

					//collision detection
					if(d < 3)
					{
						//inelastic collision
						A.setMass(A.getMass() + B.getMass());
						//delete the other
						pList.remove(j);

							//elastic collision
					}
//                            double[] temp = new double[2];
//                            temp = A.v;
//                            A.v = B.v;
//                            B.v = temp;
				}
			}
		}

                //gravity
//                if( A.getCoord().second < 495)
//		{
 //                   A.getVelocity().second = A.getVelocity().second + 0.05;
//		}
                
                //wall collision detection
                if(A.getCoord().first > width-5 && A.getVelocity().first > 0 || A.getCoord().first < 5 && A.getVelocity().first < 0)
		{
                    A.setXVelocity(A.getVelocity().first);
		}
                if(A.getCoord().second > height - 5 && A.getVelocity().second > 0 || A.getCoord().second < 5 && A.getVelocity().second < 0)
		{
                    A.setYVelocity(-(A.getVelocity().second));///1.1);
		}

                //looping edges
//                if(A.getCoord().first<5)
//                    A.getCoord().first = width-5;
//                if(A.getCoord().second>width-5)
//                    A.getCoord().second = 5;
//                if(A.getCoord().first<5)
//                    A.getCoord().first = height-5;
//                if(A.getCoord().second>height-5)
//                    A.getCoord().second = 5;
            }
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
		temp.setVelocity(me.getX(), me.getY());
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

//    public void mouseWheelMoved(MouseWheelEvent me)
//    {
//		MPxy = new Point(me.getX(),me.getY());
//		double clickRe = (MinRe + Re_factor*MPxy.getX());
//		double clickIm = (MinIm + Im_factor*MPxy.getY());
//		MinRe = clickRe - Re_factor*10;
//		MaxRe = clickRe + Re_factor*10;
//		MinIm = clickIm - Im_factor*1;
//		MaxIm = MinIm+(MaxRe-MinRe)*ImageHeight/ImageWidth;
//		Re_factor = (MaxRe-MinRe)/(ImageWidth-1);
//		Im_factor = (MaxIm-MinIm)/(ImageHeight-1);
//		makeMandlebrot();
//		updatePicture(gImage);
//    }

    public void mouseMoved(MouseEvent me){}
    public void mouseEntered(MouseEvent mouseevent){}
    public void mouseExited(MouseEvent mouseevent){}
}
