package com.onb.registration;

public enum YearLevel {
	FIRST(15, 18),
	SECOND(18, 24),
	THIRD(18, 24),
	FOURTH(0, 21);
	
	private final int minimumLoadAllowed;
	private final int maximumLoadAllowed;
	
	YearLevel(int minimumLoadAllowed, int maximumLoadAllowed){
		this.minimumLoadAllowed = minimumLoadAllowed;
		this.maximumLoadAllowed = maximumLoadAllowed;
	}
	
	public int getMinimumLoad(){
		return minimumLoadAllowed;
	}
	
	public int getMaximumLoad() {
		return maximumLoadAllowed;
	}
	
	public boolean hasExceededMaximumLoad(int load){
		return load > maximumLoadAllowed;
	}

}
