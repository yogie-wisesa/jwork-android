/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class selesai job activity
 * untuk menghandle view selesai job
 * dan menampilkan invoice
 */
package yogiewisesa.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class SelesaiJobActivity extends AppCompatActivity {

    private int jobseekerId;
    private TextView tvJobseekerName, tvInvoiceDate, tvPaymentType, tvInvoiceStatus, tvReferralCode, tvJobName, tvFeeJob, tvTotalFee;
    private Button btnCancel, btnFinished;
    private int invoiceId;

    /**
     * method oncreate untuk membuat view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);

        tvJobseekerName = findViewById(R.id.jobseeker_name);
        tvInvoiceDate = findViewById(R.id.invoice_date);
        tvPaymentType = findViewById(R.id.payment_type);
        tvReferralCode = findViewById(R.id.referralCode);
        tvJobName = findViewById(R.id.jobName);
        tvTotalFee = findViewById(R.id.total_fee);
        btnCancel = findViewById(R.id.btnCancel);
        btnFinished = findViewById(R.id.btnFinish);

        tvJobseekerName.setVisibility(View.INVISIBLE);
        tvInvoiceDate.setVisibility(View.INVISIBLE);
        tvPaymentType .setVisibility(View.INVISIBLE);
        tvReferralCode.setVisibility(View.INVISIBLE);
        tvJobName.setVisibility(View.INVISIBLE);
        tvTotalFee.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        btnFinished.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        jobseekerId = intent.getIntExtra("jobseekerId", 0);
        fetchJob();

        //Button batal
        btnCancel.setOnClickListener(new View.OnClickListener(){

            /**
             * method ketika tombol batal diklik
             * @param v
             */
            @Override
            public void onClick(View v){
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    /**
                     * method pendengar response dari jwork
                     * @param response
                     */
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(SelesaiJobActivity.this, "Your job has been canceled!", Toast.LENGTH_LONG).show();
                                btnCancel.setEnabled(false);
                                btnFinished.setEnabled(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                JobBatalRequest jobBatalRequest = new JobBatalRequest(String.valueOf(invoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(jobBatalRequest);
            }
        });

        //Button Finish
        btnFinished.setOnClickListener(new View.OnClickListener(){

            /**
             * method ketika tombol finish ditekan
             * @param v
             */
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(SelesaiJobActivity.this, "Your job has been Finished!", Toast.LENGTH_LONG).show();
                                btnFinished.setEnabled(false);
                                btnCancel.setEnabled(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                JobSelesaiRequest jobSelesaiRequest = new JobSelesaiRequest(String.valueOf(invoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(jobSelesaiRequest);
            }
        });
    }

    /**
     * method fetchjob untuk request data invoice dari jobseeker
     * dan memasukkannya ke view selesaijob
     */
    public void fetchJob(){
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            /**
             * method pendengar response dari jwork
             * @param response
             */
            @Override
            public void onResponse(String response){
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    System.out.println(jsonResponse.length());
                    if (jsonResponse != null) {
                        for(int i = 0; i < jsonResponse.length(); i++) {
                            //Object Invoice dan set text jobseekername
                            JSONObject invoice = jsonResponse.getJSONObject(i);
                            String jobseekerName = invoice.getJSONObject("jobseeker").getString("name");
                            tvJobseekerName.setText(jobseekerName);

                            invoiceId = invoice.getInt("id");

                            //Object Invoice Date
                            String date = invoice.getString("date");
                            tvInvoiceDate.setText(date);

                            //Payment Type
                            String paymentType = invoice.getString("paymentType");
                            tvPaymentType.setText(paymentType);

                            //referral Code
                            if (paymentType.equals("EwalletPayment")){
                                tvReferralCode.setText(paymentType);
                            }

                            //Job Name
                            JSONArray jobList = invoice.getJSONArray("jobs");
                            JSONObject job = jobList.getJSONObject(0);
                            String jobName = job.getString("name");
                            tvJobName.setText(jobName);


                            //Total Fee
                            int totalFee = invoice.getInt("totalFee");
                            tvTotalFee.setText(Integer.toString(totalFee));

                            System.out.println(tvJobseekerName);
                        }
                        tvJobseekerName.setVisibility(View.VISIBLE);
                        tvInvoiceDate.setVisibility(View.VISIBLE);
                        tvPaymentType .setVisibility(View.VISIBLE);
                        tvReferralCode.setVisibility(View.VISIBLE);
                        tvJobName.setVisibility(View.VISIBLE);
                        tvTotalFee.setVisibility(View.VISIBLE);
                        btnCancel.setVisibility(View.VISIBLE);
                        btnFinished.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    System.out.println(e);
                    finish();
                }

            }
        };
        JobFetchRequest jobFetchRequest = new JobFetchRequest(String.valueOf(jobseekerId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
        queue.add(jobFetchRequest);
    }
}