package com.gmail.threads.io;

import java.nio.file.Path;

public class CopyFileThread implements Runnable {

	private Path sourceFile;
	private Path targetFolder;
	
	public CopyFileThread(Path sourceFile, Path targetFolder) {
		this.sourceFile = sourceFile;
		this.targetFolder = targetFolder;
	}

	@Override
	public void run() {
		FileHandler.copyFileToFolder(sourceFile, targetFolder);
		System.out.println(Thread.currentThread().getName()+" has copied "+ sourceFile.getFileName());
	}

}
