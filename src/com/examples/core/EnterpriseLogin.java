package com.examples.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;


public class EnterpriseLogin {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static EnterpriseConnection connection;
	static String authEndPoint = "https://login.salesforce.com/services/Soap/c/37.0";
	
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

	public static EnterpriseConnection login() {
		String username = getUserInput("Enter username: ");
		String password = getUserInput("Enter password: ");
		try {
			ConnectorConfig config = new ConnectorConfig();
			config.setUsername(username);
			config.setPassword(password);
			System.out.println("AuthEndPoint: " + authEndPoint);
			config.setAuthEndpoint(authEndPoint);
			connection = new EnterpriseConnection(config);
			printUserInfo(config);
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return connection;
	}

	static void printUserInfo(ConnectorConfig config) {
		try {
			GetUserInfoResult userInfo = connection.getUserInfo();
			System.out.println("\nLogging in ...\n");
			System.out.println("UserID: " + userInfo.getUserId());
			System.out.println("User Full Name: " + userInfo.getUserFullName());
			System.out.println("User Email: " + userInfo.getUserEmail());
			System.out.println();
			System.out.println("SessionID: " + config.getSessionId());
			System.out.println("Auth End Point: " + config.getAuthEndpoint());
			System.out.println("Service End Point: " + config.getServiceEndpoint());
			System.out.println();
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public static void logout() {
		try {
			connection.logout();
			System.out.println("Logged out.");
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
