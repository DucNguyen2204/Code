package problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AB { 

	  public static String createString(int N, int K) { 
	    for (int cntB = 0; cntB <= N; cntB++) { 
	      char[] a = new char[N]; 
	      for (int i = 0; i < cntB; i++) { 
	        a[i] = 'B'; 
	      } 
	      for (int i = cntB; i < N; i++) { 
	        a[i] = 'A'; 
	      } 
	      int cur = 0; 
	      while (true) { 
	        if (cur == K) { 
	          return new String(a); 
	        } 
	        int pos = -1; 
	        for (int i = 0; i + 1 < N; i++) { 
	          if (a[i] == 'B' && a[i + 1] == 'A') { 
	            pos = i; 
	            break; 
	          } 
	        } 
	        if (pos == -1) { 
	          break; 
	        } 
	        cur++; 
	        a[pos] = 'A'; 
	        a[pos + 1] = 'B'; 
	      } 
	    } 
	    return ""; 
	  } 
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int k = scan.nextInt();
//		ArrayList<char[]> allPer = createString(n, k);
//		for(char[] c: allPer)
//		System.out.println(Arrays.toString(c));
		System.out.println(createString(n, k));
		
	}
}
