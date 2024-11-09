package com.asianpaints.apse.service_engineer.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class SampleController {

    @GetMapping("/sample")
    fun getSamplePage(model: Model): String {
        // Setting dynamic content
        val name = "John Doe"
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val items = listOf("Item 1", "Item 2", "Item 3")

        // Add content to the model
        model.addAttribute("name", name)
        model.addAttribute("currentTime", currentTime)
        model.addAttribute("items", items)

        // Return the name of the Thymeleaf template
        return "sample" // This will render src/main/resources/templates/sample.html
    }
}
