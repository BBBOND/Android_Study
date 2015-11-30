package com.kim.handler;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 先显示文字，再显示图片---分成两条线程解决
 * Created by KIM on 2015/8/31.
 */
public class SecondActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private ListView listView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        adapter = new MyAdapter(SecondActivity.this);
        listView = (ListView) findViewById(R.id.list_item);
        dialog.setTitle("提示");
        dialog.setMessage("下载中...");
        new MyTask().execute(CommonUrl.PRODUCT_URL);
    }

    public class MyTask extends AsyncTask<String, Void, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            List<Map<String, Object>> list = new ArrayList<>();
            //连接网络，获取json信息，解析
            //json，gson，fastjson解析
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(params[0]);
            try {
                HttpResponse response = client.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String jsonString = EntityUtils.toString(response.getEntity(), "utf-8");
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("product");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Map<String, Object> map = new HashMap<>();
                        //迭代输出json的key作为map的key
                        Iterator<String> iterator = object.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            Object value = object.get(key);
                            map.put(key, value);
                        }
                        list.add(map);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> list) {
            adapter.setData(list);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
            super.onPostExecute(list);
        }
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public MyAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<Map<String, Object>> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.list_content, null);
            } else {
                view = convertView;
            }
            TextView name = (TextView) view.findViewById(R.id.p_name);
            TextView address = (TextView) view.findViewById(R.id.p_adds);
            TextView price = (TextView) view.findViewById(R.id.p_price);
            name.setText(list.get(position).get("proname").toString());
            address.setText(list.get(position).get("proaddress").toString());
            price.setText(list.get(position).get("price").toString());

            final ImageView imageView = (ImageView) view.findViewById(R.id.p_img);
            DownLoadImage loadImage = new DownLoadImage(CommonUrl.PRODUCT_IMG + list.get(position).get("proimage").toString());
            loadImage.loagImage(new DownLoadImage.ImageCallBack() {
                @Override
                public void getDrawable(Drawable drawable) {
                    imageView.setImageDrawable(drawable);
//                    imageView.setImageBitmap(bitmap); 此方法更有优势
                }
            });
            return view;
        }
    }
}
