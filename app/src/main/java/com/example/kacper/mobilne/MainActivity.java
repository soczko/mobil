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
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {
    ArrayList<Processor> processorsList;
    ArrayList<String> items;
    String writeToFile;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        processorsList = new ArrayList<>();
        items = new ArrayList<>();
        writeToFile = new String();
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
                        Intent i = new Intent(getApplicationContext(), Details.class);
                        i.putExtra("company", processorsList.get(pos).getCompany());
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
        if (itemText.contains(";")) {
            String[] tab = itemText.split(";");
            if (tab.length == 5) {
                Processor proc = new Processor();
                proc.setCompany(tab[0]);
                proc.setModel(tab[1]);
                proc.setCores(tab[2]);
                proc.setThreads(tab[3]);
                proc.setClock(tab[4]);
                processorsList.add(proc);
                items.add(tab[0] + " " + tab[1]);
                etNewItem.setText("");
                writeItems();

            }
        }
    }

    private void readItems() {
        String text = "";
        try {
            InputStream is = getAssets().open("lista.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
            String[] lines = text.split("\n");
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains(";")) {
                    String line = lines[i];
                    String[] tab = line.split(";");
                    if (tab.length == 5) {
                        Processor proc = new Processor();
                        items.add(tab[0] + " " + tab[1]);
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
            e.printStackTrace();
        }

    }

    private void writeItems() {
        writeToFile = "";
        try {
            for (int i = 0; i < itemsAdapter.getCount(); i++) {
                Processor proc = new Processor();
                proc = processorsList.get(i);
                if( i == itemsAdapter.getCount() - 1)
                {
                    writeToFile += (proc.getCompany() + ";" + proc.getModel() + ";" + proc.getCores() + ";" + proc.getThreads() + ";" + proc.getClock());
                }
                else{
                    writeToFile += (proc.getCompany() + ";" + proc.getModel() + ";" + proc.getCores() + ";" + proc.getThreads() + ";" + proc.getClock()+"\n");
                }
            }
            System.out.println(writeToFile);
            FileOutputStream os = openFileOutput("lista.txt", MODE_APPEND);
            os.write(writeToFile.getBytes());
            os.close();
            File fileDir = new File(getFilesDir(), "lista.txt");
            Toast.makeText(getBaseContext(), "File Saved at "+ fileDir, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
