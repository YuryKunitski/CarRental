package by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity;

import java.sql.Date;

public class CrashHistory {

	private int crashHistoryID;
	private Date crashDate;
	private double repairBill;

	public CrashHistory() {

	}

	public CrashHistory(int crashHistoryID, Date crashDate, double repairBill) {
		this.crashHistoryID = crashHistoryID;
		this.crashDate = crashDate;
		this.repairBill = repairBill;
	}

	public int getCrashHistoryID() {
		return crashHistoryID;
	}

	public void setCrashHistoryID(int crashHistoryID) {
		if (crashHistoryID > 0) {
			this.crashHistoryID = crashHistoryID;
		}
	}

	public Date getCrashDate() {
		return crashDate;
	}

	public void setCrashDate(Date crashDate) {
		if (crashDate != null) {
			this.crashDate = crashDate;
		}
	}

	public double getRepairBill() {
		return repairBill;
	}

	public void setRepairBill(double repairBill) {
		if (repairBill > 0) {
			this.repairBill = repairBill;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CrashHistory that = (CrashHistory) o;

		if (crashHistoryID != that.crashHistoryID) return false;
		if (Double.compare(that.repairBill, repairBill) != 0) return false;
		return crashDate != null ? crashDate.equals(that.crashDate) : that.crashDate == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = crashHistoryID;
		result = 31 * result + (crashDate != null ? crashDate.hashCode() : 0);
		temp = Double.doubleToLongBits(repairBill);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
