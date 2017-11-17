package com.lanmishu.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.lanmishu.alipaytest.JsonResult;
import com.util.JsonUtils;
import com.util.NetUtils;

/**
 * Service:Check the application version.
 */
public class CheckUpdateService extends Service {
    private static final String TAG = "System.out";

    private static final String CHECK_UPDATE_URL = ""; // 检测版本地址

    private DownloadCompleteReceiver downloadCompleteReceiver;

    private DownloadManager downloadManager;
    private long downloadTaskId; // 下载任务ID

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        downloadCompleteReceiver = new DownloadCompleteReceiver();
        registerReceiver(downloadCompleteReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadCompleteReceiver);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "===============================CheckUpdateService onStartCommand===============================");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String response = NetUtils.post(CHECK_UPDATE_URL, "app=&version=2.0"); // 参数包括APP应用名称，version 自定义版本号
                Log.v(TAG, response);

                JsonResult jsonResult = JsonUtils.fromJson(response, JsonResult.class);
                Looper.prepare();

                if (jsonResult.isSuccess()) {
                    String newVersion = jsonResult.getMsg(); // 最新版本号，可以记录在本地，也可在最新安装包里配置
                    Log.v(TAG, "下载");

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(""));
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    request.setTitle("下载");
                    request.setDescription("应用下载");
                    request.setAllowedOverRoaming(false); // 手机处于漫游时，不执行下载
                    // 设置文件存放目录
                    request.setDestinationInExternalFilesDir(CheckUpdateService.this, Environment.DIRECTORY_DOWNLOADS, "LanMShu_" + newVersion + ".apk");

                    downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadTaskId = downloadManager.enqueue(request);

                    Toast.makeText(CheckUpdateService.this, "最新版本号：" + newVersion, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckUpdateService.this, jsonResult.getMsg(), Toast.LENGTH_SHORT).show();
                    stopSelf();
                }

                Looper.loop();

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        return super.onStartCommand(intent, flags, startId);

    } // end public int onStartCommand(Intent intent, int flags, int startId)

    private class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == downloadTaskId) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadTaskId);
                    Cursor cursor = downloadManager.query(query);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                            String apkPath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                            // 启动应用安装
                            Intent promptInstall = new Intent(Intent.ACTION_VIEW);
                            promptInstall.setDataAndType(Uri.parse(apkPath), "application/vnd.android.package-archive");
                            promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(promptInstall);
                        }
                    }
                }

            }
        }
    }

} // end public class CheckUpdateService