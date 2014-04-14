package com.jikexueyuan.secretclientpublish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jikexueyuan.secretclientpublish.net.GetCode;
import com.jikexueyuan.secretclientpublish.net.Login;
import com.jikexueyuan.secretclientpublish.tool.MD5Tool;

public class AtyLogin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_login);
		
		etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
		etCode = (EditText) findViewById(R.id.etCode);
		
		findViewById(R.id.btnGetCode).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (!TextUtils.isEmpty(etPhoneNum.getText())) {
					
					final ProgressDialog pd = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting_title),getResources().getString(R.string.connecting));
					
					new GetCode(etPhoneNum.getText().toString(), new GetCode.SuccessCallback() {
						
						@Override
						public void onSuccess() {
							pd.dismiss();
							Toast.makeText(AtyLogin.this, R.string.suc_to_get_code,Toast.LENGTH_LONG).show();
						}
					}, new GetCode.FailCallback() {
						
						@Override
						public void onFail() {
							pd.dismiss();
							Toast.makeText(AtyLogin.this, R.string.retry_get_code, Toast.LENGTH_LONG).show();
						}
					});
				}else{
					Toast.makeText(AtyLogin.this, R.string.phone_num_can_not_be_empty, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (TextUtils.isEmpty(etPhoneNum.getText())) {
					Toast.makeText(AtyLogin.this, R.string.phone_num_can_not_be_empty, Toast.LENGTH_LONG).show();
					return;
				}
				
				if (TextUtils.isEmpty(etCode.getText())) {
					Toast.makeText(AtyLogin.this, R.string.code_can_not_be_empty, Toast.LENGTH_LONG).show();
					return;
				}
				
				final ProgressDialog pd = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting_title), getResources().getString(R.string.connecting));
				new Login(MD5Tool.md5(etPhoneNum.getText().toString()), etCode.getText().toString(), new Login.SuccessCallback() {
					
					@Override
					public void onSuccess(String token) {
						pd.dismiss();
						
						Config.saveToken(AtyLogin.this, token);
						Config.saveCurrentLoggedPhoneNum(AtyLogin.this, etPhoneNum.getText().toString());
						
						
						Intent i = new Intent(AtyLogin.this, AtyTimeline.class);
						i.putExtra(Config.KEY_TOKEN, token);
						i.putExtra(Config.KEY_PHONE_NUM, etPhoneNum.getText().toString());
						startActivity(i);
						System.out.println("token="+token);
						
						finish();
					}
				}, new Login.FailCallback() {
					
					@Override
					public void onFail() {
						pd.dismiss();
						
						Toast.makeText(AtyLogin.this, R.string.fail_to_login, Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}
	
	private EditText etPhoneNum;
	private EditText etCode;
}
