package com.scanbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.scanbook.R;

/**
 * Created by Jay on 15/2/3.
 */
public class SearchAdapter extends BaseAdapter {

        private Context context;
        public SearchAdapter(Context context) {
            this.context=context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_search, null);
            return convertView;

        }


}
