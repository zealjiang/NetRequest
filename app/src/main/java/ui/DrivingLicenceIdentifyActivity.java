package ui;

import android.os.Bundle;
import android.view.View;

import com.zealjiang.library.netrequest.R;

import base.BaseActivity;
import butterknife.ButterKnife;
import mvpview.IDrivingLicenceInf;
import presenter.DrivingLicenceIdentifyPresenter;
import vo.DrivingLicenceModel;

/**
 * 行驶证图片识别
 * Created by zealjiang on 2016/11/16 13:49.
 * Email: zealjiang@126.com
 */

public class DrivingLicenceIdentifyActivity extends BaseActivity implements IDrivingLicenceInf {


    private DrivingLicenceIdentifyPresenter drivingLicenceIdentifyPresenter;
    private String pathDrivingDicence;
    private boolean isShowVIN;//是否显示VIN

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pathDrivingDicence = getIntent().getStringExtra("path");
        isShowVIN = getIntent().getBooleanExtra("isShowVIN",true);
    }

    @Override
    protected void setData() {
        setTitle("行驶证识别");

        init();
    }


    private void init(){
        drivingLicenceIdentifyPresenter = new DrivingLicenceIdentifyPresenter(this);
        drivingLicenceIdentifyPresenter.identifyDrivingLicence(pathDrivingDicence);
    }


    @Override
    public void succeed(DrivingLicenceModel drivingLicenceModel) {

    }

    @Override
    public void onClick(View view) {

    }
}
