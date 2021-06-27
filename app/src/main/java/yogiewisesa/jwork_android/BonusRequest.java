/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class bonus request
 * untuk menghandle pengambilan data bonus dari jwork
 */
package yogiewisesa.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BonusRequest extends StringRequest {

    private static String URL = "http://10.0.2.2:8080/bonus";
    private Map<String, String> params;

    /**
     * constructor bonus request
     * @param listener
     */
    public BonusRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
        params = new HashMap<>();
    }

    /**
     * method getparam
     * @return params 
     */
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
