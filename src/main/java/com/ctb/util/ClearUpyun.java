package com.ctb.util;

import java.util.List;

import com.ctb.util.UpYun.FolderItem;

public class ClearUpyun {

	public static void deletefolder() {
		UpYun upyun = new UpYun("iwrong", "iwrong", "iwrong@XGR008");
		String upload_folder = "resources_v3/";
		upyun.readFile(upload_folder);
		List<FolderItem> fis = upyun.readDir(upload_folder);
		for (FolderItem fi : fis) {
			if (fi.name.contains(".files")) {
				System.out.println(fi.name);
				List<FolderItem> picsFolderItems = upyun.readDir(upload_folder
						+ fi.name);
				if (picsFolderItems != null) {
					for (FolderItem picFolderItem : picsFolderItems) {
						System.out.println(upyun.deleteFile(upload_folder
								+ fi.name + "/" + picFolderItem.name));
					}
				}
				System.out.println(upyun.rmDir(upload_folder + fi.name));
				// upyun.deleteFile();
			}
		}

	}

	public static void main(String[] args) {
		System.out.println("execute ...");
		deletefolder();
	}
}
