package com.kelin.useokhttp3.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.kelin.useokhttp3.Bean.UserInfo;
import com.kelin.useokhttp3.R;
import com.kelin.useokhttp3.net.HttpRequest;
import com.kelin.useokhttp3.net.OkHttpException;
import com.kelin.useokhttp3.net.RequestParams;
import com.kelin.useokhttp3.net.ResponseByteCallback;
import com.kelin.useokhttp3.net.ResponseCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：PengJunShan.
 *
 * 描述：使用封装的Okhttp3
 */
public class EncapsulationActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_encapsulation);
  }

  /**
   * GET请求
   * @param view
   */
  public void GetRequet(View view) {

    HttpRequest.getBannerApi(null, new ResponseCallback() {
      @Override
      public void onSuccess(Object responseObj) {
        Toast.makeText(EncapsulationActivity.this, "请求成功"+responseObj.toString(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailure(OkHttpException failuer) {
        Log.e("TAG", "请求失败=" + failuer.getEmsg());
        Toast.makeText(EncapsulationActivity.this, "请求失败="+failuer.getEmsg(), Toast.LENGTH_SHORT).show();
      }
    });

  }

  /**
   * POST请求
   * @param view
   */
  public void PostKeyValueRequet(View view) {
    RequestParams params = new RequestParams();
    params.put("username", "15294792877");
    params.put("password", "15294792877pp");

    HttpRequest.postLoginApi(params, new ResponseCallback() {
      @Override
      public void onSuccess(Object responseObj) {
        UserInfo userInfo = (UserInfo) responseObj;
        Toast.makeText(EncapsulationActivity.this, "请求成功"+userInfo.toString(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailure(OkHttpException failuer) {
        Log.e("TAG", "请求失败=" + failuer.getEmsg());
        Toast.makeText(EncapsulationActivity.this, "请求失败="+failuer.getEmsg(), Toast.LENGTH_SHORT).show();
      }
    });

  }


  /**
   * 下载图片
   *  可以用GET方式||POST方式，一般是用POST方式 除非你们公司不注重隐式,
   *   本案例用的是GET方式，因为没有找到免费的POST请求api。
   *
   * @param view
   */
  public void GetImgRequet(View view) {

    HttpRequest.getImgApi(null, String.valueOf(System.currentTimeMillis()) + ".png",
        new ResponseByteCallback() {
          @Override
          public void onSuccess(File file) {
            Toast.makeText(EncapsulationActivity.this, "图片下载成功="+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            Log.e("TAG", "图片下载成功="+file.getAbsolutePath());
          }

          @Override
          public void onFailure(String failureMsg) {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                Toast.makeText(EncapsulationActivity.this, "图片下载失败="+failureMsg, Toast.LENGTH_SHORT).show();
              }
            });
            Log.e("TAG", "图片下载失败="+failureMsg);
          }
        });

  }

  /**
   * 图文混合
   * @param view
   */
  public void PostImgKeyValueRequet(View view) {
    RequestParams params = new RequestParams();
    params.put("name", "aaaaaaa");
    //添加图片
    List<File> fileList = new ArrayList<>();
//    HttpRequest.postMultipartApi(params, fileList, new ResponseCallback() {
//      @Override
//      public void onSuccess(Object responseObj) {
//
//      }
//
//      @Override
//      public void onFailure(OkHttpException failuer) {
//
//      }
//    });
  }


}
