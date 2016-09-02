package com.examples.soapheaders;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class AllOrNoneHeader {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		AllOrNoneHeader sample = new AllOrNoneHeader();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		allOrNoneHeaderSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void allOrNoneHeaderSample() {
		try {
			// Create the first contact.
			SObject[] sObjects = new SObject[2];
			Contact contact1 = new Contact();
			contact1.setFirstName("Robin");
			contact1.setLastName("Van Persie");
			// Create the second contact. This contact doesn't
			// have a value for the required
			// LastName field so the create will fail.
			Contact contact2 = new Contact();
			contact2.setFirstName("Ashley");
			sObjects[0] = contact1;
			sObjects[1] = contact2;
			// Set the SOAP header to roll back the create unless
			// all contacts are successfully created.
			connection.setAllOrNoneHeader(true);
			// Attempt to create the two contacts.
			SaveResult[] sr = connection.create(sObjects);
			for (int i = 0; i < sr.length; i++) {
				if (sr[i].isSuccess()) {
					System.out.println("Successfully created contact with id: " + sr[i].getId() + ".");
				} else {
					// Note the error messages as the operation was rolled back
					// due to the all or none header.
					System.out.println("Error creating contact: " + sr[i].getErrors()[0].getMessage());
					System.out.println("Error status code: " + sr[i].getErrors()[0].getStatusCode());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
