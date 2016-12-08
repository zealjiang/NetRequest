package mvpview;


import base.IBaseView;
import vo.DrivingLicenceModel;

/**
 * 行驶证识别
 * Created by zealjiang on 2016/11/16 11:21.
 * Email: zealjiang@126.com
 */

public interface IDrivingLicenceInf extends IBaseView {
    void succeed(DrivingLicenceModel drivingLicenceModel);
}
