package com.microspring.aop.aspect;

import com.microspring.cglib.proxy.MethodInterceptor;
import com.microspring.cglib.proxy.MethodProxy;
import com.microspring.commons.utils.ValidationUtils;


import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author czf
 * @Date 2020/5/12 7:24 下午
 */
public class AspectListExecutor implements MethodInterceptor {
    //被代理的类
    private Class<?> targetClass;

    // 排好序的Aspect列表  , 注意，这个列表保存的是能匹配到这个class的所有aspectInfo
    // 在方法执行时，还要判断是否匹配到这个方法
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList){
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }
    /**
     * 按照order的值进行升序排序，确保order值小的aspect先被织入
     *
     * @param aspectInfoList
     * @return
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                //按照值的大小进行升序排序
                return o1.getPriority() - o2.getPriority();
            }
        });
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        if(ValidationUtils.isEmpty(sortedAspectInfoList)){
            returnValue = methodProxy.invokeSuper(proxy, args);
            return returnValue;
        }
        //1.按照order的顺序升序执行完所有Aspect的before方法
        invokeBeforeAdvices(method, args);
        try{
            //2.执行被代理类的方法
            returnValue = methodProxy.invokeSuper(proxy, args);
            //3.如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Exception e){
            //4.如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
            invokeAfterThrowingAdvides(method, args, e);
        }
        return returnValue;
    }


    //4.如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing方法
    private void invokeAfterThrowingAdvides(Method method, Object[] args, Exception e) throws Throwable {
        for (int i =  sortedAspectInfoList.size() - 1; i >=0 ; i--){
            if ( sortedAspectInfoList.get(i).matchMethod(method) )
                sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }

    //3.如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning方法
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for (int i =  sortedAspectInfoList.size() - 1; i >=0 ; i--){
            if ( sortedAspectInfoList.get(i).matchMethod(method) )
                result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    //1.按照order的顺序升序执行完所有Aspect的before方法
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for(AspectInfo aspectInfo : sortedAspectInfoList){
            if ( aspectInfo.matchMethod(method) )
                aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }

    public List<AspectInfo> getSortedAspectInfoList() {
        return sortedAspectInfoList;
    }
}

