package com.radomar.customprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStartAnim;
    private Button mStopAnim;
    private MyCustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartAnim = (Button) findViewById(R.id.btStart_AM);
        mStopAnim = (Button) findViewById(R.id.btStop_AM);
        mCustomView = (MyCustomView) findViewById(R.id.myCustomView);
        mStartAnim.setOnClickListener(this);
        mStopAnim.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btStart_AM:
                mCustomView.startAnimation();
                break;
            case R.id.btStop_AM:
                mCustomView.stopAnimation();
                break;
        }
    }
}
