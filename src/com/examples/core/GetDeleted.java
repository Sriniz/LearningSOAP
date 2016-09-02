package com.examples.core;

import java.util.GregorianCalendar;

import com.sforce.soap.enterprise.DeletedRecord;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetDeletedResult;
import com.sforce.ws.ConnectionException;

public class GetDeleted {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		GetDeleted sample = new GetDeleted();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		getDeletedRecords();
		// logout
		EnterpriseLogin.logout();
	}

	public void getDeletedRecords() {
		try {
			GregorianCalendar endTime = (GregorianCalendar) connection.getServerTimestamp().getTimestamp();
			GregorianCalendar startTime = (GregorianCalendar) endTime.clone();
			// Subtract 60 minutes from the server time so that we have
			// a valid time frame.
			startTime.add(GregorianCalendar.MINUTE, -60);
			System.out.println("Checking deletes at or after: " + startTime.getTime().toString());
			// Get records deleted during the specified time frame.
			GetDeletedResult gdResult = connection.getDeleted("Account", startTime, endTime);
			System.out.println("Earliest date available - "+gdResult.getEarliestDateAvailable());
			System.out.println("Latest date covered - "+gdResult.getLatestDateCovered());
			// Check the number of records contained in the results,
			// to check if something was deleted in the 60 minute span.
			DeletedRecord[] deletedRecords = gdResult.getDeletedRecords();
			if (deletedRecords != null && deletedRecords.length > 0) {
				for (int i = 0; i < deletedRecords.length; i++) {
					DeletedRecord dr = deletedRecords[i];
					System.out.println(dr.getId() + " was deleted on " + dr.getDeletedDate().getTime().toString());
				}
			} else {
				System.out.println("No deletions of Account records in " + "the last 60 minutes.");
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}
}
