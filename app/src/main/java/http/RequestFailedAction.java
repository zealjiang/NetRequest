package http;


import java.io.IOException;

import base.IBaseView;
import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;
import utils.MyToast;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/11/15 14:35
 * @desc:
 */
public class RequestFailedAction implements Action1<Throwable>{
    private IBaseView view;
    public RequestFailedAction(IBaseView view){
        this.view = view;
    }

    @Override
    public void call(Throwable throwable) {
        view.dismissDialog();
        String error = "";
        if(throwable instanceof ResponseErrorException){
            error = throwable.getMessage();
        }else if(throwable instanceof IOException){
            error = "请检查您的网络后重试";
        }else if(throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            error = httpException.getMessage();
        }else{
            error = "未知错误";
            //throwable.printStackTrace();
        }
        MyToast.showLong(error);
    }
}
