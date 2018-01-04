package project156;

import java.util.Comparator;

public class NameOrder implements Comparator<Portfolio> {
	
	@Override
	public int compare(Portfolio p, Portfolio p2) {	
		String name1[] = p.getOwnerName().split(",");
		String lastName1 = name1[0];
		String firstName1 = name1[1];
		firstName1 = firstName1.trim();
		String name2[] = p2.getOwnerName().split(",");
		String lastName2 = name2[0];
		String firstName2 = name2[1];
		firstName2 = firstName2.trim();
		
		if(lastName1.compareTo(lastName2)>0){
			return 1;
		}else if(lastName1.compareTo(lastName2)<0){
			return -1;
		}else{
			if(firstName1.compareTo(firstName2)>0){
				return 1;
			}else if(firstName1.compareTo(firstName2)<0){
				return -1;
			}else{
				return 0;
			}
		}
	}
}
