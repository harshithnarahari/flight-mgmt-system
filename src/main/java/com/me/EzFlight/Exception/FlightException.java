package com.me.EzFlight.Exception;

public class FlightException extends Exception {

	public FlightException(String message) {
		super(message);
	}

	public FlightException(String message, Throwable cause) {
		super(message, cause);
	}

}
