package com.onb.registration;

public enum Scholarship {
	NONE (1.0),
	HALF (0.5),
	Full (0.0);
	
	private final double percentageOfPayment;
	
	Scholarship(double percentageOfPayment) {
		this.percentageOfPayment = percentageOfPayment;
	}
	
	public double getPercentageOfPayment() {
		return percentageOfPayment;
	}
}
