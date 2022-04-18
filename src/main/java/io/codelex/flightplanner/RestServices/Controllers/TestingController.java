package io.codelex.flightplanner.RestServices.Controllers;

import io.codelex.flightplanner.RestServices.Services.TestingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testing-api")
public class TestingController {
    private TestingService testingService;

    public TestingController(TestingService testingService) {
        this.testingService = testingService;
    }

    @PostMapping("/clear")
    public void clear() {
        testingService.clearFlights();
    }
}
