package com.examples.soapheaders;

public class CallOptions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void CallOptionsSample() {
		// Web Reference to the imported Partner WSDL.
		APISamples.partner.SforceService partnerBinding;
		string username = "USERNAME";
		string password = "PASSWORD";
		// The real Client ID will be an API Token provided by salesforce.com
		// to partner applications following a security review.
		// For more details, see the Security Review FAQ in the online help.
		string clientId = "SampleCaseSensitiveToken/100";
		partnerBinding = new SforceService();
		partnerBinding.CallOptionsValue = new CallOptions();
		partnerBinding.CallOptionsValue.client = clientId;
		// Optionally, if a developer namespace prefix has been registered for
		// your Developer Edition organization, it may also be specified.
		string prefix = "battle";
		partnerBinding.CallOptionsValue.defaultNamespace = prefix;
		try {
			APISamples.partner.LoginResult lr = partnerBinding.login(username, password);
		} catch (SoapException e) {
			Console.WriteLine(e.Code);
			Console.WriteLine(e.Message);
		}
	}

}
