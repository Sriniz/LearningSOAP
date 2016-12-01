package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.FieldType;
import com.sforce.soap.enterprise.PicklistEntry;
import com.sforce.ws.ConnectionException;

public class DescribeSObject {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeSObject sample = new DescribeSObject();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeSObjectSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeSObjectSample() {
		try {
			// Make the describe call
			DescribeSObjectResult describeSObjectResult = connection.describeSObject("Account");
			// Get sObject metadata
			if (describeSObjectResult != null) {
				System.out.println("sObject name: " + describeSObjectResult.getName());
				if (describeSObjectResult.isCreateable())
					System.out.println("Createable");
				if (describeSObjectResult.isCustom())
					System.out.println("Yes...im a custom object");				
				System.out.println("Object Label:"+describeSObjectResult.getLabelPlural());
				// Get the fields
				Field[] fields = describeSObjectResult.getFields();
				System.out.println("Has " + fields.length + " fields");
				// Iterate through each field and gets its properties
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					System.out.println("Field name: " + field.getName());
					System.out.println("Field label: " + field.getLabel());
					// If this is a picklist field, show the picklist values
					if (field.getType().equals(FieldType.picklist)) {
						PicklistEntry[] picklistValues = field.getPicklistValues();
						if (picklistValues != null) {
							System.out.println("Picklist values: ");
							for (int j = 0; j < picklistValues.length; j++) {
								if (picklistValues[j].getLabel() != null) {
									System.out.println("\tItem: " + picklistValues[j].getLabel());
								}
							}
						}
					}
					// If a reference field, show what it references
					if (field.getType().equals(FieldType.reference)) {
						System.out.println("Field references the " + "following objects:");
						String[] referenceTos = field.getReferenceTo();
						for (int j = 0; j < referenceTos.length; j++) {
							System.out.println("\t" + referenceTos[j]);
						}
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
