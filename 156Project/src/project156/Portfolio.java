package project156;
import java.util.List;

public class Portfolio {

	private String ownerName;
	private String managerName;
	private String pCode;
	private String bName;
	private String type;
	private List<String> aCode;
	private List<String> label;
	private List<Double> roReturn;
	private List<Double> risk;
	private List<Double> annualReturn;
	private List<Double> value;

	public Portfolio(String ownerName, String managerName, String pCode, String bName, List<String> aCode, List<String> label,
			List<Double> roReturn, List<Double> risk, List<Double> annualReturn, List<Double> value, String type) {
		this.ownerName = ownerName;
		this.managerName = managerName;
		this.pCode = pCode;
		this.bName = bName;
		this.aCode = aCode;
		this.label = label;
		this.roReturn = roReturn;
		this.risk = risk;
		this.annualReturn = annualReturn;
		this.value = value;
		this.type = type;
	}


	public String getType() {
		return type;
	}


	public String getbName() {
		return this.bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getpCode() {
		return this.pCode;
	}

	public void setCode(String code) {
		this.pCode = code;
	}
	
	
	public List<String> getaCode() {
		return aCode;
	}



	public void setaCode(List<String> aCode) {
		this.aCode = aCode;
	}



	public List<String> getLabel() {
		return label;
	}



	public void setLabel(List<String> label) {
		this.label = label;
	}

	
	public List<Double> getRoReturn() {
		return roReturn;
	}



	public void setRoReturn(List<Double> roReturn) {
		this.roReturn = roReturn;
	}



	public List<Double> getRisk() {
		return risk;
	}



	public void setRisk(List<Double> risk) {
		this.risk = risk;
	}



	public List<Double> getAnnualReturn() {
		return annualReturn;
	}



	public void setAnnualReturn(List<Double> annualReturn) {
		this.annualReturn = annualReturn;
	}



	public List<Double> getValue() {
		return value;
	}



	public void setValue(List<Double> value) {
		this.value = value;
	}



	public double getTotalValue(){
		double total = 0.0;
		for(int i = 0; i < this.value.size(); i++){
			total += this.value.get(i);
		}
		return total;
	}
	public double getAReturn(){
		double aReturn= 0.0;
		for(int i = 0;i < this.annualReturn.size(); i++){
			aReturn += this.annualReturn.get(i);
		}
		return aReturn;
	}
	
	public double aggregateRiskCal(List<Asset> asset){
		double risk = 0.0;
		for(Asset b : asset){
			for(int i = 0; i < this.getaCode().size(); i++){
				if(this.getaCode().get(i).equals(b.getAssetCode())){
					if(b.getType().equalsIgnoreCase("s")){
						risk += ((Stock)b).getBetaMeasure() * (this.getValue().get(i)/this.getTotalValue());
					}
					if(b.getType().equalsIgnoreCase("p")){
						risk = risk + ((Private)b).calOmega() * (this.getValue().get(i)/this.getTotalValue()); 
						
					}else if(b.getType().equalsIgnoreCase("d")){
						risk += 0.0;
					}
				}
			}
		}
		return risk;
	}
	
	public double calFee(List<Person> person){
		double fee = 0;
		for(int i = 0; i < this.getaCode().size(); i++){
			for(Person p : person){
				String name = p.getLastName() + ", " + p.getFirstName();
				if(this.getManagerName().equals(name) && p.getType().equalsIgnoreCase("j")){
					fee = fee + 50;
				}else if (this.getManagerName().equals(name) && p.getType().equalsIgnoreCase("e")){
					fee = fee + 10;
				}
			}
		}
		
		return fee;
	}


	@Override
	public String toString() {
		return "Portfolio [ownerName=" + ownerName + ", managerName=" + managerName + ", pCode=" + pCode + ", bName="
				+ bName + ", type=" + type + ", aCode=" + aCode + ", label=" + label + ", roReturn=" + roReturn
				+ ", risk=" + risk + ", annualReturn=" + annualReturn + ", value=" + value + "]";
	}

	
	
}
