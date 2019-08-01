package com.goodforgoodbusiness.kpabe.key;

import javax.crypto.SecretKey;

public class KPABESecretKey extends KPABEKey implements SecretKey {
	private static final long serialVersionUID = 1L;
	
	public KPABESecretKey(String publicKey) {
		super(publicKey);
	}
	
	public KPABESecretKey(byte [] publicKey) {
		super(publicKey);
	}
}
