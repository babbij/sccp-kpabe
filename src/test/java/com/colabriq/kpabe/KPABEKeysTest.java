package com.goodforgoodbusiness.kpabe;

public class KPABEKeysTest {
	public static void main(String[] args) throws Exception {
		var keyPair = KPABEKeyManager.newKeys();
		
		System.out.println("public = " + keyPair.getPublic().toString());
		System.out.println("secret = " + keyPair.getSecret().toString());
	}
}
