package com.jikexueyuan.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.jikexueyuan.secret.Config;

public class UploadContacts {

	
	public UploadContacts(String phone_md5,String token,String contacts,final SuccessCallback successCallback,final FailCallback failCallback) {
		
		
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj = new JSONObject(result);
					
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if (successCallback!=null) {
							successCallback.onSuccess();
						}
						break;
					case Config.RESULT_STATUS_INVALID_TOKEN:
						if (failCallback!=null) {
							failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
						}
						break;
					default:
						if (failCallback!=null) {
							failCallback.onFail(Config.RESULT_STATUS_FAIL);
						}
						break;
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					if (failCallback!=null) {
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
				}
			}
		}, new NetConnection.FailCallback() {
			
			@Override
			public void onFail() {
				if (failCallback!=null) {
					failCallback.onFail(Config.RESULT_STATUS_FAIL);
				}
			}
		}, Config.KEY_ACTION,Config.ACTION_UPLOAD_CONTACTS,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_TOKEN,token,Config.KEY_CONTACTS,contacts);
		
	}
	
	
	public static interface SuccessCallback{
		void onSuccess();
	}
	
	public static interface FailCallback{
		void onFail(int errorCode);
	}
}
