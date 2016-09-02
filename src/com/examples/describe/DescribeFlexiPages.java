package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeComponentInstance;
import com.sforce.soap.enterprise.DescribeComponentInstanceProperty;
import com.sforce.soap.enterprise.DescribeFlexiPageRegion;
import com.sforce.soap.enterprise.DescribeFlexiPageResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeFlexiPages {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeFlexiPages sample = new DescribeFlexiPages();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeFlexiPageSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeFlexiPageSample() {
		try {
			// Retrieve a single FlexiPage
			String flexiPageName = "MyFlexiPage";
			DescribeFlexiPageResult[] result = null;
			result = connection.describeFlexiPages(new String[] { flexiPageName }, null);
			String msg = String.format("There are %s FlexiPages described in the response", result.length);
			System.out.println(msg);
			DescribeFlexiPageResult page = result[0];
			// Iterate over the regions of the FlexiPage
			for (DescribeFlexiPageRegion region : page.getRegions()) {
				msg = String.format("Region: %s", region.getName());
				// Iterate over the components in each region
				for (DescribeComponentInstance cmp : region.getComponents()) {
					String fullComponentName = cmp.getTypeNamespace() + ":" + cmp.getTypeName();
					System.out.println("Component: " + fullComponentName);
					// Iterate over the properties of each component
					for (DescribeComponentInstanceProperty prop : cmp.getProperties()) {
						msg = String.format("Property [%s] has value [%s]", prop.getName(), prop.getValue());
						System.out.println(msg);
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
