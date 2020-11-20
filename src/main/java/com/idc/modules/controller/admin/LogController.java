package com.idc.modules.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idc.modules.service.ISysLogService;
import com.idc.common.annotation.valid.Status;
import com.idc.modules.entity.SysLog;
import com.idc.modules.model.QPage;
import com.idc.common.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author jijl
 * @since 2018-12-18
 */
@RestController("adminLog")
@RequestMapping("/api/sys-log/admin")
@Validated
@Slf4j
public class LogController {

    @Autowired
    private ISysLogService iSysLogService;

    /**
     * 获取异常信息列表
     *
     * @param logType 日志类型:1.系统 2.异常
     * @param qPage
     * @return
     */
    @GetMapping("/list")
    public ResultView get(@Status String logType, QPage qPage) {
        QueryWrapper<SysLog> qw = new QueryWrapper();
        qw.lambda().eq(SysLog::getLogType, logType).orderByDesc(SysLog::getCtime);
        IPage<SysLog> iPage = iSysLogService.page(new Page(qPage.getOffset(), qPage.getLimit()), qw);
        return ResultView.ok(iPage);
    }

}
