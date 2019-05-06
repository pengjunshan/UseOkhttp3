package com.kelin.useokhttp3.activitys;

import android.app.Application;
import android.content.Context;

/**
 * 作者：PengJunShan.
 *
 * 时间：On 2019-05-05.
 *
 * 描述：
 */
public class MyApplication extends Application {

  public static Context context;
  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();

  }
}
