package com.colabriq.kpabe;

import com.colabriq.kpabe.jna.KPABELibrary.CKeyPair;
import com.colabriq.kpabe.key.KPABEKey;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;

/**
 * Some utilities needed by both encryption and decryption.
 * @author ijmad
 */
class KPABEUtil {
	protected static final int DECRYPTION_FAILED = 10000;
	
	protected static void checkResult(int result) throws KPABEException {
		if (result != 0) {
			throw KPABEException.forResult(result);
		}
	}
	
	protected static Pointer cKeyPointer(KPABEKey key) {
		String keyString = key.toString();
		
		Pointer keyPointer = new Memory(keyString.length() + 1);
		keyPointer.setString(0, keyString);
		
		return keyPointer;
	}
	
	protected static CKeyPair.ByReference cKeyPair(KPABEKey publicKey, KPABEKey secretKey) {
		CKeyPair.ByReference keyPair = new CKeyPair.ByReference();
		
		keyPair.szPublicKey = cKeyPointer(publicKey);
		keyPair.szSecretKey = cKeyPointer(secretKey);
		
		return keyPair;
	}
}
