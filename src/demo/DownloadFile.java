package demo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class DownloadFile
{
    public static void main(String[] args) throws Exception
    {
        int min = 1;
        int max = 3;
       
        String url = "http://www.foxnews.com/sitemap.xml?idx=";
       
        while (min < max)
        {
            Thread.sleep(500);
           
            HttpClient httpClient1 = new DefaultHttpClient();
            HttpGet httpGet1 = new HttpGet(url+min);
            HttpResponse httpResponse1 = httpClient1.execute(httpGet1);
           
           
            StatusLine statusLine = httpResponse1.getStatusLine();
            if(statusLine.getStatusCode() == 200)
            {
               
                File xml = new File("d:/sitemap/"+min+".xml");
               
                FileOutputStream outputStream = new FileOutputStream(xml);
                InputStream inputStream = httpResponse1.getEntity().getContent();
                byte b[] = new byte[1024];
                int j = 0;  
                while( (j = inputStream.read(b))!=-1)
                {  
                    outputStream.write(b,0,j);  
                }
                outputStream.flush();
                outputStream.close();
                min++;
                System.out.println("´æ´¢ÁËXML: " +min);
            }  
           
            httpClient1.getConnectionManager().shutdown();
        }
    }
}
