package com.hubel.thu.thelab.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.hubel.thu.thelab.ItemAddHandler;
import com.hubel.thu.thelab.ui.activity.MainActivity;
import com.hubel.thu.thelab.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thu on 04.03.16.
 */
public class AddItemDialogFragment extends AppCompatDialogFragment implements ItemAddHandler, DialogInterface.OnClickListener,View.OnClickListener{


    @Bind(R.id.addItemEditText)
    EditText addItemEditText;
    final AlertDialog alertDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_item_dialog,null);
        ButterKnife.bind(this,view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle(R.string.add_item__title);
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.btn_ok),this);
        builder.setNegativeButton(getString(R.string.btn_cancel),this);
        return builder.create();
    }


    @Override
    public void onClick(View v) {
        Boolean wantToCloseDialog = false;
        if (TextUtils.isEmpty(addItemEditText.getText().toString())) {
            addItemEditText.requestFocus();
            addItemEditText.setError(getString(R.string.add_item_dialog_edit_text_item_validation_message));
        }else{
            onItemAdded(addItemEditText.getText().toString());
        }
        if(wantToCloseDialog)
            alertDialog.dismiss();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        //Do nothing
    }

    @Override
    public void onItemAdded(String item) {

    }

    @Override
    public void onStart()
    {
        super.onStart();
        AlertDialog alertDialog = (AlertDialog)getDialog();
        if(alertDialog != null)
        {
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(this);
        }
    }



}
