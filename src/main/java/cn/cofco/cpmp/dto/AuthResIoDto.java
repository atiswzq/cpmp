package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/29.
 * for [权限资源点DTO] in cpmp
 */
public class AuthResIoDto {

    /**
     * 权限资源点ID
     */
    private Long id;

    /**
     * 父节点ID
     */
    private Long parentId;

    /**
     * 是否叶子节点: 0-否；1-是
     */
    private String leafFlg;

    /**
     * 权限资源点URL
     */
    private String resUrl;

    /**
     * 权限资源名称
     */
    private String resNam;

    public AuthResIoDto() {

    }

    public AuthResIoDto(Long id, Long parentId, String leafFlg, String resUrl, String resNam) {
        this.id = id;
        this.parentId = parentId;
        this.leafFlg = leafFlg;
        this.resUrl = resUrl;
        this.resNam = resNam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLeafFlg() {
        return leafFlg;
    }

    public void setLeafFlg(String leafFlg) {
        this.leafFlg = leafFlg;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getResNam() {
        return resNam;
    }

    public void setResNam(String resNam) {
        this.resNam = resNam;
    }

    @Override
    public String toString() {
        return "AuthResIoDto{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", leafFlg='" + leafFlg + '\'' +
                ", resUrl='" + resUrl + '\'' +
                ", resNam='" + resNam + '\'' +
                '}';
    }
}
