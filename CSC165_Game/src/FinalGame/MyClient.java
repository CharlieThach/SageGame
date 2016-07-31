package FinalGame;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID;
import java.util.Vector;

import graphicslib3D.Vector3D;
import sage.networking.client.GameConnectionClient;

public class MyClient extends GameConnectionClient {
private MyGame game;
private UUID id;
private Vector<Avatar> avatar; 
private Vector3D ghostPosition;
	public MyClient(InetAddress remoteAddr, int remotePort, InetAddress localAddr, int localPort,
			ProtocolType protocolType, MyGame game) throws IOException {
		super(remoteAddr, remotePort, localAddr, localPort, protocolType);
		this.game = game;
		this.id = UUID.randomUUID();
		this.avatar = new Vector<Avatar>(); 
		
	}
	public void processPacket (Object msg){
		String message = (String)msg;
		String [] messageTokens = message.split(",");
		System.out.println(Arrays.toString(messageTokens));
		if(messageTokens[0].compareTo("join") == 0) // receive “join”
		 { // format: join, success or join, failure
			if(messageTokens[1].compareTo("success") == 0){ 
				System.out.println("Game Join Successfully");
				game.setIsConnected(true);
				sendCreateMessage(game.getPlayerPosition());
			}
			if(messageTokens[1].compareTo("failure") == 0){
				System.out.println("Game Join Unsuccessfully");
				game.setIsConnected(false);
			}
		 }
		
		//recieve message bye
		 if(messageTokens[0].compareTo("bye") == 0){ // format: bye, remoteId
			 	UUID ghostID = UUID.fromString(messageTokens[1]);
			 	removeGhostAvatar(ghostID);
		 	}
		 if (messageTokens[0].compareTo("dsfr") == 0 ){ 
			 // receive “details for”
			 // format: create, remoteId, x,y,z or dsfr, remoteId, x,y,z
			 UUID ghostID = UUID.fromString(messageTokens[1]);
		 
			 // extract ghost x,y,z, position from message, then:
			 createGhostAvatar(ghostID, ghostPosition);
		 }
		 
		 if(messageTokens[0].compareTo("wsds") == 0) // receive “create…”
		 { // etc….. }
		 if(messageTokens[0].compareTo("wsds") == 0) // receive “wants…”
		 { // etc….. }
		 if(messageTokens[0].compareTo("move") == 0) // receive “move”
		 { // etc….. }
		 }
		 }
		 }
	}
	private void createGhostAvatar(UUID ghostID, Object ghostPosition2) {
		// TODO Auto-generated method stub
		
	}
	private void sendCreateMessage(Vector3D object) {
		 try
		 { String message = new String("create," + id.toString());
		 	message += "," + object.getX()+"," + object.getY() + "," + object.getZ();
		 	sendPacket(message);
		 }
		 catch (IOException e) { e.printStackTrace(); }
		}
	private void removeGhostAvatar(UUID ghostID) {
		// TODO Auto-generated method stub
		
	}
	public void sendJoinMessage()
	{ // format: join, localId
	 try
	 { sendPacket(new String("join," + id.toString())); }
	 catch (IOException e) { 
		 e.printStackTrace(); 
		 }
	}
	public void sendByeMessage() {
		// TODO Auto-generated method stub
		
	}

		
}
