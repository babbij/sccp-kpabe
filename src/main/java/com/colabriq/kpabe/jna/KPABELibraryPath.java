package com.colabriq.kpabe.jna;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * This horrendous code allows us to runtime modify java.library.path
 * to find libraries from the local classpath where we expect to find things.
 * @author ijmad
 */
class KPABELibraryPath {
	public static String resourceDirToAbsolute(String path) {
		try {
			var url = KPABELibraryPath.class.getClassLoader().getResource(path);
			if (url != null) {
				var file = new File(url.toURI());
				if (file.exists() && file.isDirectory()) {
					return file.getAbsolutePath();
				}
			}
			
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getLibraryPath() {
		var os = System.getProperty("os.name").toLowerCase();
		
		if (os.indexOf("mac") >= 0) {
			return resourceDirToAbsolute("./native/darwin");
		}
		else if (os.indexOf("nux") >= 0) {
			return resourceDirToAbsolute("./native/linux");
		}
		else {
			return null;
		}
	}
	
	public static void addLibraryPath(String newPath) {
		try {
		    Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		    usrPathsField.setAccessible(true);
	
		    String[] paths = (String[]) usrPathsField.get(null);
	
		    for (String path : paths)
		        if (path.equals(newPath))
		            return;
	
		    String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
		    newPaths[newPaths.length - 1] = newPath;
		    usrPathsField.set(null, newPaths);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
