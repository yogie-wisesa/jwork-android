package yogiewisesa.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JobFetchRequest extends StringRequest {
    private static String URL = "http://10.0.2.2:8080/invoice/";
    private Map<String, String> params;

    public JobFetchRequest(int currentUserId, Response.Listener<String> listener) {
        super(Method.GET, URL+currentUserId, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
