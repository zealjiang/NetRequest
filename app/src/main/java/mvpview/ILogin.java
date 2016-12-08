package mvpview;

import base.IBaseView;
import vo.User;

/**
 * Created by voiceofnet on 2016/6/22.
 */

public interface ILogin extends IBaseView {
    void succeed(User user);
}
