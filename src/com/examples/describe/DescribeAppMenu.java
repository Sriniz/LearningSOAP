package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.AppMenuType;
import com.sforce.soap.enterprise.DescribeAppMenuItem;
import com.sforce.soap.enterprise.DescribeAppMenuResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeAppMenu {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeAppMenu sample = new DescribeAppMenu();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeAppMenu();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeAppMenu() {
		try {
			// The following two lines are equivalent
			//DescribeAppMenuResult describe = connection.describeAppMenu("AppSwitcher", "");
			DescribeAppMenuResult appMenu = connection.describeAppMenu(AppMenuType.AppSwitcher,"");
			for (DescribeAppMenuItem menuItem : appMenu.getAppMenuItems()) {
				System.out.println("Label:"+menuItem.getLabel());
				System.out.println("URL:"+menuItem.getUrl());
				if (menuItem.getType() == "Tab.apexPage") {
					String visualforceUrl = menuItem.getContent();
					System.out.println("URL to Visualforce page: " + visualforceUrl);
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
