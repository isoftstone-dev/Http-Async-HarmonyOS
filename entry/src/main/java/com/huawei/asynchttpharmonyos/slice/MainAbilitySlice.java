package com.huawei.asynchttpharmonyos.slice;

import com.example.httplibrary.utils.AsyncHttpClient;
import com.example.httplibrary.utils.JsonHttpResponseHandler;
import com.example.httplibrary.utils.RequestParams;
import com.huawei.asynchttpharmonyos.ResourceTable;
import cz.msebera.android.httpclient.Header;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice {

    private Text tvRequest,tvResult;

    private static final String TAG = "MainAbilitySlice";

    private static final HiLogLabel label=new HiLogLabel(HiLog.DEBUG,0x00100,"async-http");
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initView();
        initListener();
    }

    private void initView() {
        tvResult = (Text) findComponentById(ResourceTable.Id_tvResult);
        tvRequest = (Text) findComponentById(ResourceTable.Id_tvRequest);
    }

    private void initListener() {
        tvRequest.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String url="https://apis.juhe.cn/simpleWeather/query";
                String key="32becf485f7f174d4385957b62f28f61";
                    AsyncHttpClient client=new AsyncHttpClient();
                    RequestParams params=new RequestParams();
                    params.put("city","西安");
                    params.put("key",key);
                    client.get(url,params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            super.onSuccess(statusCode, headers, responseString);
                            HiLog.error(label,"zel-onSuccess:"+responseString,responseString);
                            // 通知主线程更新UI
                            getUITaskDispatcher().asyncDispatch(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText(responseString);
                                }
                            });
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            HiLog.error(label,"zel-onFailure:"+responseString,responseString);
                        }
                    });

            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
