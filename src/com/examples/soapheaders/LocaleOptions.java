package com.examples.soapheaders;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class LocaleOptions {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		LocaleOptions sample = new LocaleOptions();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		localeOptionsExample();
		// logout
		EnterpriseLogin.logout();
	}

	public void localeOptionsExample() {
		try {
			connection.setLocaleOptions("en_US", true);
			connection.describeSObject("Account");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
