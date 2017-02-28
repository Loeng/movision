package com.movision.mybatis.circle.mapper;

import com.movision.mybatis.category.entity.CircleAndCircle;
import com.movision.mybatis.circle.entity.*;
import com.movision.mybatis.user.entity.User;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface CircleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Circle record);

    int insertSelective(Circle record);

    Circle selectByPrimaryKey(Integer id);

    List<CircleVo> queryHotCircleList();

    CircleVo queryCircleIndex1(int circleid);

    List<CircleVo> queryCircleByCategory(int categoryid);

    List<CircleVo> queryAuditCircle();

    int queryIsSupport(Map<String, Object> parammap);

    void addSupportSum(Map<String, Object> parammap);

    void addSupportRecored(Map<String, Object> parammap);

    CircleVo queryCircleInfo(int circleid);

    int updateByPrimaryKeySelective(Circle record);

    int updateByPrimaryKey(Circle record);

    String queryCircleByPhone(int circleid);

    List<Circle> queryHotCircle();

    int queryCircleScope(int circleid);

    int queryCircleOwner(int circleid);

    List<CircleVo> findAllqueryCircleByList(Integer category);

    List<CircleVo> queryCircleByLikeList(Map map);

    User queryCircleBycirclemaster(String phone);

    List<Integer> querycirclemanagerlist(Integer circleid);

    Integer queryFollowSum(Integer circleid);

    Integer queryCircleByNum();

    Circle queryCircleByName(Integer circleid);

    List<Integer> queryListByCircleCategory();

    List<Circle> queryListByCircleList(Integer in);

    List<User> queryCircleUserList(Integer categoryid);

    List<User> queryCircleMan(Integer categoryid);

    CircleFollowNum queryFollowAndNewNum(Integer categoryid);

    CircleVo queryFollowAndNum(Integer categoryid);

    CirclePostNum queryCirclePostNum(Integer categoryid);

    CircleVo queryCircleSum(Integer circleid);

    CircleVo queryCircleSupportnum(Integer categoryid);

    List<Circle> queryDiscoverList();

    Integer updateDiscover(Map<String, Integer> map);

    Integer updateCircleIndex(Integer circleid);

    CircleDetails quryCircleDetails(Integer circleid);

    int updateCircle(CircleDetails circleDetails);

    CircleDetails queryCircleByShow(Integer circleid);

    List<Integer> queryCircleByOrderidList();

    int insertCircle(CircleDetails circleDetails);

    List<Circle> findAllMyFollowCircleList(RowBounds rowBounds, Map map);
}