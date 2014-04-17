package com.jikexueyuan.secret.atys;

import java.util.List;

import com.jikexueyuan.secret.Config;
import com.jikexueyuan.secret.R;
import com.jikexueyuan.secret.net.Comment;
import com.jikexueyuan.secret.net.GetComment;
import com.jikexueyuan.secret.net.PubComment;
import com.jikexueyuan.secret.tools.MD5Tool;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AtyMessage extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_message);
		
		adapter = new AtyMessageCommentListAdapter(this);
		setListAdapter(adapter);
		
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		etComment = (EditText) findViewById(R.id.etComment);
		
		Intent data = getIntent();
		phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
		msg = data.getStringExtra(Config.KEY_MSG);
		msgId = data.getStringExtra(Config.KEY_MSG_ID);
		token = data.getStringExtra(Config.KEY_TOKEN);
		
		tvMessage.setText(msg);
		
		getComments();
		
		findViewById(R.id.btnSendComment).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (TextUtils.isEmpty(etComment.getText())) {
					Toast.makeText(AtyMessage.this, R.string.comment_content_can_not_be_empty, Toast.LENGTH_LONG).show();
					return;
				}
				
				final ProgressDialog pd = ProgressDialog.show(AtyMessage.this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
				new PubComment(MD5Tool.md5(Config.getCachedPhoneNum(AtyMessage.this)), token, etComment.getText().toString(), msgId, new PubComment.SuccessCallback() {
					
					@Override
					public void onSuccess() {
						pd.dismiss();
						
						etComment.setText("");
						
						getComments();
					}
				}, new PubComment.FailCallback() {
					
					@Override
					public void onFail(int errorCode) {
						pd.dismiss();
						
						if (errorCode==Config.RESULT_STATUS_INVALID_TOKEN) {
							
							startActivity(new Intent(AtyMessage.this, AtyLogin.class));
							finish();
						}else{
							Toast.makeText(AtyMessage.this, R.string.fail_to_pub_comment, Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		});
	}
	private void getComments() {
		final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
		new GetComment(phone_md5, token, msgId, 1, 20, new GetComment.SuccessCallback() {
			
			@Override
			public void onSuccess(String msgId, int page, int perpage,
					List<Comment> comments) {
				
				pd.dismiss();
				
				adapter.clear();
				adapter.addAll(comments);
			}
		}, new GetComment.FailCallback() {
			
			@Override
			public void onFail(int errorCode) {
				pd.dismiss();
				
				if (errorCode==Config.RESULT_STATUS_INVALID_TOKEN) {
					startActivity(new Intent(AtyMessage.this, AtyLogin.class));
					finish();
				}else{
					Toast.makeText(AtyMessage.this, R.string.fail_to_get_comment, Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	private TextView tvMessage;
	private EditText etComment;
	private String phone_md5,msg,msgId,token;
	private AtyMessageCommentListAdapter adapter;
}
