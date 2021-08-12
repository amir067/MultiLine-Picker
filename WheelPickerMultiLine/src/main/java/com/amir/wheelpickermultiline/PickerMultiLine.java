package com.amir.wheelpickermultiline;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.wheelpicker.R;

import java.util.ArrayList;


public class PickerMultiLine extends Dialog implements View.OnClickListener {
    private static final String TAG = "PickerMultiLine";

    private Activity context;
    private final WheelPickerBtnListener wheelPickerBtnListener;
    private boolean isCancelable;

    //tittle
    private TextView headingTv;
    private String heading="Select Option";

    //RV
    private WheelPickerAdapter wheelPickerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private AppCompatButton cancelBtn, confirmBtn;
    private RecyclerView wheelPickerRV;

    //data
    private ArrayList<String> data;
    private String selectedValue = null;

    public PickerMultiLine(Activity context, ArrayList<String> dataList, String selectedValue, boolean isCancelable, WheelPickerBtnListener wheelPickerBtnListener) {
        super(context);
        this.context = context;
        this.data = dataList;
        this.selectedValue = selectedValue;
        this.wheelPickerBtnListener = wheelPickerBtnListener;
        this.isCancelable = isCancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_wheel_picker_multi_line);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setDimAmount((float) 0.89);
        //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(isCancelable);

        wheelPickerRV = findViewById(R.id.wheelPickerRV);
        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        headingTv =  findViewById(R.id.tittleTv);

        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(context);
        wheelPickerRV.setLayoutManager(linearLayoutManager);
        wheelPickerRV.setHasFixedSize(true);
        wheelPickerRV.setLayoutManager(linearLayoutManager);

        wheelPickerAdapter = new WheelPickerAdapter(context, data);
        wheelPickerRV.setAdapter(wheelPickerAdapter);
        wheelPickerRV.setHasFixedSize(true);
        wheelPickerRV.smoothScrollToPosition(0);

        wheelPickerRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                int lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (data.size() == 1) {
                    wheelPickerAdapter.setSelecteditem(0);
                    //Log.e(TAG, "onScrolled: check1");
                } else if (lastItem == data.size() - 1) {
                    //Log.e(TAG, "onScrolled: check2");
                    wheelPickerAdapter.setSelecteditem(data.size() - 2);
                } else {
                    //Log.e(TAG, "onScrolled: check3");
                    wheelPickerAdapter.setSelecteditem(firstItem + 1);
                }

            }
        });


    }

    protected void onCloseTap() {
        dismiss();
    }

    public String getHeadingText() {
        return heading;
    }

    public void setHeadingText(String title) {
        this.heading = title;
        //Log.e(TAG, "setHeadingText: " );
    }

    @Override
    protected void onStart() {
        super.onStart();
        headingTv.setText(heading);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.confirmBtn) {
            if (selectedValue == null || selectedValue.equals("")) {
               // Log.i(TAG, "value was null ");
                selectedValue = data.get(0);
            }
            wheelPickerBtnListener.onConfirmBtnClick(this, selectedValue);
            dismiss();
        } else if (id == R.id.cancelBtn) {
            wheelPickerBtnListener.onCancelClick(this);
            dismiss();
        }
        //dismiss();
    }


    public void show() {
        super.show();
    }

    public void hide() {
        dismiss();
    }

}
