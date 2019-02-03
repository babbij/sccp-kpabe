package com.goodforgoodbusiness.kpabe.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import com.goodforgoodbusiness.kpabe.KPABEException;

public interface KPABERemoteInstance extends Remote {
	public PublicKey getPublicKey() throws RemoteException;
	public SecretKey getSecretKey() throws RemoteException;
	
	public String encrypt(String cleartext, String attributes) throws RemoteException, KPABEException;
	public KeyPair shareKey(String attributes) throws RemoteException, KPABEException;
}
