package yogiewisesa.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class SelesaiJobActivity extends AppCompatActivity {

    TextView invoice_id = findViewById(R.id.invoice_id);
    TextView jobseeker_name = findViewById(R.id.jobseeker_name);
    TextView jobName = findViewById(R.id.jobName);
    TextView invoice_date = findViewById(R.id.invoice_date);
    TextView payment_type = findViewById(R.id.payment_type);
    TextView referralCode = findViewById(R.id.referralCode);
    TextView total_fee = findViewById(R.id.total_fee);
    TextView invoice_status = findViewById(R.id.invoice_status);

    private static int jobSeekerId;
    private int jobSeekerInvoiceId;
    private String date;
    private String paymentType;
    private int totalFee;
    private static String jobSeekerName;
    private static String jobNameVar;
    private static int jobFee;
    private String invoiceStatus;
    private String refCode;
    private JSONObject bonus;

    int currentUserId = getIntent().getExtras().getInt("jobseekerId");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);

        invoice_id.setVisibility(View.INVISIBLE);
        jobseeker_name.setVisibility(View.INVISIBLE);
        jobName.setVisibility(View.INVISIBLE);
        invoice_date.setVisibility(View.INVISIBLE);
        payment_type.setVisibility(View.INVISIBLE);
        referralCode.setVisibility(View.INVISIBLE);
        total_fee.setVisibility(View.INVISIBLE);
        invoice_status.setVisibility(View.INVISIBLE);

        fetchJob();

        findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(SelesaiJobActivity.this, "Invoice change successful", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelesaiJobActivity.this, "Invoice change failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                JobSelesaiRequest selesaiRequest = new JobSelesaiRequest(invoice_id + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(selesaiRequest);
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(SelesaiJobActivity.this, "Invoice change successful", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelesaiJobActivity.this, "Invoice change failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                };

                JobBatalRequest batalRequest = new JobBatalRequest(invoice_id + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(batalRequest);
            }
        });

    }

    protected void fetchJob(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject jsonInvoice = jsonResponse.getJSONObject(i);
                        invoiceStatus = jsonInvoice.getString("invoiceStatus");
                        jobSeekerInvoiceId = jsonInvoice.getInt("id");
                        date = jsonInvoice.getString("date");
                        paymentType = jsonInvoice.getString("paymentType");
                        totalFee = jsonInvoice.getInt("totalFee");
                        refCode = "---";
                        try {
                            bonus = jsonInvoice.getJSONObject("bonus");
                            refCode = bonus.getString("referralCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        invoice_date.setText(date.substring(0, 10));
                        payment_type.setText(paymentType);
                        total_fee.setText(String.valueOf(totalFee));
                        invoice_status.setText(invoiceStatus);
                        referralCode.setText(refCode);

                        JSONObject jsonCustomer = jsonInvoice.getJSONObject("jobseeker");
                        jobSeekerName = jsonCustomer.getString("name");
                        jobseeker_name.setText(jobSeekerName);

                        JSONArray jsonJobs = jsonInvoice.getJSONArray("jobs");
                        for (int j = 0; j < jsonJobs.length(); j++) {
                            JSONObject jsonJobObj = jsonJobs.getJSONObject(j);
                            jobNameVar = jsonJobObj.getString("name");
                            jobName.setText(jobNameVar);
                            jobFee = jsonJobObj.getInt("fee");
                            total_fee.setText(String.valueOf(jobFee));
                        }
                    }

                    invoice_id.setVisibility(View.VISIBLE);
                    jobseeker_name.setVisibility(View.VISIBLE);
                    jobName.setVisibility(View.VISIBLE);
                    invoice_date.setVisibility(View.VISIBLE);
                    payment_type.setVisibility(View.VISIBLE);
                    referralCode.setVisibility(View.VISIBLE);
                    total_fee.setVisibility(View.VISIBLE);
                    invoice_status.setVisibility(View.VISIBLE);

                }
                catch (JSONException e){
                    startActivity(new Intent(SelesaiJobActivity.this, MainActivity.class));
                    e.printStackTrace();
                }
            }
        };

        JobFetchRequest fetchRequest = new JobFetchRequest(currentUserId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
        queue.add(fetchRequest);
    }
}