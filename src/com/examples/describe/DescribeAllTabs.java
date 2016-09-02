package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.examples.core.Retrieve;
import com.sforce.soap.enterprise.DescribeColor;
import com.sforce.soap.enterprise.DescribeIcon;
import com.sforce.soap.enterprise.DescribeTab;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeAllTabs {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeAllTabs sample = new DescribeAllTabs();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeAllTabsSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeAllTabsSample() {
		try {
			// Describe tabs
			DescribeTab[] tabs = connection.describeAllTabs();
			System.out.println("There are " + tabs.length + " tabs available to you.");
			// Iterate through the returned tabs
			for (int j = 0; j < tabs.length; j++) {
				DescribeTab tab = tabs[j];
				System.out.println("\tTab " + (j + 1) + ":");
				System.out.println("\t\tName: " + tab.getName());
				System.out.println("\t\tAssociated SObject:" + tab.getSobjectName());
				System.out.println("\t\tLabel: " + tab.getLabel());
				System.out.println("\t\tURL: " + tab.getUrl());
				DescribeColor[] tabColors = tab.getColors();
				// Iterate through tab colors as needed
				DescribeIcon[] tabIcons = tab.getIcons();
				// Iterate through tab icons as needed
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
