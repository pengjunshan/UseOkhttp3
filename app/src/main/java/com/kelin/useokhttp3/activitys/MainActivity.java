package com.kelin.useokhttp3.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.kelin.useokhttp3.R;

/**
 * @author：PengJunShan. 时间：On 2019-04-30.
 *
 * 描述：首页 选择Okhttp3 简单使用 还是封装使用
 */
public class MainActivity extends AppCompatActivity {

  // 权限
  private static final int REQUEST_PERMISSION = 1;
  private static String[] PERMISSIONS = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_SETTINGS,
      Manifest.permission.INTERNET,
      Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initPermission();
  }

  /**
   * 初始化权限
   */
  private void initPermission() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      boolean isGranted = true;
      for (String permission : PERMISSIONS) {
        int result = ActivityCompat.checkSelfPermission(this, permission);
        if (result != PackageManager.PERMISSION_GRANTED) {
          isGranted = false;
          break;
        }
      }

      if (!isGranted) {
        // 还没有的话，去申请权限
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION);
      }
    }
  }


  public void toSimpleness(View view) {
    startActivity(new Intent(this, SimplenessActivity.class));
  }

  public void toEncapsulation(View view) {
    startActivity(new Intent(this, EncapsulationActivity.class));
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == REQUEST_PERMISSION) {
      boolean granted = true;
      for (int result : grantResults) {
        granted = result == PackageManager.PERMISSION_GRANTED;
        if (!granted) {
          break;
        }
      }

      if (!granted) {
        // 没有赋予权限
      } else {
        // 已经赋予过权限了
      }
    }
  }

}
