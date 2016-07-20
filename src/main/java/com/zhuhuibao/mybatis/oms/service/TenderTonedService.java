package com.zhuhuibao.mybatis.oms.service;/**
 * @author Administrator
 * @version 2016/5/13 0013
 */

import com.zhuhuibao.common.constant.MsgCodeConstant;
import com.zhuhuibao.common.util.ConvertUtil;
import com.zhuhuibao.exception.BusinessException;
import com.zhuhuibao.mybatis.oms.entity.TenderToned;
import com.zhuhuibao.mybatis.oms.mapper.TenderTonedMapper;
import com.zhuhuibao.utils.pagination.model.Paging;
import com.zhuhuibao.utils.pagination.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 招中标公告管理业务处理类
 *
 * @author pl
 * @create 2016/5/13 0013
 **/
@Service
@Transactional
public class TenderTonedService {

    private final static Logger log = LoggerFactory.getLogger(TenderTonedService.class);

    @Autowired
    private TenderTonedMapper tenderTonedMapper;

    /**
     * 插入招中标公共信息
     *
     * @param tt 招中标公告
     * @return
     */
    public int insertTenderTone(TenderToned tt) throws Exception {
        log.info("insert tender tone " + tt.toString());
        int result = 0;
        try {
            result = tenderTonedMapper.insertSelective(tt);
        } catch (Exception e) {
            log.error("insert into tender tone info error!");
            throw e;
        }
        return result;
    }

    /**
     * 插入招中标公共信息
     *
     * @param tt 招中标公告
     * @return
     */
    public int updateTenderTone(TenderToned tt) throws Exception {
        log.info("update tender tone " + tt.toString());
        int result = 0;
        try {
            result = tenderTonedMapper.updateByPrimaryKeySelective(tt);
        } catch (Exception e) {
            log.error("update tender tone info error!");
            throw e;
        }
        return result;
    }

    /**
     * 预览招中标公共信息
     *
     * @param id 招中标公告ID
     * @return
     */
    public TenderToned queryTenderToneByID(Long id) throws Exception {
        log.info("preview tender tone info id = " + id.toString());
        TenderToned tt = null;
        try {
            tt = tenderTonedMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.error("preview tender tone info error!");
            throw e;
        }
        return tt;
    }

    /**
     * 根据条件查询分页信息
     *
     * @param map 招中标公告查询条件
     * @return
     */
    public List<TenderToned> findAllTenderTonedPager(Map<String, Object> map, Paging<TenderToned> page) throws Exception {
        log.info("search tender tone info for pager condition = " + StringUtils.mapToString(map));
        List<TenderToned> ttList;
        try {
            ttList = tenderTonedMapper.findAllTenderTonedPager(map, page.getRowBounds());
        } catch (Exception e) {
            log.error("search tender tone info for pager error! >> {}", e);
            throw e;
        }
        return ttList;
    }

    /**
     * 查询最新招标公告或者中标公告
     *
     * @param map 搜素条件 count：指定项目信息条数
     * @return
     */
    public List<Map<String, String>> queryLatestTenderToned(Map<String, Object> map) throws Exception {
        log.info("query latest project info condition = " + StringUtils.mapToString(map));
        List<Map<String, String>> tenderTonedList;
        try {
            tenderTonedList = tenderTonedMapper.queryLatestTenderToned(map);
        } catch (Exception e) {
            log.error("query latest project info error!");
            throw e;
        }
        return tenderTonedList;
    }

    /**
     * 预览招中标公共信息
     *
     * @param id 招中标公告ID
     * @return
     */
    public TenderToned queryTenderToneByIDCode(Long id) {
        log.info("preview tender tone info id = " + id.toString());
        TenderToned tt = null;
        try {
            tt = tenderTonedMapper.selectByPrimaryKeyCode(id);
        } catch (Exception e) {
            log.error("preview tender tone info error!");
            throw e;
        }
        return tt;
    }

    /**
     * 查询招中标
     *
     * @param map
     * @param pager
     * @return
     */
    public List<Map<String, Object>> findAllTenderTonedNPager(Map<String, Object> map, Paging<Map<String, Object>> pager) {
        log.info("search tender tone info for pager condition = " + StringUtils.mapToString(map));
        List<Map<String, Object>> ttList;
        try {
            ttList = tenderTonedMapper.findAllTenderTonedNPager(map, pager.getRowBounds());
            for (Map<String,Object> tmpMap : ttList){
                String address = "";
                String provinceCode = String.valueOf(tmpMap.get("province"));
                if(!StringUtils.isEmpty(provinceCode)){
                    tmpMap = ConvertUtil.execute(tmpMap, "province", "dictionaryService", "findProvinceByCode", new Object[]{provinceCode});
                    address += tmpMap.get("provinceName");
                }
                String cityCode = String.valueOf(tmpMap.get("city"));
                if(!StringUtils.isEmpty(cityCode)){
                    tmpMap = ConvertUtil.execute(tmpMap, "city", "dictionaryService", "findCityByCode", new Object[]{cityCode});
                    tmpMap.put("city",tmpMap.get("cityName"));
                    address += tmpMap.get("cityName");
                }
                String areaCode = String.valueOf(tmpMap.get("area"));
                if(!StringUtils.isEmpty(areaCode)){
                    tmpMap = ConvertUtil.execute(tmpMap, "area", "dictionaryService", "findAreaByCode", new Object[]{areaCode});
                    address += tmpMap.get("areaName");
                }
                tmpMap.put("area",address);
            }

        } catch (Exception e) {
            log.error("search tender tone info for pager error! >> {}", e);
            throw new BusinessException(MsgCodeConstant.DB_SELECT_FAIL,"查询失败");
        }
        return ttList;
    }
}
