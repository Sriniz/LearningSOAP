package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeCompactLayout;
import com.sforce.soap.enterprise.DescribeLayoutButton;
import com.sforce.soap.enterprise.DescribeLayoutItem;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribePrimaryCompactLayout {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribePrimaryCompactLayout sample = new DescribePrimaryCompactLayout();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describePrimaryCompactLayoutsSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describePrimaryCompactLayoutsSample() {
		try {
			String[] objectsToDescribe = new String[] { "Account", "Lead" };
			DescribeCompactLayout[] primaryCompactLayouts = connection.describePrimaryCompactLayouts(objectsToDescribe);
			for (int i = 0; i < primaryCompactLayouts.length; i++) {
				DescribeCompactLayout cLayout = primaryCompactLayouts[i];
				System.out.println(" There is a compact layout with name: " + cLayout.getName());
				// Write the objectType
				System.out
						.println(" This compact layout is the primary compact layout for: " + cLayout.getObjectType());
				DescribeLayoutItem[] fieldItems = cLayout.getFieldItems();
				System.out.println(" There are " + fieldItems.length + " fields in this compact	layout.");
				// Write field items
				for (int j = 0; j < fieldItems.length; j++) {
					System.out.println(j + " This compact layout has a field with name: " + fieldItems[j].getLabel());
				}
				DescribeLayoutItem[] imageItems = cLayout.getImageItems();
				System.out.println(" There are " + imageItems.length + " image fields in this compact layout.");
				// Write the image items
				for (int j = 0; j < imageItems.length; j++) {
					System.out.println(
							j + " This compact layout has an image field with name: " + imageItems[j].getLabel());
				}
				DescribeLayoutButton[] actions = cLayout.getActions();
				System.out.println(" There are " + actions.length + " buttons in this compact layout.");
				// Write the action buttons
				for (int j = 0; j < actions.length; j++) {
					System.out.println(j + " This compact layout has a button with name: " + actions[j].getLabel());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
