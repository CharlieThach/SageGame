package FinalGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.app.BaseGame;
import sage.audio.AudioManagerFactory;
import sage.audio.AudioResource;
import sage.audio.AudioResourceType;
import sage.audio.IAudioManager;
import sage.audio.Sound;
import sage.audio.SoundType;
import sage.camera.ICamera;
import sage.display.IDisplaySystem;
import sage.input.IInputManager;
import sage.input.action.QuitGameAction;
import sage.networking.IGameConnection.ProtocolType;
import sage.physics.IPhysicsEngine;
import sage.physics.IPhysicsObject;
import sage.scene.Group;
import sage.scene.Model3DTriMesh;
import sage.scene.SceneNode;
import sage.scene.SkyBox;
import sage.terrain.TerrainBlock;
import sage.texture.Texture;
import sage.texture.TextureManager;
import GameEngine.*;

public class MyGame extends BaseGame{
	private String serverAddress;
	private int serverPort, localPort;
	private ProtocolType serverProtocol;
	private InetAddress localAddr;
	private MyClient thisClient = null;
	private IDisplaySystem display; 
	private ICamera camera; 
	private IInputManager im;
	private IAudioManager audioMgr; 
	private Sound background; 
	private IPhysicsObject ballP, groundPlaneP;
	private IPhysicsEngine physicsEngine;
	private SkyBox skybox;
	private Group rootNode;
	private TerrainBlock hillTerrain;
	private Group model;
	private Group npc;
	private String kb;
	private String gp; 
	private SetSpeedAction setSpeed;
	private Camera3Pcontroller cam;
	ScriptEngineManager factory ;
	ScriptEngine jsEngine;
	String scriptFileName;
	Avatar ava;
	SceneNode girl; 
	Model3DTriMesh mesh; 
	
