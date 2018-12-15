package com.example.kacper.mobilne;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {
    Processor proc = new Processor();
    ArrayList<Processor> processorsList;
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
        proc = new Processor();
        processorsList = new ArrayList<>();
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
                        processorsList.remove(pos);
                        //details.remove(pos);
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
                        //System.out.println(details.get(pos));
                        System.out.println(processorsList.get(pos));

                        // Launching new Activity on selecting single List Item
                        Intent i = new Intent(getApplicationContext(), Details.class);
                        // sending data to new activity
                        //i.putExtra("proc", processorsList.get(pos));
                        //i.putExtra("items", items.get(pos));
                        //i.putExtra("details", details.get(pos));
                        i.putExtra("company",processorsList.get(pos).getCompany());
                        i.putExtra("model", processorsList.get(pos).getModel());
                        i.putExtra("cores", processorsList.get(pos).getCores());
                        i.putExtra("threads", processorsList.get(pos).getThreads());
                        i.putExtra("clock", processorsList.get(pos).getClock());
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
            if(tab.length == 5)
            {
                proc.setCompany(tab[0]);
                proc.setModel(tab[1]);
                proc.setCores(tab[2]);
                proc.setThreads(tab[3]);
                proc.setClock(tab[4]);
                processorsList.add(proc);
                itemsAdapter.add(tab[0]+" "+tab[1]);
                etNewItem.setText("");
                writeItems();

            }
        }
    }


    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "list.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            for(int i =0; i<items.size(); i++)
            {
                if(items.get(i).contains(";")) {
                    String line = items.get(i);
                    String[] tab = line.split(";");
                    if(tab.length == 5)
                    {
                        items.set(i, tab[0]+" "+tab[1]);
                        proc.setCompany(tab[0]);
                        proc.setModel(tab[1]);
                        proc.setCores(tab[2]);
                        proc.setThreads(tab[3]);
                        proc.setClock(tab[4]);
                        processorsList.add(proc);

                    }
                }
            }
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }


    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "list.txt");
        writeToFile.clear();
        try {
            for(int i= 0; i<itemsAdapter.getCount(); i++)
            {
                proc = processorsList.get(i);
                //String line = items.get(i)+";"+details.get(i);
                writeToFile.add(proc.getCompany()+";"+proc.getModel()+";"+proc.getCores()+";"+proc.getThreads()+";"+proc.getClock());
                FileUtils.writeLines(todoFile, writeToFile);
            }
            FileUtils.writeLines(todoFile, writeToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
