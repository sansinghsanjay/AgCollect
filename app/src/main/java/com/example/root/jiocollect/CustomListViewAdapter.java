package com.example.root.jiocollect;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vidhiben.Sherathia on 9/25/2018.
 */

public class CustomListViewAdapter extends ArrayAdapter<RowItem>
{
    Context context;
    private static List<RowItem> items;

    public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        CheckBox name;
        //TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.textview);
            //holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);

            /*holder.imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(),"position"+pos,Toast.LENGTH_SHORT).show();

                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialog);

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.gravity = Gravity.CENTER;

                    dialog.getWindow().setAttributes(lp);
                    dialog.setTitle("Details");

                    TextView text = (TextView) dialog.findViewById(R.id.txtView);
                    text.setText(rowItem.getDesc());
                    ImageView image = (ImageView) dialog.findViewById(R.id.imageView11);
                    image.setImageResource(rowItem.getImageId());

                    dialog.show();
                }
            });*/

            convertView.setTag(holder);

            ViewHolder finalHolder = holder;

            holder.name.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    CheckBox cb = (CheckBox) v;
                    RowItem disease = (RowItem) cb.getTag();
                    disease.setCheckboxStatus(cb.isChecked());

                }
            });



        }
        else
            holder = (ViewHolder) convertView.getTag();

        /*holder.txtDesc.setText(rowItem.getDesc());
        //holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());*/

        holder.txtDesc.setText(  rowItem.getDesc() );
        //holder.name.setText(country.getName());
        holder.name.setChecked(rowItem.getCheckboxStatus());
        Log.e("status",rowItem.getCheckboxStatus()+"");
        holder.imageView.setImageResource(rowItem.getImageId());
        holder.name.setTag(rowItem);

        return convertView;
    }

    RowItem getDisease(int position) {
        return ((RowItem) getItem(position));
    }

    static List<RowItem> getBox() {
        List<RowItem> box = new ArrayList<RowItem>();
        for (RowItem p : items)
        {
            if ((p.checkboxStatus))
            {
                box.add(p);
            }
            else
            {
                box.add(p);
            }
        }
        return box;
    }
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            getDisease((Integer) buttonView.getTag()).checkboxStatus = isChecked;
        }
    };

}
