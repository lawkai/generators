package {{groupId}}.{{camelCase artifactId}}

import io.jooby.runApp
import okhttp3.OkHttpClient
import okhttp3.Request

fun main(args: Array<String>) {
    runApp(args) {
        val okHttpClient = OkHttpClient()
        get("/default") {
            val request = Request.Builder()
                .url("https://ifconfig.me")
                .header("User-Agent", "curl/7.64.1")
                .header("Accept", "*/*")
                .build()
            okHttpClient.newCall(request).execute().use {
                "Welcome ${it.body?.string()}"
            }
        }
    }
}
