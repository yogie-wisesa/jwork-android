package yogiewisesa.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ApplyJobRequest extends StringRequest {
    private static String URLBank = "http://10.0.2.2:8080/invoice/createBankPayment";
    private static String URLEwallet = "http://10.0.2.2:8080/invoice/createEwalletPayment";
    private Map<String, String> params;

    public ApplyJobRequest(double jobFee, int jobseekerId, Response.Listener<String> listener) {
        super(Method.POST, URLBank, listener, null);
        params = new HashMap<>();
        params.put("jobFee", String.valueOf(jobFee));
        params.put("jobseekerId", String.valueOf(jobseekerId));
        params.put("adminFee", "0");
    }

    public ApplyJobRequest(double jobFee, int jobseekerId, String referralCode, Response.Listener<String> listener) {
        super(Method.POST, URLEwallet, listener, null);
        params = new HashMap<>();
        params.put("jobFee", String.valueOf(jobFee));
        params.put("jobseekerId", String.valueOf(jobseekerId));
        params.put("referralCode", referralCode);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
