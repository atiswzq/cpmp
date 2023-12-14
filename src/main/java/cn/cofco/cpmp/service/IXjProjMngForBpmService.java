package cn.cofco.cpmp.service;

import cn.cofco.cpmp.bpm.entity.PiResponse;

/**
 * Created by Wzq on 2018/01/15.
 * for [询价项目管理服务接口 - BPM] in cpmp
 */
public interface IXjProjMngForBpmService {

    /**
     * 询价项目定标BPM回调
     * @param bpmSeqNo BPM审批序列号
     * @param sucFlg 成功标识：0-审核不通过；1-审核通过
     * @param memo 审核备注
     * @return
     */
    PiResponse awd(String bpmSeqNo, String sucFlg, String memo) throws Exception;

    /**
     * 询价项目废标BPM回调
     * @param bpmSeqNo BPM审批序列号
     * @param sucFlg 成功标识：0-审核不通过；1-审核通过
     * @param memo 审核备注
     * @return
     */
    PiResponse rpl(String bpmSeqNo, String sucFlg, String memo) throws Exception;
}
