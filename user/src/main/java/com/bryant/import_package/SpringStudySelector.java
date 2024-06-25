package com.bryant.import_package;

import com.bryant.bean.StudentBean;
import com.bryant.config.AppConfig;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 创建ImportSelector接口的实现类
 */
@Slf4j
public class SpringStudySelector implements ImportSelector, BeanFactoryAware {

    private BeanFactory beanFactory;

    /**
     * 导入资源，这里示例是 AppConfig 类
     * @param importingClassMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        importingClassMetadata.getAnnotationTypes().forEach(
                type -> {
                    System.out.println(String.format("select import: %s", type));
                }
        );
        System.out.println(String.format("select import beanFactory : %s", beanFactory));
        return new String[]{AppConfig.class.getName()};
    }

    /**
     * setBeanFactory方法，允许 SpringStudySelector 获取到其所在的 BeanFactory 的引用。
     *
     * 尽管BeanFactoryAware提供了灵活的操作手段，但是一般建议仅在必要的情况下使用它，因为它使得Bean与Spring容器耦合，降低了代码的清晰度和可测试性。
     * 在大多数情况下，推荐使用依赖注入的方式来管理Bean之间的依赖关系。
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        log.info("SpringStudySelector BeanFactoryAware setBeanFactory");

        // 下面的代码，是bean和容器强耦合的例子，不推荐这么使用！！
        // setBeanFactory仅仅是用于填充beanFactory的属性值
        // 真正的取bean操作，应该在程序运行态期间，进行的，见下面的
        try {
            StudentBean studentBean = (StudentBean) beanFactory.getBean("studentBean");
            if (Objects.nonNull(studentBean)) {
                log.info(String.format("get studentBean success, studentBean = %s", studentBean));
            }
        } catch (Exception e) {
            log.error(String.format("get studentBean fail, ", e.getStackTrace()));
        }
    }

}
