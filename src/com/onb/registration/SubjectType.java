package com.onb.registration;

import java.math.BigDecimal;

public enum SubjectType {
	UNDERGRADUATE( new BigDecimal( "2000.00" ) ),
	GRADUATE( new BigDecimal( "4000.00" ) );
	
	private BigDecimal fee;
	
	private SubjectType( BigDecimal fee) {
		this.fee = fee;
	}
	
	public BigDecimal getSubjectFee() {
		return fee.setScale(2);
	}
	
	public BigDecimal computeTotalCost( int units ) {
		return fee.multiply( new BigDecimal( units ) ).setScale(2); 
	}
}
