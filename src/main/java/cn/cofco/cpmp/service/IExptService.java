package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.IoExptLoginDto;
import cn.cofco.cpmp.dto.IoExptModPswDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.ExptInf;

/**
 * Created by Xujy on 2017/5/30.
 * for [专家服务接口] in cpmp
 */
public interface IExptService {

    /**
     * 根据条件查询专家信息
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    OutputDto getExptInfsByConds(ExptInf entity, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 新增专家信息
     *
     * @param entity
     * @return
     * @throws Exception
     */
    OutputDto add(ExptInf entity) throws Exception;

    /**
     * 删除专家信息
     *
     * @param exptId
     * @return
     * @throws Exception
     */
    OutputDto del(Long exptId) throws Exception;

    /**
     * 修改专家信息
     *
     * @param entity
     * @return
     */
    OutputDto mod(ExptInf entity) throws Exception;

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    OutputDto modPsw(IoExptModPswDto dto) throws Exception;

    /**
     * 重置密码
     *
     * @param exptId
     * @return
     * @throws Exception
     */
    OutputDto resetPsw(Long exptId) throws Exception;

    /**
     * 专家登录
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto login(IoExptLoginDto dto) throws Exception;

    /**
     * 根据专家ID查询专家信息
     * @param exptId
     * @return
     * @throws Exception
     */
    OutputDto view(Long exptId) throws Exception;
}
