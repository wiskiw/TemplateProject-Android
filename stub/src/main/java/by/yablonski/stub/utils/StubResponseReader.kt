package by.yablonski.stub.utils

import android.content.Context
import com.google.gson.JsonParseException
import org.json.JSONObject
import java.io.IOException

class StubResponseReader(private val context: Context) {

    companion object {
        private const val ASSET_FOLDER = "stub-response"
    }

    @Throws(IOException::class, JsonParseException::class)
    fun readJsonResponse(fileName: String): JSONObject {
        val path = getFilePath(fileName)
        val fileContent = Utils.readAssetFile(context, path)
        return JSONObject(fileContent)
    }

    private fun getFilePath(fileName: String): String {
        return "$ASSET_FOLDER/$fileName"
    }
}
