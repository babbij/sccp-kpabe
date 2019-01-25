package com.goodforgoodbusiness.kpabe;

import java.security.KeyPair;

import com.goodforgoodbusiness.kpabe.KPABEInstance;

public class KPABETest {
	public static void main(String[] args) throws Exception {
		KPABEInstance abe = KPABEInstance.newKeys();
		
//		for (int i = 0; i < 1000; i++) {
			String cipherText = abe.encrypt("this is a test of the ABE library", "foo|bar|baz");
			System.out.println(cipherText);
			
			KeyPair shareKey = abe.shareKey("bar");
			
			String clearText = KPABEInstance.decrypt(cipherText, shareKey);
			System.out.println(clearText);
//		}
		
		System.out.println("DONE!");
	}
}
