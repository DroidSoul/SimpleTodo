package com.codepath.simpletodo;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.onFragmentResult{
    ArrayList<Products> items;
    UsersAdapter itemsAdapter;
    ListView lvItems;
    MyDBHandler dbHandler;
//    TextView tvDuedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tvDuedate = (TextView)findViewById(R.id.tvDuedate);
        lvItems = (ListView)findViewById(R.id.lvItems);

        dbHandler = new MyDBHandler(this, null, null, 3);
        readItems();

        itemsAdapter = new UsersAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
/*        tvDuedate.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {orderbyDuedate(v);
                    }
                }
        );
*/
        setupListViewListener();
    }
    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(new Products(itemText, "1", System.currentTimeMillis()));
        etNewItem.setText("");
        acitivtyToFragment(items.size() - 1);
//        writeItems();
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
                        acitivtyToFragment(position);
                    }
                }
        );

    }

    private void readItems() {
        items = dbHandler.databaseToList();
    }

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
 /*   public void orderbyDuedate(View view) {
//        items.clear();
        items = dbHandler.dbOrderbyDuedate();
        itemsAdapter.notifyDataSetChanged();
    }*/
    public void acitivtyToFragment(int position) {
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
    }
}
