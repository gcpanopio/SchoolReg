package com.onb.registration;

import java.math.BigDecimal;

public enum Type {
	UNDERGRADUATE ( new BigDecimal( "2000" ) ),
	GRADUATE( new BigDecimal( "4000" ) );
	
	private final BigDecimal typeFee;
	
	private Type( BigDecimal typeFee){
		this.typeFee = typeFee;
	}
	
	public BigDecimal getTypeFee() { return this.typeFee; }
}
