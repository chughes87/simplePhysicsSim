/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplephysicssim;

/**
 *
 * @author charleshughes
 */
public interface IEngine 
{
	void addParticle(int x1, int y1, int x2, int y2);
	void draw();
	void pause();
}
