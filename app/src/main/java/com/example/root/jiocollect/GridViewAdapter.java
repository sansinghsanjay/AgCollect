package com.example.root.jiocollect;

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

public class GridViewAdapter extends ArrayAdapter<DefectTemplate> {

    // other vars
    private static ArrayList<DefectTemplate> defects;

    // init GridViewAdapter
    public GridViewAdapter(Context context, int textViewResourceId, ArrayList<DefectTemplate> defects) {
        super(context, textViewResourceId,defects);
        this.defects = new ArrayList<>();
        this.defects.addAll(defects);
    }

    public int getCount() {
        return defects.size();
    }

    public DefectTemplate getItem(int position) {
        return defects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView code;
        CheckBox name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.v("ConvertView", String.valueOf(position));
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.gridviewlayout, null);
            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.textview);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            // code to enlarge image on tap
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    DefectTemplate defect = defects.get(position);
                    TextView text = (TextView) dialog.findViewById(R.id.txtView);
                    text.setText(defect.getDefectName());
                    ImageView image = (ImageView) dialog.findViewById(R.id.imageView11);
                    image.setImageResource(defect.getImage());
                    dialog.show();
                }
            });
            convertView.setTag(holder);
            //ViewHolder finalHolder = holder;
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    DefectTemplate defect = (DefectTemplate) cb.getTag();
                    defect.setCheckboxStatus(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        DefectTemplate defect = defects.get(position);
        holder.code.setText(defect.getDefectName());
        holder.name.setChecked(defect.getCheckboxStatus());
        Log.e("status",defect.getCheckboxStatus()+"");
        holder.imageView.setImageResource(defect.getImage());
        holder.name.setTag(defect);
        return convertView;
    }

    DefectTemplate getDefect(int position) {
        return ((DefectTemplate) getItem(position));
    }

    static ArrayList<DefectTemplate> getBox() {
        ArrayList<DefectTemplate> box = new ArrayList<DefectTemplate>();
        for (DefectTemplate p : defects) {
            if ((p.checkboxStatus)) {
                box.add(p);
            }
            else {
                box.add(p);
            }
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            getDefect((Integer) buttonView.getTag()).checkboxStatus = isChecked;
        }
    };
}