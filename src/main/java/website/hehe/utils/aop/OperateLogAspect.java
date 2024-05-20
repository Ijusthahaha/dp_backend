package website.hehe.utils.aop;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import website.hehe.pojo.Operation;
import website.hehe.pojo.vo.ExceptionLog;
import website.hehe.service.OperationService;
import website.hehe.utils.JwtUtils;
import website.hehe.utils.annotations.OperateLog;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class OperateLogAspect {

    private final Logger logger = LoggerFactory.getLogger(OperateLogAspect.class);
    @Resource
    private OperationService operationService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(website.hehe.utils.annotations.OperateLog)")
    public void operateLogPointCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* website.hehe.controller..*.*(..))")
    public void operateExceptionLogPointCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     */
    @AfterReturning(value = "operateLogPointCut()", returning = "keys")
    public void saveOperateLog(JoinPoint joinPoint, Object keys) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        Operation operation = new Operation();
        operation.setId(getUUID());

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 获取切入点所在的方法
        Method method = signature.getMethod();

        // 获取操作
        OperateLog opLog = method.getAnnotation(OperateLog.class);
        if (opLog != null) {
            String operateModel = opLog.operateModel();
            String operateType = opLog.operateType().getOperation();
            String operateDesc = opLog.operateDesc();
            operation.setModule(operateModel); // 操作模块
            operation.setType(operateType); // 操作类型
            operation.setDescription(operateDesc); // 操作描述
        }

        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;

        operation.setMethod(methodName); // 请求方法

        // 请求的参数
        Map<String, String> rtnMap = null;
        if (request != null) {
            rtnMap = converMap(request.getParameterMap());
        }

        // 将参数所在的数组转换成json
        String params = JSON.toJSONString(rtnMap);
        operation.setRequestParam(params); // 请求参数
        operation.setResponseParam(JSON.toJSONString(keys)); // 返回结果
        if (request != null) {
            Integer userId = new JwtUtils().getUserId(request.getHeader("token"));
            if (userId != null) {
                operation.setUserId(userId);
            }
            operation.setUri(request.getRequestURI());
        }
        operation.setCreateTime(new Date()); // 创建时间
        operationService.save(operation);
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operateExceptionLogPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        ExceptionLog exceptionLog = new ExceptionLog();
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();

        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;

        // 请求的参数
        Map<String, String> rtnMap = null;
        if (request != null) {
            rtnMap = converMap(request.getParameterMap());
            Integer userId = new JwtUtils().getUserId(request.getHeader("token"));
            if (userId != null) {
                exceptionLog.setUserId(userId); // 操作员ID
            }
            exceptionLog.setUri(request.getRequestURI()); // 操作URI
        }

        // 将参数所在的数组转换成json
        String params = JSON.toJSONString(rtnMap);
        exceptionLog.setRequestParam(params); // 请求参数
        exceptionLog.setMethod(methodName); // 请求方法名
        exceptionLog.setName(e.getClass().getName()); // 异常名称
        exceptionLog.setMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息

        logger.error(JSON.toJSONString(exceptionLog));
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stet : elements) {
            stringBuilder.append(stet).append("n");
        }
        return exceptionName + ":" + exceptionMessage + "nt" + stringBuilder;
    }
}