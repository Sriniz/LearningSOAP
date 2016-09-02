package com.examples.soapheaders;

import java.util.Calendar;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.OwnerChangeOption;
import com.sforce.soap.enterprise.OwnerChangeOptionType;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Note;
import com.sforce.soap.enterprise.sobject.Opportunity;
import com.sforce.soap.enterprise.sobject.Task;

public class OwnerChangeOptionsHeader {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		OwnerChangeOptionsHeader sample = new OwnerChangeOptionsHeader();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		ownerChangeOptionsHeaderSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void ownerChangeOptionsHeaderSample() {
		// Create account. Accounts don't transfer activities, notes, or
		// attachments by default
		Account account = new Account();
		account.setName("Account");
		com.sforce.soap.enterprise.SaveResult[] sr = connection.create(new com.sforce.soap.enterprise.sobject.SObject[] { account });
		String accountId = null;
		if (sr[0].isSuccess()) {
			System.out.println("Successfully saved the account");
			accountId = sr[0].getId();
			// Create a note, a task, and an opportunity for the account
			Note note = new Note();
			note.setTitle("Note Title");
			note.setBody("Note Body");
			note.setParentId(accountId);
			Task task = new Task();
			task.setWhatId(accountId);
			Opportunity opportunity = new Opportunity();
			opportunity.setName("Opportunity");
			opportunity.setStageName("Prospecting");
			Calendar dt = connection.getServerTimestamp().getTimestamp();
			dt.add(Calendar.DAY_OF_MONTH, 7);
			opportunity.setCloseDate(dt);
			opportunity.setAccountId(accountId);
			sr = connection.create(new com.sforce.soap.enterprise.sobject.SObject[] { note, task, opportunity });
			if (sr[0].isSuccess()) {
				System.out.println("Successfully saved the note, task, and opportunity");
				com.sforce.soap.enterprise.QueryResult qr = connection
						.query("SELECT Id FROM User WHERE FirstName = 'Jane' AND LastName = 'Doe'");
				String newOwnerId = qr.getRecords()[0].getId();
				account.setId(accountId);
				account.setOwnerId(newOwnerId);
				// Set owner change options so account's child note, task, and
				// opportunity transfer to new owner
				OwnerChangeOption opt1 = new OwnerChangeOption();
				opt1.setExecute(true);
				opt1.setType(OwnerChangeOptionType.TransferOwnedOpenOpportunities); // Transfer
																					// Open
																					// opportunities
																					// owned
																					// by
																					// the
																					// account's
																					// owner
				OwnerChangeOption opt2 = new OwnerChangeOption();
				opt2.setExecute(true);
				opt2.setType(OwnerChangeOptionType.TransferOpenActivities);
				OwnerChangeOption opt3 = new OwnerChangeOption();
				opt3.setExecute(true);
				opt3.setType(OwnerChangeOptionType.TransferNotesAndAttachments);
				connection.setOwnerChangeOptions(new OwnerChangeOption[] { opt1, opt2, opt3 });
				connection.update(new com.sforce.soap.enterprise.sobject.SObject[] { account });
				// The account's note, task, and opportunity should be
				// transferred to the new owner.
			}
		} else {
			System.out.println("Account save failed: " + sr[0].getErrors().toString());
		}

	}

}
