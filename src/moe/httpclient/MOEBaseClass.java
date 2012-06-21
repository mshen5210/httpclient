package moe.httpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;

public class MOEBaseClass {
	
	public static final String encoding = "UTF-8";
	/**
	 * @author mshen
	 * @desc buffer entity to StringBuffer, then destroy this entity
	 * @return String
	 */
	public static String readAndDestoryEntity(HttpEntity entity){
		 try {
			 StringBuilder serverResponse = new StringBuilder();

		        BufferedReader br = new BufferedReader(new InputStreamReader(
		        		entity.getContent(), Charset.forName("UTF-8")));
		        String s;
		        while ((s = br.readLine()) != null)
		            serverResponse.append(s);
		        entity.consumeContent();
		        return serverResponse.toString();
		 } catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	/**
	 * @author mshen
	 * @desc download files then store in a new file
	 * @return String
	 */
	public static String DownloadFiles(HttpEntity entity,String filepath){
		File storeFile = new File(filepath);
		try{
			FileOutputStream output = new FileOutputStream(storeFile);
			InputStream input = entity.getContent();
			byte b[] = new byte[1024];
			int j = 0;
			while((j=input.read(b))!=-1){
				output.write(b,0,j);
			}
			output.flush();
			output.close();
			entity.consumeContent();
			return "download success";
		}catch (IOException e){
			e.printStackTrace();
		}
		return "download failed";
	}
}
