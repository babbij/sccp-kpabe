package com.goodforgoodbusiness.kpabe.jna;

import java.util.concurrent.atomic.AtomicInteger;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

/**
 * Load the KPABE library.
 * Supports loading multiple copies for concurrent access in separate memory spaces.
 * This vastly improves performance.
 * 
 * @author ijmad
 */
public class KPABELibraryLoader {
	public static final String LIBRARY_BASE_NAME = "sccp";
	public static final int LIBRARY_COPIES = 1;
	
	private static final KPABELibrary[] LIBRARIES = new KPABELibrary[LIBRARY_COPIES];
	private static final AtomicInteger ROUND_ROBIN = new AtomicInteger(0);
	
	static {
		if (System.getenv("DISABLE_KPABE_AUTOLOAD") == null) {
			var path = KPABELibraryPath.getLibraryPath();
			if (path != null) {
				KPABELibraryPath.addLibraryPath(path);
				
				for (var i = 0; i < LIBRARY_COPIES; i++) {
					NativeLibrary.addSearchPath(LIBRARY_BASE_NAME + i, path);
				}
			}
		}
		
		for (var i = 0; i < LIBRARY_COPIES; i++) {
			System.loadLibrary(LIBRARY_BASE_NAME + i);
			LIBRARIES[i] = Native.load(LIBRARY_BASE_NAME + i, KPABELibrary.class);
		}
	}
	
	public static KPABELibrary getInstance() {
		return LIBRARIES[ROUND_ROBIN.getAndIncrement() % LIBRARIES.length];
	}
}
