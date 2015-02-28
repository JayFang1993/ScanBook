package com.scanbook.common;

import java.io.File;

import android.os.Environment;

public class FileUtils {
	
	/*
	 * 获取该应用的根目录
	 */
	public static String getAppPath(){
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return "";
		}
		File sdRoot=Environment.getExternalStorageDirectory();
		File file=new File(sdRoot.getAbsolutePath()+"/ScanBook");
        if (!file.exists())
           file.mkdir();
        return file.getAbsolutePath();
	}
	
	/*
	 * 获取缓存图片的位置
	 */
	public static String getCachePath(){
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return "";
		}
		File file=new File(getAppPath()+"/cache");
        if (!file.exists())
           file.mkdir();
        return file.getAbsolutePath();	
	}
	
}
