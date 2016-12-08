package http;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;
import vo.DrivingLicenceModel;
import vo.User;

/**
 * Created by zealjiang on 2016/12/8 16:56.
 * Email: zealjiang@126.com
 * 接口相关
 */
public interface ApiServer {
    public  static final String BASE_URL="http://192.168.0.140:8066";

    @FormUrlEncoded
    @POST("/api/user/login") //登录
    public Observable<ResponseJson<User>> login(@FieldMap Map<String, String> params);

    @Multipart
    @POST("/api/PictrueIdent/CarDriveCard") //上传图片
    public Observable<ResponseJson<DrivingLicenceModel>> drivingLicenceIdentify(@PartMap Map<String, RequestBody> params);
}
