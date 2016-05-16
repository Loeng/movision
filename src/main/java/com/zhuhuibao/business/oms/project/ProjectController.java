package com.zhuhuibao.business.oms.project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.zhuhuibao.utils.pagination.model.Paging;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhuhuibao.common.JsonResult;
import com.zhuhuibao.mybatis.oms.service.ProjectService;
import com.zhuhuibao.mybatis.oms.entity.ProjectInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
@RestController
@RequestMapping(value="/rest/project/")
@Api(value="项目信息",description = "项目信息")
public class ProjectController {
	 private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

	 @Autowired
	 ProjectService projectService;

	 /**
     * 根据条件查询项目分页信息
     * @param projectInfo  项目信息
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="searchProjectPage", method = RequestMethod.GET)
	@ApiOperation(value = "根据条件查询项目分页信息",notes = "根据条件查询分页",response = JsonResult.class)
    public JsonResult searchProjectPage(@ApiParam(value = "项目信息+甲方乙方信息") ProjectInfo projectInfo,
									   @ApiParam(value = "页码") @RequestParam(required = false) String pageNo,
									   @ApiParam(value="每页显示的条数") @RequestParam(required = false) String pageSize) throws IOException {
	    //封装查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", projectInfo.getName());
		map.put("city", projectInfo.getCity());
		map.put("province", projectInfo.getProvince());
		log.info("查询工程信息：queryProjectInfo",map);
		JsonResult jsonResult = new JsonResult();
		Paging<ProjectInfo> pager = new Paging<ProjectInfo>(Integer.valueOf(pageNo),Integer.valueOf(pageSize));
		List<ProjectInfo> projectList;
		//调用查询接口
		projectList = projectService.findAllPrjectPager(map,pager);
		pager.result(projectList);
		jsonResult.setData(pager);
		return jsonResult;
    }
    
    /**
     * 添加项目信息
     * @param projectInfo  项目工程信息
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="addProjectInfo", method = RequestMethod.POST)
	@ApiOperation(value = "运营后台添加项目信息",notes = "运营后台的新增项目",response = JsonResult.class)
    public JsonResult addProjectInfo(@ApiParam(value = "项目信息+甲方乙方信息") ProjectInfo projectInfo) throws JsonGenerationException, JsonMappingException, IOException {
	   
		log.info("查询工程信息：queryProjectInfo",projectInfo);
		JsonResult jsonResult = new JsonResult();
		int reslult=0;
		try {
			//添加项目信息
			reslult = projectService.addProjectInfo(projectInfo);
		} catch (SQLException e) {
			log.error("queryProjectInfo:query sql exception",e.getMessage());
			jsonResult.setCode(400);
			jsonResult.setMessage("添加项目工程信息失败！");
			
		} 
		if(reslult==0){
			jsonResult.setCode(400);
			jsonResult.setMessage("添加项目工程失败！");
        }else{
        	jsonResult.setCode(200);
        }
		return jsonResult;
    }
    
    /**
     * 修改项目信息
     * @param projectInfo  项目工程信息
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="updateProjectInfo", method = RequestMethod.POST)
	@ApiOperation(value = "运营后台修改项目信息",notes = "运营后台的修改项目信息",response = JsonResult.class)
    public JsonResult updateProjectInfo(@ApiParam(value = "项目信息+甲方乙方信息") ProjectInfo projectInfo) throws JsonGenerationException, JsonMappingException, IOException {
	   
		log.info("修改工程信息：updateProjectInfo",projectInfo);
		JsonResult jsonResult = new JsonResult();
		int reslult=0;
		try {
			//修改项目信息
			reslult = projectService.updateProjectInfo(projectInfo);
		} catch (SQLException e) {
			log.error("updateProjectInfo:update sql exception",e.getMessage());
			jsonResult.setCode(400);
			jsonResult.setMessage("修改项目工程信息失败！");
			
		} 
		if(reslult==0){
			jsonResult.setCode(400);
			jsonResult.setMessage("修改项目工程失败！");
        }else{
        	jsonResult.setCode(200);
        }
		return jsonResult;
    }

	@RequestMapping(value = "previewProject",method = RequestMethod.GET)
	@ApiOperation(value="预览项目信息",notes = "根据Id查看项目信息",response = JsonResult.class)
	public JsonResult previewProject(@ApiParam(value = "招中标信息ID") @RequestParam Long porjectID)
	{
		JsonResult jsonResult = new JsonResult();
		int result = projectService.queryProjectInfoByID(porjectID);
		return jsonResult;
	}
}
