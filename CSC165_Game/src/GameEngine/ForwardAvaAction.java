package GameEngine;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.Model3DTriMesh;
import sage.scene.SceneNode;
import sage.terrain.TerrainBlock;

public class ForwardAvaAction extends AbstractInputAction implements IAction{
	private Model3DTriMesh ava; 
	int r; 
	private TerrainBlock terrain; 
	public ForwardAvaAction(Model3DTriMesh a, SetSpeedAction r){
		this.ava = a;  
 
	}
	
	public void performAction(float time, Event e) {
	if(e.getValue()<-.7){
		
		Matrix3D rot = ava.getLocalTranslation();
		Vector3D dir = new Vector3D(0,0,2);
		dir = dir.mult(rot);
		dir.scale((double)(.01*time));
		rot.translate((float)dir.getX(), (float)dir.getY(),(float)dir.getZ());
		ava.updateWorldBound();

	}
	if(e.getValue()>.7){
		
		Matrix3D rot = ava.getLocalTranslation();
		Vector3D dir = new Vector3D(0,0,-2);
		dir = dir.mult(rot);
		dir.scale((double)(.01*time));
		rot.translate((float)dir.getX(), (float)dir.getY(),(float)dir.getZ());
		ava.updateWorldBound();
		}
	}
	
}