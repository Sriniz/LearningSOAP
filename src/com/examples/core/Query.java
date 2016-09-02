package com.examples.core;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.AggregateResult;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class Query {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		Query sample = new Query();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		queryRecords();
		queryAllRecords();
		queryAggregateResult();
		// logout
		EnterpriseLogin.logout();
	}

	public void queryRecords() {
		QueryResult qResult = null;
		try {

			// Setting custom batch size
			connection.setQueryOptions(250);

			String soqlQuery = "SELECT FirstName, LastName FROM Contact";
			qResult = connection.query(soqlQuery);
			boolean done = false;
			if (qResult.getSize() > 0) {
				System.out.println("Logged-in user can see a total of " + qResult.getSize() + " contact records.");
				while (!done) {
					SObject[] records = qResult.getRecords();
					for (int i = 0; i < records.length; ++i) {
						Contact con = (Contact) records[i];
						String fName = con.getFirstName();
						String lName = con.getLastName();
						if (fName == null) {
							System.out.println("Contact " + (i + 1) + ": " + lName);
						} else {
							System.out.println("Contact " + (i + 1) + ": " + fName + " " + lName);
						}
					}
					if (qResult.isDone()) {
						done = true;
					} else {
						System.out.println("query more...");
						qResult = connection.queryMore(qResult.getQueryLocator());
					}
				}
			} else {
				System.out.println("No records found.");
			}
			System.out.println("\nQuery succesfully executed.");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void queryAllRecords() {
		// Setting custom batch size
		connection.setQueryOptions(250);
		try {
			String soqlQuery = "SELECT Name, IsDeleted FROM Account";
			QueryResult qr = connection.queryAll(soqlQuery);
			boolean done = false;
			if (qr.getSize() > 0) {
				System.out.println("Logged-in user can see a total of " + qr.getSize()
						+ " contact records (including deleted records).");
				while (!done) {
					SObject[] records = qr.getRecords();
					for (int i = 0; i < records.length; i++) {
						Account account = (Account) records[i];
						boolean isDel = account.getIsDeleted();
						System.out.println("Account " + (i + 1) + ": " + account.getName() + " isDeleted = "
								+ account.getIsDeleted());
					}
					if (qr.isDone()) {
						done = true;
					} else {
						System.out.println("Query more...");
						qr = connection.queryMore(qr.getQueryLocator());
					}
				}
			} else {
				System.out.println("No records found.");
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void queryAggregateResult() {
		try {
			String groupByQuery = "SELECT Account.Name n, " + "MAX(Amount) max, MIN(Amount) min "
					+ "FROM Opportunity GROUP BY Account.Name";
			QueryResult qr = connection.query(groupByQuery);
			if (qr.getSize() > 0) {
				System.out.println("Query returned " + qr.getRecords().length + " results.");
				for (SObject sObj : qr.getRecords()) {
					AggregateResult result = (AggregateResult) sObj;
					System.out.println("aggResult.Account.Name: " + result.getField("n"));
					System.out.println("aggResult.max: " + result.getField("max"));
					System.out.println("aggResult.min: " + result.getField("min"));
					System.out.println();
				}
			} else {
				System.out.println("No results found.");
			}
			System.out.println("\nQuery successfully executed.");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
