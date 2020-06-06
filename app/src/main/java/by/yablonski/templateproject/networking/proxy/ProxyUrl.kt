package by.yablonski.templateproject.networking.proxy

import by.yablonski.templateproject.app.Utils

class ProxyUrl(val endpoint: String, val methodName: String) {

    fun getUrl(): String = Utils.buildUrl(endpoint, methodName)

    override fun toString(): String = "UrlMethod[endpoint=${endpoint}, methodName=${methodName}]"
}