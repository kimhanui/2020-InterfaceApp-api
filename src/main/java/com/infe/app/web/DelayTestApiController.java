package com.infe.app.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DelayTestApiController {

    @GetMapping("/api/v1/delay")
    public void delayTest() throws Exception{
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        //https://www.baeldung.com/java-delay-code-execution
    }
}
