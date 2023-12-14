package cn.cofco.cpmp.service;

import cn.cofco.cpmp.bpm.entity.PiResponse;

/**
 * Created by Xujy on 2017/5/1.
 * for [线下竞价项目管理服务接口 - BPM] in cpmp
 */
public interface IBidProjOffMngForBpmService {

    /**
     * 线下公开招标项目立项BPM回调
     * @param bpmSeqNo BPM审批序列号
     * @param sucFlg 成功标识：0-审核不通过；1-审核通过
     * @param memo 审核备注
     * @return
     */
    PiResponse bud(String bpmSeqNo, String sucFlg, String memo) throws Exception;

    /**
     * 线下公开招标项目定标BPM回调
     * @param bpmSeqNo BPM审批序列号
     * @param sucFlg 成功标识：0-审核不通过；1-审核通过
     * @param memo 审核备注
     * @return
     */
    PiResponse awd(String bpmSeqNo, String sucFlg, String memo) throws Exception;


    /**
     * 线下公开招标项目废标BPM回调
     * @param bpmSeqNo BPM审批序列号
     * @param sucFlg 成功标识：0-审核不通过；1-审核通过
     * @param memo 审核备注
     * @return
     */
    PiResponse rpl(String bpmSeqNo, String sucFlg, String memo) throws Exception;
}
