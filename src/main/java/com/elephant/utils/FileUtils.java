package com.elephant.utils;

import java.io.File;

import org.aspectj.apache.bcel.Constants;

public class FileUtils implements Constants {

	public static void createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}
}
