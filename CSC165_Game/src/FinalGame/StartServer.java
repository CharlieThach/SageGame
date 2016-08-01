package FinalGame;

import java.io.IOException;

import sage.networking.IGameConnection.ProtocolType;

public class StartServer {

	@SuppressWarnings("unused")
	public static void main(String [] args){
		System.out.println("Starting Game Server...");
		try {
			GameServerTCP server = new GameServerTCP(4040, ProtocolType.TCP);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
