package com.examples.core;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.InvalidateSessionsResult;
import com.sforce.ws.ConnectionException;

public class InvalidateSessions {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		InvalidateSessions sample = new InvalidateSessions();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		String sessionId = connection.getSessionHeader().getSessionId();
		invalidateSessionsSample(new String[] {sessionId});
		// logout - this should fail
		EnterpriseLogin.logout();
	}

	public void invalidateSessionsSample(String[] sessionIds) {
		try {
			InvalidateSessionsResult[] results;
			results = connection.invalidateSessions(sessionIds);
			for (InvalidateSessionsResult result : results) {
				// Check results for errors
				if (!result.isSuccess()) {
					if (result.getErrors().length > 0) {
						System.out.println("Status code: " + result.getErrors()[0].getStatusCode());
						System.out.println("Error message: " + result.getErrors()[0].getMessage());
					}
				} else {
					System.out.println("Success.");
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
