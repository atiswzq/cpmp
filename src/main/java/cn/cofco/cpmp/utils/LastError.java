package cn.cofco.cpmp.utils;

/**
 * 上一个错误
 */
public class LastError {
    private boolean isError = false;
    private String errMsg = "";

    public void setErrMsg(String errMsg) {
        isError = true;
        this.errMsg = errMsg;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public String toString() {
        return "LastError{" +
                "isError=" + isError +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
