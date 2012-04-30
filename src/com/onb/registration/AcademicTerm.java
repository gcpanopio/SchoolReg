package com.onb.registration;

public class AcademicTerm {
	private final int year;
	private final int term;
	
	public AcademicTerm(int year, int term) {
		this.year = year;
		this.term = term;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getTerm() {
		return term;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AcademicTerm) {
			AcademicTerm otherTerm = (AcademicTerm)obj;
			return otherTerm.year == this.year && otherTerm.term == this.term;
		} 
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.getTerm()*17 + this.getYear();
	}
}
