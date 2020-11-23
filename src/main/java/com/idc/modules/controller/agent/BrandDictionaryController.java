package com.idc.modules.controller.agent;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.idc.common.result.ResultView;
import com.idc.modules.entity.BrandDictionary;
import com.idc.modules.entity.CompanyType;
import com.idc.modules.service.IBrandDictionaryService;
import com.idc.modules.service.ICompanyTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-23
 */
@RestController("agentBrandDictionary")
@RequestMapping("/api/brandDictionary/agent")
@Validated
@Slf4j
public class BrandDictionaryController {
    @Autowired
    private IBrandDictionaryService iBrandDictionaryService;

    @PostMapping(value = "/getBrandDicListByPCode")
    public ResultView getBrandDicListByPCode(@NotBlank(message = "请输入父级编码") String pCode) {
        QueryWrapper<BrandDictionary> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(BrandDictionary::getPCode,pCode).orderByDesc(BrandDictionary::getBySort);
        List<Map<String,Object>> brandDictionaries=iBrandDictionaryService.listMaps(queryWrapper.lambda().select(BrandDictionary::getCode,BrandDictionary::getName));
        return ResultView.ok("查询成功",brandDictionaries);
    }

    /**
     * 获取数据结构数据
     * @return
     */
    @PostMapping(value = "/getBrandDicStruct")
    public ResultView getBrandDicStruct(String pCode) {
        Map resMap=new HashMap();
        // 1.先获取没有子级的数据
        resMap.put("noChildList",iBrandDictionaryService.getNoSonDicStruct(pCode));
        // 2.有子级的数据
        return ResultView.ok("查询成功",iBrandDictionaryService.getNoSonDicStruct(pCode));
    }


}
