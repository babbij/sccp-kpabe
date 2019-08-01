package com.colabriq.kpabe.key;

import java.security.PrivateKey;

public class KPABEShareKey extends KPABEKey implements PrivateKey {
	private static final long serialVersionUID = 1L;
	
	public KPABEShareKey(String privateKey) {
		super(privateKey);
	}
	
	public KPABEShareKey(byte [] privateKey) {
		super(privateKey);
	}
}
