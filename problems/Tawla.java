package problems;

import java.util.ArrayList;
import java.util.Scanner;

public class Tawla {
	public static void main (String[]args){
		Scanner scnr = new Scanner(System.in);
		String[] arr = {"Yakk", "Doh", "Seh", "Ghar", "Bang", "Sheesh"};
		int numberOfCase = scnr.nextInt();
		for(int z = 0; z < numberOfCase;z++){
			int a = scnr.nextInt();
			int b = scnr.nextInt();
			String firstNumber = "";
			String secondNumber = "";
			String result = "";
			
			for(int i = 0; i < arr.length;i++){
				if(a == i){
					firstNumber = arr[i-1];
				}
				if(b == i){
					secondNumber = arr[i-1];
				}
			}
			
			if(a > b) result = firstNumber + " " + secondNumber;
			else if (b > a ) result = secondNumber + " " + firstNumber;
			
			if((a==5&&b==6)||(a==6&&b==5)){
				result = "Sheesh Beesh";
			}
			

			if(a==b & a==1) result = "Habb Yakk";
			if(a==b & a==2) result = "Dobara";
			if(a==b & a==3) result = "Dousa";
			if(a==b & a==4) result = "Dorgy";
			if(a==b & a==5) result = "Dabash";
			if(a==b & a==6) result = "Dosh";

			System.out.println(result);
		}
	}
}

