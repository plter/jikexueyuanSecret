package com.jikexueyuan.secret;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jikexueyuan.secret.atys.AtyLogin;
import com.jikexueyuan.secret.atys.AtyTimeline;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String token = Config.getCachedToken(this);
		String phone_num = Config.getCachedPhoneNum(this);
		
		if (token!=null&&phone_num!=null) {
			Intent i =new Intent(this, AtyTimeline.class);
			i.putExtra(Config.KEY_TOKEN, token);
			i.putExtra(Config.KEY_PHONE_NUM, phone_num);
			startActivity(i);
		}else{
			startActivity(new Intent(this, AtyLogin.class));
		}
		
		finish();
	}


}
