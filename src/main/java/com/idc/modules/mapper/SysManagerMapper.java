package com.idc.modules.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.idc.modules.entity.SysManager;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idc.modules.entity.excle.ManagerExcle;
import com.idc.modules.entity.sub.SysManagerSub;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jijl
 * @since 2018-10-02
 */
public interface SysManagerMapper extends BaseMapper<SysManager> {

    List<ManagerExcle> getManagerList(IPage ipage,
                                      @Param("userName") String userName,
                                      @Param("userAcount") String userAcount,
                                      @Param("managerLevel") String managerLevel,
                                      @Param("isFlag") String isFlag, @Param("list") List<String> list);

    List<SysManagerSub> getBySubId(String managerId);

}
