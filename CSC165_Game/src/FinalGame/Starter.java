package FinalGame;

import java.util.Scanner;

public class Starter {
	
	public static void main(String [] args){
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter ip address: ");
		String ip = scanner.nextLine(); 
				
		System.out.println("You are connecting to "+ ip);
		MyGame game = new MyGame(ip, 4040); 
		game.start();
	}
}
