package hu.nye.pandragon.wumpus;

public class Main {


	public static void main(String[] args) {
		System.out.println("Hello world!");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 30; j++) {
				System.out.print("o");
			}
			System.out.println();
		}
		new GameMain();
	}
}