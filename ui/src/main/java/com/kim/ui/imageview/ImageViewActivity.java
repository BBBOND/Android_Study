package com.kim.ui.imageview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kim.ui.R;
import com.kim.ui.utils.HttpUtil;

import java.io.InputStream;

/**
 * Created by KIM on 2015/8/13.
 */
public class ImageViewActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private TextView img1TV;
    private ImageView img3;
    private ImageView img4;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private TextView tv1;
    private TextView tv2;
    private Button netIMGBtn;
    private EditText internetEt;
    private ImageView internetIMG;

    private final int minWidth = 80;
    private static final int IMAGE_SELECT = 1;
    private static final int IMAGE_CUT = 2;
    private Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_main);

        ImageView imageView1 = (ImageView) findViewById(R.id.img1);
        img1TV = (TextView) findViewById(R.id.img1TV);
        //设置图片比例
        //宽:200，高:100
        imageView1.setLayoutParams(new LinearLayout.LayoutParams(200, 100));
        img1TV.setText("height:" + imageView1.getLayoutParams().height + "---width--->" + imageView1.getLayoutParams().width);

        //设置选择本地图片
        findViewById(R.id.imgBtn1).setOnClickListener(this);
        //选择本地图片并裁剪
        findViewById(R.id.imgBtn2).setOnClickListener(this);

        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        tv1 = (TextView) findViewById(R.id.img4TV1);
        tv2 = (TextView) findViewById(R.id.img4TV2);
        seekBar1 = (SeekBar) findViewById(R.id.img4SeekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.img4SeekBar2);
        //设置拖动改变图片大小
        seekBar1.setOnSeekBarChangeListener(this);
        //设置拖动旋转图片
        seekBar2.setOnSeekBarChangeListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //设置seebar的最大限制
        seekBar1.setMax(dm.widthPixels - minWidth);

        internetEt = (EditText) findViewById(R.id.internetEt);
        internetIMG = (ImageView) findViewById(R.id.internetIMG);
        netIMGBtn = (Button) findViewById(R.id.netIMGBtn);
        //设置获取网络图片
        netIMGBtn.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_SELECT) {
                //处理图片 按照手机屏幕大小显示
                Uri url = data.getData();
                int dw = getWindowManager().getDefaultDisplay().getWidth();
                int dh = getWindowManager().getDefaultDisplay().getHeight() / 2;
                try {
                    //实现对图片裁剪的类，内部类
                    BitmapFactory.Options factory = new BitmapFactory.Options();
                    factory.inJustDecodeBounds = true;//设置为true，允许图片不是按照像素分配内存
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(url), null, factory);
                    //对图片宽高对应手机的屏幕进行匹配
                    int hRatio = (int) Math.ceil(factory.outHeight / (float) dh);
                    int wRatio = (int) Math.ceil(factory.outWidth / (float) dw);
                    //大于1表示图片大于手机屏
                    //缩放到1/radio的尺寸和1/radio^2像素
                    if (hRatio > 1 || wRatio > 1) {
                        if (hRatio > wRatio) {
                            factory.inSampleSize = hRatio;
                        } else {
                            factory.inSampleSize = wRatio;
                        }
                    }
                    factory.inJustDecodeBounds = false;
                    //使用BitmapFactory对图片进行适屏操作
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(url), null, factory);
                    img3.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == IMAGE_CUT) {
                Bitmap bitmap = data.getParcelableExtra("data");
                img3.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.imgBtn1:
                //提取手机的图片，并进行选择
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//打开手机图库
                startActivityForResult(intent, IMAGE_SELECT);
                break;
            case R.id.imgBtn2:
                intent = getImageClipIntent();
                startActivityForResult(intent, IMAGE_CUT);
                break;
            case R.id.netIMGBtn:
                String urlPath = internetEt.getText().toString().trim();
                if (!urlPath.equals("")) {
                    getIMG(urlPath);
                } else {
                    internetEt.setError("不能为空！");
                }
                break;
        }
    }

    private Intent getImageClipIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        //实现对图片的裁剪，设置图片属性大小
        intent.setType("image/*");//获取任意图片类型
        intent.putExtra("crop", "true");//滑动选中图片区域
        intent.putExtra("aspectX", 1);//表示剪切框的比例1:1的效果
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);//输出图片大小
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.img4SeekBar1) {
            int newWidth = progress + minWidth;
            int newHeight = (int) (newWidth * 3 / 4);
            img4.setLayoutParams(new LinearLayout.LayoutParams(newWidth, newHeight));
            tv1.setText("图像宽度：" + newWidth + "|图像高度：" + newHeight);

        } else if (seekBar.getId() == R.id.img4SeekBar2) {
            Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.cover)).getBitmap();
            matrix.setRotate(progress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            img4.setImageBitmap(bitmap);
            tv2.setText(progress + "°");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void getIMG(String urlPath) {
        new AsyncTask<String, Float, InputStream>() {

            ProgressDialog dialog = new ProgressDialog(ImageViewActivity.this);

            @Override
            protected InputStream doInBackground(String... params) {
                InputStream inputStream = null;
                try {
                    inputStream = HttpUtil.getInputStream(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return inputStream;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("读取中...");
                dialog.setCancelable(true);
                dialog.show();
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                super.onPostExecute(inputStream);
                if (inputStream != null) {
                    //io流设置图片，也可以字节数组设置
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    internetIMG.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(ImageViewActivity.this, "无图片信息", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                super.onProgressUpdate(values);

            }

            @Override
            protected void onCancelled(InputStream inputStream) {
                super.onCancelled(inputStream);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(urlPath);
    }
}
