package com.scanbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scanbook.R;
import com.scanbook.bean.Annotation;

import java.util.List;

/**
 * Created by Jay on 15/2/3.
 */
public class AnnotationAdapter extends BaseAdapter {

        private Context context;
        private List<Annotation> mList;
        public AnnotationAdapter(Context context,List<Annotation> mlist) {
            this.context=context;
            this.mList=mlist;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void setList(List<Annotation> m){
            this.mList=m;
        }

        public void clear(){
            mList.clear();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null) {
                convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_annotation, null);
                holder=new ViewHolder();
                holder.cheapter=(TextView)convertView.findViewById(R.id.tv_annotation_cheapter);
                holder.time=(TextView)convertView.findViewById(R.id.tv_annotation_time);
                holder.page=(TextView)convertView.findViewById(R.id.tv_annotation_page);
                holder.summary=(TextView)convertView.findViewById(R.id.tv_annotation_summary);
                holder.username=(TextView)convertView.findViewById(R.id.tv_annotation_user);
                holder.headicon=(ImageView)convertView.findViewById(R.id.iv_annotation_head_icon);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.cheapter.setText(mList.get(position).getCheapter());
            holder.username.setText(mList.get(position).getAuthor());
            holder.time.setText(mList.get(position).getTime());
            holder.page.setText(mList.get(position).getPage()+"页");
            holder.summary.setText(mList.get(position).getAbstract());
            ImageLoader.getInstance().displayImage(mList.get(position).getAuthorHead(),holder.headicon);

            return convertView;
        }


    class ViewHolder{
        TextView username;
        ImageView headicon;
        TextView time;
        TextView page;
        TextView cheapter;
        TextView summary;
    }


}
