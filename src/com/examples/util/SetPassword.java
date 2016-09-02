package com.examples.util;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SetPasswordResult;
import com.sforce.ws.ConnectionException;

public class SetPassword {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		SetPassword sample = new SetPassword();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		doSetPassword("00590000001OO0w", "Password123");
		// logout
		EnterpriseLogin.logout();
	}

	public void doSetPassword(String userId, String newPasswd) {
		try {
			SetPasswordResult result = connection.setPassword(userId, newPasswd);
			System.out.println("The password for user ID " + userId + " changed to " + newPasswd);
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
