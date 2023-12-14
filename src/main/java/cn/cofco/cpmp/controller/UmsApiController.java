package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.holder.RegionHolder;
import cn.cofco.cpmp.service.IApiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by 杨宏毅 on 2017/5/30.
 * ums接口
 */
@Controller
public class UmsApiController {
    @Resource
    private IApiService apiService;

    /**
     * 获取全部区域
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/region/region_all", produces = "application/json;charset=UTF-8")
    public String region_all() {
        String res = apiService.region_all();
        RegionHolder.loadRegion(res);
        return res;
    }

    /**
     * 根据角色code获取页面资源的列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pageresources/bycode/{rolecode}", produces = "application/json;charset=UTF-8")
    public String pageresources_bycode(@PathVariable String rolecode) {
        String res = apiService.pageresources_bycode(rolecode);
        return res;
    }

    /**
     * 获取公司列表接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search/company-all", produces = "application/json;charset=UTF-8")
    public String companyAll(@RequestBody(required=false) String dept_Name) {
        String res = apiService.allCompany(dept_Name);
        return res;
    }
}
