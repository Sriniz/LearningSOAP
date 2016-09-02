package com.examples.soapheaders;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Case;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class EmailHeader {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		EmailHeader sample = new EmailHeader();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		createCaseWithAutoResponse("0039000001yusiz");
		// logout
		EnterpriseLogin.logout();
	}

	public void createCaseWithAutoResponse(String contactId) {
		try {
			connection.setEmailHeader(true, false, false);
			Case c = new Case();
			c.setSubject("Sample Subject");
			c.setContactId(contactId);
			SaveResult[] sr = connection.create(new SObject[] { c });
			// Parse sr array to see if case was created successfully.
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
