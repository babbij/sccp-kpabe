package com.goodforgoodbusiness.kpabe.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import com.goodforgoodbusiness.kpabe.KPABEException;

public interface KPABERemote extends Remote {
	public KPABERemoteInstance newKeys() throws RemoteException, KPABEException;
	
	public KPABERemoteInstance forKeys(PublicKey publicKey, SecretKey secretKey) throws RemoteException, InvalidKeyException;
	
	public KPABERemoteInstance forKeys(String publicKey, String secretKey) throws RemoteException, InvalidKeyException;
	
	public String decrypt(String ciphertext, KeyPair keys) throws RemoteException, KPABEException, InvalidKeyException;
}
