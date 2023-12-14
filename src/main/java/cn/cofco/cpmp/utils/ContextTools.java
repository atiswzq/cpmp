package cn.cofco.cpmp.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by 杨宏毅 on 2017/5/30.
 */
@Component
public class ContextTools implements Serializable, ApplicationContextAware {
    public static ApplicationContext appContext = null;

    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        appContext = arg0;
    }

}
