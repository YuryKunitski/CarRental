package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user;

public class Manager extends User{

	private int yearExperience;

	public Manager() {

	}

	public Manager(int userID, String login, String password, int roleID, String name, String surName, int phone,
	               String email, int yearExperience) {
//		super(login, password, roleID, name, surName, phone, email);
		this.yearExperience = yearExperience;
	}

	public int getYearExperience() {
		return yearExperience;
	}

	public void setYearExperience(int yearExperience) {
		this.yearExperience = yearExperience;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Manager manager = (Manager) o;

		return yearExperience == manager.yearExperience;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + yearExperience;
		return result;
	}

	@Override
	public String toString() {
		return "Manager{" + super.toString() +
				"yearExperience=" + yearExperience +
				'}';
	}
}
