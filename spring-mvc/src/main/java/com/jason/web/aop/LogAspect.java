package com.jason.web.aop;

import com.jason.web.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author: zhangjianhua
 * @date: 2019-12-25 11:35
 **/
@Aspect
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();


    @Pointcut("execution(* com.jason.web.controller.*.*( ..))")
    public void addLog(){
        logger.info("我是日志切面处理");
    }

    //@Before("addLog()")
    public void before(){
        System.out.println("我是日志前置处理");
    }

    @Around("@within(log) || @annotation(log)")
    public Object aroundLog(ProceedingJoinPoint point, Log log) throws Throwable{

        String logKey = log.key();
        if (log.key().isEmpty()){
            throw new Exception("logKey is not empty");
        }
        if (log.key().contains("#")){
            MethodSignature signature = (MethodSignature)point.getSignature();
            logKey = getValBySpEL(logKey, signature, point.getArgs());
        }
        logger.info("日志切面前置处理, around before");
        try {
            return point.proceed();
        }finally {
            logger.info("日志切面后置处理, around after");
        }
    }

    /**
     * 解析spEL表达式
     */
    private String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEL);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for(int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context).toString();
        }
        return null;
    }

}
