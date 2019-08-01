package com.colabriq.kpabe;

import static com.colabriq.kpabe.KPABEUtil.cKeyPair;
import static com.colabriq.kpabe.KPABEUtil.checkResult;

import java.security.KeyPair;
import java.util.concurrent.locks.Lock;

import com.colabriq.kpabe.jna.KPABELibrary;
import com.colabriq.kpabe.jna.KPABELibrary.COutString;
import com.colabriq.kpabe.jna.KPABELibraryLoader;
import com.colabriq.kpabe.jna.KPABELibraryLock;
import com.colabriq.kpabe.key.KPABEKeyPair;
import com.colabriq.kpabe.key.KPABEShareKey;

/**
 * Encryption-related things you can do with KPABE.
 * @author ijmad
 */
public class KPABEEncryption {
	private static final Lock lock = KPABELibraryLock.LOCK;
	
	public static KPABEEncryption getInstance(KPABEKeyPair keyPair) {
		return new KPABEEncryption(KPABELibraryLoader.getInstance(), keyPair);
	}
	
	private final KPABELibrary library;
	private final KPABEKeyPair keyPair;
	
	KPABEEncryption(KPABELibrary library, KPABEKeyPair keyPair) {
		this.library = library;
		this.keyPair = keyPair;
	}
	
	public KPABEKeyPair getKeyPair() {
		return keyPair;
	}
	
	public String encrypt(String cleartext, String attributes) throws KPABEException {
		lock.lock();
		
		try (var cKeyPair = cKeyPair(keyPair.getPublic(), keyPair.getSecret())) {
			COutString.ByReference cipherText = new COutString.ByReference();
			
			int result = library.encrypt(cKeyPair, attributes, cleartext, cipherText);
			checkResult(result);
			
			return cipherText.toString();
		}
		finally {
			lock.unlock();
		}
	}
	
	public KeyPair shareKey(String attributes) throws KPABEException {
		lock.lock();
		
		try (var cKeyPair = cKeyPair(keyPair.getPublic(), keyPair.getSecret())) {
			COutString.ByReference shareKey = new COutString.ByReference();
			
			int result = library.shareKey(cKeyPair, attributes, shareKey);
			checkResult(result);
			
			return new KeyPair(keyPair.getPublic(), new KPABEShareKey(shareKey.toString()));
		}
		finally {
			lock.unlock();
		}
	}
}
