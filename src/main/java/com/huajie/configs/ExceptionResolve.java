package com.huajie.configs;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Administrator
 * @Date 2018/12/27 16:17
 * @Description
 */

@Component
public class ExceptionResolve implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("msg", "随便写点什么");
        attributes.put("resultCode", "系统错误");
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}
