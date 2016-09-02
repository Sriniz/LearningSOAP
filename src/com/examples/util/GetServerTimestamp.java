package com.examples.util;

import java.util.Calendar;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetServerTimestampResult;
import com.sforce.ws.ConnectionException;

public class GetServerTimestamp {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		GetServerTimestamp sample = new GetServerTimestamp();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		doGetServerTimestamp();
		// logout
		EnterpriseLogin.logout();
	}

	public void doGetServerTimestamp() {
		try {
			GetServerTimestampResult result = connection.getServerTimestamp();
			Calendar serverTime = result.getTimestamp();
			System.out.println("Server time is: " + serverTime.getTime().toString());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
