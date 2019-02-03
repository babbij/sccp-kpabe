package com.goodforgoodbusiness.kpabe.remote.impl;

import static java.lang.Integer.parseInt;
import static java.net.InetAddress.getLocalHost;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class KPABERemoteMain {
	static {
		try (var is = KPABERemoteMain.class.getResourceAsStream("log4j.properties")) {
			Properties props = new Properties();
			props.load(is);
			PropertyConfigurator.configure(props);
		}
		catch (IOException e) {
			throw new RuntimeException("Unable to configure log4j", e);
		}
	}
	
	private static final Logger log = Logger.getLogger(KPABERemoteMain.class);
	
	public static void main(String[] args) throws RemoteException, UnknownHostException {
		int port = parseInt(args[0]);
		
		log.info("Starting registry on port " + args[0]);
		var registry = LocateRegistry.createRegistry(port);
		
		log.info("Binding at //" + getLocalHost().getHostName() + ":" + port + "/kpabe");
		registry.rebind("kpabe", new KPABERemoteImpl());
	}
}
