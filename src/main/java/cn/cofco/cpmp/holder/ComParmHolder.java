package cn.cofco.cpmp.holder;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.ComParmMapper;
import cn.cofco.cpmp.entity.ComParm;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.utils.BeanUtils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Xujy on 2017/5/1.
 * for [通用参数缓存信息类] in cpmp
 */
public class ComParmHolder {

    private static Logger logger = LoggerManager.getSysLog();

    private static List<ComParm> comParms;

    /**
     * 加载通用参数
     * @param tmpComParms
     */
    public static synchronized void loadComParms(List<ComParm> tmpComParms) {
        if (comParms == null) {
            comParms = new ArrayList<ComParm>();
        } else {
            comParms.clear();
        }

        for (ComParm c : tmpComParms) {
            comParms.add(c);
        }
    }

    /*
    * 更新通用参数
    * @param tmpComParms
    * @param tmpComParms1
    * */
    public static synchronized void updateComParms(String currTyp ,List<ComParm> tmpComParms) {
        if (comParms == null) {
            comParms = new ArrayList<ComParm>();
        }else {
           try{
                Iterator<ComParm> iterable = comParms.iterator();
                while (iterable.hasNext()){
                    ComParm comParm = iterable.next();
                    if(comParm.getParmTyp().equals(currTyp)){
                        iterable.remove();
                    }
               }
               for(ComParm c1:tmpComParms){
                   comParms.add(c1);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }



    /**
     * 根据参数类型、参数编码查询通用参数信息
     * @param parmTyp
     * @param parmCod
     * @return
     */
    public static ComParm getByParmTypAndParmCod(String parmTyp, String parmCod) {
        if (comParms == null || comParms.isEmpty()) {
            return null;
        }

        for (ComParm c : comParms) {

            if (c.getParmTyp().equals(parmTyp)
                    && c.getParmCod().equals(parmCod)
                    && Constants.EFF_FLG_ON.equals(c.getEffFlg())) {
                ComParm tmpComParm = null;
                try {
                    tmpComParm = (ComParm) BeanUtils.cloneBean(c);
                } catch (Exception e) {
                    logger.error("clone通用参数时异常 - e: " + ExceptionUtils.getFullStackTrace(e));
                    tmpComParm = null;
                }
                return tmpComParm;
            }
        }

        return null;
    }

    /**
     * 根据通用参数类型获取通用参数列表信息
     * @param parmTyp
     * @return
     */
    public static List<ComParm> getByParmTyp(String parmTyp) {
        if (comParms == null || comParms.isEmpty()) {
            return Collections.emptyList();
        }

        List<ComParm> comParmList = new ArrayList<>();

        for (ComParm c : comParms) {

            if (c.getParmTyp().equals(parmTyp)
                    && Constants.EFF_FLG_ON.equals(c.getEffFlg())) {
                ComParm tmpComParm = null;
                try {
                    tmpComParm = (ComParm) BeanUtils.cloneBean(c);
                    comParmList.add(tmpComParm);
                } catch (Exception e) {
                    logger.error("clone通用参数时异常 - e: " + ExceptionUtils.getFullStackTrace(e));
                }
            }
        }

        return comParmList;
    }

    /**
     * 获取多个参数类型列表
     * @param parmTyps
     * @return
     */
	public static Map<String, List<ComParm>> getByParmTyps(String parmTyps) {
		
		if (comParms == null || comParms.isEmpty() || parmTyps == null || parmTyps.length() == 0) {
            return Collections.emptyMap();
        }
		String[] parmTypes = parmTyps.split(Constants.COMMA);
		HashSet<String> parmTypeSet = new HashSet<String>();
		parmTypeSet.addAll(Arrays.asList(parmTypes));
		Map<String, List<ComParm>> parms = new ConcurrentHashMap<>();
		for (ComParm c : comParms) {

            if (parmTypeSet.contains(c.getParmTyp())
                    && Constants.EFF_FLG_ON.equals(c.getEffFlg())) {
                ComParm tmpComParm = null;
                try {
                    tmpComParm = (ComParm) BeanUtils.cloneBean(c);
                    List<ComParm> comParms = parms.get(c.getParmTyp());
                    if (comParms == null) {
                    	comParms = new ArrayList<ComParm>();
                    	parms.put(c.getParmTyp(), comParms);
                    }
                    if (parmTypeSet.contains(c.getParmTyp())) {
                    	parms.get(c.getParmTyp()).add(c);
                    }
                    
                } catch (Exception e) {
                    logger.error("clone通用参数时异常 - e: " + ExceptionUtils.getFullStackTrace(e));
                }
            }
        }
		
		return parms;
	}

}
