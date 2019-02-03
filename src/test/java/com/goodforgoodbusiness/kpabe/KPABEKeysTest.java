package com.goodforgoodbusiness.kpabe;

import com.goodforgoodbusiness.kpabe.local.KPABELocalInstance;

public class KPABEKeysTest {
	public static void main(String[] args) throws Exception {
		KPABELocalInstance abe = KPABELocalInstance.newKeys();
		
		System.out.println("public = " + abe.getPublicKey().toString());
		System.out.println("secret = " + abe.getSecretKey().toString());
	}
}