	public MyGame(String serveAddr, int sPort){
		super( );
		ava = new Avatar(); 
		this.serverAddress =serveAddr;
		this.serverPort =sPort;
		this.serverProtocol = ProtocolType.TCP;
		factory = new ScriptEngineManager(); 
		scriptFileName = "materials/CreateWorld.js";
		
		jsEngine = factory.getEngineByName("js");
		
		executeScript (jsEngine, scriptFileName);
		rootNode = (Group)jsEngine.get("rootNode");
		//addGameWorldObject(rootNode);
		setSpeed = new SetSpeedAction();
		
	}
	 private void executeScript(ScriptEngine engine, String scriptFileName)
	 {
	 try
	 { 
		 FileReader fileReader = new FileReader(scriptFileName);
		 engine.eval(fileReader); //execute the script statements in the file
		 fileReader.close();
	 }
	 catch (FileNotFoundException e1)
	 { System.out.println(scriptFileName + " not found " + e1); }
	 catch (IOException e2)
	 { System.out.println("IO problem with " + scriptFileName + e2); }
	 catch (ScriptException e3)
	 { System.out.println("ScriptException in " + scriptFileName + e3); }
	 catch (NullPointerException e4)
	 { System.out.println ("Null ptr exception in " + scriptFileName + e4); }
	 }
	//iniliaize the network and the game as before 
	protected void initGame(){

			System.out.println(jsEngine.get("what"));
			//display of the game
			display = getDisplaySystem(); 
			//title of the game
			display.setTitle("James 1.0");
			
			//camera 
			camera = display.getRenderer().getCamera(); 
			camera.setPerspectiveFrustum(60, 1, .01, 400);
			//camera.setLocation(new Point3D(1,20, 1));
			
		
			
			estabConnection();
		
			
			Background background = new Background(); 
			//create skybox 
			skybox = background.createScene(skybox);
			rootNode.addChild(skybox);
			skybox.updateWorldBound();
			//create hillMaps 
			hillTerrain = background.createHillMap(hillTerrain, display); 
			hillTerrain.updateWorldBound();
				rootNode.addChild(hillTerrain);
				
			loadAvatar();
			
			model = ava.getPlayerAvatar("Sphere.mesh.xml","Ball.material","Sphere.skeleton.xml");
			
			Iterator<SceneNode> itr = model.getChildren();
			int count = 0;
				while (itr.hasNext()){
					mesh = ((Model3DTriMesh) itr.next());
					//mesh.scale(1, 1, 1);
					
					//Texture text =  TextureManager.loadTexture2D("materials/Robot2.png"); 
					Texture text =  TextureManager.loadTexture2D("materials/jamesFinal.png"); 
					mesh.setTexture(text);
					Matrix3D p = mesh.getLocalTranslation();
					p.translate(0, -1, 0);
					//p.getCol(3).getY()+
					mesh.setLocalTranslation(p);
					rootNode.addChild(mesh);
					
					System.out.println("Does this have animation: "+mesh.getAnimations());
					//mesh.startAnimation("dabing");
					mesh.startAnimation("rolling");
					//mesh.startAnimation("Walking");
					count++;
				}
				System.out.println("Count" +count);
				//adding the images to game 
				for(SceneNode n: rootNode){
					addGameWorldObject(n);
				}
				rootNode.updateGeometricState(0,true);

				inputManager(); 
				initAudio();
			//	initPhysicsSystem();
			//	createSagePhysicsWorld();
				cam = new Camera3Pcontroller(camera, mesh, im, kb);
				
	}
	//update like before any other stuff recieved from server 
	protected void update(float time){
		if (thisClient != null) 
			thisClient.processPackets();
		Iterator<SceneNode> itr = model.getChildren();
		while (itr.hasNext()){
			Model3DTriMesh submesh = ((Model3DTriMesh) itr.next());
			submesh.updateAnimation(time);	
		}
	
	
		
//		Point3D avLoc = new Point3D(devil.getLocalTranslation().getCol(3));
//		float x = (float) avLoc.getX();
//		float z = (float) avLoc.getZ();
//		float terHeight = hillTerrain.getHeight(x,z);
//		float desiredHeight = terHeight + (float)hillTerrain.getOrigin().getY() + 0.5f;
//		devil.getLocalTranslation().setElementAt(1, 3, desiredHeight);
		
		rootNode.updateGeometricState(time, true);
		
	skybox.getLocalTranslation();
		
	//	temp.translate(0, 50, 0);
		//skybox.setLocalTranslation(temp);
		Point3D camLoc = camera.getLocation();
		Matrix3D camTranslation = new Matrix3D();
		camTranslation.translate(camLoc.getX(), camLoc.getY(), camLoc.getZ());
		skybox.setLocalTranslation(camTranslation);
		super.update(time); 
		cam.update(time);
	}
	
	public void initAudio(){
		AudioResource re1; 
		audioMgr = AudioManagerFactory.createAudioManager("sage.audio.joal.JOALAudioManager");
		if(!audioMgr.initialize()){
			System.out.println("Unable to start sound");
			return;
		}
		
		re1 = audioMgr.createAudioResource("music/CityLights.wav", AudioResourceType.AUDIO_SAMPLE);
		background = new Sound (re1, SoundType.SOUND_MUSIC, 20, true);
		background.initialize(audioMgr);
		background.setMaxDistance(500);
		background.setMinDistance(2);
		background.setLocation(new Point3D(mesh.getWorldTranslation().getCol(3)));
		background.play();
		
		
	}
	private void estabConnection(){
		try
		 { 
			thisClient = new MyClient(InetAddress.getByName(serverAddress),serverPort, localAddr, localPort, serverProtocol, this); 
		 }
		 catch (UnknownHostException e) { 
			 e.printStackTrace(); 
			 }
		 catch (IOException e) { 
			 e.printStackTrace(); 
		}
		 if (thisClient != null){ 
			 	thisClient.sendJoinMessage(); 
			 }
		
	}
	public void createNPC(){
		
	}

