package com.gmail.threads.io;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFileChooser;

public class Main {

	public static void main(String[] args) {

		/**
		 * 4) Реализуйте многопоточное копирование каталога, содержащего несколько
		 * файлов.
		 */
		Path source = null;
		Path target = null;
		try {
			JFileChooser sfc = new JFileChooser();
			sfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			JFileChooser dfc = new JFileChooser();
			dfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			sfc.showDialog(null, "Choose source folder");
			dfc.showDialog(null, "Choose destination folder");
			source = sfc.getSelectedFile().toPath();
			target = dfc.getSelectedFile().toPath();

//		Path source = Paths.get("C:\\Users\\datkach\\Desktop\\java\\ProgKievUa\\EclipseWorkSpace\\Lesson6_hw1");
//		Path target = Paths.get("C:\\Users\\datkach\\Desktop\\java\\ProgKievUa\\EclipseWorkSpace\\Lesson6_hw1\\new");

			ExecutorService executorService = Executors.newFixedThreadPool(4);
			ArrayList<Future<?>> futures = new ArrayList<>();

			for (Path sourceFile : FileHandler.getFilesFromFolder(source)) {
				Path newFolder = Paths.get(sourceFile.toString().substring(source.toString().length())).getParent();
				newFolder = Paths.get(target.toString() + newFolder.toString());
				futures.add(executorService.submit(new CopyFileThread(sourceFile, newFolder)));
				// FileHandler.copyFileToFolder(sourceFile, newFolder);
			}

			for (Future<?> future : futures) {
				try {
					future.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}

			executorService.shutdown();
		} catch (NullPointerException e) {
			System.out.println("at least one folder has not been choosed");
		}

		/**
		 * 5) Реализуйте программу, которая с периодичностью 1 сек, будет проверять
		 * состояние заданного каталога. Если в этом каталоге появится новый файл или
		 * исчезнет существующий, то выведется сообщение об этом событии. Программа
		 * должна работать в параллельном потоке выполнения.
		 */
		
		
		Thread folderMonitor = new Thread(new FolderMonitorThread(target.toString()),"Folder monitor");
		folderMonitor.start();

	}

}
