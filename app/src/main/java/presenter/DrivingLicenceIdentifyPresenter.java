package presenter;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import app.NetRequestApp;
import base.BasePresenter;
import http.RequestFailedAction;
import http.RequestSuccessAction;
import http.ResponseJson;
import http.RxThreadUtil;
import mvpview.IDrivingLicenceInf;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import utils.MD5Utils;
import vo.DrivingLicenceModel;

/**
 * 行驶证照片识别
 * Created by zealjiang on 2016/11/16 11:19.
 * Email: zealjiang@126.com
 */

public class DrivingLicenceIdentifyPresenter extends BasePresenter<IDrivingLicenceInf> {

    public DrivingLicenceIdentifyPresenter(IDrivingLicenceInf from) {
        super(from);
    }

    public void identifyDrivingLicence(String path) {
        File imgFile = new File(path);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), imgFile);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), "userId");
        RequestBody from = RequestBody.create(MediaType.parse("text/plain"), "android");
        Map<String, RequestBody> params = new HashMap<String, RequestBody>();
        params.put("image\"; filename=\"" + imgFile.getName() + "", fileBody);
        params.put("userid", userId);
        params.put("From", from);
        //加sign
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        RequestBody sign = RequestBody.create(MediaType.parse("text/plain"), MD5Utils.getMD5Sign(signMap));
        params.put("sign",sign);

        baseView.showDialog();

        NetRequestApp.getApiServer().drivingLicenceIdentify(params)
                .compose(RxThreadUtil.<ResponseJson<DrivingLicenceModel>>networkSchedulers())
                .subscribe(new RequestSuccessAction<ResponseJson<DrivingLicenceModel>>(){
                    @Override
                    public void onSuccess(ResponseJson<DrivingLicenceModel> response) {
                        baseView.dismissDialog();
                        DrivingLicenceModel user = response.getMemberValue();
                        baseView.succeed(user);
                    }
                },new RequestFailedAction(baseView));

    }
}
