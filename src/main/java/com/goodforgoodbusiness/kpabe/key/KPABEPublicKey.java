package com.goodforgoodbusiness.kpabe.key;

import java.security.PublicKey;

public class KPABEPublicKey extends KPABEKey implements PublicKey {
	public KPABEPublicKey(String publicKey) {
		super(publicKey);
	}
	
	public KPABEPublicKey(byte [] publicKey) {
		super(publicKey);
	}
}
