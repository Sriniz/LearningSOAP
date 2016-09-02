package com.examples.soapheaders;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class AllowFieldTruncation {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		AllowFieldTruncation sample = new AllowFieldTruncation();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		allowFieldTruncationSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void allowFieldTruncationSample() {
		try {
			Account account = new Account();
			// Construct a string that is 256 characters long.
			// Account.Name's limit is 255 characters.
			String accName = "";
			for (int i = 0; i < 256; i++) {
				accName += "a";
			}
			account.setName(accName);
			// Construct an array of SObjects to hold the accounts.
			SObject[] sObjects = new SObject[1];
			sObjects[0] = account;
			// Attempt to create the account. It will fail in API version 15.0
			// and above because the account name is too long.
			SaveResult[] results = connection.create(sObjects);
			System.out.println("The call failed because: " + results[0].getErrors()[0].getMessage());
			// Now set the SOAP header to allow field truncation.
			connection.setAllowFieldTruncationHeader(true);
			// Attempt to create the account now.
			results = connection.create(sObjects);
			System.out.println("The call: " + results[0].isSuccess());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
