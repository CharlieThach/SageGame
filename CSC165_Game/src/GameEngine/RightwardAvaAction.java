package GameEngine;
/*
 * Move forward with keyboardbind key
 */

import graphicslib3D.Matrix3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.SceneNode;

public class RightwardAvaAction extends AbstractInputAction implements IAction{
	private SceneNode ava; 
	public RightwardAvaAction(SceneNode a, SetSpeedAction r){
		this.ava = a;  
	}
	public void performAction(float time, Event e) {
	
		Matrix3D avaDir = ava.getLocalTranslation();
		double z = ava.getLocalTranslation().getRow(3).getZ(); 
		avaDir.translate(ava.getLocalTranslation().getRow(3).getX()-.1, 0, 0);
		ava.setLocalTranslation(avaDir);
		ava.updateWorldBound();
			
		
	}
	
	
}