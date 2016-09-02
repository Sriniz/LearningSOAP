package com.examples.core;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LeadConvert;
import com.sforce.soap.enterprise.LeadConvertResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Lead;
import com.sforce.ws.ConnectionException;

public class ConvertLead {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		ConvertLead sample = new ConvertLead();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		convertLeadRecords();
		// logout
		EnterpriseLogin.logout();
	}

	public String[] convertLeadRecords() {
		String[] result = new String[4];
		try {
			// Create two leads to convert
			Lead[] leads = new Lead[2];
			Lead lead = new Lead();
			lead.setLastName("Konakanchi");
			lead.setFirstName("Sriniz");
			lead.setCompany("Sriniz Company");
			lead.setPhone("(201) 887-8539");
			leads[0] = lead;
			lead = new Lead();
			lead.setLastName("Akkineni");
			lead.setFirstName("Priyanka");
			lead.setCompany("Priyanka Company");
			lead.setPhone("(502) 804-8825");
			leads[1] = lead;
			SaveResult[] saveResults = connection.create(leads);
			// Create a LeadConvert array to be used
			// in the convertLead() call
			LeadConvert[] leadsToConvert = new LeadConvert[saveResults.length];
			for (int i = 0; i < saveResults.length; ++i) {
				if (saveResults[i].isSuccess()) {
					System.out.println("Created new Lead: " + saveResults[i].getId());
					leadsToConvert[i] = new LeadConvert();
					leadsToConvert[i].setConvertedStatus("Closed - Converted");
					leadsToConvert[i].setLeadId(saveResults[i].getId());
					result[0] = saveResults[i].getId();
				} else {
					System.out.println("\nError creating new Lead: " + saveResults[i].getErrors()[0].getMessage());
				}
			}
			// Convert the leads and iterate through the results
			LeadConvertResult[] lcResults = connection.convertLead(leadsToConvert);
			for (int j = 0; j < lcResults.length; ++j) {
				if (lcResults[j].isSuccess()) {
					System.out.println("Lead converted successfully!");
					System.out.println("Account ID: " + lcResults[j].getAccountId());
					System.out.println("Contact ID: " + lcResults[j].getContactId());
					System.out.println("Opportunity ID: " + lcResults[j].getOpportunityId());
				} else {
					System.out.println("\nError converting new Lead: " + lcResults[j].getErrors()[0].getMessage());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return result;
	}

}
