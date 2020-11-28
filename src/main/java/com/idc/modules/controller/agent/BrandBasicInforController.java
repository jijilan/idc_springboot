package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.enums.ResultEnum;
import com.idc.common.result.ResultView;
import com.idc.common.utils.EmptyUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.*;
import com.idc.modules.service.IBrandBasicInforService;
import com.idc.modules.service.IBrandPersonService;
import com.idc.modules.service.IBrandUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌 基础信息表(代理商、品牌商) 前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-27
 */
@Slf4j
@RestController("BrandBasicInfor")
@RequestMapping("/api/brand-basic-infor/agent")
@Validated
public class BrandBasicInforController extends BaseController {
    @Autowired
    private IBrandBasicInforService iBrandBasicInforService;
    @Autowired
    private IBrandPersonService iBrandPersonService;
    @Autowired
    private IBrandUserRoleService iBrandUserRoleService;


    //保存品牌制造商、代理商信息
    @PostMapping(value = "/saveBrandBasicInfor")
    public ResultView saveBrandBasicInfor(String jsonParamStr) {
        JSONObject jsonParam = JSONObject.parseObject(jsonParamStr);
        String basinforStr = jsonParam.getString("basinfor");
        String brandPersonsStr = jsonParam.getString("brandPersons");
        String userIdStr = jsonParam.getString("userId");
        if (EmptyUtil.isEmpty(basinforStr) || EmptyUtil.isEmpty(brandPersonsStr) || EmptyUtil.isEmpty(userIdStr)) {
            return ResultView.error("必要信息不能为空!");
        }
        // 1.保存品牌制造商、代理商基础信息
        BrandBasicInfor brandBasicInfor = JSON.parseObject(basinforStr, BrandBasicInfor.class);
        int userId = jsonParam.getInteger("userId");

        // a.验证基础信息必填字段是否为空
        Map checkMap = iBrandBasicInforService.checkBeanIsNull(brandBasicInfor);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 2.人员信息
        List<BrandPerson> brandPersonList = JSON.parseArray(brandPersonsStr, BrandPerson.class);
        // a.验证人员信息必填字段是否为空
        checkMap = iBrandPersonService.checkBeanListIsNull(brandPersonList);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 3.验证成功后进行保存
        Boolean saveBas = iBrandBasicInforService.save(brandBasicInfor);
        if (saveBas) {
            for (int i = 0; i < brandPersonList.size(); i++) {
                brandPersonList.set(i, brandPersonList.get(i).setBrandId(brandBasicInfor.getId()));
            }
            //插入人员信息
            Boolean savePer = iBrandPersonService.saveBatch(brandPersonList);
            if (savePer) {
                // 最后保存用户与品牌绑定信息
                BrandUserRole brandUserRole = new BrandUserRole();
                brandUserRole.setUserId(userId);
                brandUserRole.setBrandId(brandBasicInfor.getId());
                brandUserRole.setCType(brandBasicInfor.getCType());
                iBrandUserRoleService.save(brandUserRole);
                return ResultView.ok(brandBasicInfor.getId());
            }
        }
        return ResultView.error("保存数据失败");
    }

    @PostMapping(value = "/updateBrandBasicInfor")
    public ResultView updateBrandBasicInfor(@RequestBody JSONObject jsonParam) {
        String basinforStr = jsonParam.getString("basinfor");
        String brandPersonsStr = jsonParam.getString("brandPersons");
        String userIdStr = jsonParam.getString("userId");
        if (EmptyUtil.isEmpty(basinforStr) || EmptyUtil.isEmpty(brandPersonsStr) || EmptyUtil.isEmpty(userIdStr)) {
            return ResultView.error("必要信息不能为空!");
        }
        // 1.保存品牌制造商、代理商基础信息
        BrandBasicInfor brandBasicInfor = JSON.parseObject(basinforStr, BrandBasicInfor.class);
        int userId = jsonParam.getInteger("userId");

        // a.验证基础信息必填字段是否为空
        Map checkMap = iBrandBasicInforService.checkBeanIsNull(brandBasicInfor);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 2.人员信息
        List<BrandPerson> brandPersonList = JSON.parseArray(brandPersonsStr, BrandPerson.class);
        // a.验证人员信息必填字段是否为空
        checkMap = iBrandPersonService.checkBeanListIsNull(brandPersonList);
        if (!"true".equals(checkMap.get("status") + "")) {
            return ResultView.error(checkMap.get("memo") + "");
        }
        // 3.验证成功后进行更新
        // 更新基础信息
        Boolean saveBas = iBrandBasicInforService.updateById(brandBasicInfor);
        if (saveBas) {
            // 更新人员信息
            iBrandPersonService.updateBatchById(brandPersonList);
            return ResultView.ok();
        }
        return ResultView.error("保存数据失败");
    }

    @PostMapping(value = "/getBrandBasicInfor")
    public ResultView getBrandBasicInfor(@NotNull(message = "用户id不能为空") int userId, @NotNull(message = "品牌商类型不能为空") int cType) {
        Map resMap = new HashMap();
        QueryWrapper<BrandUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BrandUserRole::getUserId, userId).eq(BrandUserRole::getCType, cType);
        BrandUserRole brandUserRole = iBrandUserRoleService.getOne(queryWrapper);
        if (EmptyUtil.isEmpty(brandUserRole)) {
            return ResultView.error(ResultEnum.CODE_24);
        }
        // 获取基础信息
        BrandBasicInfor brandBasicInfor = iBrandBasicInforService.getById(brandUserRole.getBrandId());
        QueryWrapper<BrandPerson> perWrapper = new QueryWrapper<>();
        perWrapper.lambda().eq(BrandPerson::getBrandId, brandUserRole.getBrandId());
        List<BrandPerson> brandPersonList = iBrandPersonService.list(perWrapper);
        resMap.put("basinfor", brandBasicInfor);
        resMap.put("brandPersons", brandPersonList);
        return ResultView.ok(resMap);

    }

}
