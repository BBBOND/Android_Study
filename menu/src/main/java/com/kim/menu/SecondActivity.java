package com.kim.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIM on 2015/8/24.
 */
public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list = new ArrayList<>();
    private ActionMode actionMode;

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
                if (actionMode != null) {
                    return false;
                }
                actionMode = startActionMode(new ActionMode.Callback() {
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        getMenuInflater().inflate(R.menu.item_menu, menu);
                        return true;
                    }

                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Toast.makeText(SecondActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.delete:
                                list.remove(position);
                                show();
                                Toast.makeText(SecondActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.share:
                                Toast.makeText(SecondActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }

                    public void onDestroyActionMode(ActionMode mode) {
                        actionMode = null;
                    }
                });
                view.setSelected(true);
                return true;
            }
        });
    }

    private void show() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
