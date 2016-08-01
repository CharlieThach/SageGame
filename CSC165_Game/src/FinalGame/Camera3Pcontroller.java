package FinalGame;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.IInputManager;
import sage.input.action.IAction;
import sage.scene.SceneNode;
import sage.util.MathUtils;

public class Camera3Pcontroller {
private ICamera cam; 
private SceneNode target;
private float camAzimuth; 
private float cameraElevation; 
private float distance; 
private Vector3D worldUpVec;

	public Camera3Pcontroller(ICamera cam, SceneNode target,
	 IInputManager inputMgr, String controllerName){ 

	 this.cam = cam;
	 this.target = target;
	 worldUpVec = new Vector3D(0,5,0);
	 new Point3D(target.getWorldTranslation().getCol(3));
	 //behind 
	 camAzimuth = 180; 
	 cameraElevation = 25.0f; // elevation is in degrees
	 distance = 15.0f;
	 setupInput(inputMgr, controllerName);
	 update(0.0f); // initialize camera state
	 }
	public void setupInput(IInputManager im, String cn) {
	//	IAction orbitAction = new OrbitAroundAction();
	//	IAction zoomAction = new ZoomAction();
	//	 im.associateAction(cn, Axis.RX, orbitAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	//	 im.associateAction(cn, Axis.RY, zoomAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN );
	}
	public void update(float f) {
		
		 cam.lookAt(new Point3D(target.getWorldTranslation().getCol(3)), worldUpVec); 
		 updateCameraPosition();
		
	}
	public void updateCameraPosition() {
		 double theta = camAzimuth;
		 double phi = cameraElevation ;
		 double r = distance; 
		//get the new cordinates for the camera 
		 Point3D relativePosition = MathUtils.sphericalToCartesian(theta, phi, r);
		 Point3D desiredCameraLoc = relativePosition.add(new Point3D(target.getWorldTranslation().getCol(3)));
		 cam.setLocation(relativePosition.add(desiredCameraLoc));
	}
	
	public class OrbitAroundAction implements IAction {
			public void performAction(float time, Event evt){
				 float rotAmount;
				 if (evt.getValue() < -0.7) 
				 	 	rotAmount=-0.4f; 
				 		
				 else { 
					 if (evt.getValue() > 0.7) 
						 rotAmount=0.4f; 
					 else { 
						 rotAmount=0.0f; 
					}
			   }
				 camAzimuth += rotAmount ;
				 camAzimuth = camAzimuth % 360 ;	
			} 
		}
	public class ZoomAction implements IAction{

		public void performAction(float arg0, Event arg) {
			 if (arg.getValue() < -0.2) 
			 	 	distance +=-0.2f;
			 else { 
				 if (arg.getValue() > 0.2) 
					 distance +=0.2f; 
				 else 
					 distance +=0;
				 }
			
		}
		
	}
}