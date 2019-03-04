package com.goodforgoodbusiness.kpabe.local;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import com.goodforgoodbusiness.kpabe.KPABEException;
import com.goodforgoodbusiness.kpabe.key.KPABEKey;
import com.goodforgoodbusiness.kpabe.key.KPABEPublicKey;
import com.goodforgoodbusiness.kpabe.key.KPABESecretKey;
import com.goodforgoodbusiness.kpabe.key.KPABEShareKey;
import com.goodforgoodbusiness.kpabe.local.KPABELibrary.CKeyPair;
import com.goodforgoodbusiness.kpabe.local.KPABELibrary.COutString;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class KPABELocalInstance {
	private static final Object LOCK = new Object();
	private static final int DECRYPTION_FAILED = 10000;
	
	private static final KPABELibrary LIBRARY;
	
	static {
		if (System.getenv("DISABLE_KPABE_AUTOLOAD") == null) {
			var path = KPABELibraryPath.getLibraryPath();
			if (path != null) {
				KPABELibraryPath.addLibraryPath(path);
			}
		}
		
		System.loadLibrary("sccp");
		LIBRARY = Native.load("sccp", KPABELibrary.class);
	}
	
	private static void checkResult(int result) throws KPABEException {
		if (result != 0) {
			throw KPABEException.forResult(result);
		}
	}
	
	public static KPABELocalInstance newKeys() throws KPABEException {
		synchronized (LOCK) {
			CKeyPair.ByReference keyPair = new CKeyPair.ByReference();
			
			int result = LIBRARY.newKeyPair(keyPair);
			checkResult(result);
			
			return new KPABELocalInstance(
				new KPABEPublicKey(keyPair.getPublicKey()),
				new KPABESecretKey(keyPair.getSecretKey())
			);
		}
	}
	
	public static KPABELocalInstance forKeys(KPABEPublicKey publicKey, KPABESecretKey secretKey) {
		return new KPABELocalInstance(publicKey, secretKey);
	}
	
	public static KPABELocalInstance forKeys(PublicKey publicKey, SecretKey secretKey) throws InvalidKeyException {
		if ((publicKey instanceof KPABEPublicKey) && (secretKey instanceof KPABESecretKey)) {
			return new KPABELocalInstance((KPABEPublicKey)publicKey, (KPABESecretKey)secretKey);
		}
		else {
			throw new InvalidKeyException("Must be ABE keys");
		}
	}
	
	public static KPABELocalInstance forKeys(String publicKey, String secretKey) throws InvalidKeyException {
		return new KPABELocalInstance(new KPABEPublicKey(publicKey), new KPABESecretKey(secretKey));
	}
	
	private final KPABEPublicKey publicKey;
	private final KPABESecretKey secretKey;
	
	private KPABELocalInstance(KPABEPublicKey publicKey, KPABESecretKey secretKey) {
		this.publicKey = publicKey;
		this.secretKey = secretKey;
	}
	
	public KPABEPublicKey getPublicKey() {
		return publicKey;
	}
	
	public KPABESecretKey getSecretKey() {
		return secretKey;
	}
	
	private static Pointer cKeyPointer(KPABEKey key) {
		String keyString = key.toString();
		
		Pointer keyPointer = new Memory(keyString.length() + 1);
		keyPointer.setString(0, keyString);
		
		return keyPointer;
	}
	
	private CKeyPair.ByReference cKeyPair() {
		CKeyPair.ByReference keyPair = new CKeyPair.ByReference();
		
		keyPair.szPublicKey = cKeyPointer(this.publicKey);
		keyPair.szSecretKey = cKeyPointer(this.secretKey);
		
		return keyPair;
	}
	
	public String encrypt(String cleartext, String attributes) throws KPABEException {
		synchronized (LOCK) {
			try (var keyPair = cKeyPair()) {
				COutString.ByReference cipherText = new COutString.ByReference();
				
				int result = LIBRARY.encrypt(keyPair, attributes, cleartext, cipherText);
				checkResult(result);
				
				return cipherText.toString();
			}
		}
	}
	
	public KeyPair shareKey(String attributes) throws KPABEException {
		synchronized (LOCK) {
			try (var keyPair = cKeyPair()) {
				COutString.ByReference shareKey = new COutString.ByReference();
				
				int result = LIBRARY.shareKey(keyPair, attributes, shareKey);
				checkResult(result);
				
				return new KeyPair(publicKey, new KPABEShareKey(shareKey.toString()));
			}
		}
	}
	
	public static String decrypt(String ciphertext, KeyPair keys) throws KPABEException, InvalidKeyException {
		synchronized (LOCK) {
			Key publicKey = keys.getPublic();
			Key shareKey = keys.getPrivate();
			
			if ((publicKey instanceof KPABEPublicKey) && (shareKey instanceof KPABEShareKey)) {
				COutString.ByReference clearText = new COutString.ByReference();
	
				int result = LIBRARY.decrypt(publicKey.toString(), ciphertext, shareKey.toString(), clearText);
				if (result == DECRYPTION_FAILED) {
					return null;
				}
				
				checkResult(result);
				return clearText.toString();
			}
			else {
				throw new InvalidKeyException("Must be ABE keys");
			}
		}
	}
}
