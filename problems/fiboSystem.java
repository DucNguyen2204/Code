package problems;

import java.util.Scanner;

public class fiboSystem {
	public static void main (String[] args){
		Scanner scnr = new Scanner(System.in);
		int a = 0;
		int b = 1;
		int c= 0;
		int display = 0;
		int counter = 1;
		String line = "";
		while (true){
			line = scnr.nextLine();
			if(line.equals("0")) break;
			for(int i = 0; i < line.length(); i++){
				if(line.charAt(i) == '0'){
					c = 0;
					continue;
				}
				for(int j = 0; j < line.length()-i ;j++){
					if(j<1){
						c = 1;
					}else{
						c = a + b;
						a = b;
						b = c;
					}
				}
				if(line.charAt(i) == '1'){
					display = display + c ;
				}
				c=0;
				a=0;
				b=1;
			}
			System.out.println("Case " + counter + ": " + display);
			counter++;
			display = 0;
		}

	}
}
