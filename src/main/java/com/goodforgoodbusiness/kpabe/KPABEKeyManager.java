package com.goodforgoodbusiness.kpabe;

import static com.goodforgoodbusiness.kpabe.KPABEUtil.checkResult;

import java.security.InvalidKeyException;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import com.goodforgoodbusiness.kpabe.jna.KPABELibrary;
import com.goodforgoodbusiness.kpabe.jna.KPABELibrary.CKeyPair;
import com.goodforgoodbusiness.kpabe.jna.KPABELibraryLoader;
import com.goodforgoodbusiness.kpabe.key.KPABEKeyPair;
import com.goodforgoodbusiness.kpabe.key.KPABEPublicKey;
import com.goodforgoodbusiness.kpabe.key.KPABESecretKey;

/**
 * Key-related things you can do with KPABE.
 * @author ijmad
 */
public class KPABEKeyManager {
	private static class KPABEKeyPairImpl implements KPABEKeyPair {
		private final KPABEPublicKey publicKey;
		private final KPABESecretKey secretKey;
		
		public KPABEKeyPairImpl(KPABEPublicKey publicKey, KPABESecretKey secretKey) {
			this.publicKey = publicKey;
			this.secretKey = secretKey;
		}
		
		@Override
		public KPABEPublicKey getPublic() {
			return publicKey;
		}

		@Override
		public KPABESecretKey getSecret() {
			return secretKey;
		}
		
	};
	
	/**
	 * Create a new {@link KPABEKeyPair} to use with {@link KPABEEncryption}.
	 * @throws KPABEException
	 */
	public static KPABEKeyPair newKeys() throws KPABEException {
		KPABELibrary library = KPABELibraryLoader.getInstance();
		
		CKeyPair.ByReference keyPair = new CKeyPair.ByReference();
		
		int result = library.newKeyPair(keyPair);
		checkResult(result);
		
		return new KPABEKeyPairImpl(
			new KPABEPublicKey(keyPair.getPublicKey()),
			new KPABESecretKey(keyPair.getSecretKey())
		);
	}
	
	public static KPABEKeyPair ofKeys(KPABEPublicKey publicKey, KPABESecretKey secretKey) {
		return new KPABEKeyPairImpl(publicKey, secretKey);
	}
	
	public static KPABEKeyPair ofKeys(PublicKey publicKey, SecretKey secretKey) throws InvalidKeyException {
		if ((publicKey instanceof KPABEPublicKey) && (secretKey instanceof KPABESecretKey)) {
			return new KPABEKeyPairImpl((KPABEPublicKey)publicKey, (KPABESecretKey)secretKey);
		}
		else {
			throw new InvalidKeyException("Must be ABE keys");
		}
	}
	
	public static KPABEKeyPair ofKeys(String publicKey, String secretKey) {
		return new KPABEKeyPairImpl(new KPABEPublicKey(publicKey), new KPABESecretKey(secretKey));
	}
}
