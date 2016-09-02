package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.KnowledgeLanguageItem;
import com.sforce.soap.enterprise.KnowledgeSettings;
import com.sforce.ws.ConnectionException;

public class DescribeKnowledge {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeKnowledge sample = new DescribeKnowledge();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeKnowledgeSettingsSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeKnowledgeSettingsSample() {
		try {
			// Make the describe call for KnowledgeSettings
			KnowledgeSettings result = connection.describeKnowledgeSettings();
			// Get the properties of KnowledgeSettings
			System.out.println("Knowledge default language: " + result.getDefaultLanguage());
			for (KnowledgeLanguageItem lang : result.getLanguages()) {
				System.out.println("Language: " + lang.getName());
				System.out.println("Active: " + lang.isActive());
			}
		} catch (ConnectionException ex) {
			ex.printStackTrace();
		}
	}

}
