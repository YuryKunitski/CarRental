package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

	private int userID;
	private String login;
	private String password;
//	private int roleID;
	private String name;
	private String surName;
	private String phone;
	private String email;
	private String passportID;

//	public enum RoleType {
//		CUSTOMER, MANAGER, ADMINISTRATOR
//	}

	public User() {
	}

	public User(String login, String password,String name, String surName, String phone,
	            String email, String passportID) {
//		this.userID = userID;
		this.login = login;
		this.password = password;
//		this.roleID = roleID;
		this.name = name;
		this.surName = surName;
		this.phone = phone;
		this.email = email;
		this.passportID = passportID;

	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int id) {
		if (id > 0) {
			this.userID = id;
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		if (login != null) {
			this.login = login;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null) {
			this.password = password;
		}
	}

//	public int getRoleID() {
//		return roleID;
//	}
//
//	public void setRole(int roleID) {
//		this.roleID = roleID;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String serName) {
		if (serName != null)
			this.surName = serName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != null) {
			this.phone = phone;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (checkEmail(email)) {
			this.email = email;
		}
	}

	private boolean checkEmail(String email) {
		String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public String getPassportID() {
		return passportID;
	}

	public void setPassportID(String passportID) {
		this.passportID = passportID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (userID != user.userID) return false;
		if (phone != user.phone) return false;
		if (login != null ? !login.equals(user.login) : user.login != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
//		if (roleID != user.roleID) return false;
		if (name != null ? !name.equals(user.name) : user.name != null) return false;
		if (surName != null ? !surName.equals(user.surName) : user.surName != null) return false;
		return email != null ? email.equals(user.email) : user.email == null;

	}

	@Override
	public int hashCode() {
		int result = userID;
		result = 31 *  (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
//		result = 31 * result + roleID;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (surName != null ? surName.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);;
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "id=" + userID +
				" login='" + login + '\'' +
				", password='" + password + '\'' +
//				", role=" + roleID +
				", name='" + name + '\'' +
				", serName='" + surName + '\'' +
				", phone=" + phone +
				", email='" + email + '\'' +
				'}';
	}
}
