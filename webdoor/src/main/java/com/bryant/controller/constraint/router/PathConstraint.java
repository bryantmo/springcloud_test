package com.bryant.controller.constraint.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>@PathConstraint 注解用于修饰类型以及Package包类型(package-info.java)</p>
 * <ul>
 * <li>其属性值 constraint 是任意一个实现了 RouterConstraints 接口的类</li>
 * <li>通配符路径，即 @RequestMapping 的value或者path值和请求路径在匹配过程中，执行 RouterConstraints 实现类的matches方法为true，方可正确匹配</li>
 * </ul>
 * <p>举例说明：</p>
 * <pre class="code">
 *
 * &#064@PathConstraint(constrait = ProjectUrlConstraints.class)
 * &#064;RestController
 * public class ProjectController {
 *
 *      &#064RequestMapping(value = "/{+namespace}/{project}", method = RequestMethod.GET)
 *      public String index() {
 *          return "project index";
 *      }
 *      &#064RequestMapping(value = "/{+namespace}/{project}", method = RequestMethod.POST)
 *      public String create() {
 *          return "project create";
 *      }
 * }
 *
 * public Class ProjectUrlConstraints implements RouterConstraints {
 *     &#064;override
 *     public boolean matches(Map&lt;string, String&gt; params) {
 *         return true
 *     }
 * }
 * </pre>
 *
 * 假设用户请求的路径为 /code/development/design/icons_project
 * 无论是get 请求还是post 请求，对于相同的 @RequestMapping path(value) 值，如果用户请求路径能够匹配，同时也要执行 ProjectUrlConstraints 的matches方法
 *
 * 因此，在实现上，建议将相同路径前缀的handler，放在同一个 Controller中进行集中管理。例如：
 * ProjectController 的路径都是类似于 /{+namespace}/{project}/xxx
 * IssuesController 的路径都是类似于 /{+namespace}/{project}/issues 等
 *
 * 如果想要对于某一个 handler 进行路径校验，推荐使用 <pre class="code">@RequestMapping(value = "/{+namespace}/{project}/issues/{iid:[^/]+}")</pre>
 * 使用正则路径通配符，从而达到对某一个路径细节匹配的效果
 *
 * <p>有关执行顺序</p>
 * <pre class="code">@PathConstraint</pre>可以修饰对象类型，也可以修饰package类型。因此，如果 <pre class="code">@PathConstraint</pre>既修饰对象类型，又修饰package类型，那么最终
 * 生效的是<strong>对象类型</strong>
 *
 * <p>注意：每一个pattern只对应一个 RouterConstraints</p>
 */

@Target({ElementType.TYPE, ElementType.PACKAGE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathConstraint {
    Class<? extends RouterConstraints> constraint();
    String resourceCondition() default "";
    int order() default 0;
}
