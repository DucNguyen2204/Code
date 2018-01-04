package project156;
public class Stock extends Asset {
	
	private double rateOfReturn = 0.0;
	private double value = 0.0;
	private double annualReturn = 0.0;
	private double risk = 0.0;
	private double quarterlyDivident = 0.0;
	private double baseRateOfReturn = 0.0;
	private double betaMeasure = 0.0;
	private String stockSymbol = "";
	private double sharePrice = 0.0;

		public Stock(String assetCode, String type, String label, double quarterlyDivident, double baseRateOfReturn,
			double betaMeasure, String stockSymbol, double sharePrice) {
		super(assetCode, type, label);
		this.quarterlyDivident = quarterlyDivident;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaMeasure = betaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
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

		public double getBetaMeasure() {
			return betaMeasure;
		}

		public void setBetaMeasure(double betaMeasure) {
			this.betaMeasure = betaMeasure;
		}

		public String getStockSymbol() {
			return stockSymbol;
		}

		public void setStockSymbol(String stockSymbol) {
			this.stockSymbol = stockSymbol;
		}

		public double getSharePrice() {
			return sharePrice;
		}

		public void setSharePrice(double sharePrice) {
			this.sharePrice = sharePrice;
		}

	
	public double calValue(double shareOwned){
		this.value = shareOwned * this.sharePrice;
		return this.value;
	}
	
	public double calAnnualReturn(double shareOwned){
		this.annualReturn = this.baseRateOfReturn * this.calValue(shareOwned) + 4*(quarterlyDivident*shareOwned);
		return this.annualReturn;
	}

	public double calRateOfReturn (double shareOwned){
		this.rateOfReturn = (this.calAnnualReturn(shareOwned)/this.calValue(shareOwned))*100;
		return rateOfReturn;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "S";
	}
}
