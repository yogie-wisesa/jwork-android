/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class menu request
 * untuk meminta data job dari jwork
 */
package yogiewisesa.jwork_android;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class MenuRequest extends StringRequest {

    private static String URL = "http://10.0.2.2:8080/job";

    /**
     * constructor menu request
     * @param listener
     */
    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });
    }
}
