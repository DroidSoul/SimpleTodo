package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    int position;
    EditText editItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editItem = (EditText)findViewById(R.id.editItem);
        position = getIntent().getIntExtra("pos", 0);
        String str = getIntent().getStringExtra("itemContent");
        editItem.setText(str);
        editItem.setSelection(str.length());
    }
    public void saveItem(View v) {
//        EditText editItem = (EditText)findViewById(R.id.editItem);
        Intent i = new Intent();
        i.putExtra("updatedItem", editItem.getText().toString());
        i.putExtra("code", position);
        setResult(RESULT_OK, i);
        finish();
    }
}