	private void loadAvatar() {
		new sage.model.loader.OBJLoader();
	
		
		npc = ava.getPlayerAvatar("stat.mesh.xml","stat.material","stat.skeleton.xml");
		
		Iterator<SceneNode> itr = npc.getChildren();
		while(itr.hasNext()){
			Model3DTriMesh mak = ((Model3DTriMesh) itr.next());
			mak.scale(5, 5, 5);
			mak.translate(15, -10, 15);
			Texture text =  TextureManager.loadTexture2D("materials/statFinal.png"); 
			mak.setTexture(text);
			rootNode.addChild(mak);
			
		}
		
		//(Model3DTriMesh) npc.setTexture(text);	
//		statue = loader.loadModel("materials/untitled.obj");
//		Texture state =  TextureManager.loadTexture2D("materials/statFinal.png"); 
//		//statue.scale(1,.7f, .7f);
//		statue.setTexture(state);
//		Matrix3D p1 = statue.getLocalTranslation();
//		p1.translate(5, 0, 5);
//		statue.setLocalTranslation(p1);
		
//		statue.setLocalTranslation(terrainLocation(statue));
//		rootNode.addChild(statue);
		
//		TriMesh princess = loader.loadModel("materials/PrinceGirlFinalblend.obj");
//		text = TextureManager.loadTexture2D("materials/PrinceGirlFinalblend.png");
//		princess.scale(.5f,.5f, .5f);
//		princess.setTexture(text);
//		Matrix3D p2 = princess.getLocalTranslation();
//		p2.translate(3, 0, 3);
//		
//		princess.setLocalTranslation(terrainLocation(princess));
//		//rootNode.addChild(princess);
	}
//	private Matrix3D terrainLocation(TriMesh avatar){
//		Point3D avLoc = new Point3D(avatar.getLocalTranslation().getCol(3));
//		float terHeight = hillTerrain.getHeight((float)avLoc.getX(), (float)avLoc.getZ());
//		Matrix3D m = hillTerrain.getLocalTranslation();
//		double ptY = m.getCol(3).getY()+terHeight;
//		Matrix3D dPos = avatar.getLocalTranslation(); 
//		dPos.translate(dPos.getCol(3).getX(), ptY, dPos.getCol(3).getZ());
//		
//		return dPos; 
//	}

	protected void shutdown(){
		super.shutdown();
		if(thisClient != null){
			thisClient.sendByeMessage();
			try{
				thisClient.shutdown();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	public void setIsConnected(boolean b) {
			
	}
	public Vector3D getPlayerPosition() {
		// TODO Auto-generated method stub
		return new Vector3D(4,5,3);
	}
	public void createSagePhysicsWorld(){ 
	 float mass = 1.0f;
	 ballP = physicsEngine.addSphereObject(physicsEngine.nextUID(), mass, mesh.getWorldTransform().getValues(),1.0f);
	 ballP.setBounciness(1.0f);
	 mesh.setPhysicsObject(ballP);
	 // add the ground plane physics
	 float up[] = {-0.05f, 0.95f, 0}; // {0,1,0} is flat
	 groundPlaneP =
	 physicsEngine.addStaticPlaneObject(physicsEngine.nextUID(),
	 hillTerrain.getWorldTransform().getValues(), up, 0.0f);
	 groundPlaneP.setBounciness(1.0f);
	 hillTerrain.setPhysicsObject(groundPlaneP);
	
	 }
	 protected void initPhysicsSystem(){ 
		// String engine = "sage.physics.JBullet.JBulletPhysicsEngine";
		// physicsEngine = PhysicsEngineFactory.createPhysicsEngine(engine);
		// physicsEngine.initSystem();
		// float[] gravity = {0, -1f, 0};
		 //physicsEngine.setGravity(gravity);
	 }
	void inputManager() {
		im = getInputManager();
		gp = im.getKeyboardName();
		//gp = im.getFirstGamepadName();

				ForwardAvaAction avaFor = new ForwardAvaAction(mesh, setSpeed);
				LeftwardAvaAction avaLeft = new LeftwardAvaAction(mesh, setSpeed );
				
				new QuitGameAction(this); 
				im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.RY, avaFor, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(gp, net.java.games.input.Component.Identifier.Axis.RX, avaLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
			
	}

}
