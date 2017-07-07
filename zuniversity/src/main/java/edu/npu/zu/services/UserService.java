package edu.npu.zu.services;

import javax.xml.bind.DatatypeConverter;

import edu.npu.zu.domain.UserAccount;


public class UserService {
	static public UserAccount extractAcctFromAuthorization(String auth) {
		
		if (auth == null || auth.length() == 0) {
			return null;
		}

		/* Get the Basic authorization */
		auth = auth.replaceFirst("[B|b]asic ", "");

		// Decode the Base64 into byte[]
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);

		// If the decode fails in any case
		if (decodedBytes == null || decodedBytes.length == 0) {
			return null;
		}

		// First elem is user name
		// Second elem is password
		String loginInfo[];
		loginInfo = new String(decodedBytes).split(":", 2);
		UserAccount acct = new UserAccount();
		acct.setAccountname(loginInfo[0]);
		acct.setPassword(loginInfo[1]);
		return acct;
	}

}
