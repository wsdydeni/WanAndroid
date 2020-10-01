package com.wsdydeni.library_base.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class GsonGetter {

    static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(NonnullTypeAdapterFactory.get(field -> {
                final Class<?> type = field.getType();
                if ( List.class.isAssignableFrom(type) ) {
                    return ArrayList::new;
                }
                return () -> null;
            })).create();
}
