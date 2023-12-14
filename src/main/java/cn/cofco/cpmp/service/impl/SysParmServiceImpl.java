package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.dao.SysParmMapper;
import cn.cofco.cpmp.entity.SysParm;
import cn.cofco.cpmp.service.ISysParmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Xujy on 2017/5/1.
 * for [系统参数服务实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class SysParmServiceImpl implements ISysParmService {

    @Resource
    private SysParmMapper sysParmMapper;

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
    public List<SysParm> getSysParmAll() {

        List<SysParm> sysParmList = sysParmMapper.selectAll();

        return sysParmList;
    }
}
