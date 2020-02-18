package com.bsoft.libcommon.update;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libbasic.widget.dialog.BaseDialog;
import com.bsoft.libbasic.widget.dialog.ViewHolder;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionCancel;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionDenied;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed;
import com.bsoft.libcommon.commonaop.permission.util.SettingUtil;
import com.bsoft.libcommon.widget.progressbar.NumberProgressBar;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.File;

public class UpdateDailog extends BaseDialog implements View.OnClickListener {

    /**
     * 是否强制更新
     */
    private boolean isForceUpdate;
    private String apkUrl;
    private String saveApkPath;
    private String apkName;
    private String desc;
    private String packageName;
    private FragmentActivity mActivity;
    private QMUIRoundButton mBtCancel;
    private QMUIRoundButton mBtOk;
    private NumberProgressBar mNumberProgressBar;
    private TextView mTvDesc,tvVersion;
    private int downloadStatus = UpdateUtils.DownloadStatus.START;

    private BaseDownloadTask downloadTask;
    /**
     * 版本更新
     * @param isForceUpdate                     是否强制更新
     * @param apkUrl                            下载链接
     * @param apkName                           下载apk名称
     * @param desc                              更新文案
     * @param packageName                       包名
     */
    public static void showFragment(FragmentActivity activity, boolean isForceUpdate ,
                                    String apkUrl , String apkName , String desc,
                                    String packageName) {
        UpdateDailog updateDailog = new UpdateDailog();
        Bundle bundle = new Bundle();
        bundle.putString("apk_url", apkUrl);
        bundle.putString("desc", desc);
        bundle.putString("apkName", apkName);
        bundle.putBoolean("isUpdate", isForceUpdate);
        bundle.putString("packageName",packageName);
        updateDailog.setArguments(bundle);
        updateDailog.show(activity.getSupportFragmentManager());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            apkUrl = arguments.getString("apk_url");
            desc = arguments.getString("desc");
            apkName = arguments.getString("apkName");
            isForceUpdate = arguments.getBoolean("isUpdate");
            packageName = arguments.getString("packageName");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            apkUrl = outState.getString("apk_url");
            desc = outState.getString("desc");
            apkName = outState.getString("apkName");
            isForceUpdate = outState.getBoolean("isUpdate");
            packageName = outState.getString("packageName");
        }
    }
    @Override
    public int setUpLayoutId() {
        return R.layout.common_dailog_update;
    }
    /**
     * 如果isForceUpdate是true，那么就是强制更新，则设置cancel为false
     * 如果isForceUpdate是false，那么不是强制更新，则设置cancel为true
     */

    protected boolean isCancel() {
        return !isForceUpdate;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {

        mTvDesc = holder.getView(R.id.tv_desc);
        mNumberProgressBar = holder.getView(R.id.numberProgressBar);
        mBtCancel = holder.getView(R.id.btCancel);
        mBtOk = holder.getView(R.id.btOk);
        tvVersion = holder.getView(R.id.tvVersion);
        mNumberProgressBar.setMax(100);
        mNumberProgressBar.setProgress(0);
        mTvDesc.setText(desc==null?"":desc);
        if (isForceUpdate) {
            mBtOk.setVisibility(View.VISIBLE);
            mBtCancel.setVisibility(View.GONE);
        } else {
            mBtOk.setVisibility(View.VISIBLE);
            mBtCancel.setVisibility(View.VISIBLE);
        }
        mBtOk.setOnClickListener(this);
        mBtCancel.setOnClickListener(this);

        onKeyListener();
        createFilePath();
    }


    private void createFilePath() {
        //获取下载保存path
        saveApkPath = UpdateUtils.getLocalApkDownSavePath(apkName);
        if (new File(saveApkPath).exists()) {
            changeUploadStatus(UpdateUtils.DownloadStatus.FINISH);
        } else {
            changeUploadStatus(UpdateUtils.DownloadStatus.START);
        }
    }
    private void changeUploadStatus(int upload_status) {
        this.downloadStatus = upload_status;
        switch (upload_status) {
            case UpdateUtils.DownloadStatus.START:
                mBtOk.setText("开始下载");
                mNumberProgressBar.setVisibility(View.GONE);
                break;
            case UpdateUtils.DownloadStatus.UPLOADING:
                mBtOk.setText("下载中……");
                mNumberProgressBar.setVisibility(View.VISIBLE);
                break;
            case UpdateUtils.DownloadStatus.FINISH:
                mBtOk.setText("开始安装");
                mNumberProgressBar.setVisibility(View.INVISIBLE);
                break;
            case UpdateUtils.DownloadStatus.PAUSED:
                mNumberProgressBar.setVisibility(View.VISIBLE);
                mBtOk.setText("暂停下载");
                break;
            case UpdateUtils.DownloadStatus.ERROR:
                mNumberProgressBar.setVisibility(View.VISIBLE);
                mBtOk.setText("错误，点击继续");
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    /**
     * 这里主要是处理返回键逻辑
     */
    private void onKeyListener() {
        if(getDialog()!=null){
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        // 返回键
                        case KeyEvent.KEYCODE_BACK:
                            if (isForceUpdate) {
                                return true;
                            }
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btOk) {
            //当下载中，点击后则是暂停下载
            //当不是下载中，点击后先判断apk是否存在，若存在则提示安装；如果不存在，则下载
            //当出现错误时，点击后继续开始下载
            switch (downloadStatus) {
                case UpdateUtils.DownloadStatus.START:
                case UpdateUtils.DownloadStatus.UPLOADING:
                    if (downloadTask != null) {
                        downloadTask.pause();
                    } else {
                        requestPermission();
                    }
                    break;
                case UpdateUtils.DownloadStatus.FINISH:
                    File file = new File(saveApkPath);
                    if (file.exists()) {
                        //检测是否有apk文件，如果有直接普通安装
                        UpdateUtils.installNormal(mActivity,saveApkPath,packageName);
                        dismiss();
                    } else {
                        requestPermission();
                    }
                    break;
                case UpdateUtils.DownloadStatus.PAUSED:
                case UpdateUtils.DownloadStatus.ERROR:
                    requestPermission();
                    break;
            }
        } else if (view.getId() == R.id.btCancel) {
            //如果正在下载，那么就先暂停，然后finish
            if (downloadStatus == UpdateUtils.DownloadStatus.UPLOADING) {
                if (downloadTask != null && downloadTask.isRunning()) {
                    downloadTask.pause();
                }
            }
            dismiss();
        }
    }
    private BaseDownloadTask downApk(String apkUrl, String saveApkPath, FileDownloadListener listener) {
        BaseDownloadTask baseDownloadTask = FileDownloader
                .getImpl()
                .create(apkUrl)
                .setPath(saveApkPath)
                .setListener(listener);
        baseDownloadTask.start();
        return baseDownloadTask;
    }



    private FileDownloadListener listener ;
    public FileDownloadListener getListener(){
        if (listener==null){
            listener = new FileDownloadListener() {
                @Override
                protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    changeUploadStatus(UpdateUtils.DownloadStatus.UPLOADING);
                }

                @Override
                protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    float total = task.getSmallFileTotalBytes();
                    float downsize = task.getSmallFileSoFarBytes();
                    int progress = (int) ((downsize / total) * 100);
                    mNumberProgressBar.setProgress(progress);
                    setNotification(progress);
                }
                @Override
                protected void completed(BaseDownloadTask task) {
                    setNotification(100);
                    if (isForceUpdate) {
                        mNumberProgressBar.setProgress(100);
                    }
                    changeUploadStatus(UpdateUtils.DownloadStatus.FINISH);
                    UpdateUtils.installNormal(mActivity,saveApkPath,packageName);
                }

                @Override
                protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    changeUploadStatus(UpdateUtils.DownloadStatus.PAUSED);
                }

                @Override
                protected void error(BaseDownloadTask task, Throwable e) {
                    setNotification(-1);
                    changeUploadStatus(UpdateUtils.DownloadStatus.ERROR);
                    Log.e("UpdateFragment",e.getLocalizedMessage());
                }

                @Override
                protected void warn(BaseDownloadTask task) {
                    changeUploadStatus(UpdateUtils.DownloadStatus.ERROR);
                }
            };
        }
        return listener;
    }
    protected void setNotification(int progress) {
        if (mActivity==null){
            return;
        }
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(mActivity.getPackageName(), R.layout.common_remote_notification_view);
        remoteViews.setTextViewText(R.id.tvTitle, getResources().getString(R.string.app_name));
        remoteViews.setProgressBar(R.id.mProgressBar, 100, progress, false);


        NotificationUtils notificationUtils = new NotificationUtils(mActivity);
        NotificationManager manager = notificationUtils.getManager();
        Notification notification = notificationUtils.setContentIntent(pendingIntent)
                .setContent(remoteViews)
                .setFlags(Notification.FLAG_AUTO_CANCEL)
                .setOnlyAlertOnce(true)
                .getNotification("来了一条消息", "下载apk", R.mipmap.ic_launcher);
        //下载成功或者失败
        if (progress == 100 || progress == -1) {
            notificationUtils.clearNotification();
        } else {
            manager.notify(1, notification);
        }
    }

    /**
     * 申请多个权限
     */
    @PermissionNeed(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode = 12)
    public void requestPermission() {
        setNotification(0);
        downloadTask = downApk(apkUrl, saveApkPath, getListener());
    }

    @PermissionCancel()
    public void permissionCancel(int requestCode) {
        Log.e("leo", "permissionCancel: " + requestCode);
    }

    @PermissionDenied()
    public void permissionDenied(int requestCode) {
        Log.e("leo", "permissionDenied: " + requestCode);
        switch (requestCode) {
            case 11:
                showDialog("定位权限被禁止，需要手动去开启");
                break;
            case 12:
                showDialog("申请读写权限可能被禁止，需要手动去开启");
                break;
        }
    }

    private void showDialog(String message) {
        new AlertDialog.Builder(ContextProvider.get().getApplication())
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SettingUtil.go2Setting(ContextProvider.get().getApplication());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }
}
