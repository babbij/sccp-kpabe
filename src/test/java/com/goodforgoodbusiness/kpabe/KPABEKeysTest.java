package com.goodforgoodbusiness.kpabe;

import com.goodforgoodbusiness.kpabe.KPABEInstance;

public class KPABEKeysTest {
	public static void main(String[] args) throws Exception {
		KPABEInstance abe = KPABEInstance.newKeys();
		
		System.out.println("public = " + abe.getPublicKey().toString());
		System.out.println("secret = " + abe.getSecretKey().toString());
	}
}
