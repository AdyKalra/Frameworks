package com.zapprx.testing.end2endtests.automation.reporting;

import javax.mail.PasswordAuthentication;

/**
 * @author prashant
 *
 */
class Authenticator extends javax.mail.Authenticator {
	private PasswordAuthentication authentication;

	public Authenticator(String username, String password) {
		authentication = new PasswordAuthentication(username, password);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}
