package base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;
import com.zealjiang.library.netrequest.R;

import app.NetRequestApp;
import utils.MyToast;
import utils.ShowDialogTool;
import utils.UIUtils;


public abstract  class BaseActivity extends RxFragmentActivity implements View.OnClickListener,IBaseView {
    protected String TAG = getClass().getName();
    public NetRequestApp appContext;
    private TextView tvTitle;
    protected TextView tvLeft;
    protected TextView tvRight;
    protected ImageView ivRight;
    private ImageView ivLeft;
    protected RelativeLayout rlTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = NetRequestApp.getAppContext();

        initViews(savedInstanceState);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRight = (TextView) findViewById(R.id.tvRight);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        rlTitle = (RelativeLayout) findViewById(R.id.rlTitle);

        setData();
    }

    /**
     * 初始化布局和控件
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 设置相关数据
     */
    protected abstract void setData();


    @Override
    protected void onPause() {
        super.onPause();
        ShowDialogTool.dismissLoadingDialog();
    }

    /**
     * 显示dialog
     */
    public void showDialog() {
        ShowDialogTool.showLoadingDialog(this);
    }
    /**
     * 显示dialog
     */
    public void showDialog(String msg) {
        ShowDialogTool.showLoadingDialog(this,msg);
    }

    /**
     * 隐藏dialog
     */
    public void dismissDialog() {
        ShowDialogTool.dismissLoadingDialog();
    }

    /**
     * 显示错误信息
     *
     * @param error 错误信息
     */
    public void showError(String error) {
        if(!TextUtils.isEmpty(error))
            MyToast.showLong(error);
    }

    protected void showBack(boolean isShow) {
        if (isShow) {
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.GONE);
        }
    }


    protected void setTextLeft(String text) {
        if (ivLeft != null) {
            ivLeft.setVisibility(View.GONE);
        }

        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
    }

    protected void setTextLeft(int res) {
        setTextLeft(UIUtils.getString(res));
    }


    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置标题
     */
    @Override
    public void setTitle(int textId) {
        tvTitle.setText(textId);
    }

    protected void setImageRight(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageDrawable(drawable);
    }

    protected  void showIvRight(boolean flag){
        ivRight.setVisibility(flag? View.VISIBLE: View.GONE);
    }


    protected void setTextRight(int resId) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(resId);
    }


    protected void setTextRight(String text) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(text);
    }

    protected void setTextRightColor(int color) {
        tvRight.setTextColor(UIUtils.getColor(color));
    }

    /**
     * 不带Extra跳转activity
     *
     * @param clazz
     * @param needFinish 是否finish当前activity
     */
    protected void jump(Class<?> clazz, boolean needFinish) {
        Intent intent = new Intent(this, clazz);
        jump(intent, needFinish);
    }

    /**
     * 带Extra跳转activity
     *
     * @param intent
     * @param needFinish 是否finish当前activity
     */
    protected void jump(Intent intent, boolean needFinish) {
        startActivity(intent);
        if (needFinish) {
            finish();
        }
    }


}
