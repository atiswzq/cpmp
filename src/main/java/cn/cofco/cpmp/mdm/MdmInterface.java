package cn.cofco.cpmp.mdm;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import cn.cofco.cpmp.mdm.entity.MdmApvlReq;
import cn.cofco.cpmp.mdm.entity.MdmApvlRes;
import cn.cofco.cpmp.mdm.entity.MdmMat;
import cn.cofco.cpmp.mdm.entity.MdmSplr;

@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface MdmInterface {
	
	/**
	 * 接收物料信息发布接口
	 * @param mdmMat
	 */
	public void mats(MdmMat mdmMat);
	
	/**
	 * 接收客商信息发布接口
	 * @param mdmSplr
	 */
	public void splrs(MdmSplr mdmSplr);
	
	/**
	 * 客商主数据审批状态通知接口
	 * @param mdmApvlReq
	 * @return
	 */
	public MdmApvlRes apvl(MdmApvlReq mdmApvlReq);

}
