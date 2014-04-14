package com.jikexueyuan.secretclientpublish.ld;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jikexueyuan.secretclientpublish.Config;
import com.jikexueyuan.secretclientpublish.tool.MD5Tool;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class MyContacts {

	public static String getContactsData(Context context){
		Cursor c = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		
		JSONArray jarr = new JSONArray();
		JSONObject obj;
		String phoneNum;
		while(c.moveToNext()){
			phoneNum = c.getString(c.getColumnIndex(Phone.NUMBER));
			
			if (phoneNum.charAt(0)=='+'&&phoneNum.charAt(1)=='8'&&phoneNum.charAt(2)=='6') {
				phoneNum = phoneNum.substring(3);
			}
			
			obj = new JSONObject();
			try {
				obj.put(Config.KEY_PHONE_MD5, MD5Tool.md5(phoneNum));
				jarr.put(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return jarr.toString();
	}
}
