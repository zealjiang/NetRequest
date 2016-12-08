package app;

import android.app.Application;
import android.widget.Toast;

import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;

import http.ApiServer;
import http.CustomerOkHttpClient;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zealjiang on 2016/12/8 16:51.
 * Email: zealjiang@126.com
 */

public class NetRequestApp extends Application {

    private static NetRequestApp app;
    private static ApiServer apiServer;
    /**
     * 判断是否有网络
     */
    public static boolean networkAvailable = true;
    /**
     * 判断是否是wifi还是移动网络
     */
    public static NetStatus isWifi = NetStatus.WIFI;

    /**
     * 网络状态 分别代表wifi、wifi无网络、运营商网络
     */
    public enum NetStatus {
        WIFI,
        WIFI_NO_INTERNET,
        MOBILE_INTERNET
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initApiServer();
        initNetworkStatusDetector();
        //初始化Logger日志
        Logger.init("PadSysApp") .methodCount(3).logTool(new AndroidLogTool()); // custom log tool, optional
    }


    /**
     * 初始化网络连接
     */
    public void initApiServer() {
        OkHttpClient client = CustomerOkHttpClient.getClient();
        Retrofit retrofit = null;
        if (apiServer == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiServer.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServer = retrofit.create(ApiServer.class);

        }
    }

    public static ApiServer getApiServer() {
        return apiServer;
    }

    public static NetRequestApp getAppContext() {
        return app;
    }
    /**
     * 初始化网络监听
     */
    public void initNetworkStatusDetector() {
        ReactiveNetwork reactiveNetwork = new ReactiveNetwork();
        reactiveNetwork.observeConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ConnectivityStatus>() {
                    @Override
                    public void call(ConnectivityStatus connectivityStatus) {
                        //判断当前网络状态是否有效
                        if (connectivityStatus.toString().equals(ConnectivityStatus.OFFLINE.toString()) || connectivityStatus.toString().equals(ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET.toString())) {
                            Toast.makeText(getApplicationContext(), "没有网络", Toast.LENGTH_SHORT).show();
                            networkAvailable = false;
                        } else {
                            networkAvailable = true;
                        }

                        //判断当前网络状态
                        if ((connectivityStatus.toString().equals(ConnectivityStatus.WIFI_CONNECTED.toString()) && connectivityStatus.toString().equals(ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET.toString()))
                                || connectivityStatus.toString().equals(ConnectivityStatus.OFFLINE.toString())) {
                            isWifi = NetStatus.WIFI_NO_INTERNET; //连接了wifi,且无线不能用 || 网络不可用
                        } else if (connectivityStatus.toString().equals(ConnectivityStatus.MOBILE_CONNECTED.toString())) {
                            isWifi = NetStatus.MOBILE_INTERNET; //连接了移动网络
                        } else {
                            isWifi = NetStatus.WIFI;//wifi网络
                        }
                    }
                });
    }
}
