package yogiewisesa.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JobSelesaiRequest extends StringRequest {
    private static String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;

    public JobSelesaiRequest(String id, Response.Listener<String> listener) {
        super(Method.PUT, URL+id, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("invoiceStatus", "Finished");
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
