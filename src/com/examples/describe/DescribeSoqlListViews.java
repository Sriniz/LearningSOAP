package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeSoqlListViewsRequest;
import com.sforce.soap.enterprise.EnterpriseConnection;

public class DescribeSoqlListViews {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeSoqlListViews sample = new DescribeSoqlListViews();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		try {
			describeSoqlListViewsSample();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// logout
		EnterpriseLogin.logout();
	}

	public void describeSoqlListViewsSample() throws Exception {
		DescribeSoqlListViewsRequest request = createDescribeSoqlListViewsRequest("00B90000004kM7o", "Account");
		this.getClient().describeSoqlListViews(request);
	}

}
