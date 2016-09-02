package com.examples.core;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.MergeRequest;
import com.sforce.soap.enterprise.MergeResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Note;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class MergeAccounts {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		MergeAccounts sample = new MergeAccounts();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		mergeRecords();
		// logout
		EnterpriseLogin.logout();
	}

	public Boolean mergeRecords() {
		Boolean success = false;
		// Array to hold the results
		String[] accountIds = new String[2];
		try {
			// Create two accounts to merge
			Account[] accounts = new Account[2];
			Account masterAccount = new Account();
			masterAccount.setName("MasterAccount");
			masterAccount.setDescription("The Account record to merge with.");
			accounts[0] = masterAccount;
			Account accountToMerge = new Account();
			accountToMerge.setName("AccountToMerge");
			accountToMerge.setDescription("The Account record that will be merged.");
			accounts[1] = accountToMerge;
			SaveResult[] saveResults = connection.create(accounts);
			if (saveResults.length > 0) {
				for (int i = 0; i < saveResults.length; i++) {
					if (saveResults[i].isSuccess()) {
						accountIds[i] = saveResults[i].getId();
						System.out.println("Created Account ID: " + accountIds[i]);
					} else {
						// If any account is not created,
						// print the error returned and exit
						System.out.println("An error occurred while creating account." + " Error message: "
								+ saveResults[i].getErrors()[0].getMessage());
						return success;
					}
				}
			}
			// Set the Ids of the accounts
			masterAccount.setId(accountIds[0]);
			accountToMerge.setId(accountIds[1]);
			// Attach a note to the account to be merged with the master,
			// which will get re-parented after the merge
			Note note = new Note();
			System.out.println("Attaching note to record " + accountIds[1]);
			note.setParentId(accountIds[1]);
			note.setTitle("Merged Notes");
			note.setBody("This note will be moved to the " + "MasterAccount during merge");
			SaveResult[] sRes = connection.create(new SObject[] { note });
			if (sRes[0].isSuccess()) {
				System.out.println("Created Note record.");
			} else {
				com.sforce.soap.enterprise.Error[] errors = sRes[0].getErrors();
				System.out.println("Could not create Note record: " + errors[0].getMessage());
			}
			// Perform the merge
			MergeRequest mReq = new MergeRequest();
			masterAccount.setDescription("Was merged");
			mReq.setMasterRecord(masterAccount);
			mReq.setRecordToMergeIds(new String[] { saveResults[1].getId() });
			MergeResult mRes = connection.merge(new MergeRequest[] { mReq })[0];
			if (mRes.isSuccess()) {
				System.out.println("Merge successful.");
				// Write the IDs of merged records
				for (String mergedId : mRes.getMergedRecordIds()) {
					System.out.println("Merged Record ID: " + mergedId);
				}
				// Write the updated child records. (In this case the note.)
				System.out.println("Child records updated: " + mRes.getUpdatedRelatedIds().length);
				success = true;
			} else {
				System.out.println("Failed to merge records. Error message: " + mRes.getErrors()[0].getMessage());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return success;
	}
}
