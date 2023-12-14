package cn.cofco.cpmp.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerManager {
	
	private static Logger sysLogger = LoggerFactory.getLogger("sys");
	private static Logger busiLogger = LoggerFactory.getLogger("busi");
	private static Logger portalMngLogger = LoggerFactory.getLogger("portal_mng");
	private static Logger bidOnlineMngLogger = LoggerFactory.getLogger("bid_online_mng");
	private static Logger splrSelfMngLogger = LoggerFactory.getLogger("splr_self_mng");
	private static Logger splrMngLogger = LoggerFactory.getLogger("splr_mng");
	private static Logger splrLogger = LoggerFactory.getLogger("splr");
	private static Logger bidOfflineMngLogger = LoggerFactory.getLogger("bid_offline_mng");
	private static Logger exptMngLogger = LoggerFactory.getLogger("expt_mng");


	public static Logger getSysLog() {
		if (sysLogger != null) {
			return sysLogger;
		}
		return LoggerFactory.getLogger("sys");
	}
	
	public static Logger getBusiLog() {
		if (busiLogger != null) {
			return busiLogger;
		}
		return LoggerFactory.getLogger("busi");
	}

	public static Logger getPortalMngLog() {
		if (portalMngLogger != null) {
			return portalMngLogger;
		}
		return LoggerFactory.getLogger("portal_mng");
	}

    public static Logger getBidOnlineMngLog() {
        if (bidOnlineMngLogger != null) {
            return bidOnlineMngLogger;
        }
        return LoggerFactory.getLogger("bid_online_mng");
    }
    public static Logger getSplrSelfMngLog() {
        if (splrSelfMngLogger != null) {
            return splrSelfMngLogger;
        }
        return LoggerFactory.getLogger("splr_self_mng");
    }
    public static Logger getSplrMngLog() {
        if (splrMngLogger != null) {
            return splrMngLogger;
        }
        return LoggerFactory.getLogger("splr_mng");
    }

	public static Logger getSplrLog() {
		if (splrLogger != null) {
			return splrLogger;
		}
		return LoggerFactory.getLogger("splr");
	}

	public static Logger getBidOfflineMngLog() {
		if (bidOfflineMngLogger != null) {
			return bidOfflineMngLogger;
		}
		return LoggerFactory.getLogger("bid_offline_mng");
	}

	/**
	 * 获取专家管理日志
	 * @return
	 */
	public static Logger getExptMngLog() {

        if (exptMngLogger != null) {
            return exptMngLogger;
        }
        return LoggerFactory.getLogger("expt_mng");
	}
}
