package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeSearchScopeOrderResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeSearchScopeOrder {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeSearchScopeOrder sample = new DescribeSearchScopeOrder();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeSearchScopeOrderSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeSearchScopeOrderSample() {
		try {
			// Get the order of objects in search smart scope for the logged-in
			// user
			DescribeSearchScopeOrderResult[] describeSearchScopeOrderResults = connection.describeSearchScopeOrder();
			// Iterate through the results and display the name of each object
			for (int i = 0; i < describeSearchScopeOrderResults.length; i++) {
				System.out.println(describeSearchScopeOrderResults[i].getName());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
