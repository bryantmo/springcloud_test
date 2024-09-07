package com.bryant.controller.constraint.router;



public interface RouterDecisionMaker {

    /**
     * 路由决策器的最终决策方法
     * @param routerPathRequest
     * @return 匹配返回的资源类型
     */
    boolean matches(RouterPathRequest routerPathRequest);
}
