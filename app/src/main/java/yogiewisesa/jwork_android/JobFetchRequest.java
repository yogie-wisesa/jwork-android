/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class job fetch request
 * untuk menghandle pengambilan data invoice
 */
package yogiewisesa.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JobFetchRequest extends StringRequest {
    private static String URL = "http://10.0.2.2:8080/invoice/jobseeker/";
    private Map<String, String> params;

    /**
     * constructor job fetch request
     * @param currentUserId
     * @param listener
     */
    public JobFetchRequest(String currentUserId, Response.Listener<String> listener) {
        super(Method.GET, URL+currentUserId, listener, null);
        params = new HashMap<>();
    }

    /**
     * method getparams
     * @return params
     */
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
