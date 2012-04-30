package com.onb.registration;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.onb.registration.Day.*;

import static com.onb.registration.TimeSlot.*;

public class ScheduleTest {

	@Test
	public void ScheduleStoresDayAndTimeSlot() {
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		assertEquals( MONTHU, schedule.getDay() );
		assertEquals( T_0830_1000, schedule.getTimeSlot() );
	}
	
	@Test ( expected = IllegalArgumentException.class )
	public void ScheduleWithNullArgumentsReturnsException() {
		new Schedule(  null, T_0830_1000 );
	}
	
	@Test
	public void CheckIfSameScheduleHasConflict() {
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		Schedule scheduleCopy = new Schedule( MONTHU, T_0830_1000 );
		assertTrue( schedule.hasConflict( scheduleCopy ) );
	} 

}
