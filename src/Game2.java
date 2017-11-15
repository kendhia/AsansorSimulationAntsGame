import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.text.Position;

public class Game2 {
	private static List<Object> yol = new ArrayList<>(10);
	private static List<Karinca> karincalar;
	private static Random random = new Random();
	private static int numOfKarincalar = 5;
	private static Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
	
		initiateOurGame(1);
		System.out.println();
		System.out.println("The second game (with 2 lifes for each Karinca) is about to start (press any button)");
		scanner.nextLine();
		initiateOurGame(2);
		
		System.out.println();
		System.out.println("Design your own game (press any button to start)");
		scanner.nextLine();
		initiateTheGame();
	}
	
	private static void initiateOurGame (int numOfLifes ) {
		if (yol.size() > 0) {
			yol.clear();
			numOfKarincalar = 5;
		}
		for (int i =0; i < 10; i ++) {
			
			yol.add(new Object());
		}
		
		karincalar = new ArrayList();
		for (int i =0; i < 5; i++) {
			Karinca karinca = new Karinca(generateRandomName());
			karinca.setLife(numOfLifes);
			karincalar.add(karinca);
		}
		System.out.print("The start is : ");
		printArray();
		
		
		designGame();
		playOurGame();
		
		System.out.println();
		System.out.print("The end is : ");
		printArray();
	}
	
	private static void initiateTheGame() {
		yol.clear();
		System.out.print("How many karinca are there ? ");
		while(!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Please enter a vaild number : ");
		}
		numOfKarincalar = scanner.nextInt();
		karincalar.clear();;
		for (int i =0; i < numOfKarincalar; i++) {
			Karinca karinca = new Karinca(generateRandomName());
			karincalar.add(karinca);
		}
		
		System.out.print("Please enter the length of the game ? ");
		while(!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Please enter a vaild number : ");
		}
		int lenOfTheGame = scanner.nextInt();
		for (int i =0; i < lenOfTheGame; i ++) {
			yol.add(new Object());
		}
		
		
		//[Adding the Cukurlar ]
		System.out.print("How many cukur are there ? ");
		while(!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Please enter a vaild number : ");
		}
		
		int numOfCukur = scanner.nextInt();
		for (int i =0; i < numOfCukur; i++) {
			System.out.format("What's the length of the %d. cukur ? ", i+1);
			while(!scanner.hasNextInt()) {
				scanner.nextLine();
				System.out.print("Please enter a vaild number : ");
			}
			int lenOfCukur = scanner.nextInt();
			
			int posOfCukur;
			System.out.format("What's the position of the %d. cukur ? ", i+1);
			while(!scanner.hasNextInt() || (posOfCukur = scanner.nextInt()) > lenOfTheGame) {
				scanner.nextLine();
				System.out.print("Please enter a vaild number : ");
			}
			
			Stack<Object> cukur = new Cukur(lenOfCukur);
			yol.set(posOfCukur-1, cukur);
		}
		//[End]
		
		
		//[Adding the Tuzaklar]
		System.out.print("How many tuzak are there ? ");
		while(!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Please enter a vaild number : ");
		}
		
		int numOfTuzak = scanner.nextInt();
		for (int i =0; i < numOfTuzak; i++) {
			int posOfTuzak;
			System.out.format("What's the position of the %d. tuzak ? ", i+1);
			while(!scanner.hasNextInt() || (posOfTuzak = scanner.nextInt()) > lenOfTheGame) {
				scanner.nextLine();
				System.out.print("Please enter a vaild number : ");
			}
			
			Stack<Object> tuzak = new Tuzak(1);
			yol.set(posOfTuzak-1, tuzak);
		}
		//[End]
		
		playOurGame();
	}
	
	
	private static void designGame() {
		Stack<Object> cukur = new Cukur(3);
		yol.set(2,  cukur);
		cukur = new Cukur(2);
		yol.set(4,  cukur);
		cukur = new Cukur(4);
		yol.set(8,  cukur);
		Stack<Object> tuzak = new Tuzak(1);
		yol.set(6, tuzak);
	}

	private static void playOurGame() {
		
		for (int i =0; i < yol.size(); i++) {
			printArray();
			Object obj = yol.get(i);
			
			//If the current position is a curuk
			if (obj instanceof Cukur) {
				obj = (Cukur) obj;
				
				//Karincalar are entreing the cukur.
				int uzun = ((Cukur) obj).getUzun();
				for (int j =0; j < uzun; j++) {
					((Cukur) obj).add(karincalar.get(j));
				}
				
				//The other Karincalar will pass the cukur
				int pos = 0;
				for (int j = uzun; j < numOfKarincalar; j++){
					karincalar.set(pos, karincalar.get(j));
					pos++;
				}
				
				//the other Karincalar will get out of the cukur now 
				int len = numOfKarincalar - uzun;
				for (int j = len; j < numOfKarincalar; j++) {
					karincalar.set(j, (Karinca) ((Cukur) obj).pop());
				}
			}
			
			//if the current position is a tuzak
			else if (obj instanceof Tuzak) {
				obj = (Tuzak) obj;
				
				//Karincalar entering the tuzak.
				int uzun = ((Tuzak) obj).getUzun();
				for (int j = 0; j < uzun; j++) {
					((Tuzak) obj).add(karincalar.get(j));
				}
				//The other Karincalar will pass the tuzak
				int pos = 0;
				for (int j = uzun; j < numOfKarincalar; j++){
					karincalar.set(pos, karincalar.get(j));
					pos++;
				}
				
				//The other Karincalar that has lives less then 2 will die.
				int len = numOfKarincalar - uzun;
				for (int j = len; j < numOfKarincalar; j++) {
					Karinca karinca = (Karinca) ((Tuzak) obj).pop();
					if (karinca.getLife() > 1) {
						karinca.setLife(karinca.getLife()-1);
						karincalar.set(j, karinca);
					}else {
						numOfKarincalar--;
					}
				}
			}
		}
		
		
	}
	private static String generateRandomName() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		final int N = 2;
		String randomName="";
		for (int i = 0; i < N; i++) {
			randomName += alphabet.charAt(random.nextInt(alphabet.length()));
		}
		return randomName;
	}

	private static void printArray() {
		for (int i = 0; i< numOfKarincalar; i++) {
			System.out.print(karincalar.get(i).getName() + "|");
		}
		System.out.println();
	}
}
