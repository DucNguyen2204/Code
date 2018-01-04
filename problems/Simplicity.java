package problems;

	import java.util.Scanner;
	
	public class Simplicity {
		public static void main (String[] args){
			Scanner scnr = new Scanner(System.in);
			while(true){
				String line = scnr.nextLine();
				if(line.equals("0")) break;
				int a=0;
				int simplicity = line.length();
				char sameChar = ' ';
				for(int i = 0; i < line.length()-1;i++){
					for(int j = i+1; j < line.length(); j++){
						if(line.charAt(i) == line.charAt(j)){
							simplicity--;
							sameChar = line.charAt(i);
							break;
						}
						
					}
				}
				if(simplicity > 2){
					a = simplicity - 2;
				}else if (simplicity == 1){
					a = 0;
				}
				System.out.println(a);
			}
		}
	}
