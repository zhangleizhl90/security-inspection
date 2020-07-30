package io.geekidea.springbootplus.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.geekidea.springbootplus.config.constant.CommonConstant;
import io.geekidea.springbootplus.system.entity.SysUser;
import io.geekidea.springbootplus.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;

/**
 * Description TODO
 * Created by linyisheng on 2020-07-30
 */
@Component
public class SysUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final SysUserMapper sysUserMapper;

    @Autowired
    public SysUserArgumentResolver(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType() == SysUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String username = (String) webRequest.getAttribute(CommonConstant.JWT_USERNAME, RequestAttributes.SCOPE_REQUEST);
        if (StringUtils.hasText(username)) {
            return sysUserMapper.selectOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, username));
        }
        return null;
    }
}
