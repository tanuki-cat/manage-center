package me.zhengjie.annotation.aspect;

import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.CheckCreate;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.utils.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
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
        String currentUserId = SecurityUtils.getCurrentUserId().toString();
        List<Role> roleList = roleService.findByUsersId(SecurityUtils.getCurrentUserId());
        List<String> roleNames = roleList.stream().map(Role::getName).toList();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method signatureMethod = signature.getMethod();
        CheckCreate checkCreate = signatureMethod.getAnnotation(CheckCreate.class);
        Class checkClass = checkCreate.clazz();
        String[] checkRoles = checkCreate.roles();
        //当当前角色是项目经理或者专利经理时
        if ((roleNames.stream().anyMatch("项目经理"::equals) && Arrays.asList(checkRoles).contains("项目经理")) ||
                (roleNames.stream().anyMatch("专利经理"::equals) && Arrays.asList(checkRoles).contains("专利经理"))) {
            for(Object arg: args) {
                if (checkClass.isInstance(arg)) {
                    for (String method : checkCreate.filedMethod()) {
                        if (method.equals("setAssignUserId")) {
                            Method setMethod = checkClass.getDeclaredMethod(method , String.class);
                            setMethod.invoke(arg, currentUserId);
                        }
                    }
                }
            }
        } else {
            for (String checkRole : checkRoles) {
                if (roleNames.contains(checkRole)) {
                    for (Object arg : args) {
                        if (checkClass.isInstance(arg)) {
                            for (String method : checkCreate.filedMethod()) {
                                if (method.equals("setAssignUserId")) {
                                    continue;
                                }
                                Method setMethod = checkClass.getDeclaredMethod(method, String.class);
                                setMethod.invoke(arg, currentUserName);
                            }
                        }
                    }
                }
            }
        }
        return pjp.proceed();
    }
}
