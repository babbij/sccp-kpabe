package com.goodforgoodbusiness.kpabe.key;

/**
 * Represents a public + secret key, as needed for encryption
 * @author ijmad
 */
public interface KPABEKeyPair {
	public KPABEPublicKey getPublic();
	public KPABESecretKey getSecret();
}
