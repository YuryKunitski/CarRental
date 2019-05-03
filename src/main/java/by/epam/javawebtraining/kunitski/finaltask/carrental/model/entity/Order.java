package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity;

import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;

import java.util.Date;


public class Order {

	private int orderID;
	private User user;
	private Car car;
	private Date rentalStartDate;
	private Date rentalEndDate;
	private double damagePrice;
	private String status;
	private String info;
	private double totalBill;

	public Order() {
	}

	public Order(int orderID, User user, Car car, Date rentalStartDate, Date rentalEndDate,
	             double damagePrice, String status, String info, double totalBill) {
		this.orderID = orderID;
		this.user = user;
		this.car = car;
		this.rentalStartDate = rentalStartDate;
		this.rentalEndDate = rentalEndDate;
		this.damagePrice = damagePrice;
		this.status = status;
		this.info = info;
		this.totalBill = totalBill;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Date getRentalStartDate() {
		return rentalStartDate;
	}

	public void setRentalStartDate(Date rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
	}

	public Date getRentalEndDate() {
		return rentalEndDate;
	}

	public void setRentalEndDate(Date rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
	}

	public double getDamagePrice() {
		return damagePrice;
	}

	public void setDamagePrice(double damagePrice) {
		this.damagePrice = damagePrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Order order = (Order) o;

		if (orderID != order.orderID) return false;
		if (Double.compare(order.damagePrice, damagePrice) != 0) return false;
		if (Double.compare(order.totalBill, totalBill) != 0) return false;
		if (user != null ? !user.equals(order.user) : order.user != null) return false;
		if (car != null ? !car.equals(order.car) : order.car != null) return false;
		if (rentalStartDate != null ? !rentalStartDate.equals(order.rentalStartDate) : order.rentalStartDate != null)
			return false;
		if (rentalEndDate != null ? !rentalEndDate.equals(order.rentalEndDate) : order.rentalEndDate != null)
			return false;
		if (status != null ? !status.equals(order.status) : order.status != null) return false;
		return info != null ? info.equals(order.info) : order.info == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = orderID;
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (car != null ? car.hashCode() : 0);
		result = 31 * result + (rentalStartDate != null ? rentalStartDate.hashCode() : 0);
		result = 31 * result + (rentalEndDate != null ? rentalEndDate.hashCode() : 0);
		temp = Double.doubleToLongBits(damagePrice);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (info != null ? info.hashCode() : 0);
		temp = Double.doubleToLongBits(totalBill);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderID=" + orderID +
				", user=" + user +
				", car=" + car +
				", rentalStartDate='" + rentalStartDate + '\'' +
				", rentalEndDate='" + rentalEndDate + '\'' +
				", damagePrice=" + damagePrice +
				", status='" + status + '\'' +
				", info='" + info + '\'' +
				", totalBill=" + totalBill +
				'}';
	}
}
