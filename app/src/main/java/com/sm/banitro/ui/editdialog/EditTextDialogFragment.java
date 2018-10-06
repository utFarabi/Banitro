package com.sm.banitro.ui.editdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sm.banitro.R;
import com.sm.banitro.util.ConstantUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditTextDialogFragment extends DialogFragment {

    // ********************************************************************************
    // Field

    // Instance
    private Interaction interaction;
    private Unbinder unbinder;

    // View
    @BindView(R.id.dialog_fragment_edit_text_tv_title)
    TextView tvTitle;
    @BindView(R.id.dialog_fragment_edit_text_tv_description)
    TextView tvDescription;
    @BindView(R.id.dialog_fragment_edit_text_et_input)
    EditText etInput;
    @BindView(R.id.dialog_fragment_edit_text_btn_send)
    Button btnSend;

    // Data Type
    private static final String KEY_TYPE = "type";
    private static final String KEY_CALL_STATUS = "callStatus";
    private int type, callStatus;

    // ********************************************************************************
    // New Instance

    public static EditTextDialogFragment newInstance(int callStatus, int type) {
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CALL_STATUS, callStatus);
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    // ********************************************************************************
    // Basic Override

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (Interaction) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;
        setType(bundle.getInt(KEY_TYPE));
        setCallStatus(bundle.getInt(KEY_CALL_STATUS));
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
            case R.string.password:
                tvTitle.setText(R.string.password);
                tvDescription.setText(R.string.password_description);
                etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                etInput.setHint(R.string.password_hint);
                break;
        }
        if (callStatus == ConstantUtil.REGISTER_DIALOG) {
            btnSend.setText(R.string.approve);
        }
    }

    // ********************************************************************************
    // Method

    public void setType(int type) {
        this.type = type;
    }

    public void setCallStatus(int callStatus) {
        this.callStatus = callStatus;
    }

    // ********************************************************************************
    // Supplementary Override

    @OnClick(R.id.dialog_fragment_edit_text_btn_send)
    public void onClickSend() {
        String text = etInput.getText().toString();
        if (type == R.string.phone) {
            if (text.length() != 11 || !text.substring(0, 2).equals("09")) {
                etInput.setText("");
                text = "";
            }
        }else if (type == R.string.password) {
            if (text.length() > 8) {
                etInput.setText("");
                text = "";
            }
        }
        if (text.isEmpty()) {
            etInput.requestFocus();
        } else {
            if (callStatus == ConstantUtil.EDIT_DIALOG) {
                interaction.setTextToProfileFragment(text, type);
            } else if (callStatus == ConstantUtil.REGISTER_DIALOG) {
                interaction.setTextToRegisterFragment(text, type);
            }
            dismiss();
        }
    }

    @OnClick(R.id.dialog_fragment_edit_text_btn_cancel)
    public void onClickCancel() {
        dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interaction = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // ********************************************************************************
    // Interface

    public interface Interaction {

        void setTextToProfileFragment(String text, int type);

        void setTextToRegisterFragment(String text, int type);
    }
}