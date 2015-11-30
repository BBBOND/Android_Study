package com.kim.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIM on 2015/8/24.
 */
public class ThirdActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);

        for (int i = 0; i < 10; i++) {
            list.add("哈哈哈" + i);
        }
        show();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(ThirdActivity.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Toast.makeText(ThirdActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.delete:
                                list.remove(position);
                                show();
                                Toast.makeText(ThirdActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.share:
                                Toast.makeText(ThirdActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.item_menu, popupMenu.getMenu());
                popupMenu.show();
                return true;
            }
        });
    }

    private void show() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ThirdActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
