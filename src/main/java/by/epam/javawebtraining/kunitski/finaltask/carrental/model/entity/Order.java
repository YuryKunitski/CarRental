package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity;

import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.Customer;

import java.sql.Date;

public class Order {

	private int orderID;
	private Customer customer;
	private Car car;
	private Date rentalStartDate;
	private int rentDuration;
	private double costCoefficient;
	private CrashHistory crashHistory;
	private String status;
	private double totalBill;

	public Order() {
	}

	public Order(int orderID, Customer customer, Car car, Date rentalStartDate, int rentDuration,
	             double costCoefficient, CrashHistory crashHistory, String status, double totalBill) {
		this.orderID = orderID;
		this.customer = customer;
		this.car = car;
		this.rentalStartDate = rentalStartDate;
		this.rentDuration = rentDuration;
		this.costCoefficient = costCoefficient;
		this.crashHistory = crashHistory;
		this.status = status;
		this.totalBill = totalBill;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		if (orderID > 0) {
			this.orderID = orderID;
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		if (customer != null) {
			this.customer = customer;
		}
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		if (car != null) {
			this.car = car;
		}
	}

	public Date getRentalStartDate() {
		return rentalStartDate;
	}

	public void setRentalStartDate(Date rentalStartDate) {
		if (rentalStartDate != null) {
			this.rentalStartDate = rentalStartDate;
		}
	}

	public int getRentDuration() {
		return rentDuration;
	}

	public void setRentDuration(int rentDuration) {
		if (rentDuration > 0) {
			this.rentDuration = rentDuration;
		}
	}

	public double getCostCoefficient() {
		return costCoefficient;
	}

	public void setCostCoefficient(double costCoefficient) {
		if (costCoefficient > 0) {
			this.costCoefficient = costCoefficient;
		}
	}

	public CrashHistory getCrashHistory() {
		return crashHistory;
	}

	public void setCrashHistory(CrashHistory crashHistory) {
		if (crashHistory != null) {
			this.crashHistory = crashHistory;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setAllowed(String status) {
		if (status != null) {
			this.status = status;
		}
	}

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		if (totalBill > 0) {
			this.totalBill = totalBill;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Order order = (Order) o;

		if (orderID != order.orderID) return false;
		if (rentDuration != order.rentDuration) return false;
		if (Double.compare(order.costCoefficient, costCoefficient) != 0) return false;
		if (Double.compare(order.totalBill, totalBill) != 0) return false;
		if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
		if (car != null ? !car.equals(order.car) : order.car != null) return false;
		if (rentalStartDate != null ? !rentalStartDate.equals(order.rentalStartDate) : order.rentalStartDate != null)
			return false;
		if (crashHistory != null ? !crashHistory.equals(order.crashHistory) : order.crashHistory != null) return false;
		return status != null ? status.equals(order.status) : order.status == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = orderID;
		result = 31 * result + (customer != null ? customer.hashCode() : 0);
		result = 31 * result + (car != null ? car.hashCode() : 0);
		result = 31 * result + (rentalStartDate != null ? rentalStartDate.hashCode() : 0);
		result = 31 * result + rentDuration;
		temp = Double.doubleToLongBits(costCoefficient);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (crashHistory != null ? crashHistory.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		temp = Double.doubleToLongBits(totalBill);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderID=" + orderID +
				", customer=" + customer +
				", car=" + car +
				", rentalStartDate=" + rentalStartDate +
				", rentDuration=" + rentDuration +
				", costCoefficient=" + costCoefficient +
				", crashHistory=" + crashHistory +
				", status='" + status + '\'' +
				", totalBill=" + totalBill +
				'}';
	}
}
