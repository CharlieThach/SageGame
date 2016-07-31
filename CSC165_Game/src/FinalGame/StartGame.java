package FinalGame;

public class StartGame {

	public static void main(String [] arg){
		MyGame game = new MyGame("192.168.1.4", 4040); 
		game.start();
	}
}
