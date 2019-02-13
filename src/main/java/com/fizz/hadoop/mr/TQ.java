package com.fizz.hadoop.mr;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author fizz
 * @version 2019/2/13 17:29
 */
public class TQ implements WritableComparable<TQ>{

	private int year;

	private int month;

	private int day;

	private int wd;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWd() {
		return wd;
	}

	public void setWd(int wd) {
		this.wd = wd;
	}

	@Override
	public int compareTo(TQ tq) {

		int r1 = Integer.compare(this.year, tq.year);

		if (r1 == 0) {

			int r2 = Integer.compare(this.month, tq.month);

			if (r2 == 0) {
				return Integer.compare(this.day, tq.day);
			}

			return r2;
		}

		return r1;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.year);
		out.writeInt(this.month);
		out.writeInt(this.day);
		out.writeInt(this.wd);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.year = in.readInt();
		this.month = in.readInt();
		this.day = in.readInt();
		this.wd = in.readInt();
	}

	@Override
	public String toString() {
		return "TQ{" +
				"year=" + year +
				", month=" + month +
				", day=" + day +
				", wd=" + wd +
				'}';
	}
}
