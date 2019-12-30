package com.enovell.yunwei.home.home;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionSavingZuulPostFilter extends ZuulFilter{

	@Override
	public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpSession httpSession = context.getRequest().getSession();
        context.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.getId());
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        String now = sdf.format(new Date());
//        System.out.println("-----------------------" + now + "-----------------------");
        System.out.println("ZuulPostFilter session proxy: {}"+ httpSession.getId());
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "post";
	}

}
