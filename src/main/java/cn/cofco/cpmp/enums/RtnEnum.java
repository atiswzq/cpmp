package cn.cofco.cpmp.enums;

/**
 * Created by Xujy on 2017/4/29.
 */
public enum RtnEnum {
    SUC("S0000", "查询成功"),
    SUC_WITH_NO_DATA("S0001", "查询成功但无数据"),
    SUC_OPR("S0002", "操作成功"),
    SUC_OPR_NIL("S0003", "无数据待操作"),
    ARG_INVALID("E0001", "参数不合法"),
    ARG_STRUCT_INVALIE("E0002", "参数结构不合法"),
    UID_SGN_NIL("E1001", "UID或SGN为空"),
    UID_SGN_ERR("E1002", "验签失败"),
    INNER_ERR("E2001", "内部错误"),

    FAIL_OPR("E3001", "操作失败-受影响行数不为1"),
    SYS_ERR("E9099", "系统错误"),

    NO_OPRT_AUTH("E4002", "无操作权限！"),
    LOGIN_ERR("E4001", "token 验证失败！"),
    USER_NUM_ERR("E4003", "供应商账户数量已达上限！"),
    ACNT_IS_EXIST("E4004", "该账户已存在！");
    
    private String cod;
    private String desc;

    private RtnEnum(String cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public String getCod() {
        return this.cod;
    }

    public String getDesc() {
        return this.desc;
    }
}
