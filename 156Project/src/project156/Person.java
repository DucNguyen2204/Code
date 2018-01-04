package project156;
import java.util.Arrays;
import java.util.List;

public class Person {
		
		private int personCode;
		private String type;
		private String secIdentifier;
		private String firstName;
		private String lastName;
		private Address address;
//		private List<Email> emailAddress;
		private List<String> emailAddress;
		
	
	public Person(String type, String firstName, String lastName, Address address, int personCode){
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.personCode = personCode;
	}
	
	

	public void setTypeAndSec(String data){
		if(!data.equals("")){
		String[] token  = data.split(",");
		this.type = token[0];
		this.secIdentifier = token[1];
		}
	}
	
	public void setPersonCode(int code){
		this.personCode = code;
	}
	public int getPersonCode(){
		return this.personCode;
	}
	
	public void setFirstName(String name){
		this.firstName = name;
	}
	
	public void setLastName(String last){
		this.lastName = last;
	}
	
	public String getType(){
		return this.type;
	}
	public String getSecIdentifier(){
		return this.secIdentifier;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	public String getLastName(){
		return this.lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}



	public List<String> getEmailAddress() {
		return emailAddress;
	}



	public void setEmailAddress(List<String> emailAddress) {
		this.emailAddress = emailAddress;
	}



	@Override
	public String toString() {
		return "Person [personCode=" + personCode + ", type=" + type + ", firstName=" + firstName + ", lastName="
				+ lastName + ", address=" + address + ", emailAddress=" + emailAddress + "]";
	}

	

	
	
	
	
}
