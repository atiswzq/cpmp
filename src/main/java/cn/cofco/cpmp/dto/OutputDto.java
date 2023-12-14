package cn.cofco.cpmp.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class OutputDto {

	private Boolean success;
	private String msgCod;
	private String msgInf;
	private Object data;


	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsgCod() {
		return msgCod;
	}

	public void setMsgCod(String msgCod) {
		this.msgCod = msgCod;
	}

	public String getMsgInf() {
		return msgInf;
	}

	public void setMsgInf(String msgInf) {
		this.msgInf = msgInf;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public JSONObject toJson() {
		return (JSONObject) JSON.toJSON(this);
	}

}
