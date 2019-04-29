package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user;

public class Customer extends User {

	//private int customerID;
	private String passportID;
	private int driveExperience;

	public Customer() { }

	public Customer(int userID, String login, String password, int roleID, String name, String surName, int phone,
	                String email, int customerID, String passportID, int driveExperience) {
//		super(login, password, roleID, name, surName, phone, email);
		//this.customerID = customerID;
		this.passportID = passportID;
		this.driveExperience = driveExperience;
	}

//	public int getCustomerID() {
//		return customerID;
//	}
//
//	public void setCustomerID(int customerID) {
//		if (customerID > 0)
//		this.customerID = customerID;
//	}

	public String getPassportID() {
		return passportID;
	}

	public void setPassportID(String passportID) {
		if (passportID != null) {
			this.passportID = passportID;
		}
	}

	public int getDriveExperience() {
		return driveExperience;
	}

	public void setDriveExperience(int driveExperience) {
		if (driveExperience > 0) {
			this.driveExperience = driveExperience;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Customer customer = (Customer) o;

		//if (customerID != customer.customerID) return false;
		if (driveExperience != customer.driveExperience) return false;
		return passportID != null ? passportID.equals(customer.passportID) : customer.passportID == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		//result = 31 * result + customerID;
		result = 31 * result + (passportID != null ? passportID.hashCode() : 0);
		result = 31 * result + driveExperience;
		return result;
	}

	@Override
	public String toString() {
		return "Customer{" + super.toString() +
			//	"customerID=" + customerID +
				", passportID='" + passportID + '\'' +
				", driveExperience=" + driveExperience +
				'}';
	}
}
