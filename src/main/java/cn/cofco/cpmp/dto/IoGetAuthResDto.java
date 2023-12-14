package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/29.
 * for [获取权限资源DTO - 暂用] in cpmp
 */
public class IoGetAuthResDto {

    /**
     *  角色标识：00-供应商, 01-供应商后台管理员, 02-工厂采购员, 03-系统管理员
     */
    private String authTyp;

    public String getAuthTyp() {
        return authTyp;
    }

    public void setAuthTyp(String authTyp) {
        this.authTyp = authTyp;
    }

    @Override
    public String toString() {
        return "IoGetAuthResDto{" +
                "authTyp='" + authTyp + '\'' +
                '}';
    }
}
