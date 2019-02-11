package com.example.apple.minidemo.dagger;

import android.content.Context;
import android.util.Log;
import com.example.apple.minidemo.application.MiniApplication;
import com.example.apple.minidemo.network.IAPIHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

import static com.example.apple.minidemo.utils.ConstantKt.BASE_URL;

@Module
public class DaggerModule {
    private MiniApplication application;

    public DaggerModule(MiniApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    Converter.Factory provideGsonConverter() {
        Gson gson = new GsonBuilder().setLenient().create();
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Converter.Factory converter) {
        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .readTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("API", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (!okHttpClient.interceptors().contains(logging))
            okHttpClient.addInterceptor(logging);


        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(converter)
                .build();
    }

    @Provides
    @Singleton
    IAPIHandler getAPIClient(Retrofit retrofit) {
        return retrofit.create(IAPIHandler.class);
    }
}
