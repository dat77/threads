package com.gmail.threads.io;

import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FolderMonitorThread implements Runnable {
	
	private String targetFolder;

	public FolderMonitorThread(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starts to operate "+targetFolder);
		String initialContent = FileHandler.getFilesFromFolder(targetFolder);
		while (true) {
			try {
				Thread.sleep(1000);
				String currentContent = FileHandler.getFilesFromFolder(targetFolder);
				if (!currentContent.equalsIgnoreCase(initialContent) && 
						JOptionPane.showConfirmDialog(null, "Content of folder was changed \nContinue monitoring?",
								Thread.currentThread().getName(), JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
					break;
				}
				initialContent = currentContent;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is finished");
	}

}
