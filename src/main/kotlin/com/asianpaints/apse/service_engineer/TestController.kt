package com.asianpaints.apse.service_engineer

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")

class TestController {

    // GET endpoint to return a welcome message
    @GetMapping("/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getData(): ResponseEntity<Any> {
        val role = Role(id = 12L, name = "Test running without database !!")
        return ResponseEntity.ok().body(role)
    }

    // POST endpoint to accept and return a JSON payload
    @PostMapping("/echo")
    fun echo(@RequestBody request: TestRequest): TestResponse {
        return TestResponse(message = "Received: ${request.message}")
    }
}

// Data class to accept incoming request payload
data class TestRequest(val message: String)

// Data class to return response payload
data class TestResponse(val message: String)
