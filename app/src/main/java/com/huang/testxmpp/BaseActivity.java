package com.huang.testxmpp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * BaseActivity
 * Created by Administrator on 2017/11/13.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * 处理toolbar和返回键
     */
    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");// Title set ""

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);// <-返回键

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置toolbar标题
     *
     * @param str 标题
     */
    public void setToolBarTitle(String str) {
        ((TextView) findViewById(R.id.toolbar_title_tx)).setText(str);
    }
}
