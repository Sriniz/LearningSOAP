package com.examples.util;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.RenderEmailTemplateBodyResult;
import com.sforce.soap.enterprise.RenderEmailTemplateError;
import com.sforce.soap.enterprise.RenderEmailTemplateRequest;
import com.sforce.soap.enterprise.RenderEmailTemplateResult;
import com.sforce.ws.ConnectionException;

public class RenderEmailTemplate {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		RenderEmailTemplate sample = new RenderEmailTemplate();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		try {
			renderTemplates("0039000001yusiz", "0019000001ka3tZ");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// logout
		EnterpriseLogin.logout();
	}

	public void renderTemplates(String whoId, String whatId)
			throws ConnectionException, RemoteException, MalformedURLException {
		// Array of three template bodies.
		// The second template body generates an error.
		final String[] TEMPLATE_BODIES = new String[] { "This is a good template body {!Contact.Name}",
				"This is a bad template body {!Opportunity.Name} {!Contact.SNARF} ",
				"This is another good template body {!Contact.Name}" };
		// Create request and add template bodies, whatId, and whoId.
		RenderEmailTemplateRequest req = new RenderEmailTemplateRequest();
		req.setTemplateBodies(TEMPLATE_BODIES);
		req.setWhatId(whatId);
		req.setWhoId(whoId);
		// An array of results is returned, one for each request.
		// We only have one request.
		RenderEmailTemplateResult[] results = connection.renderEmailTemplate(new RenderEmailTemplateRequest[] { req });
		if (results != null) {
			// Check results for our one and only request.
			// Check request was processed successfully, and if not, print the
			// errors.
			if (!results[0].isSuccess()) {
				System.out.println("The following errors were encountered while rendering email templates:");
				for (com.sforce.soap.enterprise.Error err : results[0].getErrors()) {
					System.out.println(err.getMessage());
				}
			} else {
				// Check results for each body template and print merged body
				RenderEmailTemplateBodyResult[] bodyResults = results[0].getBodyResults();
				for (Integer i = 0; i < bodyResults.length; i++) {
					RenderEmailTemplateBodyResult result = bodyResults[i];
					if (result.isSuccess()) {
						System.out.println("\nMerged body: \n" + result.getMergedBody());
					} else {
						System.out.println("\nErrors were found for body[" + i + "]: ");
						for (RenderEmailTemplateError err : result.getErrors()) {
							System.out.println(err.getMessage() + " - Field name: " + err.getFieldName());
						}
					}
				}
			}
		}
	}

}
