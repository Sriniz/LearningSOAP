package com.examples.util;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EmailFileAttachment;
import com.sforce.soap.enterprise.EmailPriority;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.soap.enterprise.SendEmailResult;
import com.sforce.soap.enterprise.SingleEmailMessage;
import com.sforce.ws.ConnectionException;

public class SendEmail {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		SendEmail sample = new SendEmail();
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
			EmailFileAttachment efa = new EmailFileAttachment();
			byte[] fileBody = new byte[1000000];
			efa.setBody(fileBody);
			efa.setFileName("attachment");
			SingleEmailMessage message = new SingleEmailMessage();
			message.setBccAddresses(new String[] { "srinizkumar.konakanchi@cognizant.com" });
			message.setCcAddresses(
					new String[] { "srinizkumar.konakanchi@cognizant.com", "srinizkumar.konakanchi@wbconsultant.com"});
			message.setBccSender(true);
			message.setEmailPriority(EmailPriority.High);
			message.setReplyTo("srinizkumar.konakanchi@gmail.com");
			message.setSaveAsActivity(false);
			message.setSubject("This is how you use the " + "sendEmail method.");
			// We can also just use an id for an implicit to address
			GetUserInfoResult guir = connection.getUserInfo();
			message.setTargetObjectId(guir.getUserId());
			message.setUseSignature(true);
			message.setPlainTextBody("This is the humongous body " + "of the message.");
			EmailFileAttachment[] efas = { efa };
			message.setFileAttachments(efas);
			message.setToAddresses(new String[] { "srinizkumar.konakanchi@gmail.com" });
			SingleEmailMessage[] messages = { message };
			SendEmailResult[] results = connection.sendEmail(messages);
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
