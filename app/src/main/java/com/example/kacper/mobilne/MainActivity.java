package com.example.kacper.mobilne;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    ArrayList<String> items;
    ArrayList<String> details;
    ArrayList<String> writeToFile;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        details = new ArrayList<>();
        writeToFile = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListenerRemove();
        setupListViewListenerDetails();
    }

    private void setupListViewListenerRemove() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        details.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }
    private void setupListViewListenerDetails() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {

                        System.out.println("MAIN");
                        System.out.println(items.get(pos));
                        System.out.println(details.get(pos));

                        // Launching new Activity on selecting single List Item
                        Intent i = new Intent(getApplicationContext(), Details.class);
                        // sending data to new activity
                        i.putExtra("items", items.get(pos));
                        i.putExtra("details", details.get(pos));
                        startActivity(i);
                    }
                }
        );
    }


    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if(itemText.contains(";"))
        {
            String[] tab = itemText.split(";");
            itemText = tab[0];
            if(tab[1] == null) {
                details.add("");
            }
            else details.add(tab[1]);
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "procesory.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            for(int i =0; i<items.size(); i++)
            {
                if(items.get(i).contains(";")) {
                    String line = items.get(i);
                    String[] tab = line.split(";");
                    items.set(i, tab[0]);
                    details.add(tab[1]);
                }
                else{
                    details.add("");
                }
            }
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "procesory.txt");
        writeToFile.clear();
        try {
            for(int i= 0; i<itemsAdapter.getCount(); i++)
            {

                String line = items.get(i)+";"+details.get(i);
                writeToFile.add(line);
                //FileUtils.writeLines(todoFile, writeToFile);
            }
            FileUtils.writeLines(todoFile, writeToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
