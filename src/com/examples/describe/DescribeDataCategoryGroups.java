package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DataCategory;
import com.sforce.soap.enterprise.DataCategoryGroupSobjectTypePair;
import com.sforce.soap.enterprise.DescribeDataCategoryGroupResult;
import com.sforce.soap.enterprise.DescribeDataCategoryGroupStructureResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeDataCategoryGroups {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeDataCategoryGroups sample = new DescribeDataCategoryGroups();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeDataCategoryGroupsSample();
		describeDataCateogryGroupStructuresSample();
		// logout
		EnterpriseLogin.logout();
	}

	public void describeDataCategoryGroupsSample() {
		try {
			// Make the describe call for data category groups
			DescribeDataCategoryGroupResult[] results = connection
					.describeDataCategoryGroups(new String[] { "Question" });
			// Get the properties of each data category group
			for (int i = 0; i < results.length; i++) {
				System.out.println("sObject: " + results[i].getSobject());
				System.out.println("Group name: " + results[i].getName());
				System.out.println("Group label: " + results[i].getLabel());
				System.out.println("Group description: "
						+ (results[i].getDescription() == null ? "" : results[i].getDescription()));
				System.out.println("Number of categories: " + results[i].getCategoryCount());
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

	public void describeDataCateogryGroupStructuresSample() {
		try {
			// Create the data category pairs
			DataCategoryGroupSobjectTypePair pair1 = new DataCategoryGroupSobjectTypePair();
			DataCategoryGroupSobjectTypePair pair2 = new DataCategoryGroupSobjectTypePair();
			pair1.setSobject("KnowledgeArticleVersion");
			pair1.setDataCategoryGroupName("Regions");
			pair2.setSobject("Question");
			pair2.setDataCategoryGroupName("Regions");
			DataCategoryGroupSobjectTypePair[] pairs = new DataCategoryGroupSobjectTypePair[] { pair1, pair2 };
			// Get the list of top level categories using the describe call
			DescribeDataCategoryGroupStructureResult[] results = connection.describeDataCategoryGroupStructures(pairs,
					false);
			// Iterate through each result and get some properties
			// including top categories and child categories
			for (int i = 0; i < results.length; i++) {
				DescribeDataCategoryGroupStructureResult result = results[i];
				String sObject = result.getSobject();
				System.out.println("sObject: " + sObject);
				System.out.println("Group name: " + result.getName());
				System.out.println("Group label: " + result.getLabel());
				System.out.println("Group description: " + result.getDescription());
				// Get the top-level categories
				DataCategory[] topCategories = result.getTopCategories();
				// Iterate through the top level categories and retrieve
				// some information
				for (int j = 0; j < topCategories.length; j++) {
					DataCategory topCategory = topCategories[j];
					System.out.println("Category name: " + topCategory.getName());
					System.out.println("Category label: " + topCategory.getLabel());
					DataCategory[] childCategories = topCategory.getChildCategories();
					System.out.println("Child categories: ");
					for (int k = 0; k < childCategories.length; k++) {
						System.out.println("\t" + k + ". Category name: " + childCategories[k].getName());
						System.out.println("\t" + k + ". Category label: " + childCategories[k].getLabel());
					}
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
