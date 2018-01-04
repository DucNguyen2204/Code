package project156;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.sdb.OutOfScaleException;
import com.sdb.PortfolioData;

public class Demo {
	private static List<Person> loadPersons(){
		List<Person> person = new ArrayList<Person>();//Array list to store persons
		List<String> email = null;//list store emails
		int personId = 0;

		createConnection c = new createConnection();//create a new object to get connection
		Connection conn = c.getConnection();

		String query = "SELECT p.personId, p.firstName, p.lastName, p.type, a.street, a.city, s.state, a.zipcode, c.country FROM Person p"
				+ " JOIN personAddress b ON b.personId = p.personId"
				+ " JOIN Address a ON a.addressID = b.addressId"
				+ " JOIN State s ON s.stateId = a.stateId"
				+ " JOIN Country c ON c.countryId = a.countryId ORDER BY p.personId"; //query to get major fields from persons except for the emails

		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				email = new ArrayList<String>();
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String type = rs.getString("type");
				personId = rs.getInt("personId");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zipCode = rs.getString("zipcode");
				String country  = rs.getString("country");
				Address a = new Address (street, city, state, country, zipCode);
				Person p = new Person(type, firstName, lastName, a, personId);


				query = "SELECT e.email, p.personId FROM Person p JOIN Email e ON p.personId = e.personId WHERE  p.personId = ?"; //query to get emails of a person that matches the personId
				ps = conn.prepareStatement(query);
				ps.setInt(1, personId);
				rs1 = ps.executeQuery();
				while(rs1.next()){
					String e = rs1.getString("email");
					email.add(e);
					p.setEmailAddress(email);
				}
				person.add(p);
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.closeConnection(conn);

		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		return person;
	}

