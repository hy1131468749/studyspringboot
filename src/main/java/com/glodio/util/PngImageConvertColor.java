package com.glodio.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

public class PngImageConvertColor extends RGBImageFilter {
	
	// 继承它实现图象ARGB的处理
	int rgb = 0;

	@Override
	public int filterRGB(int x, int y, int rgb) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public PngImageConvertColor(int rgb) {// 构造器，用来接收需要过滤图象的尺寸，以及透明度
	    this.canFilterIndexColorModel = true;
	    this.rgb = rgb;
	}
	
	
	
/*	public static void main(String[] args) {
	    try {
	        File pngFile = new File("e:\\edit_remove.png");
	        BufferedImage image = ImageIO.read(pngFile);
	        int minx = image.getMinX();  
            int miny = image.getMinY();  
	        for(int x = 0;x < image.getWidth();x++) {
	        	for(int y = 0;y < image.getHeight();y++) {
	        		int rgb = image.getRGB(x, y);
	        		Color color = new Color(rgb);
	        		int r = color.getRed();
	        		int g = color.getGreen();
	        		int b = color.getBlue();
	        		
	        		int c = r+g+b;
	        		c = c/3;
	        		
	        		if(r == 26 && g == 250 && b == 41) {
		        		Color newColor = new Color(250, 59, 48);
		        		image.setRGB(x, y, newColor.getRGB());
	        		}

	        	}
	        }
	        
	        ImageIO.write(image, "png", new File("e:\\b.png"));
	        
	    	File pngFile = new File("e:\\GSP01.png");
	        BufferedImage image = ImageIO.read(pngFile);
	        
	        BufferedImage bufIma=new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);  
	          
	        //这里是关键部分  
	        Graphics2D g2d =bufIma.createGraphics(); 
	        
	        bufIma = g2d .getDeviceConfiguration().createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);  
	        
	        //g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER)); 

	        // 释放对象  
	        //g2d.dispose();  
	        g2d = bufIma.createGraphics();  

	        g2d.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        Color oldColor = new Color(12, 192, 93);//26, 250, 41
	        Color newColor = new Color(250, 00, 00);
            for (int y = 0; y < image.getHeight(); y++) {  
                for (int x = 0; x < image.getWidth(); x++) {  
                    Color color = new Color(image.getRGB(x, y));  
                    if (color.equals(oldColor))  {
                    	g2d.setColor(newColor); 
               			g2d.drawLine(x, y, x, y); 
                    }
                }  
            } 
	          
            // 释放对象  
            g2d.dispose();
            
            
        
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufIma, "png", baos);
            byte[] bytes = baos.toByteArray();
            
            String hex = "886799";
            System.out.println(Integer.parseInt(hex.substring(0, 2), 16));
            System.out.println(Integer.parseInt(hex.substring(2, 4), 16));
            System.out.println(Integer.parseInt(hex.substring(4, 6), 16));

            //System.out.println(Image2Base64.encode(bytes));
            
	        //ImageIO.write(bufIma, "png", new File("e:/GSP05.png"));  
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}*/
	
	public static String hexTypeColor2Base64(HttpServletRequest request,String hexTypeColor) {
		String base64 = "";
		
	    try {
	    	// 读标准图标文件
	    	
			String path = "";
			path = request.getSession().getServletContext().getRealPath("/");
			path = path+"/public/PM-img/";
			path = path.replace("\\","\\/");
	    	File pngFile = new File(path+"GSPno.png");
	    	//System.out.println(path+"GSPno.png");
	        BufferedImage image = ImageIO.read(pngFile);
	        
	        // 生成RGB式缓存图片对象
	        BufferedImage bufIma=new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);  
	          
	        //这里是关键部分  
	        Graphics2D g2d =bufIma.createGraphics(); 
	        
	        bufIma = g2d .getDeviceConfiguration().createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);  
	        
	        //g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER)); 

	        // 释放对象  
	        //g2d.dispose();  
	        g2d = bufIma.createGraphics();  

	        g2d.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        Color oldColor = new Color(44, 44, 44);//26, 250, 41
	        Color newColor = new Color(Integer.parseInt(hexTypeColor.substring(0, 2), 16), Integer.parseInt(hexTypeColor.substring(2, 4), 16), 
	        		Integer.parseInt(hexTypeColor.substring(4, 6), 16));
            for (int y = 0; y < image.getHeight(); y++) {  
                for (int x = 0; x < image.getWidth(); x++) {  
                    Color color = new Color(image.getRGB(x, y));  
                    if (color.equals(oldColor))  {
                    	g2d.setColor(newColor); 
               			g2d.drawLine(x, y, x, y); 
                    }
                }  
            } 
	          
            // 释放对象  
            g2d.dispose();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufIma, "png", baos);
            byte[] bytes = baos.toByteArray();

            base64 = Image2Base64.encode(bytes);
            //System.out.println(base64);
	        //ImageIO.write(bufIma, "png", new File("e:/GSP06.png"));  
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return base64;
	}
}
