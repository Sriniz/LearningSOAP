package com.examples.describe;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.DescribeColor;
import com.sforce.soap.enterprise.DescribeGlobalResult;
import com.sforce.soap.enterprise.DescribeIcon;
import com.sforce.soap.enterprise.DescribeThemeItem;
import com.sforce.soap.enterprise.DescribeThemeResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;

public class DescribeGlobalTheme {

	static EnterpriseConnection connection;

	public static void main(String[] args) {
		DescribeGlobalTheme sample = new DescribeGlobalTheme();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		describeGlobalThemeExample();
		// logout
		EnterpriseLogin.logout();
	}

	public static void describeGlobalThemeExample() {
		try {
			// Get current theme and object information
			com.sforce.soap.enterprise.DescribeGlobalTheme globalThemeResult = connection.describeGlobalTheme();
			DescribeGlobalResult globalResult = globalThemeResult.getGlobal();
			DescribeThemeResult globalTheme = globalThemeResult.getTheme();
			// For the themes, get the array of theme items, one per object
			DescribeThemeItem[] themeItems = globalTheme.getThemeItems();
			for (int i = 0; i < themeItems.length; i++) {
				DescribeThemeItem themeItem = themeItems[i];
				System.out.println("Theme information for object " + themeItem.getName());
				// Get color and icon info for each themeItem
				DescribeColor colors[] = themeItem.getColors();
				System.out.println(" Number of colors: " + colors.length);
				int k;
				for (k = 0; k < colors.length; k++) {
					DescribeColor color = colors[k];
					System.out.println(" For Color #" + k + ":");
					System.out.println(" Web RGB Color: " + color.getColor());
					System.out.println(" Context: " + color.getContext());
					System.out.println(" Theme: " + color.getTheme());
				}
				DescribeIcon icons[] = themeItem.getIcons();
				System.out.println(" Number of icons: " + icons.length);
				for (k = 0; k < icons.length; k++) {
					DescribeIcon icon = icons[k];
					System.out.println(" For Icon #" + k + ":");
					System.out.println(" ContentType: " + icon.getContentType());
					System.out.println(" Height: " + icon.getHeight());
					System.out.println(" Theme: " + icon.getTheme());
					System.out.println(" URL: " + icon.getUrl());
					System.out.println(" Width: " + icon.getWidth());
				}
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}

}
