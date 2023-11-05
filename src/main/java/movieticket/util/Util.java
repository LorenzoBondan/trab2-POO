package movieticket.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
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
	
	 public static Date readDate(String pergunta) {
	        Scanner in = new Scanner(System.in);
	        Date date = null;
	        boolean read = false;
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	        do {
	            try {
	                System.out.print(pergunta);
	                String input = in.nextLine();
	                date = dateFormat.parse(input);
	                read = true;
	            } catch (ParseException | InputMismatchException e) {
	                System.out.println("Formato de data inválido. Digite no formato dd/MM/yyyy.");
	                in.nextLine();
	            }
	        } while (!read);
	        return date;
	    }
	 
	 public static LocalTime readTime(String pergunta) {
	        Scanner in = new Scanner(System.in);
	        LocalTime time = null;
	        boolean read = false;
	        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	        do {
	            try {
	                System.out.print(pergunta);
	                String input = in.nextLine();
	                time = LocalTime.parse(input, timeFormatter);
	                read = true;
	            } catch (DateTimeParseException | InputMismatchException e) {
	                System.out.println("Formato de hora inválido. Digite no formato HH:mm.");
	                in.nextLine(); 
	            }
	        } while (!read);
	        return time;
	    }
	
	public static void waitFor() {
		Scanner in = new Scanner(System.in);
		System.out.println("Aperte qualquer tecla para continuar...");
		in.nextLine();
	}
}
