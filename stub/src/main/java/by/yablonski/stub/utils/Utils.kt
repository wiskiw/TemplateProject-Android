package by.yablonski.stub.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

internal object Utils {

    @Throws(IOException::class)
    fun readAssetFile(context: Context, filePath: String): String {
        val inputStream: InputStream = context.assets.open(filePath)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        return buffer.toString(Charsets.UTF_8)
    }
}
