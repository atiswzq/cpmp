package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.LnkInf;

/**
 * Created by Xujy on 2017/4/29.
 */
public interface ILnkInfService {

    /**
     * 根据条件分页查询链接信息
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    OutputDto queryByConds(LnkInf entity, Integer pageNo, Integer pageSize) throws Exception;


    /**
     * 根据ID查询链接详情
     * @param lnkId
     * @return
     * @throws Exception
     */
    OutputDto view(Long lnkId) throws Exception;

    /**
     * 新增链接
     * @param entity
     * @return
     * @throws Exception
     */
    OutputDto add(LnkInf entity) throws Exception;


    /**
     * 编辑链接
     * @param entity
     * @return
     * @throws Exception
     */
    OutputDto edit(LnkInf entity) throws Exception;


    /**
     * 删除链接
     * @param lnkId
     * @return
     * @throws Exception
     */
    OutputDto del(Long lnkId) throws Exception;

    /**
     * 发布链接
     * @param lnkId
     * @return
     * @throws Exception
     */
    OutputDto pub(Long lnkId) throws Exception;

    /**
     * 取消发布
     * @param lnkId
     * @return
     * @throws Exception
     */
    OutputDto cclPub(Long lnkId) throws Exception;
}
