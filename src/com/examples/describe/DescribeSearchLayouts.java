package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeColumn;
import com.sforce.soap.enterprise.DescribeSearchLayoutResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeSearchLayouts {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeSearchLayouts sample = new DescribeSearchLayouts();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeSearchLayoutSample(new String[] { "Account" });
		// logout
		EnterpriseLogin.logout();
	}

	public void describeSearchLayoutSample(String[] sObjectTypes) {
		try {
			// Get the search layout of Account and Group
			DescribeSearchLayoutResult[] searchLayoutResults = connection.describeSearchLayouts(sObjectTypes);
			// Iterate through the results and display the label of each column
			for (int i = 0; i < sObjectTypes.length; i += 1) {
				String sObjectType = sObjectTypes[i];
				DescribeSearchLayoutResult result = searchLayoutResults[i];
				System.out.println("Top label for search results for " + sObjectType + "is " + result.getLabel()
						+ " and should display " + result.getLimitRows() + " rows");
				System.out.println("Column labels for search results for " + sObjectType + " are: ");
				for (DescribeColumn column : result.getSearchColumns()) {
					System.out.println(column.getLabel());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
