package project156;

public abstract class Asset {
	protected  String assetCode;
	protected  String type;
	protected  String label;
	
	
	public Asset(String assetCode, String type, String label) {
		this.assetCode = assetCode;
		this.type = type;
		this.label = label;
	}
	
	
	public void setAssetCode(String code){
		this.assetCode = code;
	}
	public String getAssetCode(){
		return this.assetCode;
	}
	
	
	public void setLabel(String label){
		this.label = label;
	}
	public String getLabel(){
		return this.label;
	}
	
	public abstract String getType();
}

