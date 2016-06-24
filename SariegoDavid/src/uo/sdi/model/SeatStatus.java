package uo.sdi.model;

public enum SeatStatus {
	ACCEPTED,
	EXCLUDED,
	SIN_PLAZA;
	
	public boolean isAccepted(){
	    return this.equals(SeatStatus.ACCEPTED);
	}
}



