package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeQuickActionDefaultValue;
import com.sforce.soap.enterprise.DescribeQuickActionResult;
import com.sforce.soap.enterprise.EnterpriseConnection;

public class DescribeQuickActions {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeQuickActions sample = new DescribeQuickActions();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		try {
			describeQuickActionsSample();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// logout
		EnterpriseLogin.logout();
	}

	public void describeQuickActionsSample() throws Exception {
		DescribeQuickActionResult[] result = connection
				.describeQuickActions(new String[] { "Account.QuickCreateContact", "Account.QuickCreateTask" });
		for (DescribeQuickActionResult r : result) {
			assert r != null;
			DescribeQuickActionDefaultValue[] describeQuickActionDefaultValues = r.getDefaultValues();
			for (DescribeQuickActionDefaultValue defaultValue : describeQuickActionDefaultValues) {
				System.out.println("Target Object Field: " + defaultValue.getField());
				System.out.println("Target Object Field's default Value: " + defaultValue.getDefaultValue());
			}
			System.out.println("Action name: " + r.getName());
			System.out.println("Action label: " + r.getLabel());
			System.out.println("ParentOrContext object: " + r.getContextSobjectType());
			System.out.println("Target object: " + r.getTargetSobjectType());
			System.out.println("Target object record type: " + r.getTargetRecordTypeId());
			System.out.println("Relationship field: " + r.getTargetParentField());
			System.out.println("Quick action type: " + r.getType());
			System.out.println("VF page name for custom actions: " + r.getVisualforcePageName());
			System.out.println("Icon name: " + r.getIconName());
			System.out.println("Icon URL: " + r.getIconUrl());
			System.out.println("Mini icon URL: " + r.getMiniIconUrl());
			assert r.getLayout() != null;
			System.out.println("Height of VF page for custom actions: " + r.getHeight());
			System.out.println("Width of VF page for custom actions: " + r.getWidth());
		}
	}

}
