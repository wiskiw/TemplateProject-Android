package by.yablonski.templateproject.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.yablonski.templateproject.R
import by.yablonski.templateproject.domain.repository.DemoRepository
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.java.KoinJavaComponent

class MainActivity : AppCompatActivity() {

    private val demoRepository: DemoRepository by KoinJavaComponent.inject(DemoRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        action1Button.setOnClickListener { action1() }
    }

    private fun action1() {
        demoRepository.increaseNumber(
            onSuccess = { newValue -> Toast.makeText(this@MainActivity, "result: '$newValue'", Toast.LENGTH_LONG).show() },
            onFailed = { message -> Toast.makeText(this@MainActivity, "error: '$message'", Toast.LENGTH_LONG).show() }
        )
    }
}
