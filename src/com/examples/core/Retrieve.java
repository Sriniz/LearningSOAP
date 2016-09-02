package com.examples.core;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class Retrieve {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		Retrieve sample = new Retrieve();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		retrieveRecords(new String[] {"0019000000L9Pp6"});
		// logout
		EnterpriseLogin.logout();
	}

	public void retrieveRecords(String[] ids) {
		try {
			SObject[] sObjects = connection.retrieve("ID, Name, Website", "Account", ids);
			// Verify that some objects were returned.
			// Even though we began with valid object IDs,
			// someone else might have deleted them in the meantime.
			if (sObjects != null) {
				for (int i = 0; i < sObjects.length; i++) {
					// Cast the SObject into an Account object
					Account retrievedAccount = (Account) sObjects[i];
					if (retrievedAccount != null) {
						System.out.println("Account ID: " + retrievedAccount.getId());
						System.out.println("Account Name: " + retrievedAccount.getName());
						System.out.println("Account Website: " + retrievedAccount.getWebsite());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
