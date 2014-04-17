package com.jikexueyuan.secret.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jikexueyuan.secret.Config;

public class Timeline {

	public Timeline(String phone_md5,String token,int page,int perpage,final SuccessCallback successCallback,final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL	, HttpMethod.POST, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject obj = new JSONObject(result);
					
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if (successCallback!=null) {
							List<Message> msgs = new ArrayList<Message>();
							JSONArray msgJsonArray = obj.getJSONArray(Config.KEY_TIMELINE);
							JSONObject msgObj;
							for (int i = 0; i <msgJsonArray.length(); i++) {
								msgObj = msgJsonArray.getJSONObject(i);
								msgs.add(new Message(
										msgObj.getString(Config.KEY_MSG_ID),
										msgObj.getString(Config.KEY_MSG),
										msgObj.getString(Config.KEY_PHONE_MD5)
										));
							}
							
//							successCallback.onSuccess(obj.getInt(Config.KEY_PAGE), obj.getInt(Config.KEY_PERPAGE), msgs);
							successCallback.onSuccess(1, 20, msgs);
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
		}, Config.KEY_ACTION,Config.ACTION_TIMELINE,
		Config.KEY_PHONE_MD5,phone_md5,
		Config.KEY_TOKEN,token,
		Config.KEY_PAGE,page+"",
		Config.KEY_PERPAGE,perpage+"");
	}
	
	public static interface SuccessCallback{
		void onSuccess(int page,int perpage,List<Message> timeline);
	}
	
	public static interface FailCallback{
		void onFail(int errorCode);
	}
}
