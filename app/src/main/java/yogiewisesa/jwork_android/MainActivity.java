package yogiewisesa.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    MainListAdapter listAdapter;
    ExpandableListView expListView;

    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int jobseekerId = getIntent().getExtras().getInt("userId");


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        refreshList();

        findViewById(R.id.btnApplied).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelesaiJobActivity.class);
                intent.putExtra("jobseekerId", jobseekerId);
                startActivity(intent);
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);
                intent.putExtra("jobseekerId", jobseekerId);
                intent.putExtra("jobId", childMapping.get(listRecruiter.get(i)).get(i1).getId());
                intent.putExtra("jobName", childMapping.get(listRecruiter.get(i)).get(i1).getName());
                intent.putExtra("jobCategory", childMapping.get(listRecruiter.get(i)).get(i1).getCategory());
                intent.putExtra("jobFee", childMapping.get(listRecruiter.get(i)).get(i1).getFee());
                startActivity(intent);

                return true;
            }
        });

    }

    protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null){
                        for (int i = 0; i < jsonResponse.length(); i++){
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            Location loc = new Location(
                                    location.getString("province"),
                                    location.getString("description"),
                                    location.getString("city")
                            );

                            Recruiter rec = new Recruiter(  recruiter.getInt("id"),
                                                            recruiter.getString("name"),
                                                            recruiter.getString("email"),
                                                            recruiter.getString("phoneNumber"),
                                                            loc);

//                            listRecruiter.add(rec);

                            Job j = new Job(
                                    job.getInt("id"),
                                    job.getString("name"),
                                    rec,
                                    job.getInt("fee"),
                                    job.getString("category")
                            );

                            jobIdList.add(j);

                            boolean tempStatus = true;
                            for(Recruiter recPtr : listRecruiter) {
                                if(recPtr.getId() == rec.getId()){
                                    tempStatus = false;
                                }
                            }
                            if(tempStatus){
                                listRecruiter.add(rec);
                            }




                        }
                        for (Recruiter rr : listRecruiter){
                            ArrayList<Job> temp = new ArrayList<>();
                            for (Job jj : jobIdList){
                                if (jj.getRecruiter().getName().equals(rr.getName()) ||
                                        jj.getRecruiter().getEmail().equals(rr.getEmail()) ||
                                        jj.getRecruiter().getPhoneNumber().equals(rr.getPhoneNumber()))
                                {
                                    temp.add(jj);
                                }
                            }
                            childMapping.put(rr, temp);
                        }
                        listAdapter = new MainListAdapter(getApplicationContext(), listRecruiter, childMapping);
                        expListView.setAdapter(listAdapter);
                    }
                }  catch (JSONException e){
                    System.out.println(e.getMessage());
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}

