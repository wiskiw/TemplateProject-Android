package by.yablonski.templateproject.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.yablonski.templateproject.R
import by.yablonski.templateproject.app.App
import by.yablonski.templateproject.networking.messenger.MessageResult
import by.yablonski.templateproject.networking.messenger.messenger.JsonMessenger
import by.yablonski.templateproject.networking.request.NextNumberRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        app = application as App

        action1Button.setOnClickListener { action1() }
    }

    private fun action1() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = JsonMessenger(this@MainActivity, app.getJsonProxy(), NextNumberRequest())
                .also {

                }
                .send()

            when (result) {
                is MessageResult.Success -> {
                    Toast.makeText(this@MainActivity, "result: '${result.data}'", Toast.LENGTH_LONG).show()
                }
                is MessageResult.Error -> {
                    Toast.makeText(this@MainActivity, "error: '${result.message}'", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}
