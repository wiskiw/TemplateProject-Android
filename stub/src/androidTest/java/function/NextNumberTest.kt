package function

import androidx.test.ext.junit.runners.AndroidJUnit4
import by.yablonski.stub.environment.MainStubEnvironment
import by.yablonski.stub.function.NextNumber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NextNumberTest {

    private lateinit var nextNumber: NextNumber

    @Before
    fun init() {
        nextNumber = NextNumber(MainStubEnvironment())
    }

    @Test
    fun increaseTest() {
        // ToDo implement testing
//        val params = JSONObject()
//            .apply {
//                put("token", "qwertyuiop")
//            }
//
//        val stubResponse = nextNumberFunction.invoke(emptyMap(), params)
//        val bodyData = stubResponse.body.getJSONObject("data")
//
//        assertEquals(requestCurrencyTag, bodyData.getString(RESPONSE_KEY_CURRENCY))
    }

}