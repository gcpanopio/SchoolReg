package com.onb.registration;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import static com.onb.registration.SubjectType.*;

import org.junit.Test;

import com.onb.registration.exception.SubjectCreationException;

public class SubjectTest {

	@Test
	public void SubjectStoresCourseCodeAndSubjectType() {
		Subject subject = new Subject( 101, UNDERGRADUATE );
		assertEquals( 101, subject.getCode() );
		assertEquals( UNDERGRADUATE, subject.getSubjectType() );
	}
	
	@Test ( expected = IllegalArgumentException.class )
	public void SubjectWithNullArgumentsReturnException() {
		new Subject( 101, null );
	}
	
	@Test
	public void NumberOfUnitsCanBeEditedSuccessfully() {
		Subject subject = new Subject( 101, UNDERGRADUATE );
		assertEquals( 3, subject.getUnits() );
		subject.setUnits(2);
		assertEquals( 2, subject.getUnits() );
	}
	
	@Test
	public void CostOfSubjectComputedCorrectly() {
		Subject subject = new Subject( 101, UNDERGRADUATE );
		BigDecimal expected = new BigDecimal( "6000.00" );
		assertEquals(expected, subject.getTotalCost());
	}
	
	@Test
	public void AddPrerequisiteSuccessfully() throws SubjectCreationException {
		Subject prereq = new Subject( 101, UNDERGRADUATE );
		Subject subject = new Subject( 102, UNDERGRADUATE );
		subject.addPrerequisite(prereq);
		assertTrue(subject.hasPrerequisites());
	}
	
	@Test (expected = SubjectCreationException.class)
	public void AddingSamePrerequisitesThrowsException() throws SubjectCreationException {
		Subject prereq = new Subject( 101, UNDERGRADUATE );
		Subject subject = new Subject( 102, UNDERGRADUATE );
		subject.addPrerequisite(prereq);
		subject.addPrerequisite(prereq);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void AddingNullPrerequisitesThrowsException() throws SubjectCreationException {;
		Subject subject = new Subject( 101, UNDERGRADUATE );
		subject.addPrerequisite(null);
	}

}
