package cn.cofco.cpmp.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by xiaox on 2017/6/5.
 */
@Component
public class SplrContextTools implements Serializable, ApplicationContextAware {
    public static ApplicationContext appContext = null;

    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        appContext = arg0;
    }

}
