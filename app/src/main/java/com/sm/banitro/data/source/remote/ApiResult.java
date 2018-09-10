package com.sm.banitro.data.source.remote;

public interface ApiResult<T> {

    void onSuccess(T result);

    void onFail(String errorMessage);
}