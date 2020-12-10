package com.idc.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idc.common.enums.DictionaryEnum;
import com.idc.modules.entity.SysManager;
import com.idc.modules.entity.SysManagerRole;
import com.idc.modules.entity.SysRole;
import com.idc.common.exception.MyException;
import com.idc.modules.entity.sub.SysManagerSub;
import com.idc.modules.mapper.SysManagerMapper;
import com.idc.common.redis.RedisService;
import com.idc.common.enums.ResultEnum;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.modules.model.QPage;
import com.idc.modules.service.ISysManagerRoleService;
import com.idc.modules.service.ISysManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.idc.modules.service.ISysMenuService;
import com.idc.modules.service.ISysRoleService;
import com.idc.common.utils.DESCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
@Service
public class SysManagerServiceImpl extends ServiceImpl<SysManagerMapper, SysManager> implements ISysManagerService {


    @Autowired
    private RedisService redisService;
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysManagerRoleService iSysManagerRoleService;
    @Autowired
    private ISysMenuService iSysMenuService;

    @Override
    public SysManager login(String userAccount, String userPassword) {
        QueryWrapper<SysManager> qw = new QueryWrapper<>();
        qw.lambda().eq(SysManager::getUserAcount, userAccount);
        SysManager manager = getOne(qw);
        if (manager == null) {
            throw new MyException(ResultEnum.CODE_14);
        }
        if (!DESCode.encode(userPassword).equals(manager.getPassWord())) {
            throw new MyException(ResultEnum.CODE_15);
        }
        return manager;
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView updatePwd(String managerId, String oldPassWord, String passWord) {
        SysManager manager = getById(managerId);
        if (!DESCode.encode(oldPassWord).equals(manager.getPassWord())) {
            return ResultView.error(ResultEnum.CODE_15);
        }
        SysManager updateManager = new SysManager()
                .setManagerId(managerId)
                .setPassWord(DESCode.encode(passWord));
        if (updateById(updateManager)) {
            redisService.delAuthorizedSubject(managerId);
            return ResultView.ok();
        }
        return ResultView.error(ResultEnum.CODE_2);
    }

    @Override
    public List<SysRole> getSysRoleListByManager(String key) {
        return iSysRoleService.getRoleListByManager(key);
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView setRoleByManager(String managerId, String[] roleIds) {
        QueryWrapper<SysManagerRole> qw = new QueryWrapper();
        qw.lambda().eq(SysManagerRole::getManagerId, managerId);
        if (iSysManagerRoleService.remove(qw) && iSysManagerRoleService.setRoleByManager(managerId, roleIds) > 0) {
            return ResultView.ok();
        }
        new MyException(ResultEnum.CODE_2);
        return ResultView.error(ResultEnum.CODE_2);
    }

    @Transactional(rollbackFor = MyException.class)
    @Override
    public ResultView delManager(String key) {
        QueryWrapper<SysManagerRole> qw = new QueryWrapper();
        qw.lambda().eq(SysManagerRole::getManagerId, key);
        return removeById(key) && iSysManagerRoleService.remove(qw) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }


    @Override
    public ResultView getManagerList(QPage qPage, String userName, String userAcount, String managerLevel, String isFlag, SysManager sysManager) {
        IPage ipage = new Page(qPage.getOffset(), qPage.getLimit());
        ipage.setRecords(baseMapper.getManagerList(ipage, userName, userAcount, managerLevel, isFlag, getListBySubAll(sysManager)));
        return ResultView.ok(ipage);
    }

    @Override
    public ResultView operationManager(String managerId, int state) {
        SysManager sysManager = getById(managerId);
        if (state == 3) {
            sysManager.setPassWord(DESCode.encode(DictionaryEnum.DEFAULT_PASSWORD.getName()));
        } else {
            sysManager.setIsFlag(state);
        }
        //重新往redis赋值
        redisService.setAuthorizedSubject(managerId, sysManager, SysConstant.USER_AUTH_TIMEOUT);
        return updateById(sysManager) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }

    /**
     * @return com.laiganhlj.dlc.common.result.ResultView
     * @Author jijl
     * @Description: 编辑代理商
     * @Date 13:42 2019/2/25
     * @Param [sysManager]
     **/
    @Override
    public ResultView editManager(SysManager sysManager) {
        QueryWrapper queryWrapper = new QueryWrapper();
        List<SysManager> sysManagerNew = baseMapper.selectList(queryWrapper);
        SysManager sysManagernew = null;
        for (SysManager sysManagerlist : sysManagerNew) {
            //查找匹配的实体
            if (sysManager.getManagerId().equals(sysManagerlist.getManagerId())) {
                sysManagernew = sysManagerlist;
            }
            //判断重复手机号哦
            if (!sysManager.getManagerId().equals(sysManagerlist.getManagerId()) && sysManager.getUserAcount().equals(sysManagerlist.getUserAcount())) {
                return ResultView.error(ResultEnum.CODE_206);
            }
        }
        if (sysManagerNew != null) {
            sysManagernew.setUserAcount(sysManager.getUserAcount());
            sysManagernew.setUserName(sysManager.getUserName());
        }
        return updateById(sysManagernew) ? ResultView.ok() : ResultView.error(ResultEnum.CODE_2);
    }


    @Override
    public ResultView wxLogin(String userAccount, String userPassword) {
        QueryWrapper<SysManager> qw = new QueryWrapper<>();
        qw.lambda().eq(SysManager::getUserAcount, userAccount);
        SysManager manager = getOne(qw);
        if (manager == null) {
            return ResultView.error(ResultEnum.CODE_14);
        }
        if (!DESCode.encode(userPassword).equals(manager.getPassWord())) {
            return ResultView.error(ResultEnum.CODE_15);
        }
//        if (manager.getManagerLevel() == DictionaryEnum.MANAGER_LEVLE_0.getCode()) {
//            return ResultView.error(ResultEnum.CODE_21);
//        }
        if (manager.getIsFlag() == DictionaryEnum.ISFLAG_N.getCode()) {
            return ResultView.error(ResultEnum.CODE_9);
        }
        return ResultView.ok(manager);
    }

    /**
     * 获取上级代理
     *
     * @param managerId
     * @return
     */
    @Override
    public List<SysManagerSub> getBySubId(String managerId) {
        return baseMapper.getBySubId(managerId);
    }

    /**
     * 获取所有上级代理
     *
     * @param managerId
     * @return
     */
    @Override
    public List<SysManager> getBySuperIdAll(String managerId) {
        List<SysManager> sysManagerList = new ArrayList<>();
        SysManager sysManager = getById(managerId);
        if (sysManager != null) {
            sysManagerList.add(sysManager);
        }
//        while (true) {
//            if (StringUtils.isNotBlank(sysManager.getSuperId())) {
//                sysManager = getById(sysManager.getSuperId());
//                if (sysManager != null) {
//                    sysManagerList.add(sysManager);
//                } else {
//                    break;
//                }
//            } else {
//                break;
//            }
//        }
        return sysManagerList;
    }

    /**
     * 获取所有下级代理
     *
     * @param managerId
     * @return
     */
    @Override
    public List<SysManagerSub> getBySubAll(String managerId) {
        List<SysManagerSub> managerList = getBySubId(managerId);
        if (managerList != null && managerList.size() > 0) {
            managerList.forEach(value -> value.setSysManagerSubList(getBySubAll(value.getManagerId())));
        }
        return managerList;
    }

    /**
     * 移动递归list到同级中
     *
     * @param moveList 移动的list
     * @param newList  新的list
     * @return
     */
    @Override
    public List<String> mobilePeer(List<SysManagerSub> moveList, List<String> newList) {
        if (moveList != null && moveList.size() > 0) {
            moveList.forEach(value -> {
                newList.add(value.getManagerId());
                mobilePeer(value.getSysManagerSubList(), newList);
            });
        }
        return newList;
    }


    /**
     * @return com.idc.common.result.ResultView
     * @Author jijl
     * @Description: 获取全部总代理
     * @Date 11:31 2019/3/6
     * @Param []
     **/
    @Override
    public ResultView getAllOneAgent() {
        QueryWrapper<SysManager> queryWrapper = new QueryWrapper();
//        queryWrapper.lambda().eq(SysManager::getManagerLevel, DictionaryEnum.MANAGER_LEVLE_2.getCode());
        return ResultView.ok(baseMapper.selectList(queryWrapper));
    }

    /**
     * 获取自己下级的二级代理
     *
     * @param sysManager
     * @return
     */
    @Override
    public ResultView getAllTwoAgent(SysManager sysManager, String type) {
        QueryWrapper<SysManager> queryWrapper = new QueryWrapper();
//        queryWrapper.lambda().eq(SysManager::getManagerLevel, DictionaryEnum.MANAGER_LEVLE_3.getCode());
        if (2 == Integer.parseInt(type)) {
            queryWrapper.eq("superId", sysManager.getManagerId());
        }
        return ResultView.ok(baseMapper.selectList(queryWrapper));
    }

    @Override
    public List<String> getListBySubAll(SysManager sysManager) {
        List<String> list = new ArrayList<>();
        if (sysManager.getManagerType() == DictionaryEnum.MANAGER_TYPE_GENERAL.getCode()) {
            //获取所有下级
            list.add(sysManager.getManagerId());
            List<SysManagerSub> sysManagerSubList = getBySubAll(sysManager.getManagerId());
            list = mobilePeer(sysManagerSubList, list);
        }
        return list;
    }

}
