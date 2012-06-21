package demo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;


public class HttpRequestDemo {
	public static void main(String[] args) throws URISyntaxException
	{
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("q", "httpclient"));
		qparams.add(new BasicNameValuePair("btnG", "Google Search"));
		qparams.add(new BasicNameValuePair("aq", "f"));
		qparams.add(new BasicNameValuePair("oq", null));
		URI uri = URIUtils.createURI("http", "www.google.com", -1, "/search",
		URLEncodedUtils.format(qparams, "UTF-8"), null);
		HttpGet httpget = new HttpGet(uri);
		System.out.println(httpget.getURI());
	}
}
