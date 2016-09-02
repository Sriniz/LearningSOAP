package com.examples.soapheaders;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class MRUHeader {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		MRUHeader sample = new MRUHeader();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		mruHeaderSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void mruHeaderSample() {
		connection.setMruHeader(true);
		Account account = new Account();
		account.setName("This will be in the MRU");
		try {
			SaveResult[] sr = connection.create(new SObject[] { account });
			System.out.println("ID of account added to MRU: " + sr[0].getId());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
