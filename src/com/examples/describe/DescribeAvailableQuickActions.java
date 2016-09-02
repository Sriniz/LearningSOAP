package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeAvailableQuickActionResult;
import com.sforce.soap.enterprise.EnterpriseConnection;

public class DescribeAvailableQuickActions {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeAvailableQuickActions sample = new DescribeAvailableQuickActions();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		try {
			describeAvailableQuickActionsSample();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// logout
		EnterpriseLogin.logout();
	}

	public void describeAvailableQuickActionsSample() throws Exception {
		DescribeAvailableQuickActionResult[] aResult = connection.describeAvailableQuickActions("Account");
		for (DescribeAvailableQuickActionResult ar : aResult) {
			System.out.println("Action label: " + ar.getLabel());
			System.out.println("Action name: " + ar.getName());
			System.out.println("Action type: " + ar.getType());
		}
	}

}
