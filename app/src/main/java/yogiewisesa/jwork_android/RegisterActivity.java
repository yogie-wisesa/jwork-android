/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class register activity
 * untuk menghandle view dan activity register
 */
package yogiewisesa.jwork_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    
    /**
     * method oncreate untuk pembuatan view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText etName = findViewById(R.id.etEmailReg);
        EditText etEmail = findViewById(R.id.etEmailReg);
        EditText etPassword = findViewById(R.id.etPasswordReg);
        Button btnRegister = findViewById(R.id.btnRegister);

        //listener tombol register
        btnRegister.setOnClickListener(new View.OnClickListener() {

            /**
             * method pendengar ketika tombol register ditekan
             * @param view
             */
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    /**
                     * method pendengar response json dari jwork
                     * @param response
                     */
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(RegisterActivity.this, "Register Successful",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            Toast.makeText(RegisterActivity.this, "Register Failed",
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(e.getMessage());
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}