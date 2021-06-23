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

    int currentUserId = getIntent().getExtras().getInt("jobseekerId");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);

        invoice_id.setVisibility(View.GONE);
        jobseeker_name.setVisibility(View.GONE);
        jobName.setVisibility(View.GONE);
        invoice_date.setVisibility(View.GONE);
        payment_type.setVisibility(View.GONE);
        referralCode.setVisibility(View.GONE);
        total_fee.setVisibility(View.GONE);
        invoice_status.setVisibility(View.GONE);

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
                    JSONObject invoice = jsonResponse.getJSONObject(jsonResponse.length()-1);
                    JSONObject job = invoice.getJSONArray("jobs").getJSONObject(0);
                    JSONObject jobseeker = invoice.getJSONObject("jobseeker");

                    invoice_id.setText(String.valueOf(invoice.getInt("id")));
                    jobseeker_name.setText(jobseeker.getString("name"));
                    jobName.setText(job.getString("name"));
                    invoice_date.setText(invoice.getString("date"));
                    total_fee.setText(String.valueOf(invoice.getInt("total_fee")));
                    invoice_status.setText(invoice.getString("invoiceStatus"));

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
                    Log.d(TAG, "Load data failed.");
                }
            }
        };

        JobFetchRequest fetchRequest = new JobFetchRequest(currentUserId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
        queue.add(fetchRequest);
    }
}