package com.jikexueyuan.secretclientpublish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String token = Config.getToken(this);
        String currentLoggedPhoneNum = Config.getCurrentLoggedPhoneNum(this);
        
        Intent i;
        if (token!=null&&currentLoggedPhoneNum!=null) {
			i = new Intent(this, AtyTimeline.class);
			i.putExtra(Config.KEY_TOKEN, token);
			i.putExtra(Config.KEY_PHONE_NUM, currentLoggedPhoneNum);
		}else{
			i = new Intent(this, AtyLogin.class);
		}
        
        startActivity(i);
        finish();
    }
}
