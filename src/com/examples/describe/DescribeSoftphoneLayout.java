package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutCallType;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutInfoField;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutItem;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutResult;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutSection;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeSoftphoneLayout {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeSoftphoneLayout sample = new DescribeSoftphoneLayout();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeSoftphoneLayout();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeSoftphoneLayout() {
		try {
			DescribeSoftphoneLayoutResult result = connection.describeSoftphoneLayout();
			System.out.println("ID of retrieved Softphone layout: " + result.getId());
			System.out.println("Name of retrieved Softphone layout: " + result.getName());
			System.out.println("\nContains following " + "Call Type Layouts\n");
			for (DescribeSoftphoneLayoutCallType type : result.getCallTypes()) {
				System.out.println("Layout for " + type.getName() + " calls");
				System.out.println("\tCall-related fields:");
				for (DescribeSoftphoneLayoutInfoField field : type.getInfoFields()) {
					System.out.println("\t\t{" + field.getName());
				}
				System.out.println("\tDisplayed Objects:");
				for (DescribeSoftphoneLayoutSection section : type.getSections()) {
					System.out.println(
							"\t\tFor entity " + section.getEntityApiName() + " following records are displayed:");
					for (DescribeSoftphoneLayoutItem item : section.getItems()) {
						System.out.println("\t\t\t" + item.getItemApiName());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
