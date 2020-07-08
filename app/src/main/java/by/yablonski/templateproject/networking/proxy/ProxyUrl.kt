package by.yablonski.templateproject.networking.proxy

import by.yablonski.templateproject.app.Utils

class ProxyUrl(val endpoint: String, val functionName: String) {

    fun getUrl(): String = Utils.buildUrl(endpoint, functionName)

    override fun toString(): String = "UrlMethod[endpoint=${endpoint}, function=${functionName}]"
}