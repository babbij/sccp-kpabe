package com.goodforgoodbusiness.kpabe.remote.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import org.apache.log4j.Logger;

import com.goodforgoodbusiness.kpabe.KPABEException;
import com.goodforgoodbusiness.kpabe.local.KPABELocalInstance;
import com.goodforgoodbusiness.kpabe.remote.KPABERemote;
import com.goodforgoodbusiness.kpabe.remote.KPABERemoteInstance;

public class KPABERemoteImpl extends UnicastRemoteObject implements KPABERemote {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(KPABERemoteImpl.class);
	
	protected KPABERemoteImpl() throws RemoteException {
		super();
	}

	@Override
	public KPABERemoteInstance newKeys() throws RemoteException, KPABEException {
		log.debug("Serving newKeys");
		return new KPABERemoteInstanceImpl(KPABELocalInstance.newKeys());
	}

	@Override
	public KPABERemoteInstance forKeys(PublicKey publicKey, SecretKey secretKey) throws RemoteException, InvalidKeyException {
		log.debug("Serving forKeys");
		return new KPABERemoteInstanceImpl(KPABELocalInstance.forKeys(publicKey, secretKey));
	}

	@Override
	public KPABERemoteInstance forKeys(String publicKey, String secretKey) throws RemoteException, InvalidKeyException {
		log.debug("Serving forKeys");
		return new KPABERemoteInstanceImpl(KPABELocalInstance.forKeys(publicKey, secretKey));
	}

	@Override
	public String decrypt(String ciphertext, KeyPair keys) throws RemoteException, KPABEException, InvalidKeyException {
		log.debug("Serving decrypt");
		return KPABELocalInstance.decrypt(ciphertext, keys);
	}
}
