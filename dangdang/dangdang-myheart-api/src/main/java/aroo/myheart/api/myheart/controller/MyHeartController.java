package aroo.myheart.api.myheart.controller;

import aroo.myheart.api.myheart.service.MyHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myheart/api")
public class MyHeartController {

    private final MyHeartService myHeartService;

    @GetMapping(value = "/v1/test", produces = "application/json")
    public void test() {
        myHeartService.test();
    }

    @GetMapping(value = "/v1/test2", produces = "application/json")
    public void test2() {
        myHeartService.test2();
    }
}
