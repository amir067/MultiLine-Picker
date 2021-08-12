package com.amir.wheelpickermultiline;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.wheelpicker.R;

import java.util.ArrayList;

public class WheelPickerAdapter extends RecyclerView.Adapter<WheelPickerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> arrayList= new ArrayList<>();
    private int selectedItem = -1;

    public WheelPickerAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public WheelPickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_view_wheel_picker,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WheelPickerAdapter.ViewHolder holder, int position) {

        holder.tvValue.setText(arrayList.get(position));

        if (position == selectedItem) {
            Log.d("CenterPosition", "center" + position);
            //holder.tvValue.setBackgroundResource(R.drawable.item_view_background_wheel_picker);
            holder.tvValue.setTextColor(context.getResources().getColor(R.color.picker_item_selected));
            holder.tvValue.setTypeface(holder.tvValue.getTypeface(), Typeface.BOLD);
           holder.tvValue.setTextSize(18);

            //experimental
            //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(layoutParams);
            holder.itemView.setPadding(5,3,5,3);

        } else {
            holder.tvValue.setTypeface(holder.tvValue.getTypeface(), Typeface.NORMAL);
            holder.tvValue.setTextColor(context.getResources().getColor(R.color.picker_item_normal));
            holder.tvValue.setTextSize(14);//13
            //holder.tvValue.setBackgroundColor(Color.WHITE);

            //experimental
           // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//75
            holder.itemView.setLayoutParams(layoutParams);
            holder.itemView.setPadding(5,2,5,2);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public String getSelectedItem() {
        if(selectedItem==0){
            return  null;
        }
        if(arrayList.get(selectedItem).equals("")){
            return  null;
        }

        return arrayList.get(selectedItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvValue;
        View rootView;
        public ViewHolder(View itemView) {
            super(itemView);

            rootView=itemView;
            tvValue=itemView.findViewById(R.id.tvValue);

            tvValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Clicked : "+arrayList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void setSelecteditem(int selecteditem) {
        Log.d("POSITION",String.valueOf(selecteditem));
        this.selectedItem = selecteditem;
        notifyDataSetChanged();
    }
}
