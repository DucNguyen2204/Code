package problems;

import java.util.Scanner;

public class CFact {
	public static void main (String[] args){
		Scanner scnr = new Scanner(System.in);
		int a = 0;
		int factorial = 1;
		int counter = 1;
		while(true){
			a = scnr.nextInt();
			if(a==0) break;
			
			for(int i = a; i > 0; i--){
				factorial = factorial*i;
			}

			System.out.println("Case " + counter + ": " + factorial);
			counter++;
			factorial = 1;
		}
	}
}
