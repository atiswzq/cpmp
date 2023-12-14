package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.AuthResIoDto;
import cn.cofco.cpmp.dto.IoGetAuthResDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xujy on 2017/5/29.
 * for [权限资源管控 - 暂用] in cpmp
 */
@Controller
@RequestMapping("/authCtrl")
public class AuthCtrlController {

    private static Logger logger = LoggerManager.getBusiLog();

    private static final Map authResMap = new HashMap<String, List<AuthResIoDto>>() {
        {
            // 角色标识：00-供应商, 01-供应商后台管理员, 02-工厂采购员, 03-专家, 04-系统管理员
            // 00 - 供应商
            put("00", new ArrayList<AuthResIoDto>() {
                        {
                            add(new AuthResIoDto(1l, 0l, "0", "", "招标申请"));
                            add(new AuthResIoDto(2l, 1l, "1", "/supplier_self/public_bidding_list.html", "公共投标列表"));
                            add(new AuthResIoDto(3l, 1l, "1", "/supplier_self/invite_bidding_list.html", "邀请函列表"));

                            add(new AuthResIoDto(4l, 1l, "1", "/supplier_self/my_bidding_apply.html", "我的申请"));
                            add(new AuthResIoDto(2l, 1l, "1", "/supplier_self/public_bidding_list.html", "公共投标列表"));
                            add(new AuthResIoDto(3l, 1l, "1", "/supplier_self/invite_bidding_list.html", "邀请函列表"));
                            add(new AuthResIoDto(4l, 1l, "1", "/supplier_self/my_bidding_apply.html", "我的申请"));

                            add(new AuthResIoDto(5l, 0l, "0", "", "投标管理"));
                            add(new AuthResIoDto(6l, 5l, "1", "/supplier_self/my_bidding_list.html", "我的投标列表"));
                            add(new AuthResIoDto(7l, 5l, "1", "/supplier_self/my_bidding_success.html", "已中标"));

                            add(new AuthResIoDto(8l, 0l, "0", "", "产品自荐"));
                            add(new AuthResIoDto(9l, 8l, "1", "/supplier_self/product_list.html", "产品列表"));
                            add(new AuthResIoDto(10l, 8l, "1", "/supplier_self/add_product.html", "添加产品"));

                            add(new AuthResIoDto(11l, 11l, "0", "", "信息管理"));
                            add(new AuthResIoDto(12l, 11l, "1", "/supplier_self/user_register.html", "注册信息完善"));
                            add(new AuthResIoDto(13l, 11l, "1", "/supplier_self/user_manage.html", "用户列表"));
                            add(new AuthResIoDto(14l, 11l, "1", "/supplier_self/change_password.html", "修改密码"));
                        }
                    }
            );

            // 01 - 供应商后台管理员, 即工厂人员
            put("01", new ArrayList<AuthResIoDto>() {
                        {
                            add(new AuthResIoDto(15l, 0l, "0", "", "供应商管理"));
                            add(new AuthResIoDto(16l, 15l, "1", "/supplier_mng/supplier_apply_list.html", "注册审核"));
                            add(new AuthResIoDto(17l, 15l, "1", "/supplier_mng/supplier_develop_apply_list.html", "开发申请"));
                            add(new AuthResIoDto(18l, 15l, "1", "/supplier_mng/supplier_list.html", "供应商列表"));
                            add(new AuthResIoDto(19l, 15l, "1", "/supplier_mng/supplier_black_list.html", "已冻结供应商"));
                            add(new AuthResIoDto(20l, 15l, "1", "/supplier_mng/supplier_black_list.html", "供应商黑名单"));
                            add(new AuthResIoDto(21l, 15l, "1", "/supplier_mng/examination_list.html", "绩效考核"));

                        }
                    }
            );


            // 02-工厂采购员
            put("02", new ArrayList<AuthResIoDto>() {
                        {
                            add(new AuthResIoDto(22l, 0l, "0", "", "招投标竞价子系统"));
                            add(new AuthResIoDto(23l, 22l, "1", "/bid_online/proj_list.html", "网上竞价项目列表"));
                            add(new AuthResIoDto(24l, 22l, "1", "/bid_offline/proj_list.html", "线下招标项目列表"));
                        }
                    }
            );

            // 03-专家
            put("03", new ArrayList<AuthResIoDto>() {
                        {
                            add(new AuthResIoDto(25l, 0l, "0", "", "专家评标子系统"));
                            add(new AuthResIoDto(26l, 25l, "1", "/expert_grid/proj_list.html", "评标项目列表"));
                            add(new AuthResIoDto(27l, 25l, "1", "/expert_grid/user_information.html", "查询个人信息"));
                            add(new AuthResIoDto(28l, 25l, "1", "/expert_grid/change_password.html", "修改密码"));
                        }
                    }
            );

            // 04-系统管理员 总部
            put("04", new ArrayList<AuthResIoDto>() {
                        {
                            add(new AuthResIoDto(29l, 0l, "0", "", "专家管理"));
                            add(new AuthResIoDto(30l, 29l, "1", "/expt_mng/expt_list.html", "专家列表"));

                            add(new AuthResIoDto(31l, 0l, "0", "", "供应商管理"));
                            add(new AuthResIoDto(32l, 31l, "1", "/supplier_mng/supplier_apply_list.html", "注册审核"));
                            add(new AuthResIoDto(33l, 31l, "1", "/supplier_mng/supplier_develop_apply_list.html", "开发申请"));
                            add(new AuthResIoDto(34l, 31l, "1", "/supplier_mng/supplier_list.html", "供应商列表"));
                            add(new AuthResIoDto(35l, 31l, "1", "/supplier_mng/supplier_black_list.html", "已冻结供应商"));
                            add(new AuthResIoDto(36l, 31l, "1", "/supplier_mng/supplier_black_list.html", "供应商黑名单"));
                            add(new AuthResIoDto(37l, 31l, "1", "/supplier_mng/examination_list.html", "绩效考核"));
                        }
                    }
            );
        }
    };

    @RequestMapping(value = "/getAuthRes", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取权限资源信息", httpMethod = "POST", response = OutputDto.class, notes = "获取权限资源信息")
    public @ResponseBody
    JSONObject getAuthRes(HttpServletRequest request,
                          @RequestBody IoGetAuthResDto dto) {
        logger.info("request for -> 获取权限资源信息");
        try {
            List<AuthResIoDto> list = (List<AuthResIoDto>) authResMap.get(dto.getAuthTyp());
            OutputDto outputDto = OutputDtoUtil.setOutputDto(true, RtnEnum.SUC, list);
            return outputDto.toJson();

        } catch (Exception e) {
            logger.info("获取权限资源信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

}
