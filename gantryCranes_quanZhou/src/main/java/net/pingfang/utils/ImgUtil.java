package net.pingfang.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImgUtil {
	
	/**
	 * 获取图片的长和宽
	 * @param imgUrl
	 * @return
	 */
	public static String getImgWidthHeight(String imgUrl) {
		String widthHeight = "";
		try {
			if(null !=imgUrl && !"".equals(imgUrl)) {
				InputStream murl = new URL(imgUrl).openStream();
				BufferedImage sourceImg =ImageIO.read(murl); 
				widthHeight = sourceImg.getWidth()+","+sourceImg.getHeight();
		        //System.out.println(sourceImg.getWidth());   // 源图宽度
		       // System.out.println(sourceImg.getHeight());   // 源图高度
			}			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return widthHeight;
	}
	
	/**
	 * 判断文件是否存在
	 * @param fileUrl
	 * @return
	 */
	public static boolean existsFile(String filePath) {
		boolean exists = false;
		try {
			if(null !=filePath && !"".equals(filePath)) {
				File file =new File(filePath);
				//判断文件目录是否存在 
				if(file.exists() && file.isFile()){
					exists = true;
				}	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	/**
	 * 判断文件是否存在
	 * 查看服务器上文件是否存在
	 * @param fileUrl
	 * @return
	 */
	public static boolean existsFile2(String filePath) {
		boolean exists = false;
		try {
			if(null !=filePath && !"".equals(filePath)) {
				URL url = new URL(filePath);
				HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
				if(200 == urlcon.getResponseCode()) {
					exists = true;
				}
				/**
				 * 如果文件存在：
					HTTP/1.1 200 OK
					200
					OK
					如果文件不存在：
					HTTP/1.1 404 Not Found
					404
					Not Found
					
				String message = urlcon.getHeaderField(0);
				int responseCode = urlcon.getResponseCode();
				String responseMessage = urlcon.getResponseMessage();
				
				System.out.println(message);
				System.out.println(responseCode);
				System.out.println(responseMessage);
				 */
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

}
