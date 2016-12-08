package presenter;


import java.util.HashMap;
import java.util.Map;

import app.NetRequestApp;
import base.BasePresenter;
import http.RequestFailedAction;
import http.RequestSuccessAction;
import http.ResponseJson;
import http.RxThreadUtil;
import mvpview.ILogin;
import utils.LogUtil;
import utils.MD5Utils;
import utils.UIUtils;
import vo.User;


/**
 * 登录
 * @author zealjiang
 * @time 2016/11/17 15:40
 */
public class LoginPresenter extends BasePresenter<ILogin> {
    public LoginPresenter(ILogin from) {
        super(from);
    }
    public void login(final String username, final String password, final boolean isShowDialog){
        Map<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params = MD5Utils.encryptParams(params);
        LogUtil.e(TAG, UIUtils.getUrl(params));
        if(isShowDialog){
            baseView.showDialog();
        }
        NetRequestApp.getApiServer().login(params)
                .compose(RxThreadUtil.<ResponseJson<User>>networkSchedulers())
                .subscribe(new RequestSuccessAction<ResponseJson<User>>(){
                    @Override
                    public void onSuccess(ResponseJson<User> response) {
                        baseView.dismissDialog();
                        User user = response.getMemberValue();
                        baseView.succeed(user);
                    }
                },new RequestFailedAction(baseView));
    }
}
