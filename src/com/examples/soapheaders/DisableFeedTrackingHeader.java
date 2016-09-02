package com.examples.soapheaders;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class DisableFeedTrackingHeader {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DisableFeedTrackingHeader sample = new DisableFeedTrackingHeader();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		disableFeedTrackingHeaderSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void disableFeedTrackingHeaderSample() {
		try {
			// Insert a large number of accounts.
			SObject[] sObjects = new SObject[500];
			for (int i = 0; i < 500; i++) {
				Account a = new Account();
				a.setName("Account-Sriniz" + i);
				sObjects[i] = a;
			}
			// Set the SOAP header to disable feed tracking to avoid generating
			// a
			// large number of feed items because of this bulk operation.
			connection.setDisableFeedTrackingHeader(true);
			// Perform the bulk create. This won't result in 500 feed items,
			// which
			// would otherwise be generated without the
			// DisableFeedTrackingHeader.
			SaveResult[] sr = connection.create(sObjects);
			for (int i = 0; i < sr.length; i++) {
				if (sr[i].isSuccess()) {
					System.out.println("Successfully created account with id: " + sr[i].getId() + ".");
				} else {
					System.out.println("Error creating account: " + sr[i].getErrors()[0].getMessage());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
