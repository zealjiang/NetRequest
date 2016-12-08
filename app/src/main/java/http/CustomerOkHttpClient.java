package http;

import android.text.TextUtils;

import java.io.IOException;

import app.NetRequestApp;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import utils.LogUtil;

public class CustomerOkHttpClient {

    public static OkHttpClient client;

    private CustomerOkHttpClient() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static void create() {
        int maxCacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(NetRequestApp.getAppContext().getCacheDir(), maxCacheSize);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=60 ,max-stale=2419200";
                }
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };

        // add your other interceptors …add logging as last interceptor
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(new LoggingInterceptor())
//                .retryOnConnectionFailure(true)
                .cache(cache)
                .build();
    }

    public static OkHttpClient getClient() {
        if (client == null) {
            create();
        }
        return client;
    }

    static class LoggingInterceptor implements Interceptor {
        private static final String TAG = "CustomerOkHttpClient";

        @Override
        public Response intercept(Chain chain) throws IOException {
            try {
                if(chain!=null){
                    Request request = chain.request();
                    long t1 = System.nanoTime();
                    LogUtil.e(TAG, String.format("Request: %s", request.url()));
                    if(request!=null){
                        Response response = chain.proceed(request);
                        if (response!=null) {
                            long t2 = System.nanoTime();
                            String responseString = new String(response.body().bytes());
                            LogUtil.e(TAG, String.format("Access Server in %.1fms ,Response: %s", (t2 - t1) / 1e6d,responseString));
                            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseString)).build();
                        } else {
                            return null;
                        }
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


}
