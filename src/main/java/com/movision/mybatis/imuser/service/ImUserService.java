package com.movision.mybatis.imuser.service;

import com.movision.mybatis.imuser.entity.ImUser;
import com.movision.mybatis.imuser.entity.ImuserAppuser;
import com.movision.mybatis.imuser.mapper.ImUserMapper;
import com.movision.mybatis.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author zhuangyuhao
 * @Date 2017/3/7 17:11
 */
@Service
@Transactional
public class ImUserService {

    private static Logger log = LoggerFactory.getLogger(ImUserService.class);

    @Autowired
    private ImUserMapper imUserMapper;

    public int addImUser(ImUser imUser) {
        try {
            log.info("增加im用户");
            return imUserMapper.insertSelective(imUser);
        } catch (Exception e) {
            log.error("增加im用户失败", e);
            throw e;
        }
    }

    public ImUser selectByUserid(Integer id, Integer type) {
        try {
            log.info("根据用户id查询IM用户");
            return imUserMapper.selectByUserid(id, type);
        } catch (Exception e) {
            log.error("根据用户id查询IM用户失败", e);
            throw e;
        }
    }

    public List<ImUser> selectAllAPPImuser() {
        try {
            log.info("查询所有的app用户对应的im账号");
            return imUserMapper.selectAllAPPImuser();
        } catch (Exception e) {
            log.error("查询所有的app用户对应的im账号", e);
            throw e;
        }
    }

    public ImUser selectAPPImuser(int userid){
        try {
            log.info("根据userid查询当前的IM用户");
            return imUserMapper.selectAPPImuser(userid);
        }catch (Exception e){
            log.error("根据userid查询当前的IM用户失败", e);
            throw e;
        }
    }

    public Map<String, Object> queryAccidAndNickname(Map map) {
        try {
            log.info("查询指定用户的accid和昵称");
            return imUserMapper.queryAccidAndNicknameByUserid(map);
        } catch (Exception e) {
            log.error("查询指定用户的accid和昵称失败", e);
            throw e;
        }
    }

    public int updateImUserByUserid(ImUser imUser) {
        try {
            log.info("更新DBim用户信息");
            return imUserMapper.updateImUser(imUser);
        } catch (Exception e) {
            log.error("更新DBim用户信息失败", e);
            throw e;
        }
    }

    public List<ImuserAppuser> selectRelatedAppuserAndImuser() {
        try {
            log.info("查询相关联的app用户和im用户");
            return imUserMapper.selectRelatedAppuserAndImuser();
        } catch (Exception e) {
            log.error("查询相关联的app用户和im用户失败", e);
            throw e;
        }
    }

    public List<User> queryNotExistImUser() {
        try {
            log.info("查询本地数据库中没有yw_im_user数据的app用户信息");
            return imUserMapper.queryNotExistImUser();
        } catch (Exception e) {
            log.error("查询本地数据库中没有yw_im_user数据的app用户信息失败", e);
            throw e;
        }
    }

}
