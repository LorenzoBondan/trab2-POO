package movieticket.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {

	public static int readInt(String pergunta) {
		Scanner in = new Scanner(System.in);
		int var = 0;
		boolean leu = false;
		do {
			try {
				System.out.print(pergunta);
				var = in.nextInt();
				leu = true;
			}catch(InputMismatchException e) {
			}
			in.nextLine();
		}while(!leu);
		return var;
	}
	
	public static Long readLong(String pergunta) {
		Scanner in = new Scanner(System.in);
		Long var = 0L;
		boolean read = false;
		do {
			try {
				System.out.print(pergunta);
				var = in.nextLong();
				read = true;
			}catch(InputMismatchException e) {
			}
			in.nextLine();
		}while(!read);
		return var;
	}

	public static String readString(String pergunta) {
		Scanner in = new Scanner(System.in);
		String var = "";
		boolean read = false;
		do {
			try {
				System.out.print(pergunta);
				var = in.nextLine();
				read = true;
			}catch(InputMismatchException e) {
			}
			
		}while(!read);
		return var;
	}

	public static double readDouble(String pergunta) {
		Scanner in = new Scanner(System.in);
		double var = 0;
		boolean read = false;

		do {
			try {
				System.out.print(pergunta);
				var = in.nextDouble();
				read = true;
			}catch(InputMismatchException e) {

			}
			in.nextLine();

		}while(!read);
		return var;
	}
	
	public static void waitFor() {
		Scanner in = new Scanner(System.in);
		System.out.println("Aperte qualquer tecla para continuar...");
		in.nextLine();
	}
}