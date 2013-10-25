package com.time.master.adapter;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle.Control;

import com.time.master.R;

import android.R.anim;
import android.R.integer;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class YearFrontAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater; // LayoutInflater��������layout��xml�����ļ�
	private List<Map<String, Object>> mData;
	private Context context;

	public YearFrontAdapter(Context context, List<Map<String, Object>> mData) {
		this.context = context;
		// ȡ�ò����ļ�
		this.layoutInflater = layoutInflater.from(context);
		this.mData = mData;

	}

	@Override
	public int getCount() {

		return mData.size();
	}

	@Override
	public Object getItem(int position) {

		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	/**
	 * �� ListViewÿ��ʾһ����Ŀ��ʱ��ͻ�����������е�getView���� �÷�������������
	 *  ��һ���������� position ���λ��
	 *  �ڶ����������� ���ǶԵ�һ�����ݽ����˻���,��ʾ�ڶ����� �ͻ����û����View��ʾ�������󶨵�����
	 *  ������������parent���Ǹ�������
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {

			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.year_item, null);
			holder.tvTime = (TextView) convertView.findViewById(R.id.time);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		if (position % 2 == 0) {
			convertView.setBackgroundResource(R.drawable.yearwhite);

		} else {
			convertView.setBackgroundResource(R.drawable.yeargray);

		}

		holder.tvTime.setText((String) mData.get(position).get("time"));
		return convertView;
	}

	class ViewHolder {

		private TextView tvTime;
		private TextView tvTitle;
	}

}
