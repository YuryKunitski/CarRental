package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity;

import java.sql.Date;

public class Car {

	private int carID;
	private Date yearIssue;
	private double pricePerDay;
	private boolean isAvailable;
	private CarModelType carBrandType;
	private CarClassType carClassType;


	public enum CarModelType{
		FORD_FOCUS, FORD_TRANSIT, MAZDA_6, MERCEDES_E650, PEUGEOT_208, PORSCHE_CAYENNE, RENO_LOGAN, VOLKSWAGEN_POLO,
		VOLKSWAGEN_TRANSPORTER, VOLVO_XC60;
	}

	public enum CarClassType{
		ECONOM, BUSINESS, TRUCK;
	}

	public Car(){

	}

	public Car(int carID, Date yearIssue, double pricePerDay, boolean isAvailable, CarModelType carBrandType,
	           CarClassType carClassType) {
		this.carID = carID;
		this.yearIssue = yearIssue;
		this.pricePerDay = pricePerDay;
		this.isAvailable = isAvailable;
		this.carBrandType = carBrandType;
		this.carClassType = carClassType;
	}

	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		if (carID > 0) {
			this.carID = carID;
		}
	}

	public Date getYearIssue() {
		return yearIssue;
	}

	public void setYearIssue(Date yearIssue) {
		if (yearIssue != null) {
			this.yearIssue = yearIssue;
		}
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		if (pricePerDay > 0) {
			this.pricePerDay = pricePerDay;
		}
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public CarModelType getCarBrandType() {
		return carBrandType;
	}

	public void setCarBrandType(CarModelType carBrandType) {
		this.carBrandType = carBrandType;
	}

	public CarClassType getCarClassType() {
		return carClassType;
	}

	public void setCarClassType(CarClassType carClassType) {
		this.carClassType = carClassType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Car car = (Car) o;

		if (carID != car.carID) return false;
		if (Double.compare(car.pricePerDay, pricePerDay) != 0) return false;
		if (isAvailable != car.isAvailable) return false;
		if (yearIssue != null ? !yearIssue.equals(car.yearIssue) : car.yearIssue != null) return false;
		if (carBrandType != car.carBrandType) return false;
		return carClassType == car.carClassType;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = carID;
		result = 31 * result + (yearIssue != null ? yearIssue.hashCode() : 0);
		temp = Double.doubleToLongBits(pricePerDay);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (isAvailable ? 1 : 0);
		result = 31 * result + (carBrandType != null ? carBrandType.hashCode() : 0);
		result = 31 * result + (carClassType != null ? carClassType.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Car{" +
				"carID=" + carID +
				", yearIssue=" + yearIssue +
				", pricePerDay=" + pricePerDay +
				", isAvailable=" + isAvailable +
				", carBrandType=" + carBrandType +
				", carClassType=" + carClassType +
				'}';
	}
}
