package com.jikexueyuan.secretclientpublish.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.jikexueyuan.secretclientpublish.Config;

public class GetCode {

	public GetCode(final String phoneNum,final SuccessCallback successCallback,final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST,new NetConnection.SuccessCallback() {
			
			@Override
			public void onResult(String result) {
				if (result!=null) {
					try {
						JSONObject obj = new JSONObject(result);
						switch (obj.getInt(Config.KEY_STATUS)) {
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
					
				}else{
					if (failCallback!=null) {
						failCallback.onFail();
					}
				}
			}
		},new NetConnection.FailCallback() {
			
			@Override
			public void onFail() {
				if (failCallback!=null) {
					failCallback.onFail();
				}
			}
		}, Config.KEY_ACTION,Config.ACTION_GET_CODE,Config.KEY_PHONE_NUM,phoneNum);
	}
	
	public static interface SuccessCallback{
		void onSuccess();
	}
	
	public static interface FailCallback{
		void onFail();
	}
	
}
