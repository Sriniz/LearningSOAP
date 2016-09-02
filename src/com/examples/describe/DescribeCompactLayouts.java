package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeCompactLayout;
import com.sforce.soap.enterprise.DescribeCompactLayoutsResult;
import com.sforce.soap.enterprise.DescribeLayoutButton;
import com.sforce.soap.enterprise.DescribeLayoutItem;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.RecordTypeCompactLayoutMapping;
import com.sforce.ws.ConnectionException;

public class DescribeCompactLayouts {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeCompactLayouts sample = new DescribeCompactLayouts();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		DescribeCompactLayoutsSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void DescribeCompactLayoutsSample() {
		try {
			String objectToDescribe = "Account";
			DescribeCompactLayoutsResult compactLayoutResult = connection.describeCompactLayouts(objectToDescribe,
					null);
			System.out.println("There are " + compactLayoutResult.getCompactLayouts().length
					+ " compact layouts for the " + objectToDescribe + " object.");
			// Get all the compact layouts for the sObject
			for (int i = 0; i < compactLayoutResult.getCompactLayouts().length; i++) {
				DescribeCompactLayout cLayout = compactLayoutResult.getCompactLayouts()[i];
				System.out.println(" There is a compact layout with name: " + cLayout.getName());
				DescribeLayoutItem[] fieldItems = cLayout.getFieldItems();
				System.out.println(" There are " + fieldItems.length + " fields in this compact layout.");
				// Write field items
				for (int j = 0; j < fieldItems.length; j++) {
					System.out.println(j + " This compact layout has a field with name: " + fieldItems[j].getLabel());
				}
				DescribeLayoutItem[] imageItems = cLayout.getImageItems();
				System.out.println(" There are " + imageItems.length + " image fields in this compact layout.");
				// Write the image items
				for (int j = 0; j < imageItems.length; j++) {
					System.out.println(
							j + " This compact layout has an image field with name:" + imageItems[j].getLabel());
				}
				DescribeLayoutButton[] actions = cLayout.getActions();
				System.out.println(" There are " + actions.length + " buttons in this compact layout.");
				// Write the action buttons
				for (int j = 0; j < actions.length; j++) {
					System.out.println(j + " This compact layout has a button with name: " + actions[j].getLabel());
				}
				System.out.println(
						"This object's default compact layout is: " + compactLayoutResult.getDefaultCompactLayoutId());
				RecordTypeCompactLayoutMapping[] mappings = compactLayoutResult.getRecordTypeCompactLayoutMappings();
				System.out.println("There are " + mappings.length + " record type to compact layout mapping for the "
						+ objectToDescribe + " object.");
				for (int j = 0; j < mappings.length; j++) {
					System.out.println(j + " Record type " + mappings[j].getRecordTypeId()
							+ " is mapped to compact layout " + mappings[j].getCompactLayoutId());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
