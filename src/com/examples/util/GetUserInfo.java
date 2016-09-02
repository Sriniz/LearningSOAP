package com.examples.util;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.ws.ConnectionException;

public class GetUserInfo {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		GetUserInfo sample = new GetUserInfo();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		doGetUserInfo();
		// logout
		EnterpriseLogin.logout();
	}

	public void doGetUserInfo() {
		try {
			GetUserInfoResult result = connection.getUserInfo();
			System.out.println("\nUser Information");
			System.out.println("\tFull name: " + result.getUserFullName());
			System.out.println("\tEmail: " + result.getUserEmail());
			System.out.println("\tLocale: " + result.getUserLocale());
			System.out.println("\tTimezone: " + result.getUserTimeZone());
			System.out.println("\tCurrency symbol: " + result.getCurrencySymbol());
			System.out.println("\tOrganization is multi-currency: " + result.isOrganizationMultiCurrency());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
