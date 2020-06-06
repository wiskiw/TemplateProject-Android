package by.yablonski.templateproject.networking.proxy.volley;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Реализация {@link JsonObjectRequest} с возможностью установки headers,
 * параметров тела запроса и приоритета запроса в {@link Volley}
 * <p>
 *
 * Created by Andrey Yablonsky on 09.02.18.
 */
public class MutableJsonRequest extends JsonObjectRequest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutableJsonRequest.class);

    private Priority priority = Priority.NORMAL;
    private Map<String, String> headers;

    private String url;
    private JSONObject body;

    public MutableJsonRequest(int method, String url, @Nullable JSONObject body, Response.Listener<JSONObject> listener,
                              @Nullable Response.ErrorListener errorListener) {

        super(method, url, body, listener, errorListener);

        this.url = url;
        this.body = body;
    }

    private void logParameters(String url, @Nullable JSONObject params) {
        String message = params != null
                ? String.format("Volley JSON request to '%s'. Params: %s", url, params.toString())
                : String.format("Volley JSON request to '%s' with no params.", url);

        LOGGER.debug(message);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers != null) {
            return headers;
        } else {
            return super.getHeaders();
        }
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("url: '%s'\n", url));

        if (body != null){
            stringBuilder.append(String.format("body: %s\n", body.toString()));
        } else {
            stringBuilder.append("body: empty\n");
        }

        if (headers != null){
            stringBuilder.append(String.format("headers: %s\n", headers.toString()));
        } else {
            stringBuilder.append("headers map: empty\n");
        }

        return stringBuilder.toString();
    }
}
