package com.examples.core;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.UndeleteResult;
import com.sforce.ws.ConnectionException;

public class Undelete {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		Undelete sample = new Undelete();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		undeleteRecords();
		// logout
		EnterpriseLogin.logout();
	}

	public void undeleteRecords() {
		try {
			// Get the accounts that were last deleted
			// (up to 5 accounts)
			QueryResult qResult = connection.queryAll("SELECT Id, SystemModstamp FROM "
					+ "Account WHERE IsDeleted=true " + "ORDER BY SystemModstamp DESC LIMIT 5");
			String[] Ids = new String[qResult.getSize()];
			// Get the IDs of the deleted records
			for (int i = 0; i < qResult.getSize(); i++) {
				Ids[i] = qResult.getRecords()[i].getId();
			}
			// Restore the records
			UndeleteResult[] undelResults = connection.undelete(Ids);
			// Check the results
			for (UndeleteResult result : undelResults) {
				if (result.isSuccess()) {
					System.out.println("Undeleted Account ID: " + result.getId());
				} else {
					if (result.getErrors().length > 0) {
						System.out.println("Error message: " + result.getErrors()[0].getMessage());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
