package sample.client.wiremock

import com.github.kittinunf.fuel.Fuel
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class WiremockClientTest {

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun setUp() {
            configureFor("wiremock-service", 80)
        }
    }

    @Test
    internal fun simple() {
        stubFor(
            get(urlEqualTo("/some/thing"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")
                )
        )

        Fuel.get("http://wiremock-service/some/thing")
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    val responseString = String(bytes)
                    println("[response bytes] $responseString")
                    assertEquals("Hello world!!", responseString, "Response not as expected")
                }
            }
        .join()
    }

    @Test
    internal fun riskResponseTU() {
        stubFor(
            get(urlEqualTo("/some/thing"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")
                )
        )
    }
}