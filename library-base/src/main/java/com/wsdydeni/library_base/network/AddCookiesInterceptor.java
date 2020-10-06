package com.wsdydeni.library_base.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

// 鸣谢: Mitaer
// 链接: https://www.jianshu.com/p/437d0f032ed4
public class AddCookiesInterceptor implements Interceptor {
    private static final String COOKIE_PREF = "cookies_prefs";
    private Context mContext;

    public AddCookiesInterceptor(Context context) {
        mContext = context;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }

    private String getCookie(String url, String domain) {
        SharedPreferences sp = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url, ""))) {
            return sp.getString(url, "");
        }
        if (!TextUtils.isEmpty(domain) && sp.contains(domain) && !TextUtils.isEmpty(sp.getString(domain, ""))) {
            return sp.getString(domain, "");
        }

        return null;
    }
}
