package GameEngine;
import graphicslib3D.Matrix3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.SceneNode;

public class BackwardAvaAction extends AbstractInputAction implements IAction{
	private SceneNode ava; 
	public BackwardAvaAction(SceneNode a, SetSpeedAction r){
		ava = a;  
	}
	public void performAction(float time, Event e) {
	
		Matrix3D avaDir = ava.getLocalRotation();
		avaDir.translate(0, 0, (ava.getLocalTranslation().getRow(3).getZ())-.1);
		ava.setWorldTranslation(avaDir);
		ava.updateWorldBound();
		
	}
	
	
}