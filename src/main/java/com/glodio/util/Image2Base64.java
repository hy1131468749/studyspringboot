package com.glodio.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Blob;

import javax.imageio.stream.FileImageInputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/*
 * @function Image2Base64 ͼƬת����base64�ַ���
 * @package com.raycomm.util 
 * @filename Image2Base64.java
 * @create 2016-12-22
 */
public class Image2Base64 {
	private static final int BUFFER_SIZE = 1024*1024;	//��������С1M
	
	/*
	 * @function �޸ı���blob�ֶ�ֵ�������ϴ����ļ����Ƽ���
	 * @param sqlBlob ����blob�ֶ�ֵ
	 * @param strImgsName �ϴ����ļ����Ƽ���
	 * @return String �޸ĺ��µ�base64����
	 * */
	public static String sql2Base64(byte[] blobBytes,String strImgsName) {
		String strBase64;
		String[] strArrayUserBase64;
		String[] strArrayImgsName;
		
		strBase64 = bytesToString(blobBytes);

		strArrayUserBase64 = strBase64.split(";");		//�ָ���ַ�������
		strArrayImgsName = strImgsName.split(";");	//�ָ���ַ�������
		
		boolean bDelResult;;
		for(int j = 1;j < strArrayUserBase64.length;j=j+2) {
			bDelResult = false;
			
			//�жϱ���base64�Ƿ��Ѿ���ǰ��ɾ��������ǣ���ɾ�����е�base64�������������
			for(int k = 0;k < strArrayImgsName.length;k++) {
				if((strArrayUserBase64[j].equals(strArrayImgsName[k]))) {
					bDelResult = true;
				}
			}
			if(!bDelResult) {
				strArrayUserBase64[j] = "";
				strArrayUserBase64[j-1] = "";
			}
		}
		//�����������ӳ��ַ���
		strBase64 = "";
		for(int j = 0;j < strArrayUserBase64.length;j++) {
			if(strArrayUserBase64[j] != "") {
				strBase64 +=strArrayUserBase64[j];
				strBase64 +=";";
			}
		}
		
		if(strBase64.equals(";")) {
			strBase64 = "";
		}
		
		//System.out.println(strBase64);
		return strBase64;
	}
	
	public static String files2Base64(CommonsMultipartFile[] files,String strImgsName) {
		String strBase64 = "";
		String strTemp;
		byte[] arrayByteImage;
		
		for(int i = 0;i <files.length;i++) {
			//System.out.println(files.length);
			//System.out.println(files[i].getOriginalFilename());
			if(strImgsName.indexOf(files[i].getOriginalFilename()) != -1) {	//�ж�ͼƬ�ļ����Ƿ����ļ���������
				arrayByteImage = files[i].getBytes();	//ͼƬת�ֽ�����
				strTemp = encode(arrayByteImage);		//�ֽ�����תbase64����
				strBase64 += strTemp;
				strBase64 +=";";
				strBase64 +=files[i].getOriginalFilename();
				strBase64 +=";";
				arrayByteImage = null;
			}
			//System.out.println(strBase64);
		}
		
		return strBase64;
	}
	
	/*
	 * @function blobתString
	 * @param blob 
	 * @return String  
	 * @create 2016-12-22
	 */
	public static String blob2String(Blob blob) {
		String strBase64 = null;
		
		try {
			strBase64 = new String(blob.getBytes((long)1, (int)blob.length()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return strBase64;
	}
	
	/*
	 * @function �ֽ�����ת�����ַ���
	 * @param byte[] ��Ҫת�����ֽ�����
	 * @return String
	 */
	public static String bytesToString(byte[] bytes) { 
		String strTemp;
		
		try {
			strTemp = new String(bytes,"utf-8");
			return strTemp;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * @function ͼƬת�����ֽ�����
	 * @param src ͼƬ�ļ�
	 * @return byte[] ͼƬ�ֽ�����
	 * @create 2016-12-22
	 */
	protected static byte[] img2Bytes(File src) {
		byte[] data = new byte[BUFFER_SIZE];
		
		try {
			FileImageInputStream in = null;	//������
			ByteArrayOutputStream out = null;	//�ֽ����������
			try {
				in = new FileImageInputStream(src);
				out = new ByteArrayOutputStream();
				byte[] buf = new byte[BUFFER_SIZE];
				int bytesRead = 0;
				
				//��ͼƬ�ļ�
				while ((bytesRead = in.read(buf)) !=-1) {
					out.write(buf,0,bytesRead);
				}
				
				data = out.toByteArray();//��������鸳��data
		
			} finally {
				if(out != null) {
					out.close();
				}
				
				if(in != null) {
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/*
	 * @function �ֽ�����ת����base64����
	 * @param byte[] ��Ҫת�����ֽ�����
	 * @return String
	 */
	@SuppressWarnings("restriction")
	protected static String encode(byte[] bstr){
		 return new sun.misc.BASE64Encoder().encode(bstr);    
	}

}
