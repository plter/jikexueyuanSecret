package com.jikexueyuan.secretclientpublish;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.jikexueyuan.secretclientpublish.ld.MyContacts;
import com.jikexueyuan.secretclientpublish.net.Timeline;
import com.jikexueyuan.secretclientpublish.net.UploadContacts;
import com.jikexueyuan.secretclientpublish.tool.MD5Tool;

public class AtyTimeline extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_timeline);
		
		final String phoneNum = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
		final String phoneMd5 = MD5Tool.md5(phoneNum);
		final String token = getIntent().getStringExtra(Config.KEY_TOKEN);

		//sync contacts
		final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting_title), getResources().getString(R.string.connecting));
		new UploadContacts(MD5Tool.md5(phoneNum), Config.getToken(AtyTimeline.this), MyContacts.getContactsData(AtyTimeline.this), new UploadContacts.SuccessCallback() {

			@Override
			public void onSuccess() {
				System.out.println("success to upload contacts");
				pd.dismiss();
				
				final ProgressDialog pd1 = ProgressDialog.show(AtyTimeline.this, getResources().getString(R.string.connecting_title), getResources().getString(R.string.connecting));
				//get timeline data
				new Timeline(phoneMd5, token, ""+1, ""+30, new Timeline.SuccessCallback() {
					
					
					@Override
					public void onSuccess() {
						
						pd1.dismiss();
						//TODO AtyTimeline
					}
				}, new Timeline.FailCallback() {
					
					@Override
					public void onFail() {
						pd1.dismiss();
						
						Toast.makeText(AtyTimeline.this, R.string.fail_to_load_timeline, Toast.LENGTH_LONG).show();
					}
				});
			}
		}, new UploadContacts.FailCallback() {

			@Override
			public void onFail() {
				System.out.println("fail to upload contacts");
				pd.dismiss();
			}
		});
	}

}
