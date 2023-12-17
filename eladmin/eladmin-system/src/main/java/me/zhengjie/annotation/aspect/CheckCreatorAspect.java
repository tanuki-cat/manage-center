package me.zhengjie.annotation.aspect;

import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.CheckCreate;
import me.zhengjie.annotation.Limit;
import me.zhengjie.modules.security.service.dto.JwtUserDto;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.mapper.UserRoleMapper;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.utils.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangzy
 * @date 2023/12/17 11:00
 * @describe
 * @since version 1.0
 */
@Component
@Aspect
@RequiredArgsConstructor
public class CheckCreatorAspect {
    private final RoleService roleService;

    @Pointcut("@annotation(me.zhengjie.annotation.CheckCreate)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object addCreator(ProceedingJoinPoint pjp ) throws Throwable {
        Object[] args = pjp.getArgs();
        String currentUserName = SecurityUtils.getCurrentUsername();
        List<Role> roleList = roleService.findByUsersId(SecurityUtils.getCurrentUserId());
//        JwtUserDto jwtUserDto = (JwtUserDto) SecurityUtils.getCurrentUser();
        List<String> roleNames = roleList.stream().map(Role::getName).collect(Collectors.toList());
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method signatureMethod = signature.getMethod();
        CheckCreate checkCreate = signatureMethod.getAnnotation(CheckCreate.class);
        Class checkClass = checkCreate.clazz();
        //todo 单账号 多角色
        String[] checkRoles = checkCreate.roles();
        for (String checkRole : checkRoles) {
            if (roleNames.contains(checkRole)) {
                for(Object arg: args) {
                    if (checkClass.isInstance(arg)) {
                        for (String method : checkCreate.filedMethod()) {
                            Method setCreatedBy = checkClass.getDeclaredMethod(method , String.class);
                            setCreatedBy.invoke(arg, currentUserName);
                        }
                        return pjp.proceed();
                    }
                }
            }
        }
        return pjp.proceed();
    }
}
