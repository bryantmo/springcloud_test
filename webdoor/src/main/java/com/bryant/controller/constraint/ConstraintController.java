package com.bryant.controller.constraint;

import com.bryant.controller.constraint.router.ApiGrayDecisionMaker;
import com.bryant.controller.constraint.router.ApiNotGrayDecisionMaker;
import com.bryant.controller.constraint.router.PathRouterDecisionMaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstraintController {

    @PathRouterDecisionMaker(decision = ApiNotGrayDecisionMaker.class)
    @GetMapping("/test_constraint")
    public String test() {
        return "非灰度：老API..";
    }

    @PathRouterDecisionMaker(decision = ApiGrayDecisionMaker.class)
    @GetMapping("/test_constraint")
    public String test2() {
        return "灰度：新API..";
    }
}
