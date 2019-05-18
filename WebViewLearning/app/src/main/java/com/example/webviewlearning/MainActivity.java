package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.logging.Logger;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {


    private WebView testWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        WebSettings webSettings = testWeb.getSettings();
        //设置为可调用js方法
        webSettings.setJavaScriptEnabled(true);
        //window.别名.方法名，这里的别名就是android
        testWeb.addJavascriptInterface(new jsInteraction(), "android");
        testWeb.setWebViewClient(new WebViewClient());
        testWeb.setWebChromeClient(new WebChromeClient());
    }

    /**
     * 初始化VIEW
     */
    private void initView() {
        testWeb = findViewById(R.id.web_view_test_web);
        testWeb.loadUrl("file:///android_asset/test.html");
    }

    public void click(View view) {
        testWeb.loadUrl("javascript:alertMessage('这是一个来自原生的点击事件，调用js中的方法')");
        String content = "我是来自android的内容！";
        //如果传入变量名，则需要进行转义
        testWeb.loadUrl("javascript:alertMessage(\"" + content + "\")");
        testWeb.evaluateJavascript("sum(5,20)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(MainActivity.this, "JS返回了结果，你们看看！ ：" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class jsInteraction {
        @JavascriptInterface
        public String back() {
            return "我是JAVA中的back方法，大家好！";
        }
        @JavascriptInterface
        public void goBack() {
//            return "第二个页面";
//            Logger(testWeb.canGoBack());
        }
    }
}
