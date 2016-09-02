package com.examples.core;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.sobject.*;
import com.sforce.soap.partner.*;
import com.sforce.ws.ConnectionException;
import com.sforce.soap.partner.Error;
import java.util.*;

public class PartnerSamples {

	static PartnerConnection partnerConnection = null;
	String authEndPoint = "https://login.salesforce.com/services/Soap/u/37.0";

	public static void main(String[] args) {

		partnerConnection = PartnerLogin.login();

		String Id;
		// Do a describe global
		describeGlobalSample();
		// Describe an object
		describeSObjectsSample();
		// Retrieve some data using a query
		querySample();
		// Retrieve some data using a search
		searchSample("2018878539");
		// Create some data using a create
		Id = createSample();
		// Update some data using a update
		updateSample(Id);
		// Log out
		PartnerLogin.logout();

	}

	/**
	 * To determine the objects that are available to the logged-in user, the
	 * sample client application executes a describeGlobal call, which returns
	 * all of the objects that are visible to the logged-in user. This call
	 * should not be made more than once per session, as the data returned from
	 * the call likely does not change frequently. The DescribeGlobalResult is
	 * simply echoed to the console.
	 */
	private static void describeGlobalSample() {
		try {
			// describeGlobal() returns an array of object results that
			// includes the object names that are available to the logged-in
			// user.
			com.sforce.soap.partner.DescribeGlobalResult dgr = partnerConnection.describeGlobal();
			System.out.println("\nDescribe Global Results:\n");
			// Loop through the array echoing the object names to the console
			for (int i = 0; i < dgr.getSobjects().length; i++) {
				System.out.println(dgr.getSobjects()[i].getName());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	/**
	 * The following method illustrates the type of metadata information that
	 * can be obtained for each object available to the user. The sample client
	 * application executes a describeSObject call on a given object and then
	 * echoes the returned metadata information to the console. Object metadata
	 * information includes permissions, field types and length and available
	 * values for picklist fields and types for referenceTo fields.
	 */
	private static void describeSObjectsSample() {
		String objectToDescribe = PartnerLogin
				.getUserInput("\nType the name of the object to " + "describe (try Account): ");
		try {
			// Call describeSObjects() passing in an array with one object type
			// name
			DescribeSObjectResult[] dsrArray = partnerConnection.describeSObjects(new String[] { objectToDescribe });
			// Since we described only one sObject, we should have only
			// one element in the DescribeSObjectResult array.
			DescribeSObjectResult dsr = dsrArray[0];
			// First, get some object properties
			System.out.println("\n\nObject Name: " + dsr.getName());
			if (dsr.getCustom())
				System.out.println("Custom Object");
			if (dsr.getLabel() != null)
				System.out.println("Label: " + dsr.getLabel());
			// Get the permissions on the object
			if (dsr.getCreateable())
				System.out.println("Createable");
			if (dsr.getDeletable())
				System.out.println("Deleteable");
			if (dsr.getQueryable())
				System.out.println("Queryable");
			if (dsr.getReplicateable())
				System.out.println("Replicateable");
			if (dsr.getRetrieveable())
				System.out.println("Retrieveable");
			if (dsr.getSearchable())
				System.out.println("Searchable");
			if (dsr.getUndeletable())
				System.out.println("Undeleteable");
			if (dsr.getUpdateable())
				System.out.println("Updateable");
			if (dsr.isCustom())
				System.out.println("Custom");
			else
				System.out.println("Standard");
			System.out.println("Number of fields: " + dsr.getFields().length);
			// Now, retrieve metadata for each field
			for (int i = 0; i < dsr.getFields().length; i++) {
				// Get the field
				com.sforce.soap.partner.Field field = dsr.getFields()[i];
				// Write some field properties
				System.out.println("Field name: " + field.getName());
				System.out.println("\tField Label: " + field.getLabel());
				// This next property indicates that this
				// field is searched when using
				// the name search group in SOSL
				if (field.getNameField())
					System.out.println("\tThis is a name field.");
				if (field.getRestrictedPicklist())
					System.out.println("This is a RESTRICTED picklist field.");
				System.out.println("\tType is: " + field.getType());
				if (field.getLength() > 0)
					System.out.println("\tLength: " + field.getLength());
				if (field.getScale() > 0)
					System.out.println("\tScale: " + field.getScale());
				if (field.getPrecision() > 0)
					System.out.println("\tPrecision: " + field.getPrecision());
				if (field.getDigits() > 0)
					System.out.println("\tDigits: " + field.getDigits());
				if (field.getCustom())
					System.out.println("\tThis is a custom field.");
				// Write the permissions of this field
				if (field.getNillable())
					System.out.println("\tCan be nulled.");
				if (field.getCreateable())
					System.out.println("\tCreateable");
				if (field.getFilterable())
					System.out.println("\tFilterable");
				if (field.getUpdateable())
					System.out.println("\tUpdateable");
				// If this is a picklist field, show the picklist values
				if (field.getType().equals(FieldType.picklist)) {
					System.out.println("\t\tPicklist values: ");
					com.sforce.soap.partner.PicklistEntry[] picklistValues = field.getPicklistValues();
					for (int j = 0; j < field.getPicklistValues().length; j++) {
						System.out.println("\t\tValue: " + picklistValues[j].getValue());
					}
				}
				// If this is a foreign key field (reference),
				// show the values
				if (field.getType().equals(FieldType.reference)) {
					System.out.println("\tCan reference these objects:");
					for (int j = 0; j < field.getReferenceTo().length; j++) {
						System.out.println("\t\t" + field.getReferenceTo()[j]);
					}
				}
				System.out.println("");
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	private static void querySample() {
		try {
			// Set query batch size
			partnerConnection.setQueryOptions(10);
			// SOQL query to use
			String soqlQuery = "SELECT FirstName, LastName FROM Contact";
			// Make the query call and get the query results
			QueryResult qr = partnerConnection.query(soqlQuery);
			boolean done = false;
			int loopCount = 0;
			// Loop through the batches of returned results
			while (!done) {
				System.out.println("Records in results set " + loopCount++ + " - ");
				SObject[] records = qr.getRecords();
				// Process the query results
				for (int i = 0; i < records.length; i++) {
					SObject contact = records[i];
					Object firstName = contact.getField("FirstName");
					Object lastName = contact.getField("LastName");
					if (firstName == null) {
						System.out.println("Contact " + (i + 1) + ": " + lastName);
					} else {
						System.out.println("Contact " + (i + 1) + ": " + firstName + " " + lastName);
					}
				}
				if (qr.isDone()) {
					done = true;
				} else {
					qr = partnerConnection.queryMore(qr.getQueryLocator());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		System.out.println("\nQuery execution completed.");
	}

	public static void searchSample(String phoneNumber) {
		try {
			// Example of phoneNumber format: 4155551212
			String soslQuery = "FIND {" + phoneNumber + "} IN Phone FIELDS " + "RETURNING "
					+ "Contact(Id, Phone, FirstName, LastName), " + "Lead(Id, Phone, FirstName, LastName),"
					+ "Account(Id, Phone, Name)";
			// Perform SOSL query
			SearchResult sResult = partnerConnection.search(soslQuery);
			// Get the records returned by the search result
			SearchRecord[] records = sResult.getSearchRecords();
			// Create lists of objects to hold search result records
			List<SObject> contacts = new ArrayList<SObject>();
			List<SObject> leads = new ArrayList<SObject>();
			List<SObject> accounts = new ArrayList<SObject>();
			// Iterate through the search result records
			// and store the records in their corresponding lists
			// based on record type.
			if (records != null && records.length > 0) {
				for (int i = 0; i < records.length; i++) {
					SObject record = records[i].getRecord();
					if (record.getType().toLowerCase().equals("contact")) {
						contacts.add(record);
					} else if (record.getType().toLowerCase().equals("lead")) {
						leads.add(record);
					} else if (record.getType().toLowerCase().equals("account")) {
						accounts.add(record);
					}
				}
				// Display the contacts that the search returned
				if (contacts.size() > 0) {
					System.out.println("Found " + contacts.size() + " contact(s):");
					for (SObject contact : contacts) {
						System.out.println(contact.getId() + " - " + contact.getField("FirstName") + " "
								+ contact.getField("LastName") + " - " + contact.getField("Phone"));
					}
				}
				// Display the leads that the search returned
				if (leads.size() > 0) {
					System.out.println("Found " + leads.size() + " lead(s):");
					for (SObject lead : leads) {
						System.out.println(lead.getId() + " - " + lead.getField("FirstName") + " "
								+ lead.getField("LastName") + " - " + lead.getField("Phone"));
					}
				}
				// Display the accounts that the search returned
				if (accounts.size() > 0) {
					System.out.println("Found " + accounts.size() + " account(s):");
					for (SObject account : accounts) {
						System.out.println(
								account.getId() + " - " + account.getField("Name") + " - " + account.getField("Phone"));
					}
				}
			} else {
				// The search returned no records
				System.out.println("No records were found for the search.");
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public static String createSample() {
		String result = null;
		try {
			// Create a new sObject of type Contact
			// and fill out its fields.
			SObject contact = new SObject();
			contact.setType("Contact");
			contact.setField("FirstName", "Srinizkumar");
			contact.setField("LastName", "Konakanchi");
			contact.setField("Salutation", "Mr.");
			contact.setField("Phone", "2018878539");
			contact.setField("Title", "Developer");
			// Add this sObject to an array
			SObject[] contacts = new SObject[1];
			contacts[0] = contact;
			// Make a create call and pass it the array of sObjects
			SaveResult[] results = partnerConnection.create(contacts);
			// Iterate through the results list
			// and write the ID of the new sObject
			// or the errors if the object creation failed.
			// In this case, we only have one result
			// since we created one contact.
			for (int j = 0; j < results.length; j++) {
				if (results[j].isSuccess()) {
					result = results[j].getId();
					System.out.println("\nA contact was created with an ID of: " + result);
				} else {
					// There were errors during the create call,
					// go through the errors array and write
					// them to the console
					for (int i = 0; i < results[j].getErrors().length; i++) {
						Error err = results[j].getErrors()[i];
						System.out.println("Errors were found on item " + j);
						System.out.println("Error code: " + err.getStatusCode().toString());
						System.out.println("Error message: " + err.getMessage());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return result;
	}

	public static void updateSample(String id) {
		try {
			// Create an sObject of type contact
			SObject updateContact = new SObject();
			updateContact.setType("Contact");
			// Set the ID of the contact to update
			updateContact.setId(id);
			// Set the Phone field with a new value
			updateContact.setField("Phone", "5028048825");
			// Create another contact that will cause an error
			// because it has an invalid ID.
			SObject errorContact = new SObject();
			errorContact.setType("Contact");
			// Set an invalid ID on purpose
			errorContact.setId("SLFKJLFKJ");
			// Set the value of LastName to null
			errorContact.setFieldsToNull(new String[] { "LastName" });
			// Make the update call by passing an array containing
			// the two objects.
			SaveResult[] saveResults = partnerConnection.update(new SObject[] { updateContact, errorContact });
			// Iterate through the results and write the ID of
			// the updated contacts to the console, in this case one contact.
			// If the result is not successful, write the errors
			// to the console. In this case, one item failed to update.
			for (int j = 0; j < saveResults.length; j++) {
				System.out.println("\nItem: " + j);
				if (saveResults[j].isSuccess()) {
					System.out.println("Contact with an ID of " + saveResults[j].getId() + " was updated.");
				} else {
					// There were errors during the update call,
					// go through the errors array and write
					// them to the console.
					for (int i = 0; i < saveResults[j].getErrors().length; i++) {
						Error err = saveResults[j].getErrors()[i];
						System.out.println("Errors were found on item " + j);
						System.out.println("Error code: " + err.getStatusCode().toString());
						System.out.println("Error message: " + err.getMessage());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}