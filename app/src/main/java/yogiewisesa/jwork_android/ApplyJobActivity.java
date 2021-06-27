/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class apply job activity
 * untuk menghandle view dan activity apply job
 */
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

import org.json.JSONException;
import org.json.JSONObject;

public class ApplyJobActivity extends AppCompatActivity {

    private int jobseekerID, jobID, bonus;
    private String jobName, jobCategory, selectedPayment;
    private double jobFee;

    /**
     * method oncreate untuk pembuatan view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        // mengambil intent extra dari activity sebelumnya
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerID = extras.getInt("jobseekerId");
            jobID = extras.getInt("jobId");
            jobName= extras.getString("jobName");
            jobCategory = extras.getString("jobCategory");
            jobFee = extras.getInt("jobFee");
        }

        // inisiasi view
        EditText referral_code = findViewById(R.id.referral_code);
        TextView textCode = findViewById(R.id.staticReferralCode);
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

        /**
         * listener untuk radio group metode pembayaran
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * method untuk membaca radio button
             * @param radioGroup
             * @param i
             */
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

        /**
         * listener button hitung
         */
        hitung.setOnClickListener(new View.OnClickListener() {
            /**
             * method untuk membaca klik tombol
             * @param view
             */
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
                                    JSONObject jsonResponse = new JSONObject(response);
                                    int extraFee = jsonResponse.getInt("extraFee");
                                    int minTotalFee = jsonResponse.getInt("minTotalFee");
                                    boolean bonusStatus = jsonResponse.getBoolean("active");

                                    if (!bonusStatus) {
                                        Toast.makeText(ApplyJobActivity.this, "This bonus is invalid!", Toast.LENGTH_SHORT).show();
                                    } else if (bonusStatus) {
                                        if (jobFee < extraFee || jobFee < minTotalFee) {
                                            Toast.makeText(ApplyJobActivity.this, "Referral code invalid!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ApplyJobActivity.this, "Referral code applied!", Toast.LENGTH_SHORT).show();
                                            total_fee.setText(String.valueOf(jobFee));
                                            hitung.setVisibility(View.INVISIBLE);
                                            btnApply.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(ApplyJobActivity.this, "Referral code not Exist!", Toast.LENGTH_SHORT).show();
                                    total_fee.setText(String.valueOf(jobFee));
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

        /**
         * listener button apply
         */
        btnApply.setOnClickListener(new View.OnClickListener() {

            /**
             * method untuk membaca klik tombol
             * @param view
             */
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
                            if (jsonObject != null){
                                Toast.makeText(ApplyJobActivity.this, "Apply successful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ApplyJobActivity.this, "Apply failed", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };

                if(selected.equals("Bank")){
                    applyRequest = new ApplyJobRequest(String.valueOf(jobID), jobseekerID+"", responseListenerOrder);
                }
                else if(selected.equals("E-Wallet")){
                    applyRequest = new ApplyJobRequest(String.valueOf(jobID), jobseekerID+"", referral_code.getText().toString(), responseListenerOrder);
                }

                RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                queue.add(applyRequest);

            }
        });

    }
}