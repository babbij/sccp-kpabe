package com.goodforgoodbusiness.kpabe.key;

import java.security.PrivateKey;

public class KPABEShareKey extends KPABEKey implements PrivateKey {
	public KPABEShareKey(String privateKey) {
		super(privateKey);
	}
	
	public KPABEShareKey(byte [] privateKey) {
		super(privateKey);
	}
}
