package com.jikexueyuan.secretclientpublish.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.jikexueyuan.secretclientpublish.Config;

import android.os.AsyncTask;



public class NetConnection {


	public NetConnection(final String url,final HttpMethod httpMethod,final SuccessCallback callback,final FailCallback failCallback,final String ...kvs) {

		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... ps) {
				StringBuffer content = new StringBuffer();
				
				for (int i = 0; i < kvs.length; i+=2) {
					content.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
				}

				try {
					URLConnection uc;
					switch (httpMethod) {
					case POST:
						uc = new URL(url).openConnection();
						uc.setDoInput(true);
						uc.setDoOutput(true);
						OutputStream os = uc.getOutputStream();
						os.write(content.toString().getBytes(Config.CHARSET));
						os.flush();
						break;
					default:
						uc = new URL(url+"?"+content.toString()).openConnection();
						uc.setDoInput(true);
						uc.setDoOutput(false);
						break;
					}

					System.out.println("request url="+uc.getURL());
					System.out.println("request content="+content);
					
					InputStream is = uc.getInputStream();
					StringBuffer result = new StringBuffer();
					BufferedReader br = new BufferedReader(new InputStreamReader(is, Config.CHARSET));
					String line = null;
					while((line = br.readLine())!=null){
						result.append(line);
					}
					System.out.println("request result="+result);
					return result.toString();

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result!=null) {
					if (callback!=null) {
						callback.onResult(result);
					}
				}else{
					if (failCallback!=null) {
						failCallback.onFail();
					}
				}
				super.onPostExecute(result);
			}
		}.execute();
	}

	public static interface SuccessCallback{
		void onResult(String result);
	}
	public static interface FailCallback{
		void onFail();
	}

}
