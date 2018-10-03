package com.example.robet.rtrain.AdminPage;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.robet.rtrain.R;
import com.example.robet.rtrain.support.Loading;
import com.example.robet.rtrain.support.RestApi;
import com.example.robet.rtrain.support.Value;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotification extends AppCompatActivity {

    @BindView(R.id.etTitle)
    TextInputEditText etTitle;
    @BindView(R.id.btCancell)
    Button btCancell;
    @BindView(R.id.btSend)
    Button btSend;
    @BindView(R.id.etMessage)
    TextInputEditText etMessage;

    String title, message, tokens;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_notification);
        ButterKnife.bind(this);
        loading = new Loading(this);
    }

    @OnClick({R.id.btCancell, R.id.btSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btCancell:
                finish();
                break;
            case R.id.btSend:

                title = etTitle.getText().toString();
                message = etMessage.getText().toString();
                tokens = FirebaseInstanceId.getInstance().getToken();

                if(title.isEmpty()){
                    etTitle.setError("wajib diisi");
                } else if (message.isEmpty()){
                    etMessage.setError("wajib diisi");
                } else {

                    loading.start();
                    RestApi.getData().sendNotification(title, message, tokens).enqueue(new Callback<Value>() {
                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            loading.stop();
                            Toast.makeText(getApplicationContext(), "berhasil mengirim notifikasi", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            loading.stop();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                break;
        }
    }
}
