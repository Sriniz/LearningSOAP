package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeApprovalLayoutResult;
import com.sforce.soap.enterprise.DescribeLayoutItem;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeApprovalLayout {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeApprovalLayout sample = new DescribeApprovalLayout();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeApprovalLayoutSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeApprovalLayoutSample() {
		try {
			String objectToDescribe = "Account";
			DescribeApprovalLayoutResult approvalLayoutResult = connection.describeApprovalLayout(objectToDescribe,
					null);
			System.out.print("There are " + approvalLayoutResult.getApprovalLayouts().length);
			System.out.println(" approval layouts for the " + objectToDescribe + " object.");
			// Get all the approval layouts for the sObject
			for (int i = 0; i < approvalLayoutResult.getApprovalLayouts().length; i++) {
				com.sforce.soap.enterprise.DescribeApprovalLayout aLayout = approvalLayoutResult
						.getApprovalLayouts()[i];
				System.out.println(" There is an approval layout with name: " + aLayout.getName());
				DescribeLayoutItem[] layoutItems = aLayout.getLayoutItems();
				System.out.print(" There are " + layoutItems.length);
				System.out.println(" fields in this approval layout.");
				for (int j = 0; j < layoutItems.length; j++) {
					System.out.print("This approval layout has a field with name: ");
					System.out.println(layoutItems[j].getLabel());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}
}
