package GameEngine;


import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;

public class Yaw extends AbstractInputAction implements IAction{
	private ICamera camera; 
	public Yaw(ICamera c, SetSpeedAction r){
		camera =c; 
	}
	public void performAction(float time, Event e) {
		
		if(e.getValue()< -.2){
		Matrix3D rotationAmt = new Matrix3D();
		Vector3D vd = camera.getViewDirection();
		Vector3D rd = camera.getRightAxis();
		Vector3D up = camera.getUpAxis();
		
		rotationAmt.rotate(.1, up);
		
		vd= vd.mult(rotationAmt);
		rd = rd.mult(rotationAmt);
		camera.setRightAxis(rd.normalize());
		camera.setViewDirection(vd.normalize());
		}
		if(e.getValue()>.2){
			Matrix3D rotationAmt = new Matrix3D();
			Vector3D vd = camera.getViewDirection();
			Vector3D rd = camera.getRightAxis();
			Vector3D up = camera.getUpAxis();
			
			rotationAmt.rotate(-.1, up);	
			vd = vd.mult(rotationAmt);
			rd = rd.mult(rotationAmt);
			camera.setRightAxis(rd.normalize());
			camera.setViewDirection(vd.normalize());
		}
	}
	
	
}