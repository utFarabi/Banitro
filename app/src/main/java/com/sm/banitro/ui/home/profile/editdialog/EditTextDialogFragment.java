package com.sm.banitro.ui.home.profile.editdialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sm.banitro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditTextDialogFragment extends DialogFragment {

    // ********************************************************************************
    // Field

    // Instance
    private Unbinder unbinder;
    private int type;

    // View
    @BindView(R.id.dialog_fragment_edit_text_tv_title)
    TextView tvTitle;
    @BindView(R.id.dialog_fragment_edit_text_tv_description)
    TextView tvDescription;
    @BindView(R.id.dialog_fragment_edit_text_et_input)
    EditText etInput;

    // Data Type
    private static final String KEY_TYPE = "type";

    // ********************************************************************************
    // New Instance

    public static EditTextDialogFragment newInstance(int type) {
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;
        setType(bundle.getInt(KEY_TYPE));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_fragment_edit_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init Instance
        unbinder = ButterKnife.bind(this, view);

        // Init View
        switch (type) {
            case R.string.full_name:
                tvTitle.setText(R.string.full_name);
                tvDescription.setText(R.string.full_name_description);
                etInput.setInputType(InputType.TYPE_CLASS_TEXT);
                etInput.setHint(R.string.full_name_hint);
                break;
            case R.string.phone:
                tvTitle.setText(R.string.phone);
                tvDescription.setText(R.string.phone_description);
                etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                etInput.setHint(R.string.phone_hint);
                break;
            case R.string.address:
                tvTitle.setText(R.string.address);
                tvDescription.setText(R.string.address_description);
                etInput.setInputType(InputType.TYPE_CLASS_TEXT);
                etInput.setHint(R.string.address_hint);
                break;
        }
    }

    // ********************************************************************************
    // Method

    public void setType(int type) {
        this.type = type;
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.dialog_fragment_edit_text_btn_send)
    public void onClickSend() {

    }

    @OnClick(R.id.dialog_fragment_edit_text_btn_cancel)
    public void onClickCancel() {
        dismiss();
    }
}