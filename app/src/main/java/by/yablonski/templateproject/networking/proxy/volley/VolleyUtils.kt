package by.yablonski.templateproject.networking.proxy.volley

import com.android.volley.*

/**
 * Volley library utils
 */
object VolleyUtils {

    /**
     * Determines whether the error is related to network
     * @param error
     * @return
     */
    fun isNetworkProblem(error: Any): Boolean {
        return error is NetworkError || error is NoConnectionError
    }

    /**
     * Determines whether the error is related to server
     * @param error
     * @return
     */
    fun isServerProblem(error: Any): Boolean {
        return error is ServerError
    }

    /**
     * Determines whether the error is related to failed authentication
     * @param error
     * @return
     */
    fun isAuthFailed(error: Any): Boolean {
        return error is AuthFailureError
    }

}