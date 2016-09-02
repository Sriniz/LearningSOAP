package com.examples.core;

import java.util.ArrayList;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SearchRecord;
import com.sforce.soap.enterprise.SearchResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.Lead;
import com.sforce.soap.enterprise.sobject.SObject;

public class Search {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		Search sample = new Search();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		searchSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void searchSample() {
		try {
			// Perform the search using the SOSL query.
			SearchResult sr = connection
					.search("FIND {2018878539} IN Phone FIELDS RETURNING " + "Contact(Id, Phone, FirstName, LastName), "
							+ "Lead(Id, Phone, FirstName, LastName), " + "Account(Id, Phone, Name)");
			// Get the records from the search results.
			SearchRecord[] records = sr.getSearchRecords();
			ArrayList<Contact> contacts = new ArrayList<Contact>();
			ArrayList<Lead> leads = new ArrayList<Lead>();
			ArrayList<Account> accounts = new ArrayList<Account>();
			// For each record returned, find out if it's a
			// contact, lead, or account and add it to the
			// appropriate array, then write the records
			// to the console.
			if (records.length > 0) {
				for (int i = 0; i < records.length; i++) {
					SObject record = records[i].getRecord();
					if (record instanceof Contact) {
						contacts.add((Contact) record);
					} else if (record instanceof Lead) {
						leads.add((Lead) record);
					} else if (record instanceof Account) {
						accounts.add((Account) record);
					}
				}
				System.out.println("Found " + contacts.size() + " contacts.");
				for (Contact c : contacts) {
					System.out.println(
							c.getId() + ", " + c.getFirstName() + ", " + c.getLastName() + ", " + c.getPhone());
				}
				System.out.println("Found " + leads.size() + " leads.");
				for (Lead d : leads) {
					System.out.println(
							d.getId() + ", " + d.getFirstName() + ", " + d.getLastName() + ", " + d.getPhone());
				}
				System.out.println("Found " + accounts.size() + " accounts.");
				for (Account a : accounts) {
					System.out.println(a.getId() + ", " + a.getName() + ", " + a.getPhone());
				}
			} else {
				System.out.println("No records were found for the search.");
			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
	}
}
