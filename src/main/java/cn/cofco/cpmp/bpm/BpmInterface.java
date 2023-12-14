package cn.cofco.cpmp.bpm;

import cn.cofco.cpmp.bpm.entity.PiRequest;
import cn.cofco.cpmp.bpm.entity.PiResponse;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

//@WebService(targetNamespace = "http://cofco.com/lyt/bpm", name = "SI_BPM2GOS_FEEDBACK_OUT")
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface BpmInterface {

    /**
     * BPM回调接口
     * @param piRequest
     * @return PiResponse
     */
    @WebResult(name = "E_RESPONSE")
    PiResponse BPM2GOS_FEEDBACK(@WebParam(name="I_REQUEST") PiRequest piRequest);

//	/**
//	 * BPM 供应商准入审核回调接口
//	 * @param bpmSeqNo
//	 * @param msg
//	 * @param status
//	 */
//	OutputDto splrAdmtCallBack(@WebParam(name="bpmSeqNo") String bpmSeqNo, @WebParam(name="msg") String msg, @WebParam(name="status") String status);

//	/**
//	 * BPM 供应商开发申请审核回调接口
//	 * @param bpmSeqNo
//	 * @param msg
//	 * @param status
//	 */
//	OutputDto splrAplyCallBack(@WebParam(name="bpmSeqNo") String bpmSeqNo, @WebParam(name="msg") String msg, @WebParam(name="status") String status) throws Exception;
//
//	/**
//	 * 招标项目流程节点BPM审批回调接口
//	 * @param uid 用户ID
//	 * @param signature 用户签名
//	 * @param bpmSeqNo BPM审批流水号
//	 * @param reqTyp 请求类型
//	 * @param sucFlg 成功标识：0-审核不通过；1-审核通过
//	 * @param memo 备注
//	 * @return
//	 */
//	OutputDto bidCallBack(@WebParam(name="uid") String uid,
//								 @WebParam(name="signature") String signature,
//								 @WebParam(name="bpmSeqNo") String bpmSeqNo,
//								 @WebParam(name="reqTyp") String reqTyp,
//								 @WebParam(name="sucFlg") String sucFlg,
//								 @WebParam(name="memo") String memo);

}
