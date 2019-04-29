package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user;

public enum RoleType {

	CUSTOMER(1), ADMINISTRATOR(2);

	private int ordinal;

	private RoleType(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public static RoleType getValue(int roleID) {

		RoleType roleType = null;

		if (roleID == 1) {
			roleType = CUSTOMER;
		} else if (roleID == 2) {
			roleType = ADMINISTRATOR;
		}

		return roleType;
	}
}
