package com.goodforgoodbusiness.kpabe.key;

import javax.crypto.SecretKey;

public class KPABESecretKey extends KPABEKey implements SecretKey {
	public KPABESecretKey(String publicKey) {
		super(publicKey);
	}
	
	public KPABESecretKey(byte [] publicKey) {
		super(publicKey);
	}
}
