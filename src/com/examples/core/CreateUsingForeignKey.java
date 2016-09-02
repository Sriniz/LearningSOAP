package com.examples.core;

import java.util.Calendar;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LeadConvert;
import com.sforce.soap.enterprise.LeadConvertResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Lead;
import com.sforce.soap.enterprise.sobject.Opportunity;
import com.sforce.ws.ConnectionException;

public class CreateUsingForeignKey {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		ConvertLead sample = new ConvertLead();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		createForeignKeySample();
		// logout
		EnterpriseLogin.logout();
	}

	public void createForeignKeySample() {
		try {
			Opportunity newOpportunity = new Opportunity();
			newOpportunity.setName("OpportunityWithAccountInsert");
			newOpportunity.setStageName("Prospecting");
			Calendar dt = connection.getServerTimestamp().getTimestamp();
			dt.add(Calendar.DAY_OF_MONTH, 7);
			newOpportunity.setCloseDate(dt);
			// Create the parent reference.
			// Used only for foreign key reference
			// and doesn't contain any other fields.
			Account accountReference = new Account();
			accountReference.setMyExtID__c("SAP111111");
			newOpportunity.setAccount(accountReference);
			// Create the Account object to insert.
			// Same as above but has Name field.
			// Used for the create call.
			Account parentAccount = new Account();
			parentAccount.setName("Hallie");
			parentAccount.setMyExtID__c("SAP111111");
			// Create the account and the opportunity.
			SaveResult[] results = connection.create(new SObject[] { parentAccount, newOpportunity });
			// Check results.
			for (int i = 0; i < results.length; i++) {
				if (results[i].isSuccess()) {
					System.out.println("Successfully created ID: " + results[i].getId());
				} else {
					System.out.println("Error: could not create sobject " + "for array element " + i + ".");
					System.out.println(" The error reported was: " + results[i].getErrors()[0].getMessage() + "\n");
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
