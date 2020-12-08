package com.idc.modules.controller.agent;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.idc.common.redis.RedisService;
import com.idc.common.result.ResultView;
import com.idc.common.result.SysConstant;
import com.idc.common.sms.SzcSMS;
import com.idc.common.utils.EmptyUtil;
import com.idc.common.utils.FileUtils;
import com.idc.common.utils.IdentityUtil;
import com.idc.common.utils.MD5Util;
import com.idc.modules.controller.base.BaseController;
import com.idc.modules.entity.SysUser;
import com.idc.modules.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Dylan
 * @since 2020-11-21
 */
@RestController("agentUser")
@RequestMapping("/api/sys-user/agent")
@Validated
@Slf4j
public class SysUserController  extends BaseController {
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SzcSMS szcSMS;


    /**
     * 登录接口
     * @param userName
     * @param password
     * @param verCode
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public ResultView login(@NotBlank(message = "用户名不能为空") String userName,
                            @NotBlank(message = "密码不能为空") String password,@NotBlank(message = "验证码不能为空")String verCode,HttpServletRequest request) {
        // 验证码是否正确
        String imgCode=request.getSession().getAttribute("imgCode")+"";
        if(EmptyUtil.isEmpty(imgCode) || !verCode.equals(imgCode)){
            return ResultView.error("验证码错误!");
        }
        password=MD5Util.getMd5Code(password);
        SysUser sysUser = iSysUserService.loginByUserName(userName,password);

        if (EmptyUtil.isNotEmpty(sysUser)) {
            // 更新当前用户的最后登陆时间
            iSysUserService.updateLastLoginTime(sysUser.getId());
            sysUser.setPassWord("");
            String token = jwtToken(SysConstant.MANAGER_ID, sysUser.getId()+"", sysUser, SysConstant.ADMIN_AUTH_TIMEOUT);
            Map resMap=new HashMap();
            resMap.put("token",token);
            resMap.put("user", JSON.toJSON(sysUser));
            return ResultView.ok(resMap);
        }
        return ResultView.error("账号密码错误");
    }

    /**
     * 根据手机号登录
     * @param phoneNum
     * @param verCode
     * @param request
     * @return
     */
    @PostMapping(value = "/loginByPhone")
    public ResultView loginByPhone(@NotBlank(message = "手机号不能为空") String phoneNum,@NotBlank(message = "短信验证码不能为空")String verCode,HttpServletRequest request) {
        // 验证手机号是否合法
        if(!IdentityUtil.isMobileNO(phoneNum)){
            return ResultView.error("手机号不合法!");
        }
        // 验证验证码是否正确
        String phoneCode=redisService.getAuthorizedSubject(phoneNum+"login")+"";
        if(EmptyUtil.isEmpty(phoneCode) || !verCode.equals(phoneCode)){
            return ResultView.error("短信验证码错误!");
        }
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUser::getPhoneNum, phoneNum);
        SysUser sysUser = iSysUserService.getOne(qw);
        sysUser.setPassWord("");
        if (EmptyUtil.isNotEmpty(sysUser)) {
            String token = jwtToken(SysConstant.MANAGER_ID, sysUser.getId()+"", sysUser, SysConstant.ADMIN_AUTH_TIMEOUT);
            Map resMap=new HashMap();
            resMap.put("token",token);
            resMap.put("user", JSON.toJSON(sysUser));
            return ResultView.ok(resMap);
        }
        return ResultView.error("账号密码错误");
    }
    /**
     * 手机号注册
     * @param phoneNum
     * @param verCode
     * @return
     */
    @PostMapping(value = "/phoneRegist")
    public ResultView regist(@NotBlank(message = "手机号不能为空") @Length(min = 11, max = 11, message = "手机号长度必须为11位") String phoneNum,
                            @NotBlank(message = "验证码不能为空")
                            @Length(min = 6, max = 6, message = "验证码长度必须为6位") String verCode,@NotBlank(message = "密码不能为空") String passWord,@NotBlank(message = "品牌类型不能为空") String companyType,HttpServletRequest request) {
        // 验证手机号是否合法
        if(!IdentityUtil.isMobileNO(phoneNum)){
            return ResultView.error("手机号不合法!");
        }
        // 验证验证码是否正确
        String phoneCode=redisService.getAuthorizedSubject(phoneNum+"regist")+"";
        if(EmptyUtil.isEmpty(phoneCode) || !verCode.equals(phoneCode)){
            return ResultView.error("手机验证码错误!");
        }
        if(!"1".equals(companyType) && !"2".equals(companyType)){
            return ResultView.error("品牌类型错误!");
        }
        SysUser sysUser=new SysUser();
        sysUser.setUserName(phoneNum);
        sysUser.setPhoneNum(phoneNum);
        sysUser.setCType(Integer.parseInt(companyType));
        sysUser.setPassWord(MD5Util.getMd5Code(passWord));
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUser::getUserName, sysUser.getUserName());
        // 注册时检验当前账号是否已被注册
        if(EmptyUtil.isNotEmpty(iSysUserService.getOne(qw))){
            return ResultView.error("手机号已被注册!");
        }
        boolean saveUser=iSysUserService.save(sysUser);
        // 将数据重新查出来获取id
        qw = new QueryWrapper<>();
        qw.lambda().eq(SysUser::getUserName, sysUser.getUserName());
        sysUser=iSysUserService.getOne(qw);
        if (saveUser) {
            String token = jwtToken(SysConstant.MANAGER_ID, sysUser.getId()+"", sysUser, SysConstant.ADMIN_AUTH_TIMEOUT);
            Map resMap=new HashMap();
            resMap.put("token",token);
            resMap.put("user", JSON.toJSON(sysUser));
            return ResultView.ok(resMap);
        }
        return ResultView.error("注册失败");
    }

    /**
     * 获取登录图形验证码
     * @param request
     * @param response
     */
    @GetMapping(value = "/verifCode")
    public void verifCode(HttpServletRequest request, HttpServletResponse response) {
        // 通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        Map imgMap= fileUtils.getImageVerifCode();
        String resCodeStr=imgMap.get("sRand")+"";
        BufferedImage image=(BufferedImage)imgMap.get("image");
        request.getSession().setAttribute("imgCode", resCodeStr);
        System.out.println("********************************************验证码："+resCodeStr);
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取验证码-根据手机号
     * @param phoneNum 手机号
     * @param codeType 短信验证码类型 1 注册,2 登录 ,3 忘记密码
     * @return
     */
    @PostMapping(value = "/sendPhoneCode")
    public ResultView sendPhoneCode(@NotBlank(message = "手机号不能为空") @Length(min = 11, max = 11, message = "手机号长度必须为11位") String phoneNum,@NotBlank(message = "验证码类型不能为空！")String codeType) {
        String codeTypeStr="";
        if(codeType.equals("1")){
            // 注册
            codeTypeStr="regist";
        }else if(codeType.equals("2")){
            // 登录
            codeTypeStr="login";
        }else if(codeType.equals("3")){
            // 忘记密码
            codeTypeStr="forget";
        }
        if(EmptyUtil.isEmpty(codeTypeStr)){
            return ResultView.error("短信验证码类型不能为空!");
        }
        // 验证手机号是否合法
        if(!IdentityUtil.isMobileNO(phoneNum)){
            return ResultView.error("手机号不合法!");
        }
        // 查询当前手机号是否被注册
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUser::getPhoneNum, phoneNum);
        SysUser sysUser = iSysUserService.getOne(qw);
        // 注册功能检查该手机号是否被注册
        if(codeType.equals("1")){
            if(EmptyUtil.isNotEmpty(sysUser)){
                return ResultView.error("该手机号已被注册!");
            }
        }else{
            // 登录和忘记密码检查该手机号是否有注册
            if(EmptyUtil.isEmpty(sysUser)){
                return ResultView.error("该手机号未注册!");
            }
        }
        String phoneCode=redisService.getAuthorizedSubject(phoneNum+codeTypeStr)+"";
        if(EmptyUtil.isNotEmpty(phoneCode)){
            return ResultView.error("请勿重复获取验证码!");
        }
        phoneCode=IdentityUtil.getRandomNum(6);
        Map smsMap= JSON.parseObject(szcSMS.sendPhoneCode(phoneNum,szcSMS.getMsg(phoneCode)),Map.class);
        if("1".equals(smsMap.get("result")+"")){
            // 往redis中设置验证码
            redisService.setAuthorizedSubject(phoneNum+codeTypeStr, phoneCode, 60*3);
            return ResultView.ok(smsMap.get("tips"));
        }else{
            return ResultView.error(smsMap.get("tips")+"");
        }
    }

    /**
     * 短信验证码是否正确-忘记密码
     * @param phoneNum
     * @param verCode
     * @return
     */
    @PostMapping(value = "/checkForgetPhoneCode")
    public ResultView checkForgetPhoneCode(@NotBlank(message = "手机号不能为空") @Length(min = 11, max = 11, message = "手机号长度必须为11位") String phoneNum,@NotBlank(message = "短信验证码不能为空！")String verCode) {
        // 验证手机号是否合法
        if(!IdentityUtil.isMobileNO(phoneNum)){
            return ResultView.error("手机号不合法!");
        }
        // 验证验证码是否正确
        String phoneCode=redisService.getAuthorizedSubject(phoneNum+"forget")+"";
        if(EmptyUtil.isEmpty(phoneCode) || !verCode.equals(phoneCode)){
            return ResultView.error("手机验证码错误!");
        }
        return ResultView.ok("验证码正确!");
    }

    /**
     * 修改密码,通过手机号
     * @param phoneNum 手机号
     * @param passWord 密码
     * @return
     */
    @PostMapping(value = "/modifyPasswordByPhone")
    public ResultView modifyPasswordByPhone(@NotBlank(message = "手机号不能为空") @Length(min = 11, max = 11, message = "手机号长度必须为11位") String phoneNum,@NotBlank(message = "密码不能为空！")String passWord) {
        // 验证手机号是否合法
        if(!IdentityUtil.isMobileNO(phoneNum)){
            return ResultView.error("手机号不合法!");
        }
        // 验证手机号是否有用户
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUser::getPhoneNum, phoneNum);
        SysUser sysUser = iSysUserService.getOne(qw);
        if(EmptyUtil.isEmpty(sysUser)){
            return ResultView.error("该手机号未注册!");
        }
        sysUser.setPassWord(MD5Util.getMd5Code(passWord));
        UpdateWrapper<SysUser> uw = new UpdateWrapper<>();
        uw.lambda().eq(SysUser::getPhoneNum, phoneNum);
        boolean updateBool= iSysUserService.update(sysUser,uw);
        if(!updateBool){
            return ResultView.error("修改失败!");
        }
        // 修改密码
        return ResultView.ok();
    }

}
