package FinalGame;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.display.IDisplaySystem;
import sage.scene.SkyBox;
import sage.scene.state.RenderState;
import sage.scene.state.TextureState;
import sage.terrain.AbstractHeightMap;
import sage.terrain.HillHeightMap;
import sage.terrain.TerrainBlock;
import sage.texture.Texture;
import sage.texture.TextureManager;

public class Background {
	public TerrainBlock createHillMap(TerrainBlock hillTerrain, IDisplaySystem display){
	
	HillHeightMap hill =  new HillHeightMap(200, 2000, 5.0f, 20.0f,(byte)1, 2000);
	hill.setHeightScale(0.5f);
	hillTerrain = CreaterBlock(hill);
	
	TextureState floor;
	Texture floortext = TextureManager.loadTexture2D("skybox/ground2.jpg");
	floortext.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
	floor = (TextureState)
	display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
	floor.setTexture(floortext, 0);
	floor.setEnabled(true);
	hillTerrain.setRenderState(floor);
	hillTerrain.scale(3, 3, 3);
	Matrix3D point = hillTerrain.getLocalTranslation();
	point.translate(-400, -5, -400);
	hillTerrain.setLocalTranslation(point);
	hillTerrain.updateWorldBound();
	return hillTerrain; 
	}
	public TerrainBlock CreaterBlock(AbstractHeightMap height){
	float heightScale = 0.07f;
	Vector3D terrainScale = new Vector3D(1, 0, 1);
	
	int terrainSize = height.getSize();
		
	float cornerHeight = height.getTrueHeightAtPoint(0, 0)* heightScale;
	Point3D terranOrig = new Point3D(0, -cornerHeight, 0);
	String name = "Terrain:" + height.getClass().getSimpleName();
	TerrainBlock terran = new TerrainBlock(name, terrainSize, terrainScale, height.getHeightData(), terranOrig);
	return terran;
		
	}
	
	public SkyBox createScene(SkyBox skybox){
		skybox = new SkyBox("Home",150.0f, 60.0f,150.0f);
		
		Texture eTexture = TextureManager.loadTexture2D("skybox/LongNorthFace.jpg");
		Texture gTexture = TextureManager.loadTexture2D("skybox/groundFinal2.jpg");
		Texture sky = TextureManager.loadTexture2D("skybox/sky2.jpg");
		skybox.setTexture(SkyBox.Face.North, eTexture);
		skybox.setTexture(SkyBox.Face.South, eTexture);
		skybox.setTexture(SkyBox.Face.East, eTexture);
		skybox.setTexture(SkyBox.Face.West, eTexture);
		
		skybox.setTexture(SkyBox.Face.Down, gTexture);
		skybox.setTexture(SkyBox.Face.Up, sky);
		Matrix3D temp = skybox.getLocalTranslation();
		temp.translate(0,50,0);
		skybox.setLocalTranslation(temp);
		return skybox;
	}

}
