import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.text.Position;

public class Game3 {
	private static List<Object> yol = new ArrayList<>(10);
	private static List<Karinca> karincalar;
	private static List<Ari> arilar;
	private static Random random = new Random();
	private static int numOfKarincalar = 5;
	private static int numOfArilar = 3;
	private static Scanner scanner = new Scanner(System.in);
	private static String[] karincaAdlari  = {"karA", "karB", "karC", "karD", "karE"};
	private static String[] ariAdlari= {"ariA", "ariB","ariC"};


	public static void main(String[] args) {

		initiateOurGame();
		System.out.println();
	}

	private static void initiateOurGame () {
		if (yol.size() > 0) {
			yol.clear();
			numOfKarincalar = 5;
		}
		for (int i =0; i < 10; i ++) {

			yol.add(new Object());
		}

		karincalar = new ArrayList();
		for (int i =0; i < numOfKarincalar; i++) {
			Karinca karinca = new Karinca(karincaAdlari[i]);
			karinca.setLife(2);
			karincalar.add(karinca);
		}
		System.out.print("The start from right is : ");
		printArrayKarincalar();

		arilar = new ArrayList();
		for (int i =0; i < numOfArilar; i++) {
			Ari ari = new Ari(ariAdlari[i]);
			ari.setLife(3);
			arilar.add(ari);
		}
		System.out.print("The start from right is : ");
		printArrayArilar();


		designGame();
		playOurGame();

		System.out.println();
		System.out.print("The end is : ");
		printArrayKarincalar();
		printArrayArilar();
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

	private static void karincaMoves(Object obj) {
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
			for (int j = len; j < numOfKarincalar && j >= 0; j++) {
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


	private static void ariMoves(Object obj) {
		//If the current position is a curuk
		if (obj instanceof Cukur) {
			obj = (Cukur) obj;

			//Karincalar are entreing the cukur.
			int uzun = ((Cukur) obj).getUzun();
			for (int j =0; j < uzun && j < arilar.size(); j++) {
				((Cukur) obj).add(arilar.get(j));
			}

			//The other Karincalar will pass the cukur
			int pos = 0;
			for (int j = uzun; j < numOfArilar; j++){
				arilar.set(pos, arilar.get(j));
				pos++;
			}

			//the other Karincalar will get out of the cukur now 
			int len = numOfArilar - uzun;
			for (int j = len; j < numOfArilar && len >= 0; j++) {
				arilar.set(j, (Ari) ((Cukur) obj).pop());
			}
		}

		//if the current position is a tuzak
		else if (obj instanceof Tuzak) {
			obj = (Tuzak) obj;

			//Karincalar entering the tuzak.
			int uzun = ((Tuzak) obj).getUzun();
			for (int j = 0; j < uzun && j < arilar.size(); j++) {
				((Tuzak) obj).add(arilar.get(j));
			}
			//The other Karincalar will pass the tuzak
			int pos = 0;
			for (int j = uzun; j < arilar.size(); j++){
				arilar.set(pos, arilar.get(j));
				pos++;
			}

			//The other Karincalar that has lives less then 2 will die.
			int len = numOfArilar - uzun;
			for (int j = len; j < numOfArilar; j++) {
				Ari ari = (Ari) ((Tuzak) obj).pop();
				if (ari.getLife() > 1) {
					ari.setLife(ari.getLife()-1);
					arilar.set(j, ari);
				}else {
					numOfArilar--;
				}
			}
		}
	}

	private static void playOurGame() {

		int ariPos = 0;
		for (int karincaPos =0; karincaPos < yol.size(); karincaPos++) {
			printArrayKarincalar();
			printArrayArilar();
			ariPos = yol.size() - karincaPos -1;

			if (karincaPos +1 == ariPos) {
				startTheFight();
			}
			Object obj = yol.get(karincaPos);
			karincaMoves(obj);

			Object obj2 = yol.get(ariPos);
			ariMoves(obj2);
				
		}
	}

	
	private static void startTheFight() {
	
		System.out.println("start the fight!");
		while (numOfArilar != 0 && numOfKarincalar != 0) {
			printArrayKarincalar();
			printArrayArilar();
			//get the first Karinca and the first Ari
			Ari ari = arilar.get(numOfArilar-1);
			Karinca karinca = karincalar.get(0);
			
			if (ari.getLife() > karinca.getLife()) {
				ari.setLife(ari.getLife() - karinca.getLife());
				killAKarinca();
			}
			else if (ari.getLife() < karinca.getLife()) {
				karinca.setLife(karinca.getLife() - ari.getLife());
				killAnAri();
			} else {
				killAKarinca();
				killAnAri();
			}
			
			
			
		}
	}

	private static void killAKarinca() {
		for (int i = 0; i> karincalar.size()-1; i++) {
			karincalar.set(i, karincalar.get(i+1));
		}
		numOfKarincalar--;
	}
	
	private static void killAnAri() {
		for (int i = 0; i> arilar.size()-1; i++) {
			arilar.set(i, arilar.get(i+1));
		}
		numOfArilar--;
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

	private static void printArrayKarincalar() {
		for (int i = 0; i< numOfKarincalar; i++) {
			System.out.print(karincalar.get(i).getName() + "|" + karincalar.get(i).getLife() + "|");
		}
		System.out.println();
	}

	private static void printArrayArilar() {
		for (int i = 0; i< numOfArilar; i++) {
			System.out.print(arilar.get(i).getName() + "|" + arilar.get(i).getLife() + "|");
		}
		System.out.println();
	}
}
