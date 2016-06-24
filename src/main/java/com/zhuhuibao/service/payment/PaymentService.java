package com.zhuhuibao.service.payment;

import com.zhuhuibao.common.Response;
import com.zhuhuibao.common.constant.MsgCodeConstant;
import com.zhuhuibao.common.constant.ZhbPaymentConstant;
import com.zhuhuibao.common.util.ShiroUtil;
import com.zhuhuibao.exception.AuthException;
import com.zhuhuibao.mybatis.expert.service.ExpertService;
import com.zhuhuibao.mybatis.memCenter.entity.Resume;
import com.zhuhuibao.mybatis.memCenter.service.ResumeService;
import com.zhuhuibao.mybatis.payment.service.PaymentGoodsService;
import com.zhuhuibao.mybatis.project.service.ProjectService;
import com.zhuhuibao.mybatis.tech.service.TechCooperationService;
import com.zhuhuibao.mybatis.witkey.entity.Cooperation;
import com.zhuhuibao.mybatis.witkey.service.CooperationService;
import com.zhuhuibao.utils.MsgPropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.zhuhuibao.common.constant.ZhbPaymentConstant.goodsType.*;

/**
 * 平台服务使用筑慧币支付
 *
 * @author pl
 * @version 2016/6/16 0016
 */
@Service
@Transactional
public class PaymentService {

    private final static Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    PaymentGoodsService goodsService;

    @Autowired
    ResumeService resume;

    @Autowired
    ProjectService projectService;

    //威客
    @Autowired
    private CooperationService cooperationService;

    //技术成果
    @Autowired
    TechCooperationService techService;

    @Autowired
    private ExpertService expertService;

    /**
     * 查看已消费的商品信息
     * @param goodsID  商品ID
     * @return
     * @throws Exception
     */
    public Response viewGoodsRecord(Long goodsID, String type) throws Exception {
        Response response = new Response();
        Long createId = ShiroUtil.getCreateID();
        Long companyId = ShiroUtil.getCompanyID();
        if(createId != null) {
            Map<String,Object> dataMap = new HashMap<String,Object>();
            Map<String,Object> con = new HashMap<String,Object>();
            //商品ID
            con.put("goodsId", goodsID);
            con.put("companyId",companyId);
            con.put("type",type);
            //项目是否已经被同企业账号查看过
            int viewNumber = goodsService.checkIsViewGoods(con);
            if(viewNumber == 0) {
                if(CKXMXX.toString().equals(type))//查看项目信息
                {
                    Map<String,Object> map  = projectService.previewUnLoginProject(goodsID);
                    dataMap.put("info",map);
                }else if(CKJSCG.toString().equals(type))//查看技术成果
                {
                    Map<String,Object> techCoop = techService.previewUnloginTechCoopDetail(String.valueOf(goodsID));
                    dataMap.put("info",techCoop);
                }else if(CKZJJSCG.toString().equals(type))//查看专家技术成果
                {
                    Map<String,String> map = expertService.queryUnloginAchievementById(String.valueOf(goodsID));
                    dataMap.put("info",map);
                }else if(CKZJXX.toString().equals(type))//查看专家信息
                {
                    Map map = expertService.getExpertDetail(String.valueOf(goodsID),viewNumber);
                    dataMap.put("info",map);
                }
                dataMap.put("payment", ZhbPaymentConstant.PAY_ZHB_NON_PURCHASE);
                response.setData(dataMap);

            }else {
                //查看下载简历
                if(CXXZJL.toString().equals(type)) {
                    Resume resume2 = resume.previewResume(String.valueOf(goodsID));
                    dataMap.put("info",resume2);
                    Resume resumeBean=new Resume();
                    resumeBean.setViews("1");
                    resumeBean.setId(String.valueOf(goodsID));
                    //更新点击率
                    resume.updateResume(resumeBean);
                    Map<String,Object> map1 = new HashMap<String,Object>();
                    map1.put("resumeID", goodsID);
                    map1.put("companyID",companyId);
                    map1.put("createId",resume2.getCreateid());
                    //谁查看过我的简历 同一个人可以查看相同简历多次。
                    resume.addLookRecord(map1);
                }else if(CKXMXX.toString().equals(type))//查看项目信息
                {
                    Map<String,Object> map  = projectService.queryProjectDetail(goodsID);
                    dataMap.put("info",map);
                }else if(CKWKRW.toString().equals(type))//查看威客任务
                {
                    Cooperation cooperation = cooperationService.queryCooperationInfoById(String.valueOf(goodsID));
                    dataMap.put("info",cooperation);
                    cooperation.setViews(String.valueOf(Integer.parseInt(cooperation.getViews())+1));
                    cooperationService.updateCooperationViews(cooperation);
                }else if(CKJSCG.toString().equals(type))//查看技术成果
                {
                    Map<String,Object> techMap = new HashMap<String,Object>();
                    techMap.put("id",goodsID);
                    Map<String,Object> techCoop = techService.previewTechCooperationDetail(techMap);
                    techService.updateTechCooperationViews(String.valueOf(goodsID));
                    dataMap.put("info",techCoop);
                }else if(CKZJJSCG.toString().equals(type))//查看专家技术成果
                {
                    Map<String,String> map = expertService.queryAchievementById(String.valueOf(goodsID));
                    dataMap.put("info",map);
                }else if(CKZJXX.toString().equals(type))//查看专家信息
                {
                    Map map = expertService.getExpertDetail(String.valueOf(goodsID),viewNumber);
                    dataMap.put("info",map);
                }
                dataMap.put("payment", ZhbPaymentConstant.PAY_ZHB_PURCHASE);

                response.setData(dataMap);
            }
        }else{
            throw new AuthException(MsgCodeConstant.un_login, MsgPropertiesUtils.getValue(String.valueOf(MsgCodeConstant.un_login)));
        }
        return response;
    }
}
