package com.kim.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem1 = menu.add(1001, 100, 1, "第二页");
        menuItem1.setIcon(R.drawable.abc_ic_commit_search_api_mtrl_alpha);
        menuItem1.setShortcut('c', 'c');
        menuItem1.setShowAsAction(1);
        MenuItem menuItem2 = menu.add(1001, 101, 2, "用户设置");
        menuItem2.setVisible(false);
        MenuItem menuItem3 = menu.add(1001, 102, 3, "第三页");
        menuItem3.setCheckable(false);

        return true;
    }

    private void show() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case 100:
                intent = new Intent(MainActivity.this, SecondActivity.class);
                item.setIntent(intent);
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 101:
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 102:
                intent = new Intent(MainActivity.this, ThirdActivity.class);
                item.setIntent(intent);
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_1:
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = menuInfo.position;
                list.remove(position);
                show();
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(MainActivity.this, "You Click " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
