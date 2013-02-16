/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simplephysicssim;

/**
 *
 * @author charleshughes
 */
/*
 * A window object that is created when GUI is to be displayed
 * and interacted with
 *
 * @author Charles Hughes
 * @version 0.1.0
 */

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class window extends JPanel implements java.util.Observer
{
    private BufferedImage displayImage; //global image variable
    private BufferedImage orig;
    private JLabel image;
    private JFrame frame;
    private int width;
    private int height;

	@Override
    public int getWidth()
    {
	    return width;
    }
	@Override
    public int getHeight()
    {
	    return height;
    }
    //engine eng = new engine();

//    public static void main(String args[])
//    {
//        new window().setVisible(true);
//    
//    }
	// Called from the Model
    	public void update(Observable obs, Object obj) {}

		//who called us and what did they send?
		//System.out.println ("View      : Observable is " + obs.getClass() + ", object passed is " + obj.getClass());

		//model Pull 
		//ignore obj and ask model for value, 
		//to do this, the view has to know about the model (which I decided I didn't want to do)
		//uncomment next line to do Model Pull
    		//myTextField.setText("" + model.getValue());

		//model Push 
		//parse obj
//		myTextField.setText("" + ((Integer)obj).intValue());	//obj is an Object, need to cast to an Integer

//    	} //update()

    public void clear()
    {
        try
        {
            File file = new File("blank.jpg");
            displayImage = ImageIO.read(file); //passes this variable up a scope level
        }
        catch (IOException e)
        {
            System.out.println("file does not exist");
        }
    }

    window()
    {
        try
        {
            File file = new File("blank.jpg");
            displayImage = ImageIO.read(file); //passes this variable up a scope level
	    width = displayImage.getWidth();
	    height = displayImage.getHeight();
        }
        catch (IOException e)
        {
            System.out.println("file does not exist");
        }
        //eng.width = displayImage.getWidth();
        //eng.height = displayImage.getHeight();
        //makeMandlebrot();
        ImageIcon imageicon = new ImageIcon(displayImage);
        image = new JLabel(imageicon);
        frame = makeFrame(image);
        frame.pack();
        frame.setVisible(true);
    }

    private JFrame makeFrame(JLabel label)
    {
        JFrame frame1 = new JFrame("physics time!");
        frame1.setDefaultCloseOperation(3);
        frame1.add(label);
//        frame1.addMouseListener(this);
        //frame1.addMouseWheelListener(this);
        //frame1.addMouseMotionListener(this);
        return frame1;
    }

    public void updatePicture()
    {
        image.setIcon(new ImageIcon(displayImage));
        frame.setVisible(true);
    }

    public void circle(int x, int y)
    {
        displayImage.setRGB(x+2,y-1,0x8A2BE2);
        displayImage.setRGB(x+2,y,0x8A2BE2);
        displayImage.setRGB(x+2,y+1,0x8A2BE2);
        displayImage.setRGB(x-2,y-1,0x228B22);
        displayImage.setRGB(x-2,y,0x228B22);
        displayImage.setRGB(x-2,y+1,0x228B22);
        displayImage.setRGB(x+1,y+2,0x8A2BE2);
        displayImage.setRGB(x,y+2,0x8A2BE2);
        displayImage.setRGB(x-1,y+2,0x8A2BE2);
        displayImage.setRGB(x+1,y-2,0x228B22);
        displayImage.setRGB(x,y-2,0x228B22);
        displayImage.setRGB(x-1,y-2,0x228B22);
    }

    public void draw(final ArrayList<Particle> pList)
    {
        for(int i = 0; i<pList.size(); i++)
        {
            Particle A = pList.get(i);
            if(A.getCoord().first < width-5 && A.getCoord().first > 5 && A.getCoord().second < height-5 && A.getCoord().second > 5)
            {
                if(A.getMass() > 10)
		{
                    circle((int)A.getCoord().first,(int)A.getCoord().second);
		}
                else if(A.getMass() > 0)
		{
                    displayImage.setRGB((int)A.getCoord().first,(int)A.getCoord().second,0x000000);
		}
            }
//            else
//                System.out.println("("+A.getCoord().first+","+A.getCoord().second+")");
        }
        updatePicture();
    }

    public void addController(MouseListener controller)
    {
	    frame.addMouseListener(controller);
    }
}


class Vector
{
    int x1, y1, x2, y2, vx, vy, m=-1;
    Vector()
    {
        x1=0;
        y1=0;
        x2=0;
        y2=0;
    }
}
