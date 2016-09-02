package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeGlobalResult;
import com.sforce.soap.enterprise.DescribeGlobalSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeGlobal {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeGlobal sample = new DescribeGlobal();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeGlobalSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeGlobalSample() {
		try {
			// Make the describeGlobal() call
			DescribeGlobalResult describeGlobalResult = connection.describeGlobal();
			// Get the sObjects from the describe global result
			DescribeGlobalSObjectResult[] sobjectResults = describeGlobalResult.getSobjects();
			// Write the name of each sObject to the console
			for (int i = 0; i < sobjectResults.length; i++) {
				System.out.println(sobjectResults[i].getName());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}
}
