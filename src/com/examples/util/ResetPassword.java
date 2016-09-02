package com.examples.util;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.ResetPasswordResult;
import com.sforce.ws.ConnectionException;

public class ResetPassword {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		ResetPassword sample = new ResetPassword();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		doResetPassword("00590000001OO0w");
		// logout
		EnterpriseLogin.logout();
	}

	public String doResetPassword(String userId) {
		String result = "";
		try {
			ResetPasswordResult rpr = connection.resetPassword(userId);
			result = rpr.getPassword();
			System.out.println("The temporary password for user ID " + userId + " is " + result);
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return result;
	}

}
