package com.goodforgoodbusiness.kpabe.remote.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import org.apache.log4j.Logger;

import com.goodforgoodbusiness.kpabe.KPABEException;
import com.goodforgoodbusiness.kpabe.local.KPABELocalInstance;
import com.goodforgoodbusiness.kpabe.remote.KPABERemoteInstance;

public class KPABERemoteInstanceImpl extends UnicastRemoteObject implements KPABERemoteInstance {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(KPABERemoteInstanceImpl.class);
	
	private final KPABELocalInstance instance;

	protected KPABERemoteInstanceImpl(KPABELocalInstance instance) throws RemoteException {
		super();
		this.instance = instance;
	}

	@Override
	public PublicKey getPublicKey() throws RemoteException {
		log.debug("Serving getPublicKey");
		return instance.getPublicKey();
	}

	@Override
	public SecretKey getSecretKey() throws RemoteException {
		log.debug("Serving getSecretKey");
		return instance.getSecretKey();
	}

	@Override
	public String encrypt(String cleartext, String attributes) throws KPABEException {
		log.debug("Serving encrypt");
		return instance.encrypt(cleartext, attributes);
	}

	@Override
	public KeyPair shareKey(String attributes) throws KPABEException {
		log.debug("Serving shareKey");
		return instance.shareKey(attributes);
	}

}
