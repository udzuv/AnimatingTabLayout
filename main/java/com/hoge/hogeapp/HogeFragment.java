package com.hoge.hogeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HogeFragment extends Fragment {
    private TextView textView;
    private HogeOpenHelper helper;
    private EditText editTextTitle, editTextDetail;

    public HogeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoge, container, false);

        helper = new HogeOpenHelper(getContext());

        editTextTitle = (EditText) view.findViewById(R.id.title_edittext);
        editTextDetail = (EditText) view.findViewById(R.id.detail_edittext);
        Button writeButton = view.findViewById(R.id.add_button);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
                readAllData();
            }
        });

        Button deleteAllButton = view.findViewById(R.id.delete_all_button);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllData();
                readAllData();
            }
        });

        textView = view.findViewById(R.id.hoge_textview);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readAllData();
    }

    private void readAllData(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                HogeOpenHelper.TABLE_NAME,
                new String[] { HogeOpenHelper.COLUMN_NAME_TITLE, HogeOpenHelper.COLUMN_NAME_DETAIL },
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < cursor.getCount(); i++) {
            stringBuilder.append(cursor.getString(0));
            stringBuilder.append(": ");
            stringBuilder.append(cursor.getString(1));
            stringBuilder.append("\n\n");
            cursor.moveToNext();
        }
        cursor.close();
        textView.setText(stringBuilder.toString());
    }

    private void writeData() {
        // not empty
        boolean titleFlag = TextUtils.isEmpty(editTextTitle.getText().toString());
        boolean detailFlag = TextUtils.isEmpty(editTextDetail.getText().toString());
        if (titleFlag & detailFlag) {
            editTextTitle.setError(getString(R.string.input_warning1));
            editTextDetail.setError(getString(R.string.input_warning2));
        } else if (titleFlag) {
            editTextTitle.setError(getString(R.string.input_warning1));
        } else if (detailFlag) {
            editTextDetail.setError(getString(R.string.input_warning2));
        } else {
//            Context context = getContext();
//            Toast.makeText(context, titleString + " : " + detailString, Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = helper.getWritableDatabase();
            helper.saveData(db, editTextTitle.getText().toString(), editTextDetail.getText().toString());

            editTextTitle.setText("");
            editTextDetail.setText("");
        }
    }

    private void deleteAllData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.onUpgrade(db, 1, 2);
    }

}
