package com.examples.soapheaders;

import com.examples.core.EnterpriseLogin;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.PackageVersion;

public class PackageVersionHeader {

	EnterpriseConnection connection;

	public static void main(String[] args) {
		PackageVersionHeader sample = new PackageVersionHeader();
		sample.run();

	}

	public void run() {
		// Make a login call
		connection = EnterpriseLogin.login();
		try {
			PackageVersionHeaderSample("Code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// logout
		EnterpriseLogin.logout();
	}

	public void PackageVersionHeaderSample(String code) throws Exception {
		_PackageVersionHeader pvh = new _PackageVersionHeader();
		PackageVersion pv = new PackageVersion();
		pv.setNamespace("installedPackageNamespaceHere");
		pv.setMajorNumber(1);
		pv.setMinorNumber(0);
		// In this case, we are only referencing one installed package.
		PackageVersion[] pvs = new PackageVersion[] { pv };
		pvh.setPackageVersions(pvs);
		connection.setHeader(new SforceServiceLocator().getServiceName().getNamespaceURI(), "PackageVersionHeader",
				pvh);
		// Execute the code passed into the method.
		ExecuteAnonymousResult r = apexBinding.executeAnonymous(code);
		if (r.isSuccess()) {
			System.out.println("Code executed successfully");
		} else {
			System.out.println("Exception message: " + r.getExceptionMessage());
			System.out.println("Exception stack trace: " + r.getExceptionStackTrace());
		}
	}

}
