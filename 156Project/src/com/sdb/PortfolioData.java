package com.sdb; //DO NOT CHANGE THIS

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import project156.Portfolio;
import project156.createConnection;
/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {
		public static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Portfolio.class);
	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();

		String query = "DELETE FROM Email"; //DELETE ALL THE EMAIL

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			query = "DELETE FROM personAddress";//DELETE ALL RECORDS IN TABLE personAddress
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "DELETE FROM portfolioAsset";//DELETE ALL RECORDS IN TABLE portfolioAsset
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "DELETE FROM Portfolio";//DELETE ALL THE PORTFOLIO
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "DELETE FROM Person";//DELETE EVERY PERSON
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}

		
	}

	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
		
		int personId = 0;//holds personId
		int portfolioId = 0;//holds portfolioId
		
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		String query = "SELECT * FROM Person";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("personCode").equals(personCode)){//FOUND THE PERSON THAT NEEDS TO BE REMOVED
					personId = rs.getInt("personId");//GET THE ID
				}
			}
			query = "DELETE FROM Email WHERE personId = ?";//DELETE THE EMAIL THAT ASSOCIATES WITH THE PERSON
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			ps.executeUpdate();
			query = "DELETE FROM personAddress WHERE personId = ?";//DELETE THE ADDRESS
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			ps.executeUpdate();
			query = "SELECT * FROM Portfolio a JOIN portfolioAsset b ON a.portfolioId = b.portfolioId";//JOIN Portfolio TABLE AND portfolioAsset
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getInt("ownerId") == personId || rs.getInt("managerId") == personId || rs.getInt("beneficiaryId") == personId){//IF THE PERSON EITHER OWN, MANAGE OR GET BENEFIT
					portfolioId = rs.getInt("portfolioId");//GET THE PORTFOLIO ID
					query = "DELETE FROM portfolioAsset WHERE portfolioId = ?";//DELETE THE RECORDS IN PortfolioAsset
					ps = conn.prepareStatement(query);
					ps.setInt(1, portfolioId);
					ps.executeUpdate();
					query = "DELETE FROM Portfolio WHERE ownerId = ?";//DELETE THE PORTFOLIO WHERE THE PERSON OWNS IT
					ps = conn.prepareStatement(query);
					ps.setInt(1, personId);
					ps.executeUpdate();
					query = "DELETE FROM Portfolio WHERE managerId = ?";//DELETE THE PORTFOLIO WHERE THE PERSON MANAGES IT
					ps = conn.prepareStatement(query);
					ps.setInt(1, personId);
					ps.executeUpdate();
					query = "DELETE FROM Portfolio WHERE beneficiaryId =?";//DELETE THE PORTFOLIO WHERE THE PERSON IS GETTING BENEFIT
					ps = conn.prepareStatement(query);
					ps.setInt(1, personId);
					ps.executeUpdate();
				}
			}
			query = "DELETE FROM Person WHERE personId = ?";//DELETE THE PERSON
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, 
			String brokerType, String secBrokerId) {
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		
		int stateId = 0;
		int countryId = 0;
		int addressId = 0;
		int personId = 0;

		String query = "SELECT * FROM State where state = ?";//CHECK IF THE STATE IS ALREADY IN THE TABLE
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, state);
			rs = ps.executeQuery();
			if(rs.next() == false){//THE STATE IS NOT IN THE TABLE
				query = "INSERT INTO State(state) VALUES (?)";//INSERT THE NEW STATE
				ps = conn.prepareStatement(query);
				ps.setString(1, state);
				ps.executeUpdate();
				query = "SELECT LAST_INSERT_ID()";//GET THE ID JUST INSERTED
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				rs.next();
				stateId = rs.getInt("LAST_INSERT_ID()");
			}else {//ALREADY IN THE STATE TABLE
				stateId = rs.getInt("stateId");
			}

			query = "SELECT * FROM Country WHERE country = ?";//QUERY TO CHECK IF THE COUNTRY IS IN THE TABLE
			ps = conn.prepareStatement(query);
			ps.setString(1, country);
			rs = ps.executeQuery();
			if(rs.next() == false){//THE COUNTRY IS NOT IN THE TABLE
				query = "INSERT INTO Country(country) VALUES(?)";//INSERT THE NEW COUNTRY
				ps = conn.prepareStatement(query);
				ps.setString(1, country);
				ps.executeUpdate();
				query = "SELECT LAST_INSERT_ID()";//GET THE COUNTRY ID JUST INSERTED
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				rs.next();
				countryId = rs.getInt("LAST_INSERT_ID()");
			}else{//COUNTRY ALREADY EXISTS IN THE TABLE
				countryId = rs.getInt("countryId");
			}

			query = "SELECT * FROM Address WHERE street = ?";//QUERY TO CHECK IF THE STREET ALREADY IN THE TABLE
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			rs = ps.executeQuery();
			if(rs.next() == false){//IF NOT
				query = "INSERT INTO Address (street, city, stateId, zipcode, countryId) VALUES (?,?,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setInt(3, stateId);
				ps.setString(4, zip);
				ps.setInt(5, countryId);
				ps.executeUpdate();
				query = "SELECT LAST_INSERT_ID()";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				rs.next();
				addressId = rs.getInt("LAST_INSERT_ID()");
			}else{//IF IS
				addressId = rs.getInt("addressId");
			}

			query = "INSERT INTO Person (personCode, type, secId, firstName, lastName) VALUES (?,?,?,?,?) ";//ADD A PERSON IN DATABASE
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, brokerType);
			ps.setString(3, secBrokerId);
			ps.setString(4, firstName);
			ps.setString(5, lastName);
			ps.executeUpdate();

			query = "SELECT LAST_INSERT_ID()";//GET THE PERSON ID THAT WAS JUST INSERTED
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			personId = rs.getInt("LAST_INSERT_ID()");

			query = "INSERT INTO personAddress(addressId, personId) VALUES(?,?)";//INSERT INTO THE PERSON ADDRESS TABLE WHICH JOIN THE PERSON AND ADDRESS TABLE
			ps = conn.prepareStatement(query);
			ps.setInt(1, addressId);
			ps.setInt(2, personId);
			ps.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		int personId = 0;
		
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();

		String query = "SELECT * FROM Person";//GET ALL THE PERSON
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				if(personCode.equalsIgnoreCase(rs.getString("personCode"))){//FOUND THE PERSON THAT NEEDS TO ASSOCIATE WITH THE NEW EMAIL
					personId = rs.getInt("personId");
				}
			}
			query = "INSERT INTO Email(email, personId) VALUES (?,?)";//INSERT THE EMAIL
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setInt(2, personId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		String query = "DELETE FROM Email";//REMOVE ALL EMAIL
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate(query);
			query = "DELETE FROM personAddress";//REMOVE ADDRESS
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "DELETE FROM portfolioAsset";//REMOVE ALL THE RECORDS IN PORTFOLIO ASSET TABLE
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "DELETE FROM Portfolio";//REMOVE EVERY PORTFOLIO
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "DELETE FROM Asset";//DELETE ALL ASSET
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}


	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {
		int assetId = 0;
		
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		String query = "SELECT * FROM Asset";//QUERY TO GET ALL THE ASSET
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("assetCode").equalsIgnoreCase(assetCode)){//FOUND THE ASSET THAT NEEDS TO BE REMOVED
					assetId = rs.getInt("assetId");
					System.out.println(assetId);
				}
			}
			query = "DELETE FROM portfolioAsset WHERE assetId = ?";//DELETE THE PORTFOLIO ASSET RECORDS THAT HAS THE ASSET NEEDING TO DELETE
			ps = conn.prepareStatement(query);
			ps.setInt(1, assetId);
			ps.executeUpdate();
			query = "DELETE FROM Asset WHERE assetId = ?";//DELETE THE ASSET
			ps = conn.prepareStatement(query);
			ps.setInt(1, assetId);
			ps.executeUpdate();
			//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		String type = "D";
		String query = "INSERT INTO Asset(assetCode, label, apr, type) VALUES (?,?,?,?)";//INSERT INTO ASSET DEPOSIT ACCOUNT
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, label);
			ps.setDouble(3, apr);
			ps.setString(4, type);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * Adds a private investment asset record to the database with the
	 * provided data.  The <code>baseRateOfReturn</code> is assumed to be on the
	 * scale [0, 1].
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param baseOmega
	 * @param totalValue
	 * @throws OutOfScaleException 
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double baseOmega, Double totalValue) throws OutOfScaleException {

		if(baseRateOfReturn > 1 || baseRateOfReturn < 0){
			throw new OutOfScaleException("Base rate of return is not in the scale [0,1]");
		}//THE BASE RATE IS NOT IN THE SCALE [0,1]
		
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		String type = "P";
		String query = "INSERT INTO Asset(assetCode, type, label, quarterlyDivident, baseRateOfReturn, omega, totalValue) "
				+ "VALUES(?,?,?,?,?,?,?)";//INSERT THE PRIVATE INVESTMENT ASSET
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, type);
			ps.setString(3, label);
			ps.setDouble(4, quarterlyDividend);
			ps.setDouble(5, baseRateOfReturn);
			ps.setDouble(6, baseOmega);
			ps.setDouble(7, totalValue);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a stock asset record to the database with the
	 * provided data.  The <code>baseRateOfReturn</code> is assumed to be on the 
	 * scale [0, 1].
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 * @throws OutOfScaleException 
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) throws OutOfScaleException {

		if(baseRateOfReturn > 1 || baseRateOfReturn < 0){
			throw new OutOfScaleException("Base rate of return is not in the scale [0,1]");
		}//BASE RATE NOT IN THE SCALE [0,1]
		
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		String type = "S";
		String query = "INSERT INTO Asset(assetCode, type, label, quarterlyDivident, baseRateOfReturn, beta, stockSymbol, sharePrice) "
				+ "VALUES(?,?,?,?,?,?,?,?)";//INSERT STOCK ASSET
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, type);
			ps.setString(3, label);
			ps.setDouble(4, quarterlyDividend);
			ps.setDouble(5, baseRateOfReturn);
			ps.setDouble(6, beta);
			ps.setString(7, stockSymbol);
			ps.setDouble(8, sharePrice);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();

		String query = "DELETE FROM portfolioAsset";//DELETE ALL RECORDS FROM PORTFOLIO ASSET TABLE
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			query = "DELETE FROM Portfolio";//DELETE ALL PORTFOLIOS
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}


	}

	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {
		/**
		 * Create a connection 
		 */
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		int portfolioId = 0;
		String query = "SELECT * FROM Portfolio";//GET ALL PORTFOLIOS
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("portfolioCode").equalsIgnoreCase(portfolioCode)){//FOUND PORTFOLIO THAT NEEDS TO BE DELETED
					portfolioId = rs.getInt("portfolioId");
				}
			}
			query = "DELETE FROM portfolioAsset WHERE portfolioId = ?";//DELETE THE RECORD IN PORTFOLIO ASSET THAT HAS THE SAME PORTFOLIO ID
			ps = conn.prepareStatement(query);
			ps.setInt(1, portfolioId);
			ps.executeUpdate();
			query = "DELETE FROM Portfolio WHERE portfolioCode = ?";//DELETE THE PORTFOLIO
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		int ownerId = 0;
		int managerId = 0;
		int beneficiaryId = 0;
		String query = "SELECT * FROM Person";//GET PEOPLE
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs =  ps.executeQuery();
			while(rs.next()){
				if(rs.getString("personCode").equalsIgnoreCase(ownerCode))//CHECK WHO IS THE OWNER
					ownerId = rs.getInt("personId");
				if(rs.getString("personCode").equalsIgnoreCase(managerCode))//CHECK WHO IS THE MANAGER
					managerId = rs.getInt("personId");
				if(rs.getString("personCode").equalsIgnoreCase(beneficiaryCode) && !beneficiaryCode.equals(null))//CHECK WHO IS GETTING BENEFIT
					beneficiaryId = rs.getInt("personId");
			}
			query = "INSERT INTO Portfolio(portfolioCode, ownerId, ownerCode, managerId, managerCode, beneficiaryId, beneficiaryCode) "
					+ "VALUES (?,(SELECT personId FROM Person WHERE personId = ?),(SELECT personCode FROM Person WHERE personCode = ?),"
					+ "(SELECT personId FROM Person WHERE personId = ?),(SELECT personCode FROM Person WHERE personCode = ?),"
					+ "(SELECT personId FROM Person WHERE personId = ? ),(SELECT personCode FROM Person WHERE personCode = ?))";//INSERT THE PORTFOLIO
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.setInt(2, ownerId);
			ps.setString(3, ownerCode);
			ps.setInt(4, managerId);
			ps.setString(5, managerCode);
			ps.setInt(6, beneficiaryId);
			ps.setString(7, beneficiaryCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the 
	 * portfolio corresponding to the provided <code>portfolioCode</code>.  The third 
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number of shares</i>
	 * or <i>stake percentage</i> (on the scale [0, 1]) depending on the type of asset the <code>assetCode</code> is
	 * associated with.  
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value) {
		createConnection c = new createConnection();
		Connection conn = c.getConnection();
		int portfolioId = 0;
		int assetId = 0;
		String query = "SELECT * FROM Portfolio";//GET ALL ASSETS
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("portfolioCode").equalsIgnoreCase(portfolioCode)){//FOUND THE PORTFOLIO THAT NEEDS TO BE ADDED ASSET
					portfolioId = rs.getInt("portfolioId");
				}
			}
			query = "SELECT * FROM Asset";//GET ALL ASSETS
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()){
				if(assetCode.equalsIgnoreCase(rs.getString("assetCode"))){//FOUND THE ASSET THAT NEEDS TO BE ADDED TO THE PORTFOLIO
					assetId = rs.getInt("assetId");
					if(rs.getString("type").equalsIgnoreCase("d")){//DEPOSIT 
						query = "INSERT INTO portfolioAsset (portfolioId, assetId, balance) VALUES (?,?,?)";
						ps = conn.prepareStatement(query);
						ps.setInt(1, portfolioId);
						ps.setInt(2, assetId);
						ps.setDouble(3, value);
						ps.executeUpdate();
					}else if(rs.getString("type").equalsIgnoreCase("s")){//STOCK
						query = "INSERT INTO portfolioAsset (portfolioId, assetId, shareOwned) VALUES (?,?,?)";
						ps = conn.prepareStatement(query);
						ps.setInt(1, portfolioId);
						ps.setInt(2, assetId);
						ps.setDouble(3, value);
						ps.executeUpdate();

					}else if(rs.getString("type").equalsIgnoreCase("p")){//PRIVATE
						query = "INSERT INTO portfolioAsset (portfolioId, assetId, stake) VALUES (?,?,?)";
						ps = conn.prepareStatement(query);
						ps.setInt(1, portfolioId);
						ps.setInt(2, assetId);
						ps.setDouble(3, value);
						ps.executeUpdate();

					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		
		/**
		 * CLOSE CONNECTION, PREPAREDSTATEMENT AND RESULT SET IF NEEDED
		 */
		c.closeConnection(conn);
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();

		} catch (SQLException e) {
			System.out.println("SQLException: ");
			log.error(e);
			throw new RuntimeException(e);
		}

	}
}

