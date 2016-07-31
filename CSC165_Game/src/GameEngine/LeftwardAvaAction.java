package GameEngine;
/*
 * Move forward with keyboardbind key
 */

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.SceneNode;

public class LeftwardAvaAction extends AbstractInputAction implements IAction{
	private SceneNode ava; 
	public LeftwardAvaAction(SceneNode a, SetSpeedAction r){
		ava = a;  
	}
	public void performAction(float time, Event e) {
	
		if(e.getValue()<-.7){
			Matrix3D rot = ava.getLocalTranslation();
			Vector3D dir = new Vector3D(2,0,0);
			dir = dir.mult(rot);
			dir.scale((double)(.01*time));
			rot.translate((float)dir.getX(), (float)dir.getY(),(float)dir.getZ());
			ava.updateWorldBound();
		}
		if(e.getValue()>.4){
			Matrix3D rot = ava.getLocalTranslation();
			Vector3D dir = new Vector3D(-2,0,0);
			dir = dir.mult(rot);
			dir.scale((double)(.01*time));
			rot.translate((float)dir.getX(), (float)dir.getY(),(float)dir.getZ());
			ava.updateWorldBound();
		}
			
	}
	
	
}