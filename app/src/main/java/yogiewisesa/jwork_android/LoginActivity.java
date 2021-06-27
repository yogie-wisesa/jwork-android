/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class login activity
 * untuk menghandle view dan aktifitas login
 */
package yogiewisesa.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    /**
     * class oncreate saat pembuatan view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        etEmail.setText("test@test.com");
        etPassword.setText("Test1234");

        // listener tombol login
        btnLogin.setOnClickListener(new View.OnClickListener(){

            /**
             * method membaca oncreate
             * @param view
             */
            @Override
            public void onClick(View view){
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    /**
                     * method untuk mendengar response dari jwork
                     * @param response
                     */
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(LoginActivity.this, "Login Successful",
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userId", jsonObject.getInt("id"));
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            Toast.makeText(LoginActivity.this, "Login Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        /**
         * listener untuk tombol register
         */
        tvRegister.setOnClickListener(new View.OnClickListener() {

            /**
             * method untuk membaca tombol ditekan
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}