package io.codelex.flightplanner.RestServices.Controllers;

import io.codelex.flightplanner.RestServices.Services.CustomerFlightService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerFlightController {
    private CustomerFlightService customerFlightService;

    public CustomerFlightController(CustomerFlightService customerFlightService) {
        this.customerFlightService = customerFlightService;
    }
}
