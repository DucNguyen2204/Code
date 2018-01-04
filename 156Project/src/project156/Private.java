package project156;
public class Private extends Asset{
		private double quarterlyDivident = 0.0;
		private double baseRateOfReturn = 0.0;
		private double omegaMeasure =0.0;
		private double totalValue = 0.0;
		private double annualReturn = 0.0;
		private double rateOfReturn = 0.0;
		private double value = 0.0;
		private double o = 0.0;
		private double omega = 0.0;
	public Private(String assetCode, String type, String label, double quarterlyDivident, double baseRateOfReturn,
			double omegaMeasure, double totalValue) {
		super(assetCode, type, label);
		this.quarterlyDivident = quarterlyDivident;
		this.baseRateOfReturn = baseRateOfReturn;
		this.omegaMeasure = omegaMeasure;
		this.totalValue = totalValue;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public double getQuarterlyDivident() {
		return quarterlyDivident;
	}



	public void setQuarterlyDivident(double quarterlyDivident) {
		this.quarterlyDivident = quarterlyDivident;
	}



	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}



	public void setBaseRateOfReturn(double baseRateOfReturn) {
		this.baseRateOfReturn = baseRateOfReturn;
	}



	public double getOmegaMeasure() {
		return omegaMeasure;
	}



	public void setOmegaMeasure(double omegaMeasure) {
		this.omegaMeasure = omegaMeasure;
	}



	public double getTotalValue() {
		return totalValue;
	}



	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}



	public double calValue(double stake){
		this.value = (totalValue/100) * stake;
		return this.value;
	}
	public double calAnnualReturn(double stake){
		this.annualReturn = baseRateOfReturn * totalValue + 4*quarterlyDivident;
		this.annualReturn = (this.annualReturn / 100)*stake;
		
		return this.annualReturn;
	}
	public double calRateOfReturn (double stake){
		
		this.rateOfReturn = (this.calAnnualReturn(stake)/this.calValue(stake))*100;
		return rateOfReturn;
	}	
	public double calOmega(){
		this.o = Math.exp(-100000/this.totalValue);
		this.omega = this.omegaMeasure + o;
		return this.omega;
	}



	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "P";
	}
}
