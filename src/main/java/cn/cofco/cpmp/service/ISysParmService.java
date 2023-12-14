package cn.cofco.cpmp.service;

import cn.cofco.cpmp.entity.SysParm;

import java.util.List;

/**
 * Created by Xujy on 2017/5/1.
 * for [系统参数服务接口] in cpmp
 */
public interface ISysParmService {

    /**
     * 获取所有通用参数信息
     * @return
     */
    List<SysParm> getSysParmAll();

}
