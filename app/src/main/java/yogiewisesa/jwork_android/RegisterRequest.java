/**
 * @author Yogie Wisesa
 * @version 26/6/21
 * 
 * class register request
 * untuk memasukkan data jobseeker baru
 * ke database di jwork
 */
package yogiewisesa.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/jobseeker/register";
    private Map<String, String> params;

    /**
     * constructor register request
     * @param name
     * @param email
     * @param password
     * @param listener
     */
    public RegisterRequest(String name, String email, String password,
                           Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);


    }

    /**
     * method getter params
     * @return params
     * @throws AuthFailureError
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return params;
    }
}
