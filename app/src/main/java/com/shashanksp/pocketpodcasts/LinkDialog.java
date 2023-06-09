package com.shashanksp.pocketpodcasts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class LinkDialog extends AppCompatDialogFragment {
    private EditText ytlinkEdt;
    private LinkDialogListener linkDialogListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view)
                .setTitle("Youtube Link")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            //nothing
                    }
                })
                .setPositiveButton("Summarize", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Summarizing from Link Code or Function goes here.
                        String ytlink = ytlinkEdt.getText().toString();
                        linkDialogListener.getLink(ytlink);
                        Intent intent = new Intent(getContext(),SummarizedActivity.class);
                        startActivity(intent);
                    }
                });
       ytlinkEdt = view.findViewById(R.id.link_edt);
        return builder.create();
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            linkDialogListener = (LinkDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement DialogListener");
        }
    }
    public interface LinkDialogListener{
        void getLink(String ytlink);
    }
}
