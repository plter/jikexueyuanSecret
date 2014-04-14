package com.jikexueyuan.secretclientpublish.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.jikexueyuan.secretclientpublish.Config;

public class UploadContacts {

	public UploadContacts(String phone_md5,String token,String contactsJsonStr,final SuccessCallback successCallback,final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			
			@Override
			public void onResult(String result) {
				
				try {
					JSONObject jsonObj = new JSONObject(result);
					
					switch (jsonObj.getInt(Config.KEY_STATUS)) {
					case Config.STATUS_OK:
						if (successCallback!=null) {
							successCallback.onSuccess();
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
		}, Config.KEY_ACTION,Config.ACTION_UPLOAD_CONTACTS,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_TOKEN,token,Config.KEY_CONTACTS,contactsJsonStr);
	}


	public static interface SuccessCallback{
		void onSuccess();
	}

	public static interface FailCallback{
		void onFail();
	}
}
