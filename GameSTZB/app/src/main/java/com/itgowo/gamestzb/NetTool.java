package com.itgowo.gamestzb;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class NetTool {


    public interface onNetResultListener<resultType> {
        void onResult(String requestStr, String responseStr, resultType result);

        void onError(Throwable throwable);
    }
}
