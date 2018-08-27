package com.v.testapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<MyItem> modelArrayList;


    public CustomAdapter(Context context, ArrayList<MyItem> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.myadapter, null, true);

            holder.ivCheckBox = (ImageView) convertView.findViewById(R.id.iv_check_box);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.adaptertextview = (TextView) convertView.findViewById(R.id.adaptertextview);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        MyItem model = modelArrayList.get(position);

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.uncheck_icon);


//        holder.checkBox.setText("Checkbox "+position);
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        holder.tvTitle.setText(modelArrayList.get(position).getTitle());
        holder.adaptertextview.setText(modelArrayList.get(position).getCreate() + " " + modelArrayList.get(position).getTime());
//
//        holder.checkBox.setChecked(modelArrayList.get(position).getSelected());
//
//        holder.checkBox.setTag(R.integer.btnplusview, convertView);
//        holder.checkBox.setTag( position);
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
////                TextView tv = (TextView) tempview.findViewById(R.id.animal);
//                Integer pos = (Integer)  holder.checkBox.getTag();
//                String posi = String.valueOf(pos);
//               // Toast.makeText(context, "Checkbox "+posi+" clicked!", Toast.LENGTH_SHORT).show();
//
//                if(modelArrayList.get(pos).getSelected()){
//                    modelArrayList.get(pos).setSelected(false);
//                }else {
//                    modelArrayList.get(pos).setSelected(true);
//                }
//
//                Intent intent = new Intent("custom-message");
//                intent.putExtra("get", posi);
//                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//
//            }
//        });

        return convertView;
    }

    public void updateRecords(List<MyItem> users){
        this.modelArrayList = (ArrayList<MyItem>) users;

        notifyDataSetChanged();
    }


    private class ViewHolder {

        ImageView ivCheckBox;
        private TextView tvTitle;
        private TextView adaptertextview;

    }

}
