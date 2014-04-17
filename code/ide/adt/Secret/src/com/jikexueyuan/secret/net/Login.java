package com.jikexueyuan.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.jikexueyuan.secret.Config;

public class Login {

	public Login(String phone_md5,String code,final SuccessCallback successCallback,final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj = new JSONObject(result);
					
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if (successCallback!=null) {
							successCallback.onSuccess(obj.getString(Config.KEY_TOKEN));
						}
						break;
					default:
						if (failCallback!=null) {
							failCallback.onFail();
						}
						break;
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					
					if (failCallback!=null) {
						failCallback.onFail();
					}
				}
			}
		}, new NetConnection.FailCallback() {
			
			@Override
			public void onFail() {
				if (failCallback!=null) {
					failCallback.onFail();
				}
			}
		}, Config.KEY_ACTION,Config.ACTION_LOGIN,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_CODE,code);
	}
	
	public static interface SuccessCallback{
		void onSuccess(String token);
	}
	
	public static interface FailCallback{
		void onFail();
	}
}
