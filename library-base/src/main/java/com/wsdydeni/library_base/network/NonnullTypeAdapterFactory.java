package com.wsdydeni.library_base.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class NonnullTypeAdapterFactory implements TypeAdapterFactory {

    private final Function<? super Field, ? extends Supplier <?>> getDefaultFieldValue;

    private NonnullTypeAdapterFactory(final Function <? super Field, ? extends Supplier<?>> getDefaultFieldValue) {
        this.getDefaultFieldValue = getDefaultFieldValue;
    }

    static TypeAdapterFactory get(final Function<? super Field, ? extends Supplier<?>> getDefaultFieldValue) {
        return new NonnullTypeAdapterFactory(getDefaultFieldValue);
    }

    @Override
    @Nullable
    public <T> TypeAdapter <T> create(final Gson gson, final TypeToken <T> typeToken) {
        @SuppressWarnings("unchecked")
        final TypeAdapter<T> delegateTypeAdapter = (TypeAdapter<T>) gson.getDelegateAdapter((TypeAdapterFactory) this, (TypeToken<?>) typeToken);
        if ( !(delegateTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter) ) {
            return null;
        }
        return NonnullTypeAdapter.of(delegateTypeAdapter, typeToken, getDefaultFieldValue);
    }

    private static final class NonnullTypeAdapter<T> extends TypeAdapter<T> {

        private final TypeAdapter<T> typeAdapter;
        private final Map <Field, Supplier<?>> nonnullFields;

        private NonnullTypeAdapter(final TypeAdapter<T> typeAdapter, final Map<Field, Supplier<?>> nonnullFields) {
            this.typeAdapter = typeAdapter;
            this.nonnullFields = nonnullFields;
        }

        public static <T> NonnullTypeAdapter<T> of(final TypeAdapter<T> typeAdapter, final TypeToken<T> typeToken,
                                                   final Function<? super Field, ? extends Supplier<?>> getDefaultFieldValue) {
            final List <Class<?>> hierarchyClasses = getHierarchyClasses(typeToken.getRawType());
            final Map<Field, Supplier<?>> nonnullFields = hierarchyClasses.stream()
                    .flatMap(clazz -> Stream.of(clazz.getDeclaredFields()))
                    .filter(field -> field.getAnnotation(NonNull.class) != null)
                    .peek(field -> field.setAccessible(true))
                    .collect(Collectors.collectingAndThen(Collectors.toMap(Function.identity(), getDefaultFieldValue), Collections::unmodifiableMap));
            return new NonnullTypeAdapter<>(typeAdapter, nonnullFields);
        }

        @Override
        public void write(final JsonWriter out, final T value) throws IOException {
            typeAdapter.write(out, value);
        }

        @Override
        public T read(final JsonReader in) throws IOException {
            try {
                final T object = typeAdapter.read(in);
                for (final Map.Entry<Field, Supplier<?>> entry : nonnullFields.entrySet()) {
                    final Field field = entry.getKey();
                    final Supplier<?> defaultValue = entry.getValue();
                    field.set(object, defaultValue.get());
                }
                return object;
            } catch ( final IllegalArgumentException | IllegalAccessException ex ) {
                throw new RuntimeException(ex);
            }
        }

    }

    private static List<Class<?>> getHierarchyClasses(final Class<?> clazz) {
        final List<Class<?>> classes = new ArrayList <>();
        for ( Class<?> c = clazz; c != null && c != Object.class; c = c.getSuperclass() ) {
            classes.add(c);
        }
        return Collections.unmodifiableList(classes);
    }

}
