package GameEngine;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;

public class LeftAndRight extends AbstractInputAction {
	private ICamera camera; 
	public LeftAndRight(ICamera c, SetSpeedAction r){
		camera = c;
	}

	public void performAction(float arg0, Event e) {
		if (e.getValue()>.2){
			float moveAmount = (float)0;
			Vector3D viewDir = camera.getViewDirection().normalize();
			Vector3D curLocVector = new Vector3D(camera.getLocation());
			Vector3D newLocVec = curLocVector.add(viewDir.mult(moveAmount));
			
			double newX = newLocVec.getX()+.1;
			double newY = newLocVec.getY();
			double newZ = newLocVec.getZ();
			Point3D newLoc = new Point3D(newX, newY, newZ);
			camera.setLocation(newLoc);
		
		}else if(e.getValue()<-.2){
			float moveAmount = (float)0;
			Vector3D viewDir = camera.getViewDirection().normalize();
			Vector3D curLocVector = new Vector3D(camera.getLocation());
			Vector3D newLocVec = curLocVector.add(viewDir.mult(moveAmount));
			
			double newX = newLocVec.getX()-.1;
			double newY = newLocVec.getY();
			double newZ = newLocVec.getZ();
			Point3D newLoc = new Point3D(newX, newY, newZ);
			camera.setLocation(newLoc);
		}
		
	}

}