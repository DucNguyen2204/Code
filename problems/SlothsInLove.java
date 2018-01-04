package problems;

import java.util.Scanner;

public class SlothsInLove {

	public static double CalculateDistance2(int x, int y, int x1, int x2, int y1, int y2 ){
		double Branch1Distance = 0;
		double distance = 0;
		double Branch2Distance = 0;
		if(x1 != x){
			double ySquare = Math.pow((y1- y),2);
			double xSquare = Math.pow((x1 - x), 2);
			Branch1Distance = Math.sqrt(xSquare + ySquare);
		}else {
			Branch1Distance = y1-y;
		}
		if(x2 != x1 && y2 != y1){
			double ySquare = Math.pow((y2- y1),2);
			double xSquare = Math.pow((x2 - x1), 2);
			Branch2Distance = Math.sqrt(xSquare + ySquare);
		}else {
			Branch2Distance = y2-y1;
		}
		
		distance = Branch2Distance + Branch1Distance;
		return distance;
	}

	public static void main (String[] args){
		Scanner scnr = new Scanner(System.in);
		int a = 0;
		int b = 0;
		int x1distance = 0;
		int x2distance = 0;
		double ydistance = 0;
		double distance = 0;
		int nCase = 0;
		for(int i = 0; i < 2; i++){
			int branches1 = scnr.nextInt();
			int xGround = scnr.nextInt();
			if(b > xGround)  x2distance = b - xGround; 
			else if (xGround > b) x2distance = xGround - b;
			b = xGround;
			int yGround = scnr.nextInt();
			int x1Branch = scnr.nextInt();
			int y1Branch = scnr.nextInt();
			int x2Branch = scnr.nextInt();
			if(a > x2Branch)  x1distance = a - x2Branch; 
			else if (x2Branch > a) x1distance = x2Branch - a;
			int y2Branch = scnr.nextInt();
			a = x2Branch;
			distance = distance + y2Branch;
			ydistance = ydistance + CalculateDistance2(xGround, yGround, x1Branch, x2Branch, y1Branch, y2Branch);
		}
		while (nCase < 5){
			nCase++;
			switch (nCase){
			case 1 : distance = distance + x1distance;
			System.out.println(distance);
			distance = 0;
			break;
			case 2 : distance = x2distance + ydistance;
			System.out.println(distance);
			}
		}
	}
}
