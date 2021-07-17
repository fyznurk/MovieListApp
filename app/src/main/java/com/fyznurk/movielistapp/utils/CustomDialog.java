package com.fyznurk.movielistapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.fyznurk.movielistapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialog extends Dialog {


    @BindView(R.id.txtMessage)
    TextView txtMessage;

    public CustomDialog(Context context, String message) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.custm_dialog);
        ButterKnife.bind(this);
        txtMessage.setText(message);
    }

    @OnClick(R.id.btnOk)
    void btnOk() {
        dismiss();
    }

}
