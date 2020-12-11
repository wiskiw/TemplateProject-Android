package by.yablonski.templateproject.ui.main

import by.yablonski.templateproject.domain.repository.DemoRepository
import org.koin.java.KoinJavaComponent

class MainPresenter(
    private val view: View
) {

    private val demoRepository: DemoRepository by KoinJavaComponent.inject(DemoRepository::class.java)

    fun onActionClicked() {
        demoRepository.increaseNumber(
            onSuccess = { newValue -> view.showToast("result: '$newValue'") },
            onFailed = { message -> view.showToast("error: '$message'") }
        )
    }

    interface View {
        fun showToast(message: String)

    }
}
