package com.idc.modules.controller.admin;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.idc.common.result.ResultView;
import com.idc.common.utils.DateUtils;
import com.idc.common.utils.EmptyUtil;
import com.idc.common.utils.ExcelUtil;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.excle.BrandInforListExcle;
import com.idc.modules.entity.excle.SysUserExcle;
import com.idc.modules.model.QPage;
import com.idc.modules.service.IBackStageService;
import com.idc.modules.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther Dyaln
 * @Date 2020/12/14
 */
@RestController("AdminUser")
@RequestMapping("/api/admin-user/admin")
@Validated
public class AdminUserController  extends BaseController {
    @Autowired
    private IBackStageService iBackStageService;
    @Autowired
    private ISysUserService iSysUserService;

    @PostMapping(value = "/getSysUserListByPage")
    public ResultView getSysUserListByPage(String qiylx, String userName, String isActive, Integer offset, Integer limit, HttpServletRequest request) {
        QPage qPage=new QPage();
        if(EmptyUtil.isNotEmpty(offset) && EmptyUtil.isNotEmpty(limit)){
            qPage.setLimit(limit);
            qPage.setOffset(offset);
        }
        Map parMap=new HashMap<>();
        parMap.put("qiylx",qiylx);
        parMap.put("userName",userName);
        parMap.put("isActive",isActive);
        IPage ipage= iBackStageService.selectSysUserList(qPage,parMap);
        return ResultView.ok(ipage);
    }

    @PostMapping(value = "/exportSysUserListByPage")
    public void exportSysUserListByPage(String qiylx, String userName, String isActive, HttpServletRequest request, HttpServletResponse response) {
        Map parMap=new HashMap<>();
        parMap.put("qiylx",qiylx);
        parMap.put("userName",userName);
        parMap.put("isActive",isActive);
        List<SysUserExcle> sysUserExcles = iBackStageService.selectSysUserList(parMap);
        ExcelUtil.defaultExport(sysUserExcles,SysUserExcle.class, DateUtils.getCurrentDateTime(DateUtils.DATE_TIME_FORMAT_UNSIGNED) +".xls",response,new ExportParams());
    }

    /**
     * 停用启用当前用户-根据用户id
     * @param id 用户id
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/activeSysUser")
    public ResultView activeSysUser(@NotNull(message = "id不能为空") Integer id, HttpServletRequest request, HttpServletResponse response) {
        iSysUserService.activeSysUser(id);
        return ResultView.ok();
    }

}
