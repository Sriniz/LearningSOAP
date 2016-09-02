package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeLayoutItem;
import com.sforce.soap.enterprise.DescribeLayoutResult;
import com.sforce.soap.enterprise.DescribeLayoutRow;
import com.sforce.soap.enterprise.DescribeLayoutSection;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeLayout {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeLayout sample = new DescribeLayout();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeLayoutSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeLayoutSample() {
		try {
			String objectToDescribe = "Account";
			DescribeLayoutResult dlr = connection.describeLayout(objectToDescribe, null, null);
			System.out.println(
					"There are " + dlr.getLayouts().length + " layouts for the " + objectToDescribe + " object.");
			// Get all the layouts for the sObject
			for (int i = 0; i < dlr.getLayouts().length; i++) {
				com.sforce.soap.enterprise.DescribeLayout layout = dlr.getLayouts()[i];
				DescribeLayoutSection[] detailLayoutSectionList = layout.getDetailLayoutSections();
				System.out.println(" There are " + detailLayoutSectionList.length + " detail layout sections");
				DescribeLayoutSection[] editLayoutSectionList = layout.getEditLayoutSections();
				System.out.println(" There are " + editLayoutSectionList.length + " edit layout sections");
				// Write the headings of the detail layout sections
				for (int j = 0; j < detailLayoutSectionList.length; j++) {
					System.out.println(j + " This detail layout section has a heading of "
							+ detailLayoutSectionList[j].getHeading());
				}
				// Write the headings of the edit layout sections
				for (int x = 0; x < editLayoutSectionList.length; x++) {
					System.out.println(
							x + " This edit layout section has a heading of " + editLayoutSectionList[x].getHeading());
				}
				// For each edit layout section, get its details.
				for (int k = 0; k < editLayoutSectionList.length; k++) {
					DescribeLayoutSection els = editLayoutSectionList[k];
					System.out.println("Edit layout section heading: " + els.getHeading());
					DescribeLayoutRow[] dlrList = els.getLayoutRows();
					System.out.println("This edit layout section has " + dlrList.length + " layout rows.");
					for (int m = 0; m < dlrList.length; m++) {
						DescribeLayoutRow lr = dlrList[m];
						System.out.println(" This row has " + lr.getNumItems() + " layout items.");
						DescribeLayoutItem[] dliList = lr.getLayoutItems();
						for (int n = 0; n < dliList.length; n++) {
							DescribeLayoutItem li = dliList[n];
							if ((li.getLayoutComponents() != null) && (li.getLayoutComponents().length > 0)) {
								System.out.println("\tLayout item " + n + ", layout component: "
										+ li.getLayoutComponents()[0].getValue());
							} else {
								System.out.println("\tLayout item " + n + ", no layout component");
							}
						}
					}
				}
			}
			// Get record type mappings
			if (dlr.getRecordTypeMappings() != null) {
				System.out.println("There are " + dlr.getRecordTypeMappings().length + " record type mappings for the "
						+ objectToDescribe + " object");
			} else {
				System.out.println("There are no record type mappings for the " + objectToDescribe + " object.");
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
