package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.IoArtclDtlDto;
import cn.cofco.cpmp.dto.IoArtclQureyDto;
import cn.cofco.cpmp.dto.OutputDto;

/**
 * Created by Xujy on 2017/4/29.
 */
public interface IArtclService {

    /**
     * 根据条件分页查询文章信息
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    OutputDto queryByConds(IoArtclQureyDto dto, Integer pageNo, Integer pageSize) throws Exception;


    /**
     * 根据ID查询文章详情
     * @param artclId
     * @return
     * @throws Exception
     */
    OutputDto view(Long artclId) throws Exception;

    /**
     * 新增文章
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto add(IoArtclDtlDto dto) throws Exception;


    /**
     * 编辑文章
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto edit(IoArtclDtlDto dto) throws Exception;


    /**
     * 删除文章
     * @param artclId
     * @return
     * @throws Exception
     */
    OutputDto del(Long artclId) throws Exception;

    /**
     * 发布文章
     * @param artclId
     * @return
     * @throws Exception
     */
    OutputDto pub(Long artclId) throws Exception;

    /**
     * 取消发布
     * @param artclId
     * @return
     * @throws Exception
     */
    OutputDto cclPub(Long artclId) throws Exception;
}
