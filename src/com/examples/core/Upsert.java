package com.examples.core;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class Upsert {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		Upsert sample = new Upsert();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		upsertRecords();
		// logout
		EnterpriseLogin.logout();
	}

	public void upsertRecords() {
		SObject[] upserts = new Account[2];
		Account upsertAccount1 = new Account();
		upsertAccount1.setName("Begonia");
		upsertAccount1.setIndustry("Education");
		upsertAccount1.setMyExtId__c("1111111111");
		upserts[0] = upsertAccount1;
		Account upsertAccount2 = new Account();
		upsertAccount2 = new Account();
		upsertAccount2.setName("Bluebell");
		upsertAccount2.setIndustry("Technology");
		upsertAccount2.setMyExtId__c("2222222222");
		upserts[1] = upsertAccount2;
		try {
			// Invoke the upsert call and save the results.
			// Use External_Id custom field for matching records.
			UpsertResult[] upsertResults = connection.upsert("MyExtId__c", upserts);
			for (UpsertResult result : upsertResults) {
				if (result.isSuccess()) {
					System.out.println("\nUpsert succeeded.");
					System.out.println((result.isCreated() ? "Insert" : "Update") + " was performed.");
					System.out.println("Account ID: " + result.getId());
				} else {
					System.out.println("The Upsert failed because: " + result.getErrors()[0].getMessage());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
