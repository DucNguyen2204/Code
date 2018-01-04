package problems;

import java.util.Scanner;

public class intriNumber {
	public static int calculateNumber(int number){
		int sum = 0;
		for(int i = 0; i <= number; i++){
			sum = sum + i;
		}
		return sum;
	}

	public static void main (String[] args){
		Scanner scnr = new Scanner(System.in);
		while(true){
			int a = scnr.nextInt();
			if(a == -1) break;
			int bingo = 0;
			for(int i = a ; i > 0; i--){
				if(calculateNumber(i) == a){
					bingo++;
					System.out.println(i);
				}
			}

			if(bingo == 0)
				System.out.println("bad");
		}
	}
}
