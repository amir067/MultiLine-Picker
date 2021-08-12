package com.amir.wheelpickermultiline;

import android.app.Dialog;

public interface WheelPickerBtnListener {
    public void onConfirmBtnClick(Dialog dialog, String selectedValue);

    public void onCancelClick(Dialog dialog);

}
