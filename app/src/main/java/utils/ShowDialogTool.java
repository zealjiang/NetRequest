package utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.zealjiang.library.netrequest.R;

import dialog.ShowMsgDialog;


/**
 * 弹出框控制类
 *
 * @author jzg
 * @Date 2015-05-11
 */
public class ShowDialogTool {


    private static ShowMsgDialog mLoadingDialog;


    public static void showLoadingDialog(Context context, String msg) {
        if (context == null) return;
        if (mLoadingDialog == null) {

            if(android.os.Build.VERSION.SDK_INT>=21){
                mLoadingDialog = new ShowMsgDialog(context, android.R.style.Theme_Material_Light_Dialog);
            }else{
                mLoadingDialog = new ShowMsgDialog(context, android.R.style.Theme_DeviceDefault_Dialog);
            }
            mLoadingDialog.setContentView(R.layout.dialog_loading_layout);
            if(!TextUtils.isEmpty(msg)){
                ((TextView)mLoadingDialog.findViewById(R.id.tvDialogMsg)).setText(msg);
            }

			mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.getWindow().setGravity(Gravity.CENTER);
            mLoadingDialog.setCancelable(true);
			mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_SEARCH){
						return true;
					}
					return false;
				}
			});
        }
        if (!mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }
    public static void showLoadingDialog(Context context) {
        if (context == null) return;
        if (mLoadingDialog == null) {

            if(android.os.Build.VERSION.SDK_INT>=21){
                mLoadingDialog = new ShowMsgDialog(context, android.R.style.Theme_Material_Light_Dialog);
            }else{
                mLoadingDialog = new ShowMsgDialog(context, android.R.style.Theme_DeviceDefault_Dialog);
            }
            mLoadingDialog.setContentView(R.layout.dialog_loading_layout);
			mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.getWindow().setGravity(Gravity.CENTER);
            mLoadingDialog.setCancelable(true);
			mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_SEARCH){
						return true;
					}
					return false;
				}
			});
        }
        if (!mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }

    public static void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
    }


}
