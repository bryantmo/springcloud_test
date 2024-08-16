package com.bryant.controller.constraint;

import com.bryant.controller.constraint.router.ControllerRouterGrayConstraints;
import com.bryant.controller.constraint.router.ControllerRouterNotGrayConstraints;
import com.bryant.controller.constraint.router.PathConstraint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstraintController {

    @PathConstraint(constraint = ControllerRouterGrayConstraints.class)
    @GetMapping("/test_constraint")
    public String test() {
        return "test: old logic..";
    }

    @PathConstraint(constraint = ControllerRouterNotGrayConstraints.class)
    @GetMapping("/test_constraint")
    public String test2() {
        return "test: new logic..";
    }
}
