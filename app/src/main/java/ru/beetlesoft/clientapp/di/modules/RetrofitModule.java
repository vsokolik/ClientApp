package ru.beetlesoft.clientapp.di.modules;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.beetlesoft.clientapp.constant.SharedPrefKeys;
import ru.beetlesoft.clientapp.constant.VkConstants;
import ru.beetlesoft.clientapp.utils.SharedPrefUtils;

@Module(includes = ContextModule.class)
public class RetrofitModule {

    private Context context;

    public RetrofitModule(Context context) {
        this.context = context;
    }

	@Provides
	@Singleton
	public Retrofit provideRetrofit(Retrofit.Builder builder) {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		Interceptor paramAdds = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl.Builder builder = request
                        .url()
                        .newBuilder()
                        .addQueryParameter("v", VkConstants.VERSION_API);
                String token = SharedPrefUtils.getString(context, SharedPrefKeys.TOKEN);
                if(!TextUtils.isEmpty(token)){
                    builder.addQueryParameter("access_token", token);
                }
                HttpUrl url = builder.build();
                request = request.newBuilder().url(url).build();

                Response response = chain.proceed(request);

                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < 3) {
                    Log.d("intercept", "Request is not successful - " + tryCount);
                    tryCount++;
                    // retry the request
                    response = chain.proceed(request);
                }

                // otherwise just pass the original response on
                return response;
            }
        };
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).addInterceptor(paramAdds).build();
		return builder.baseUrl(VkConstants.BASE_API_URL).client(client).build();
	}

	@Provides
	@Singleton
	public Retrofit.Builder provideRetrofitBuilder() {
		return new Retrofit.Builder()
				.addConverterFactory(ScalarsConverterFactory.create());
	}
}
