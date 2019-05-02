package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car;

import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;

public enum CarClassType {
	ECONOM(1), BUSINESS(2), TRUCK(3);

	private int ordinal;

	private CarClassType(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public static CarClassType getValue(int carClassID) {

		CarClassType carClassType = null;

		if (carClassID == 1) {
			carClassType = ECONOM;
		} else if (carClassID == 2) {
			carClassType = BUSINESS;
		} else if (carClassID == 3) {
			carClassType = TRUCK;
		}

		return carClassType;
	}
}
