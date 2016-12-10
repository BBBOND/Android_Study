package com.kim.processesandthreads;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private EditText imgURL;
    private Button imgBtn;
    private ImageView imgIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgURL = (EditText) findViewById(R.id.img_url);
        imgBtn = (Button) findViewById(R.id.img_show_btn);
        imgIV = (ImageView) findViewById(R.id.img_IV);

        imgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_show_btn:
                String imgUrl = imgURL.getText().toString().trim();
                if (imgUrl.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入图片地址！", Toast.LENGTH_SHORT).show();
                } else {
                    showImg(imgUrl);
                }
                break;
        }
    }

    public void showImg(String imgUrl) {
        new AsyncTask<String, Integer, Bitmap>() {

            ProgressDialog dialog = null;

            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap = null;
                InputStream inputStream;
                HttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    HttpResponse response = client.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        inputStream = response.getEntity().getContent();
                        long file_length = response.getEntity().getContentLength();
                        int len = 0;
                        byte[] data = new byte[1024];
                        int total_length = 0;
                        while ((len = inputStream.read(data)) != -1) {
                            outputStream.write(data, 0, len);
                            total_length += len;
                            int value = (int) ((total_length / (float) file_length) * 100);
                            publishProgress(value);
                        }
                        byte[] result = outputStream.toByteArray();
                        bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("正在下载...");
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                imgIV.setImageBitmap(bitmap);
                dialog.dismiss();
                super.onPostExecute(bitmap);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                dialog.setProgress(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(Bitmap bitmap) {
                super.onCancelled(bitmap);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(imgUrl);
    }
}
