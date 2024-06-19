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
     * setBeanFactory方法，允许 SpringStudySelector 获取到其所在的 BeanFactory 的引用。
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        log.info("SpringStudySelector BeanFactoryAware setBeanFactory");
        try {
            StudentBean studentBean = (StudentBean) beanFactory.getBean("studentBean");
            if (Objects.nonNull(studentBean)) {
                log.info(String.format("get studentBean success, studentBean = %s", studentBean));
            }
        } catch (Exception e) {
            log.error(String.format("get studentBean fail, ", e.getStackTrace()));
        }
    }

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
}
