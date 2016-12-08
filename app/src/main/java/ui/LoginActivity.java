package ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.AppUtils;
import com.zealjiang.library.netrequest.R;

import app.NetRequestApp;
import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mvpview.ILogin;
import presenter.LoginPresenter;
import utils.MyToast;
import vo.User;

public class LoginActivity extends BaseActivity implements ILogin {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;


    private LoginPresenter presenter;
    private String userName;
    private String password;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        init();
    }


    public void init(){

        String versionName = AppUtils.getAppInfo(this).getVersionName();
        tvVersionName.setText(versionName);

        userName = "BJPGS3";
        password = "BJPGS3";

        if (TextUtils.isEmpty(userName)) {
            userName = "";
        }
        if (TextUtils.isEmpty(password)) {
            password = "";
        }
        etAccount.setText(userName);
        etPassword.setText(password);

        presenter = new LoginPresenter(this);
    }

    @OnClick({R.id.btnLogin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnLogin:
                if (!check(true)) {
                    return;
                }
                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (!NetRequestApp.networkAvailable) {
                    MyToast.showShort("没有网络");
                    return;
                }
                presenter.login(account, password, true);

            break;
        }
    }

    /**
     * 检验用户名和密码是否为空
     *
     * @return 都不为空返回true，反之返回false
     */
    private boolean check(boolean showError) {
        String account = etAccount.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            if (showError)
                etAccount.setError(getString(R.string.username_cannot_be_null));
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            if (showError)
                etPassword.setError(getString(R.string.pwd_cannot_be_null));
            return false;
        }
        return true;
    }

    @Override
    public void succeed(User user) {

    }
}
