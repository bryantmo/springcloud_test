package com.bryant.controller.constraint.router;



public interface RouterConstraints {

    /**
     * @param pathPartRequest
     * @return 匹配返回的资源类型
     */
    boolean matches(PathPartRequest pathPartRequest);
}
