package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IUserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger = LoggerManager.getBusiLog();
	
	@Resource
	private IUserService userService;
	
//	@RequestMapping(value="/add", method=RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "增加用户", httpMethod = "POST", response = OutputDto.class, notes = "增加用户")
//	public @ResponseBody JSONObject test(HttpServletRequest request, @ApiParam(value = "用户", required = true)@RequestBody(required=true) User user) {
//
//		logger.info("收到请求：");
//		Result result = userService.addUser(user);
//		return result.toJson();
//	}
//
//	@RequestMapping(value="/test", method=RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "測試", httpMethod = "POST", response = Result.class, notes = "增加用户")
//	public @ResponseBody JSONObject testAAA(HttpServletRequest request) {
//		logger.info("收到请求：");
//		OutputDto result = new OutputDto();
//		result.setSuccess(true);
//		logger.info(result.toJson().toJSONString());
//		return result.toJson();
//	}
}
