package com.kelin.useokhttp3.net;

/**
 * 创建： PengJunShan
 * 描述：封装回调接口和要转换的实体对象
 */

public class ResposeDataHandle {

    public ResponseCallback mListener = null;
    public Class<?> mClass = null;

    public ResposeDataHandle(ResponseCallback listener) {
        this.mListener = listener;
    }

    public ResposeDataHandle(ResponseCallback listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }
    
}
