package com.example.ricemills.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ricemills.Koneksi.API;
import com.example.ricemills.MainActivity;
import com.example.ricemills.R;
import com.example.ricemills.helper.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout textInputEmail;
    TextInputLayout textInputPassword;
    TextView textForgot;
    SessionManager sessionManager;
    ProgressDialog progressDialog;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        membuat new request queue
        queue = Volley.newRequestQueue(this);

//        instance session manager
        sessionManager = new SessionManager(this);

//        apabila sudah login dan belum logout maka langsung diarahkan ke activity utama
        if (sessionManager.isLogin() == true){
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(main);
            finish();
        }

//        mencari elemen by id
        textInputEmail = findViewById(R.id.text_email);
        textInputPassword = findViewById(R.id.text_password);
        textForgot = findViewById(R.id.forgotPasswordText);

//        progress dialog instance
        progressDialog = new ProgressDialog(this);


        textForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPass = new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(forgotPass);
            }
        });
    }

    //    fungsi validasi email
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Email tidak boleh kosong");
            return false;
        } else {
            textInputEmail.setError(null);
            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    //    fungsi validasi password
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Password tidak boleh kosong");
            return false;
        } else {
            textInputPassword.setError(null);
            textInputPassword.setErrorEnabled(false);
            return true;
        }
    }

    private void _loginProcess() {
        progressDialog.setMessage("Sedang Memproses...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.AUTH_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status == "false") {
                        Snackbar.make(findViewById(R.id.loginActivity), message, Snackbar.LENGTH_LONG).show();
                    } else {
                        JSONObject data = jsonObject.getJSONObject("user");

                        String id = data.getString("id");
                        String username = data.getString("username");
                        String nama = data.getString("nama");

                        sessionManager.createSession(username, nama, id);

                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        main.putExtra("USERNAME", username);
                        main.putExtra("NAMA", nama);
                        main.putExtra("ID", id);
                        startActivity(main);
                        finish();
                    }
                } catch (Exception e) {
                    Snackbar.make(findViewById(R.id.loginActivity), e.toString(), Snackbar.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String message = "Terjadi error. Coba beberapa saat lagi.";

                if (error instanceof NetworkError){
                    message = "Tidak dapat terhubung ke internet. Harap periksa koneksi anda.";
                } else if (error instanceof AuthFailureError) {
                    message = "Gagal login. Harap periksa email dan password anda.";
                } else if (error instanceof ClientError) {
                    message = "Gagal login. Harap periksa email dan password anda.";
                } else if (error instanceof NoConnectionError){
                    message = "Tidak ada koneksi internet. Harap periksa koneksi anda.";
                } else if (error instanceof TimeoutError){
                    message = "Connection Time Out. Harap periksa koneksi anda.";
                }

                Snackbar.make(findViewById(R.id.loginActivity), message, Snackbar.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", textInputEmail.getEditText().getText().toString().trim());
                params.put("password", textInputPassword.getEditText().getText().toString().trim());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    //    fungsi u/ menjalankan konfirmasi form
    public void confirmInputLogin(View v) {
        if (validateEmail() | validatePassword()) {
            _loginProcess();
        }
    }
}
