package project156;

import java.util.Comparator;

public class valueOrder implements Comparator<Portfolio> {

		@Override
		public int compare(Portfolio o1, Portfolio o2) {
			double value1 = 0.0;
			double value2 = 0.0;
			value1 = o1.getTotalValue();
			value2 = o2.getTotalValue();
			return this.compareValue(value1, value2);
		}
	
		public int compareValue(double value1, double value2){
			if(value1 > value2){
				return -1;
			}else if (value1 < value2){
				return 1;
			}
			return 0;
		}

}
