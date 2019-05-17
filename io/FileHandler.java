package com.gmail.threads.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

	public static String getFilesFromFolder(String folderName) {
		StringBuilder filesAndFolders = new StringBuilder();
		try {
			Files.list(Paths.get(folderName)).forEach((x) -> {
				if (Files.isDirectory(x)) {
					filesAndFolders.append(getFilesFromFolder(x.toString()));
				} else {
					filesAndFolders.append(x.toString() + "\n");
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filesAndFolders.toString();
	}

	public static Path[] getFilesFromFolder(Path path) {
		if (path == null) {
			throw new IllegalArgumentException(); 
		}
		Path[] pathes = null;
		if (Files.isDirectory(path)) {
			String[] files = FileHandler.getFilesFromFolder(path.toString()).split("\n");
			pathes = new Path[files.length];
			for (int i = 0; i < files.length; i++) {
				pathes[i] = Paths.get(files[i]);
			} 
		} else {
			pathes = new Path[1];
			pathes[0] = path;
		}
		return pathes;
	}
	
	public static void copyFileToFolder(Path sourceFile, Path targetFolder) {
		Path newFile = Paths.get(targetFolder.toString()+"\\"+sourceFile.getFileName());
		try {
			Files.createDirectories(targetFolder);
			Files.copy(sourceFile, newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
