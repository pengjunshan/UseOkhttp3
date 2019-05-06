package com.kelin.useokhttp3.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kelin.useokhttp3.Bean.UserInfo;
import com.kelin.useokhttp3.R;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author：PengJunShan.

 * 时间：On 2019-04-30.

 * 描述：简单使用Okhttp3,普通GET请求、POST键值对、POST-Json字符串、
 */
public class SimplenessActivity extends AppCompatActivity {

  private Handler mHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what){
        case 0:
          Toast.makeText(SimplenessActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
          break;

        case 1:
          String json = (String) msg.obj;
          Toast.makeText(SimplenessActivity.this, "请求成功="+json, Toast.LENGTH_SHORT).show();
          break;
      }
    }
  };

  private File file;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simpleness);
  }


  /**
   * Get请求
   * @param view
   */
  public void GetRequet(View view) {

    //1.第一步创建OkHttpClient对象
    final OkHttpClient okHttpClient = new OkHttpClient();

    String url ="https://www.wanandroid.com/banner/json";

    //2. 如果需要拼接参数 （一般有参数的都会用Post请求，除非参数不重要）
//        Map<String, String> params = new HashMap<>();
//        params.put("movieid", "246363");
//        params.put("limit", "3");
//        params.put("offset", "5");
//        url = appendParams(url,params);

    //3.第二步创建request
    Request.Builder builder = new Request.Builder();
    final Request request = builder.url(url)
        .get()
        .build();

    //4.新建一个Call对象
    final Call call = okHttpClient.newCall(request);

    //5.异步请求网络enqueue(Callback)
    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        mHandler.sendEmptyMessage(0);
        Log.e("TAG", "请求失败="+e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        Message message = new Message();
        message.what=1;
        message.obj =json;
        mHandler.sendMessage(message);
        Log.e("TAG", "请求成功="+json);
      }
    });

  }


  /**
   * 拼接参数
   * @param url
   * @param params
   * @return
   */
  private String appendParams(String url, Map<String, String> params) {
    StringBuilder sb = new StringBuilder();
    sb.append(url + "?");
    if (params != null && !params.isEmpty()) {
      for (String key : params.keySet()) {
        sb.append(key).append("=").append(params.get(key)).append("&");
      }
    }
    sb = sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  /**
   * 登录接口
   * POST请求  Key-Value
   */
  public void PostKeyValueRequet(View view) {

    // 1.拿到okhttpClient对象
    OkHttpClient okHttpClient = new OkHttpClient();

    //2.创建 FormBody 添加需要的键值对
    FormBody formBody = new FormBody.Builder()
        .add("username","15294792877")
        .add("password","15294792877pp")
        .build();

    // 3.构造Request
    Request.Builder builder = new Request.Builder();
    Request request = builder.url("https://www.wanandroid.com/user/login")
        .post(formBody)//键值对
        .build();

    //4.创建一个Call对象
    Call call = okHttpClient.newCall(request);

    //5.异步请求enqueue(Callback)
    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        mHandler.sendEmptyMessage(0);
        Log.e("TAG", "登录失败="+e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {

        String json = response.body().string();
        Gson gson = new GsonBuilder().serializeNulls().create();
        UserInfo userInfo = gson.fromJson(json,UserInfo.class);
        if(userInfo!=null) {
          if(userInfo.getErrorCode()!=0) {
            mHandler.sendEmptyMessage(0);
            Log.e("TAG", userInfo.getErrorMsg());
          }else {
            Message message = new Message();
            message.what=1;
            message.obj =json;
            mHandler.sendMessage(message);
            Log.e("TAG", "登录成功="+json);
          }
        }

      }
    });

  }


  /**
   * POST 提交 json数据
   * @param view
   * 没有测试服务器地址
   */
  public void PostJsonRequet(View view) {

    // 1.拿到okhttpClient对象
    OkHttpClient okHttpClient = new OkHttpClient();

    //需要提交的json字符串
    String jsonStr = "hahaha";

    //2.创建 RequestBody 设置提交类型MediaType+json字符串
    RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);

    // 3.构造Request
    Request.Builder builder = new Request.Builder();
    Request request = builder.url("https://www.wanandroid.com/user/login")
        .post(requestBody)
        .build();

    //4.创建一个Call对象
    Call call = okHttpClient.newCall(request);

    //5.异步请求enqueue(Callback)
    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        mHandler.sendEmptyMessage(0);
        Log.e("TAG", "失败="+e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();

        Gson gson = new GsonBuilder().serializeNulls().create();
        UserInfo userInfo = gson.fromJson(json,UserInfo.class);
        if(userInfo!=null) {
          if(userInfo.getErrorCode()!=0) {
            mHandler.sendEmptyMessage(0);
            Log.e("TAG", userInfo.getErrorMsg());
          }else {
            mHandler.sendEmptyMessage(1);
            Log.e("TAG", "登录成功="+json);
          }
        }
      }
    });

  }


  /**
   * 提交txt文件
   * POST请求
   * 没有测试服务器地址
   */
  public void PostFileRequet(View view) {
    //1.创建OkHttpClient对象
    OkHttpClient okHttpClient = new OkHttpClient();

    //2.获取文件地址，设置上传文件类型，构造RequestBody对象
    File fileAdress = new File("/sdcard/wangshu.txt");
    MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
    RequestBody requestBody = RequestBody.create(mediaType,fileAdress);

    //3.构造Requst对象
    Request request = new Request.Builder()
        .url("http://www.baidu.com")
        .post(requestBody)
        .build();

    //4.构造Call对象进行 异步请求enqueue(Callback)
    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        mHandler.sendEmptyMessage(0);
        Log.e("TAG", "post"+e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        mHandler.sendEmptyMessage(1);
        Log.e("TAG", "请求成功="+json);
      }
    });
  }

  /**
   * 上传图片
   * 没有测试服务器地址
   */
  public void PostImgRequet(View view) {
    //1.创建OkHttpClient对象
    OkHttpClient okHttpClient = new OkHttpClient();

    //2.设置文件类型
    MediaType mediaType = MediaType.parse("image/png");

    if (file != null && file.exists()) {

      //3.构造RequestBody 指定文件类型和文件
      RequestBody image = RequestBody.create(mediaType, file);
      RequestBody requestBody = new MultipartBody.Builder()
          .setType(MultipartBody.FORM)
          .addFormDataPart("img", file.getName(), image)
          .build();

      //4.创建Request对象
      Request request = new Request.Builder()
          .header("Authorization", "Client-ID " + "...")
          .url("www.baidu.login")
          .post(requestBody)
          .build();

      //5.异步请求newCall（Callback）
      okHttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          mHandler.sendEmptyMessage(0);
          Log.e("TAG", "图片上传失败="+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          String result = response.body().string();
          mHandler.sendEmptyMessage(1);
          Log.e("TAG", "成功上传图片=" + result);
        }
      });

    }
  }

  /**
   * 下载图片
   */
  public void GetImgRequetSimpleness(View view) {
    //1.创建OkHttpClient对象
    OkHttpClient okHttpClient = new OkHttpClient();
    String url = "http://p0.meituan.net/165.220/movie/7f32684e28253f39fe2002868a1f3c95373851.jpg";
    //2.创建Request对象
    Request request  = new Request.Builder()
        .url(url)
        .build();
    //3.异步请求newCall（Callback）
    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        Log.e("TAG", "下载失败");
        mHandler.sendEmptyMessage(0);
      }
      @Override
      public void onResponse(Call call, Response response) throws IOException {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(SimplenessActivity.this, "下载图片成功", Toast.LENGTH_SHORT).show();
          }
        });
        /**
         * 用java文件输入流下载图片
         */
               /* InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/okhttp.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("TAG", "IOException");
                    e.printStackTrace();
                }*/

        //方法一,获取byte数组，然后转换成图片
        byte[] bytes = response.body().bytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        //方法二,可以获取字节流,然后转换成图片
