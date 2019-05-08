package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car;

public class Car {

	private int carID;
	private String carModel;
	private CarClassType carClassType;
	private String yearIssue;
	private double pricePerDay;
	private String image;



	public Car(){

	}

	public Car(int carID, String carModel, CarClassType carClassType, String yearIssue, double pricePerDay,
	           String image) {
		this.carID = carID;
		this.carModel = carModel;
		this.carClassType = carClassType;
		this.yearIssue = yearIssue;
		this.pricePerDay = pricePerDay;
		this.image = image;
	}

	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		this.carID = carID;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public CarClassType getCarClassType() {
		return carClassType;
	}

	public void setCarClassType(CarClassType carClassType) {
		this.carClassType = carClassType;
	}

	public String getYearIssue() {
		return yearIssue;
	}

	public void setYearIssue(String yearIssue) {
		this.yearIssue = yearIssue;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Car car = (Car) o;

		if (carID != car.carID) return false;
		if (Double.compare(car.pricePerDay, pricePerDay) != 0) return false;
		if (carModel != null ? !carModel.equals(car.carModel) : car.carModel != null) return false;
		if (carClassType != car.carClassType) return false;
		if (yearIssue != null ? !yearIssue.equals(car.yearIssue) : car.yearIssue != null) return false;
		return image != null ? image.equals(car.image) : car.image == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = carID;
		result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
		result = 31 * result + (carClassType != null ? carClassType.hashCode() : 0);
		result = 31 * result + (yearIssue != null ? yearIssue.hashCode() : 0);
		temp = Double.doubleToLongBits(pricePerDay);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (image != null ? image.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Car{" +
				"carID=" + carID +
				", carModel='" + carModel + '\'' +
				", carClassType=" + carClassType +
				", yearIssue='" + yearIssue + '\'' +
				", pricePerDay=" + pricePerDay +
				", image='" + image + '\'' +
				'}';
	}
}
