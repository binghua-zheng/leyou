package com.leyou.common.advice;

import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * @ClassName ConsoleLogAspect
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/3 22:48
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class ConsoleLogAspect {

    //设置切面点（切面地址根据自己的项目填写）
    @Pointcut(value = "(execution(* com.leyou.*.controller.*.*(..)))")
    public void webLog() {}

    //指定切点前的处理方法
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        //获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder sb = new StringBuilder();
        //拼接请求内容
        sb.append("\n请求路径:" + request.getRequestURL().toString() + "  " + request.getMethod() + "\n");
        //判断请求是什么请求
        if (request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> paramMap = new HashMap<>();
            parameterMap.forEach((key, value) -> paramMap.put(key, Arrays.stream(value).collect(joining(","))));
            sb.append("请求内容:" + JsonUtils.serialize(paramMap));
        } else if (request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())) {
            Object[] args = joinPoint.getArgs();
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.stream(args).forEach(object -> stringBuilder.append(object.toString().replace("=",":")));
            if (stringBuilder.length() == 0){
                stringBuilder.append("{}");
            }
            sb.append("请求内容:" + stringBuilder.toString());
        }
        log.info(sb.toString());
    }

    //指定切点前的处理方法
    @AfterReturning(pointcut = "webLog()",returning = "result")
    public void doAfterReturning(Object result) {
        if (ObjectUtils.isEmpty(result)){
            return;
        }
        log.info("\n返回結果:" + JsonUtils.serialize(result));
    }

    //指定切点前的处理方法
    @AfterThrowing(pointcut = "webLog()",throwing = "lyException")
    public void doAfterThrow(LyException lyException) {
        if (ObjectUtils.isEmpty(lyException)){
            return;
        }
        log.info("\n返回結果:" + JsonUtils.serialize(lyException.getExceptionEnum()));
    }

}
