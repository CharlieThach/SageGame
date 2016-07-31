package GameEngine;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;

public class PitchUpAction extends AbstractInputAction implements IAction{
	private ICamera camera; 
	public PitchUpAction(ICamera c){
		camera =c; 
	}
	public void performAction(float time, Event e) {
		Matrix3D rotationAmt = new Matrix3D();
		Vector3D vd = camera.getViewDirection();
		Vector3D ud = camera.getUpAxis();
		Vector3D rd = camera.getRightAxis();
		
		rotationAmt.rotate(.1, rd);	
		vd=vd.mult(rotationAmt);
		ud = ud.mult(rotationAmt);
		camera.setUpAxis(ud.normalize());
		camera.setViewDirection(vd.normalize());
	}
	
	
}