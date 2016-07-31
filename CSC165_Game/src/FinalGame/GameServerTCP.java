package FinalGame;

import sage.networking.server.GameConnectionServer;
import sage.networking.server.IClientInfo; 
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID; 

public class GameServerTCP extends GameConnectionServer<UUID>{

	public GameServerTCP(int localPort, ProtocolType protocolType) throws IOException {
		super(localPort, protocolType);
		// TODO Auto-generated constructor stub
	}
	public void acceptClient(IClientInfo ci, Object o){
		String message = (String)o; 
		String [] messageTokens = message.split(",");
		
		if(messageTokens.length >0){
			if(messageTokens[0].compareTo("join")==0){
				UUID clientID = UUID.fromString(messageTokens[1]);
				addClient(ci, clientID);
				sendJoinedMessage(clientID, true);
			}
		}
	}
	
	public void processPacket(Object o, InetAddress senderIP, int sndPort){
		
		String message = (String)o;
		String[] msgTokens = message.split(",");
		
		System.out.println(Arrays.toString(msgTokens));
		if(msgTokens.length>0){
			if(msgTokens[0].compareTo("bye")==0){
				UUID clientID = UUID.fromString(msgTokens[1]);
				sendByeMessage(clientID);
				removeClient(clientID);
				
			}
			if(msgTokens[0].compareTo("create")==0){
				UUID clientID = UUID.fromString(msgTokens[1]);
				String[]pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
				sendCreateMessage(clientID, pos);
				sendWantsDetailsMessage(clientID);
				}
			}
		}
	private void sendWantsDetailsMessage(UUID clientID) {
		System.out.println("Client ID is:" +clientID);
		
	}

	private void sendCreateMessage(UUID clientID, String[] pos) {
		try{
		String message = new String("create," + clientID.toString());
		message +=","+pos[0];
		message +=","+pos[1];
		message +=","+pos[2];
		forwardPacketToAll(message, clientID);
		sendPacketToAll(message);
		}catch(IOException e){
			e.printStackTrace();
		
	}
	}
	private void sendByeMessage(UUID clientID) {
		// TODO Auto-generated method stub
		
	}

	private void sendJoinedMessage(UUID clientID, boolean b) {
		try{
			String message = new String("join,");
			if(b) 
				message += "success";
			else
				message += "failure";
			sendPacket(message, clientID);
	}catch (IOException e){
			e.printStackTrace(); 
		}
	}
}
