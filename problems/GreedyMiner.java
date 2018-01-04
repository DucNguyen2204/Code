package problems;

import java.util.Scanner;

public class GreedyMiner {

	public static int getMin(int a, int b, int c){
		int min = a;
		if(b<a && b < c) min = b;
		if(c<b && c < a) min = c;
		return min;
	}

	public static void main (String[] args){
		int counter = 0;
		Scanner scnr =new Scanner(System.in);
		int sum = 0;
		int length = 0;
		String line = "";
		int min = 0;
		while(true){
			counter++;
			for(int i = 0; i < 4;i++){
				line = scnr.nextLine();
				if(line.compareTo("0") == 0) break;
				if(i==0)
					length = Integer.parseInt(line);
				else{
					String[] split = line.split(" ");
					int[] number = new int[length]; 
					for(int a = 0; a < split.length ; a++){
						number[a] = Integer.parseInt(split[a]);
					}
					for(int j = 0; j < length;j=j+3){
						if(length < 3) sum = sum + number[j] + number[j+1];
						if(length%3 == 0){
							min = getMin(number[j],number[j+1],number[j+2]);
							sum = sum + (number[j] + number[j+1] + number[j+2] - min);
						}
					}
				}
			}
			if(line.compareTo("0") == 0) break;
			System.out.println("Case " + counter + ": " + sum);
			sum = 0;
		}
	}
}


