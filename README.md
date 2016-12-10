## Android_Study
android的基础学习

###[Dail](https://github.com/BBBOND/Android_Study/tree/master/dail)

* 说明：电话拨打
* 实现方式：

    ```java
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:"+number));
    startActivity(intent);
    ```

###[Dialogs](https://github.com/BBBOND/Android_Study/tree/master/dialogs)

* 说明：各式对话框
* 注意点：
    * `AlertDialog` 需要通过 `AlertDialog.Builder` 配置
    * 通过 `builder.create()` 创建
    * 通过 `alertDialog.show()` 显示

###[Handler](https://github.com/BBBOND/Android_Study/tree/master/handler)

* 说明：Handler(用于沟通不同线程间的数据)
* 实现方法:
        
    ```java
    //The data must be transmitted by Message
    Message message = Message.obtain();
    message.obj = data;
    message.what = IS_FINISH;
    handler.sendMessage(message);
    ```

###[Http](https://github.com/BBBOND/Android_Study/tree/master/http)

* 说明：Android数据传输（Http、HttpClient方式）
* 知识点：
    * HttpGet：
        1. 通过路径获取`URL`类
        2. 通过使用`url.openConnection()`获取`HttpURLConnection`
        3. 通过`conn.setConnectTimeout(Timeout)`设置连接超时时间
        4. 连接时设置 `conn.setRequestMethod(Method)` 为`"GET"`，即可实现GET方式访问
        5. 通过`conn.getResponseCode()`获得链接状态代码
        6. 如果`responseCode==200`表示服务器成功处理，则通过`conn.getInputStream()`获取输入流
        7. 最后通过`InputStream`获取传输的数据
</br></br>
    * HttpPost：
        1. 通过路径获得`URL`类
        2. 通过使用`url.openConnection()`获取`HttpURLConnection`
        3. 通过`conn.setConnectTimeout(Timeout)`设置连接超时时间
        4. 连接时设置 `conn.setRequestMethod(Method)` 为`"GET"`，即可实现GET方式访问
        5. 获取`OutputStreamWriter`(输出流)，通过`BufferedWriter`向输出流中写入需要传递的参数
        6. 通过`conn.getInputStream()`获取输入流
        7. 最后通过`InputStream`获取传输的数据
</br></br>
    * HttpClientGet：
        1. 使用`new DefaultHttpClient()`获取`HttpClient`对象
        2. 使用`new HttpGet(URL_PATH)`获取`HttpGet`对象
        3. 执行`client.execute(httpGet)`即可获得`HttpResponse`对象
        4. 通过`httpResponse.getEntity()`获得`HttpEntity`对象
        5. 可以使用`EntityUtils`类的`toXxxx(httpEntity)`方法将`HttpEntity`类转化为对应来行的数据
</br></br>
    * HttpClientPost
        1. 使用`new DefaultHttpClient()`获取`HttpClient`对象
        2. 使用`new HttpPost(URL_PATH)`获取`HttpPost`对象
        3. 使用`List<BasicNameValuePair>`保存传输的参数
        4. `list`中插入的类型为`BasicNameValuePair`
        5. 使用`new UrlEncodedFormEntity(list)`将`list`转化为`HttpEntity`对象
        6. 使用`httpPost.setEntity(httpEntity)`设置参数
        7. 通过`httpResponse.getEntity()`获得`HttpEntity`对象
        8. 可以使用`EntityUtils`类的`toXxxx(httpEntity)`方法将`HttpEntity`类转化为对应来行的数据