	private static List<Asset> loadAssets(){

		List<Asset> asset = new ArrayList<Asset>();//list to store assets
		Asset a = null;

		createConnection c = new createConnection();//create a new object to get connection
		Connection conn = c.getConnection();
		String query = "SELECT * FROM Asset"; //query to get all the assets

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while(rs.next()){
				if(rs.getString("type").equalsIgnoreCase("d")){
					a = new Deposit(rs.getString("assetCode"), rs.getString("type"), rs.getString("label"), rs.getDouble("apr"));
				}else if (rs.getString("type").equalsIgnoreCase("s")){
					a =  new Stock(rs.getString("assetCode"), rs.getString("type"),rs.getString("label"), rs.getDouble("quarterlyDivident"),
							rs.getDouble("baseRateOfReturn"), rs.getDouble("beta"),rs.getString("stockSymbol"),rs.getDouble("sharePrice"));
				}else if (rs.getString("type").equalsIgnoreCase("p")){
					a =  new Private(rs.getString("assetCode"), rs.getString("type"),rs.getString("label"), rs.getDouble("quarterlyDivident"),
							rs.getDouble("baseRateOfReturn"), rs.getDouble("omega"),rs.getDouble("totalValue"));
				}

				asset.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
 			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return asset;
	}

	public static <T> myList<Portfolio> PortfolioDetail(List<Person> person, List<Asset> asset, Comparator<Portfolio> comp){
		String ownerName = "";
		String managerName = "";
		String beneficiaryName = "";
		String type = "";
		List<String> label = null;
		List<String> aCode = null;
		List<Double> roReturn = null;
		List<Double> risk = null;
		List<Double> annualReturn = null;
		List<Double> value = null;
		int portfolioId = 1;
		String portfolioCode = "";
		myList<Portfolio> ml = new myList<Portfolio>(comp);
		createConnection c = new createConnection();
		Connection conn = c.getConnection();

		String query = "SELECT * FROM Portfolio";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				label = new ArrayList<String>();
				aCode = new ArrayList<String>();
				roReturn = new ArrayList<Double>();
				risk = new ArrayList<Double>();
				annualReturn = new ArrayList<Double>();
				value = new ArrayList<Double>();
				portfolioId = rs.getInt("portfolioId");
				portfolioCode = rs.getString("portfolioCode");
				int beneficiaryId = rs.getInt("beneficiaryId");
				for(Person p : person){
					if(p.getPersonCode() == rs.getInt("ownerId")){
						ownerName = p.getLastName() + ", " + p.getFirstName(); 
					}if(p.getPersonCode() == rs.getInt("managerId")){
						managerName = p.getLastName() + ", " + p.getFirstName();
						type = p.getType();
					}if(p.getPersonCode() == beneficiaryId){
						beneficiaryName = p.getLastName() + ", " + p.getFirstName();
					}
				}

				query = "SELECT * FROM portfolioAsset p JOIN Asset a ON a.assetId = p.AssetId WHERE portfolioId = ?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, portfolioId);
				rs1 = ps.executeQuery();
				while(rs1.next()){
					label.add(rs1.getString("label"));
					aCode.add(rs1.getString("assetCode"));
					if(rs1.getString("type").equalsIgnoreCase("d")){
						value.add(rs1.getDouble("balance"));
						for(Asset a : asset){
							if(rs1.getString("assetCode").equalsIgnoreCase(a.getAssetCode())){
								roReturn.add(((Deposit)a).ReturnRate(rs1.getDouble("balance")));
								risk.add(0.00);
								annualReturn.add(((Deposit)a).calAnnualReturn(rs1.getDouble("balance")));
							}
						}
					}else if(rs1.getString("type").equalsIgnoreCase("s")){
						for(Asset a : asset){
							if(rs1.getString("assetCode").equalsIgnoreCase(a.getAssetCode())){
								roReturn.add(((Stock)a).calRateOfReturn(rs1.getDouble("shareOwned")));
								risk.add(((Stock)a).getBetaMeasure());
								annualReturn.add(((Stock)a).calAnnualReturn(rs1.getDouble("shareOwned")));
								value.add(((Stock)a).calValue(rs1.getDouble("shareOwned")));
							}
						}
					}else if(rs1.getString("type").equalsIgnoreCase("p")){
						for(Asset a : asset){
							if(rs1.getString("assetCode").equalsIgnoreCase(a.getAssetCode())){
								roReturn.add(((Private)a).calRateOfReturn(rs1.getDouble("stake")*100));
								risk.add(((Private)a).calOmega());
								annualReturn.add(((Private)a).calAnnualReturn(rs1.getDouble("stake")*100));
								value.add(((Private)a).calValue(rs1.getDouble("stake")*100));
							}
						}
					}
				}

				Portfolio p =  new Portfolio(ownerName, managerName, portfolioCode, beneficiaryName, aCode, label, roReturn, risk, annualReturn, value,type);
				ml.addElement(p);
				beneficiaryName = "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return  ml;
	}

//	public static void printPortfolioDetail(myList<Portfolio> ml, List<Asset> asset){
//		System.out.println("\nPortfolio Details\n==================================================================================================");
//		for(Portfolio a : ml){
//
//			System.out.println("\nPortfolio " + a.getpCode());
//			System.out.println("-----------------------------------------------------------------------------------------------------------------");
//			System.out.printf("%-15s %-20s\n", "Owner: ", a.getOwnerName());
//			System.out.printf("%-15s %-20s\n", "Manager: ", a.getManagerName());
//			System.out.printf("%-15s %-20s\n", "Beneficiary: ", a.getbName());
//			System.out.println("Assets");
//			System.out.printf("%-10s %-40s %-15s %-10s %-22s %-15s\n", "Code", "Asset", "Return Rate", "Risk", "Annual Return", "Value");
//			for(int i = 0; i < a.getaCode().size(); i++){
//				System.out.printf("%-10s %-40s %8.2f%% %10.2f       $%12.2f      $%12.2f\n", a.getaCode().get(i), a.getLabel().get(i), a.getRoReturn().get(i), a.getRisk().get(i), a.getAnnualReturn().get(i), a.getValue().get(i));
//			}
//			System.out.printf("\n%50s", "Totals");
//
//			if(a.getaCode().size() != 0){
//				System.out.printf("%22.4f       $%12.2f      $%12.2f\n",a.aggregateRiskCal(asset), a.getAReturn(), a.getTotalValue());
//			}
//		}
//	}
	public static void printPortfolioSummary(myList<Portfolio> ml ,List<Person> person, List<Asset> asset){
		double totalFee = 0.0;
		double commision = 0.0;
		double totalCommision = 0.0;
		double totalReturn = 0.0;
		double totalValue = 0.0;
		System.out.println("Portfolio Summary Report");
		System.out.println("====================================================================================================================================================");
		System.out.printf("%-15s %-20s %-25s %8s %18s %20s %15s %15s\n", "Portfolio", "Owner", "Manager", "Fees", "Commisions", "Weighted Risk", "Return", "Total");
		for(Portfolio a : ml){	
			for(Person e : person){
				if(a.getManagerName().equals(e.getLastName()+", "+e.getFirstName())){
					if(e.getType().equalsIgnoreCase("j")){
						commision = (a.getAReturn() / 100)*2;
					}else if (e.getType().equalsIgnoreCase("e")){
						commision = (a.getAReturn()/100)*5;
					}
				}
			}

			System.out.printf("%-15s %-20s %-25s %-3s $%.2f        $%10.2f       $%10.4f        $%10.2f   $%15.2f\n", a.getpCode() , a.getOwnerName(), a.getManagerName(),a.getType(),a.calFee(person),commision, a.aggregateRiskCal(asset), a.getAReturn(), a.getTotalValue());
			totalFee += a.calFee(person); 
			totalCommision += commision;
			totalReturn += a.getAReturn();
			totalValue += a.getTotalValue();
		}

		System.out.printf("\n%60s", "Total");
		System.out.printf("   $%8.2f        $%10.2f                          $%10.2f   $%12.2f\n", totalFee, totalCommision,totalReturn, totalValue);
	}

	public static void main (String[] args) throws OutOfScaleException{
		List<Person> person = Demo.loadPersons();
		List<Asset> asset = Demo.loadAssets();
		myList<Portfolio> ml1 = Demo.PortfolioDetail(person, asset, new NameOrder());
		myList<Portfolio> ml2 = Demo.PortfolioDetail(person, asset, new valueOrder());
		myList<Portfolio> ml3 = Demo.PortfolioDetail(person, asset, new typeOrder());
		printPortfolioSummary(ml1, person, asset);
		printPortfolioSummary(ml2, person, asset);
		printPortfolioSummary(ml3, person, asset);
	}
}
