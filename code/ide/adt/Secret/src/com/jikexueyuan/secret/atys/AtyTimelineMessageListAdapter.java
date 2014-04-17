package com.jikexueyuan.secret.atys;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jikexueyuan.secret.R;
import com.jikexueyuan.secret.net.Message;

public class AtyTimelineMessageListAdapter extends BaseAdapter{

	
	public AtyTimelineMessageListAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}
	
	@Override
	public Message getItem(int position) {
		return data.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell, null);
			convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.tvCellLabel)));
		}
		
		ListCell lc = (ListCell) convertView.getTag();
		
		Message msg = getItem(position);
		
		lc.getTvCellLabel().setText(msg.getMsg());
		
		return convertView;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void addAll(List<Message> data){
		this.data.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear(){
		data.clear();
		notifyDataSetChanged();
	}
	
	private List<Message> data = new ArrayList<Message>();
	private Context context=null;
	
	private static class ListCell{
		
		public ListCell(TextView tvCellLabel) {
			this.tvCellLabel = tvCellLabel;
		}
		
		private TextView tvCellLabel;
		
		public TextView getTvCellLabel() {
			return tvCellLabel;
		}
	}
}
