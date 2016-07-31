package FinalGame;

import java.io.File;

import sage.model.loader.ogreXML.OgreXMLParser;
import sage.scene.Group;

public class Avatar {
   //load the animation of the player 
	public Group getPlayerAvatar(String mesh, String material, String skeleton){
		Group model = null;
		OgreXMLParser loader = new OgreXMLParser(); 
		
		String slash = File.separator;
		
		try {
			model = loader.loadModel("OgreFiles"+slash+mesh,
									 "OgreFiles"+slash+material, 
									 "OgreFiles"+slash+skeleton);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
	}
}
