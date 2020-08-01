package com.andycraft.tech2day;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andycraft.tech2day.helpers.Api;
import com.andycraft.tech2day.helpers.ApiURL;
import com.andycraft.tech2day.utils.SharedPrefManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseActivity extends AppCompatActivity {
    protected Api api;
    protected SharedPrefManager sp;
    protected Toast toast;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = SharedPrefManager.getInstance(this);
//        doInitialiseAPI();
        setupProgressDialog();
    }

    protected void setupToolbar(Toolbar toolbar, TextView tvTitle, String title, boolean showBack) {
        if (tvTitle != null)
            tvTitle.setText(title);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (ab != null && showBack) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @SuppressLint("ShowToast")
    protected void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    private void doInitialiseAPI() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiURL.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }


    protected void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        progressDialog.hide();
    }

    protected void moveToNextScreen(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_enter_in, R.anim.slide_enter_out);
    }

    protected void moveToNextScreenWithFinish(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_enter_in, R.anim.slide_enter_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_exit_in);

    }

}
