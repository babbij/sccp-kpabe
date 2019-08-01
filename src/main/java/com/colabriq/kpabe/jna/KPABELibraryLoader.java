package com.goodforgoodbusiness.kpabe.jna;

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
	private static final String LIBRARY_BASE_NAME = "sccp";
	private static final KPABELibrary LIBRARY;
	
	static {
		synchronized (KPABELibraryLoader.class) {
			if (System.getenv("DISABLE_KPABE_AUTOLOAD") == null) {
				var path = KPABELibraryPath.getLibraryPath();
				if (path != null) {
					KPABELibraryPath.addLibraryPath(path);
					NativeLibrary.addSearchPath(LIBRARY_BASE_NAME, path);
				}
			}
			
			System.loadLibrary(LIBRARY_BASE_NAME);
			LIBRARY = Native.load(LIBRARY_BASE_NAME, KPABELibrary.class);
		}
	}
	
	public static KPABELibrary getInstance() {
		return LIBRARY;
	}
}
