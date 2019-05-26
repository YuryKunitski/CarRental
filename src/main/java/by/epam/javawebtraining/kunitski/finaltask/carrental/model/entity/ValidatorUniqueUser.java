package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity;
/**
 *
 * <p>
 *     The ValidatorUniqueUser class checks for uniqueness user data (login, email, passportID)
 * </p>
 */
public class ValidatorUniqueUser {

	private boolean uniqueLogin;
	private boolean uniqueEmail;
	private boolean uniquePassportID;

	public boolean isUniqueLogin() {
		return uniqueLogin;
	}

	public void setUniqueLogin(boolean uniqueLogin) {
		this.uniqueLogin = uniqueLogin;
	}

	public boolean isUniqueEmail() {
		return uniqueEmail;
	}

	public void setUniqueEmail(boolean uniqueEmail) {
		this.uniqueEmail = uniqueEmail;
	}

	public boolean isUniquePassportID() {
		return uniquePassportID;
	}

	public void setUniquePassportID(boolean uniquePassportID) {
		this.uniquePassportID = uniquePassportID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ValidatorUniqueUser that = (ValidatorUniqueUser) o;

		if (uniqueLogin != that.uniqueLogin) return false;
		if (uniqueEmail != that.uniqueEmail) return false;
		return uniquePassportID == that.uniquePassportID;

	}

	@Override
	public int hashCode() {
		int result = (uniqueLogin ? 1 : 0);
		result = 31 * result + (uniqueEmail ? 1 : 0);
		result = 31 * result + (uniquePassportID ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ValidatorUniqueUser{" +
				"uniqueLogin=" + uniqueLogin +
				", uniqueEmail=" + uniqueEmail +
				", uniquePassportID=" + uniquePassportID +
				'}';
	}
}
