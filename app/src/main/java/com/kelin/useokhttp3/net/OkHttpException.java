package com.kelin.useokhttp3.net;

/**
 * 创建： PengJunShan
 * 描述：自定义异常类，返回ecode,emsg到业务层
 */

public class OkHttpException extends Exception {

  private static final long serialVersionUID = 1L;

  private int ecode; //错误码
  private String emsg; //错误消息

  public OkHttpException(int ecode, String emsg) {
    this.ecode = ecode;
    this.emsg = emsg;
  }

  public int getEcode() {
    return ecode;
  }

  public String getEmsg() {
    return emsg;
  }

}
