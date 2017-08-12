package com.codepath.simpletodo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.onFragmentResult{
    ArrayList<Products> items;
    UsersAdapter itemsAdapter;
    ListView lvItems;
    static final int REQUEST_CODE = 1;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
//        items = new ArrayList<>();
        dbHandler = new MyDBHandler(this, null, null, 3);
        readItems();
//        items.set(1, "test");
        itemsAdapter = new UsersAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
//        items.add("first item");
        setupListViewListener();
    }
    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(new Products(itemText, "1", System.currentTimeMillis()));
        etNewItem.setText("");
        writeItems();
    }
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener (
                new OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
        });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//                        i.putExtra("pos", position);
//                        i.putExtra("itemContent", items.get(position).get_productname());
//                        i.putExtra("itemPriority", items.get(position).get_priority());
//                        i.putExtra("itemDuedate", items.get(position).get_time());
                        Bundle bundle = new Bundle();
                        bundle.putInt("pos", position);
                        bundle.putString("itemContent", items.get(position).get_productname());
                        bundle.putString("itemPriority", items.get(position).get_priority());
                        bundle.putLong("itemDuedate", items.get(position).get_time());
                        EditItemDialogFragment fragobj = EditItemDialogFragment.newInstance("title");
//                        EditItemDialogFragment fragobj = new EditItemDialogFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        fragobj.setArguments(bundle);
                        fragobj.show(fm, "fragment_edit_item");
//                        items.remove(position);
//                        itemsAdapter.notifyDataSetChanged();
//                        writeItems();
//                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );

    }
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("updatedItem");
            String pri = data.getExtras().getString("updatedPriority");
            long duedate = data.getExtras().getLong("updatedDuedate");
            int code = data.getExtras().getInt("code", 0);
//           if (items.get(code).get_time() <= duedate) {
            items.set(code, new Products(name, pri, duedate));
            itemsAdapter.notifyDataSetChanged();
            writeItems();
//            }
//            items.add(code, name);
        }
    }
*/

/*    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }*/
    private void readItems() {
        items = dbHandler.databaseToList();
    }
/*    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    private void writeItems() {
        dbHandler.deleteAll();
        for (Products p : items) {
            dbHandler.addProduct(p);
        }
    }
    @Override
    public void returnData(String body, String priority, long time, int code) {
        items.set(code, new Products(body, priority, time));
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }
}
