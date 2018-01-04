package project156;
public class Deposit extends Asset{
		private double apr;
		private double APY = 0.0;
		private double annualReturn = 0.0;
	public Deposit(String assetCode, String type, String label, double apr) {
		super(assetCode, type, label);
		this.apr = apr;
		// TODO Auto-generated constructor stub
	}
	
	public double ReturnRate(double totalBalance){
		this.APY = (Math.exp(apr) - 1)*100;
		return this.APY;
	}
	
	public double calAnnualReturn(double totalBalance){
		this.annualReturn = (totalBalance/100)*this.ReturnRate(totalBalance);
		return this.annualReturn;
	}
	
	public void setApr(double apr){
		this.apr = apr;
	}
	
	public double getApr(){
		return this.apr;
	}

	@Override
	public String getType() {
		return "D";// TODO Auto-generated method stub
	}
}
