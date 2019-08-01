package com.colabriq.kpabe;

import java.security.KeyPair;

public class KPABETest {
	public static void main(String[] args) throws Exception {
		long started = System.currentTimeMillis();
		
		var keys = KPABEKeyManager.newKeys();
		
		for (int i = 0; i < 500; i++) {
			KPABEEncryption abe = KPABEEncryption.getInstance(keys);
		
			String cipherText = abe.encrypt("this is a test of the ABE library", "foo|bar|baz|bop|bem|bee|boo");
//			System.out.println(cipherText);
			
			KeyPair shareKey = abe.shareKey("bar");
			
			String clearText = KPABEDecryption.getInstance().decrypt(cipherText, shareKey);
//			System.out.println(clearText);
		}
		
		System.out.println("DONE in " + (System.currentTimeMillis() - started) + "ms");
	}
}
