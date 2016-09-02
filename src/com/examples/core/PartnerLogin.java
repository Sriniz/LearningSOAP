package com.examples.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class PartnerLogin {

	public static PartnerConnection partnerConnection = null;
	static String authEndPoint = "https://login.salesforce.com/services/Soap/u/37.0";
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	static String getUserInput(String prompt) {
		String result = "";
		try {
			System.out.print(prompt);
			result = reader.readLine();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}

	static PartnerConnection login() {
		String username = getUserInput("Enter username: ");
		String password = getUserInput("Enter password: ");
		try {
			ConnectorConfig config = new ConnectorConfig();
			config.setUsername(username);
			config.setPassword(password);
			config.setAuthEndpoint(authEndPoint);
			config.setTraceFile("traceLogs.txt");
			config.setTraceMessage(true);
			config.setPrettyPrintXml(true);
			partnerConnection = new PartnerConnection(config);
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		return partnerConnection;
	}

	static void logout() {
		try {
			partnerConnection.logout();
			System.out.println("Logged out.");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
