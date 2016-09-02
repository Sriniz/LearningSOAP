package com.examples.util;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.SendEmailResult;
import com.sforce.soap.enterprise.sobject.Attachment;
import com.sforce.soap.enterprise.sobject.Case;
import com.sforce.soap.enterprise.sobject.EmailMessage;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class SendEmailMessage {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		SendEmailMessage sample = new SendEmailMessage();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		doSendEmail();
		// logout
		EnterpriseLogin.logout();
	}

	public void doSendEmail() {
		try {
			// Create a case
			Case theCase = new Case();
			theCase.setSubject("Sample Case");
			SaveResult[] saveResult = connection.create(new SObject[] { theCase });
			String caseId = saveResult[0].getId();
			// Create a draft EmailMessage
			EmailMessage message = new EmailMessage();
			message.setParentId(theCase.getId());
			message.setBccAddress("bcc@email.com");
			message.setCcAddress("cc1@salesforce.com; cc2@email.com");
			message.setSubject("This is how you use the sendEmailMessage method.");
			message.setFromAddress("from@email.com");
			message.setFromName("Sample Code");
			message.setTextBody("This is the text body of the message.");
			message.setStatus("5"); // "5" means Draft
			message.setToAddress("to@email.com");
			saveResult = connection.create(new SObject[] { message });
			String emailMessageId = saveResult[0].getId();
			// Create an attachment for the draft EmailMessage
			Attachment att = new Attachment();
			byte[] fileBody = new byte[1000000];
			att.setBody(fileBody);
			att.setName("attachment");
			att.setParentId(emailMessageId);
			connection.create(new SObject[] { att });
			// Send the draft EmailMessage

			SendEmailResult[] results = connection.sendEmailMessage(null);
			if (results[0].isSuccess()) {
				System.out.println("The email was sent successfully.");
			} else {
				System.out.println("The email failed to send: " + results[0].getErrors()[0].getMessage());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
