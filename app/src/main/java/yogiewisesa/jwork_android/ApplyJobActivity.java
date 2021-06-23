package yogiewisesa.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApplyJobActivity extends AppCompatActivity {

    private int jobseekerID, jobID, bonus;
    private String jobName, jobCategory, selectedPayment;
    private double jobFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerID = extras.getInt("jobseekerId");
            jobID = extras.getInt("jobId");
            jobName= extras.getString("jobName");
            jobCategory = extras.getString("jobCategory");
            jobFee = extras.getInt("jobFee");
        }

        EditText referral_code = findViewById(R.id.referral_code);
        TextView textCode = findViewById(R.id.textCode);
        TextView job_name = findViewById(R.id.job_name);
        TextView job_category = findViewById(R.id.job_category);
        TextView job_fee = findViewById(R.id.job_fee);
        TextView total_fee = findViewById(R.id.total_fee);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button hitung = findViewById(R.id.btnFinish);
        Button btnApply = findViewById(R.id.btnCancel);

        btnApply.setVisibility(View.GONE);
        textCode.setVisibility(View.GONE);
        referral_code.setVisibility(View.GONE);

        job_name.setText(jobName);
        job_category.setText(jobCategory);
        job_fee.setText(String.valueOf(jobFee));
        total_fee.setText("0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton mRadioButton = findViewById(i);
                String selected = mRadioButton.getText().toString();
                switch (selected){
                    case "Bank":
                        textCode.setVisibility(View.GONE);
                        referral_code.setVisibility(View.GONE);
                        hitung.setEnabled(true);
                        break;

                    case "E-Wallet":
                        textCode.setVisibility(View.VISIBLE);
                        referral_code.setVisibility(View.VISIBLE);
                        hitung.setEnabled(true);
                        break;
                }
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);
                String selected = radioButton.getText().toString();
                switch (selected){
                    case "Bank":
                        total_fee.setText(String.valueOf(jobFee));
                        break;

                    case "E-Wallet":
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonResponse = new JSONArray(response);
                                    for(int i = 0; i <jsonResponse.length(); i++){
                                        JSONObject b = jsonResponse.getJSONObject(i);
                                        if(referral_code.getText().toString().equals(b.getString("referralCode")) && b.getBoolean("active")){
                                            if(jobFee > b.getInt("minTotalFee")){
                                                bonus = b.getInt("extraFee");
                                                total_fee.setText(String.valueOf(jobFee + bonus));
                                            }
                                        }
                                        else{
                                            total_fee.setText(String.valueOf(jobFee));
                                        }
                                    }
                                }
                                catch (JSONException e){
                                    e.getMessage();
                                }
                            }
                        };

                        BonusRequest bonusRequest = new BonusRequest(responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                        queue.add(bonusRequest);

                        break;
                }

                hitung.setVisibility(View.GONE);
                btnApply.setVisibility(View.VISIBLE);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);
                String selected = radioButton.getText().toString();
                ApplyJobRequest applyRequest = null;

                Response.Listener<String> responseListenerOrder = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (response != null){
                                Toast.makeText(ApplyJobActivity.this, "Order successful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ApplyJobActivity.this, "Order failed", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };

                if(selected.equals("Bank")){
                    applyRequest = new ApplyJobRequest(jobFee, jobseekerID, responseListenerOrder);
                }
                else if(selected.equals("Via CASHLESS")){
                    applyRequest = new ApplyJobRequest(jobFee, jobseekerID, referral_code.getText().toString(), responseListenerOrder);
                }

                RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                queue.add(applyRequest);

            }
        });

    }
}