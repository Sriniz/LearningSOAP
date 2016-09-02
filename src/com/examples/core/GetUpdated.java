package com.examples.core;

import java.util.GregorianCalendar;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUpdatedResult;
import com.sforce.ws.ConnectionException;

public class GetUpdated {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		GetUpdated sample = new GetUpdated();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		getUpdatedRecords();
		// logout
		EnterpriseLogin.logout();
	}

	public void getUpdatedRecords() {
		try {
			GregorianCalendar endTime = (GregorianCalendar) connection.getServerTimestamp().getTimestamp();
			GregorianCalendar startTime = (GregorianCalendar) endTime.clone();
			// Subtract 60 minutes from the server time so that we have
			// a valid time frame.
			startTime.add(GregorianCalendar.MINUTE, -60);
			System.out.println("Checking updates as of: " + startTime.getTime().toString());
			// Get the updated accounts within the specified time frame
			GetUpdatedResult ur = connection.getUpdated("Account", startTime, endTime);
			System.out.println("GetUpdateResult: " + ur.getIds().length);
			// Write the results
			if (ur.getIds() != null && ur.getIds().length > 0) {
				for (int i = 0; i < ur.getIds().length; i++) {
					System.out.println(ur.getIds()[i] + " was updated between " + startTime.getTime().toString()
							+ " and " + endTime.getTime().toString());
				}
			} else {
				System.out.println("No updates to accounts in " + "the last 60 minutes.");
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
