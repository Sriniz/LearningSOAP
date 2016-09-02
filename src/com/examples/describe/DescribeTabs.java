package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeColor;
import com.sforce.soap.enterprise.DescribeIcon;
import com.sforce.soap.enterprise.DescribeTab;
import com.sforce.soap.enterprise.DescribeTabSetResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeTabs {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeTabs sample = new DescribeTabs();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeTabsSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeTabsSample() {
		try {
			// Describe tabs
			DescribeTabSetResult[] dtsrs = connection.describeTabs();
			System.out.println("There are " + dtsrs.length + " tab sets defined.");
			// For each tab set describe result, get some properties
			for (int i = 0; i < dtsrs.length; i++) {
				System.out.println("Tab Set " + (i + 1) + ":");
				DescribeTabSetResult dtsr = dtsrs[i];
				System.out.println("Label: " + dtsr.getLabel());
				System.out.println("\tLogo URL: " + dtsr.getLogoUrl());
				System.out.println("\tTab selected: " + dtsr.isSelected());
				// Describe the tabs for the tab set
				DescribeTab[] tabs = dtsr.getTabs();
				System.out.println("\tTabs defined: " + tabs.length);
				// Iterate through the returned tabs
				for (int j = 0; j < tabs.length; j++) {
					DescribeTab tab = tabs[j];
					System.out.println("\tTab " + (j + 1) + ":");
					System.out.println("\t\tName: " + tab.getSobjectName());
					System.out.println("\t\tLabel: " + tab.getLabel());
					System.out.println("\t\tURL: " + tab.getUrl());
					DescribeColor[] tabColors = tab.getColors();
					// Iterate through tab colors as needed
					DescribeIcon[] tabIcons = tab.getIcons();
					// Iterate through tab icons as needed
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
