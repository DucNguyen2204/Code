package project156;

import java.util.Comparator;

public class typeOrder implements Comparator<Portfolio>{

	@Override
	public int compare(Portfolio o1, Portfolio  o2) {
		String type1 = o1.getType();
		String type2 = o2.getType();
		if(type1.compareTo(type2) < 0){
			return -1;
		}else if (type1.compareTo(type2) > 0){
			return 1;
		}else{
			managerNameOrder mno = new managerNameOrder();
			return mno.compare(o1, o2);

		}
	}
}
