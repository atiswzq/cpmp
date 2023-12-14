package cn.cofco.cpmp.factory;

import cn.cofco.cpmp.dao.MatklSapMapper;
import cn.cofco.cpmp.entity.MatklSap;
import cn.cofco.cpmp.utils.PageUtils;
import cn.cofco.cpmp.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Xujy on 2017/11/3.
 * for [用于物料相关的数据加工] in cpmp
 */
@Component
public class MatFactory {

    @Resource
    private MatklSapMapper matklSapMapper;


    /**
     * 根据物料类型获取物料类型描述
     *
     * @param matTyp 物料类型
     * @return
     */
    public String getMatTypDesc(String matTyp) {

        if (StringUtils.isEmpty(matTyp)) {
            return "";
        }

        MatklSap matklSap = new MatklSap();

        if (matTyp.length() == 2) {
            matklSap.setMatkl1(matTyp);
            Map map = PageUtils.getQueryCondsMap(matklSap, 0, 1);
            List<MatklSap> list = matklSapMapper.selectByMap(map);
            if (list == null || list.isEmpty()) {
                return "";
            } else {
                return list.get(0).getMatkl1name();
            }
        } else if (matTyp.length() == 4) {
            matklSap.setMatkl2(matTyp);
            Map map = PageUtils.getQueryCondsMap(matklSap, 0, 1);
            List<MatklSap> list = matklSapMapper.selectByMap(map);
            if (list == null || list.isEmpty()) {
                return "";
            } else {
                String name1 = list.get(0).getMatkl1name();
                String name2 = list.get(0).getMatkl2name();
                if (StringUtils.isEmpty(name1)) {
                    name1 = "";
                }
                if (StringUtils.isEmpty(name2)) {
                    name2 = "";
                }
                return name1 + "-" + name2;
            }
        } else {
            return "";
        }
    }


}
