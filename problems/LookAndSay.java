package problems;

import java.util.Scanner;

public class LookAndSay {


	public static String calculateNumber(String input){
		Character[] array = new Character[] {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
		int counter=0;
		String result = "";
		for(int i = 0; i < input.length();i++){
			if(i > 0){
				if(input.charAt(i) != input.charAt(i-1)){
					result = result + Integer.toString(counter) + input.charAt(i-1);
					counter = 0;
				}
			}
			for(int j = 0; j < array.length;j++){
				if(input.charAt(i) == array[j]){
					counter++;
				}
			}
		}
		result = result + Integer.toString(counter)+input.charAt(input.length()-1);
		return result;
	}
	public static void main (String[] args){

		Scanner scnr = new Scanner(System.in);
		String input = "";
		input = scnr.nextLine();
		String output1 = calculateNumber(input);
		String output2 = calculateNumber(output1);

		System.out.println(output1);
		System.out.println(output2);

	}
}
