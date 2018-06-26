package com.glodio.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.glodio.bean.device.MCData;

public class FileUtil
{
	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath)
	{
		File file = new File(filePath);
		
		return getBytes(file);
	}
	
	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(File file)
	{
		byte[] buffer = null;
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return buffer;
	}
	
/*	public static void main(String[] args) throws UnsupportedEncodingException {
//		byte[] b = getBytes("D:\\software\\Tomcat8\\webapps\\IRGDD\\logs\\root_debug.log");
//		String strTemp = new String(b, "utf-8");
//		System.out.println(strTemp);
		
		
		ArrayList<MCData> list = new ArrayList<MCData>();
		
		MCData mcData = new MCData();
		mcData.setIsOnline("1");
		mcData.setDeviceName("test1");
		
		list.add(mcData);
		
		MCData test2 = new MCData();
		test2.setDeviceName("test2");
		test2.setIsOnline("1");
		
		MCData test3 = new MCData();
		test3.setDeviceName("test3");
		test3.setIsOnline("0");
		
		list.add(test3);
		list.add(test2);
		
		for(MCData test:list) {
			System.out.println(test.toString());
		}
		
		Collections.sort(list, new Comparator<MCData>() {

			@Override
			public int compare(MCData o1, MCData o2) {
				
				if(Integer.parseInt(o1.getIsOnline()) > Integer.parseInt(o2.getIsOnline()))
				{
					return -1;
				}
				
				if(Integer.parseInt(o1.getIsOnline()) == Integer.parseInt(o2.getIsOnline()))
				{
					return 0;
				}
				
				return 1;
				
			}
		});
		
		for(MCData test:list) {
			System.out.println("sort:"+test.toString());
		}
	}*/
	
}