//        InputStream inputStream = response.body().byteStream();
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        /**
         * 保存图片
         */
 /*       File file=new File("/sdcard/okhttp.jpg");
        file.createNewFile();
        //创建文件输出流对象用来向文件中写入数据
        FileOutputStream out=new FileOutputStream(file);
        //将bitmap存储为jpg格式的图片
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        //刷新文件流
        out.flush();
        out.close();*/

        if(bitmap!=null) {
          Log.e("TAG", "图片下载成功");
        }

      }
    });
  }

  /**
   * 图文混合上传
   * 没有测试服务器地址
   */
  public void PostImgKeyValueRequet(View view) {

    //1.创建OkHttpClient对象
    OkHttpClient okHttpClient = new OkHttpClient();

    //2.构建多部件builder
    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

    //3.创建 Map 添加需要的键值对
    Map<String, String> params = new HashMap<>();
    params.put("username","15294792877");
    params.put("password","15294792877pp");

    //4.获取要上传的图片集合
    List<File> fileList = new ArrayList<>();

    //5.获取参数并放到请求体中
    try {
      if (params != null) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
          //将请求参数逐一遍历添加到我们的请求构建类中
          bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    //6.添加图片集合到请求体中
    if (fileList != null) {
      for (File f : fileList) {
        bodyBuilder.addFormDataPart("files", f.getName(),
            RequestBody.create(MediaType.parse("image/png"), f));
      }
    }

    //7.构造Request
    Request request = new Request.Builder()
        .url("https://www.wanandroid.com/user/login")
        .post(bodyBuilder.build())
        .build();

    //8.异步请求enqueue(Callback)
    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        mHandler.sendEmptyMessage(0);
        Log.e("TAG", "失败="+e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        Log.e("TAG", "成功="+json);
      }
    });


  }


}
