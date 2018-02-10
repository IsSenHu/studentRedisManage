package ecjtu.husen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 11785
 * 显示页面
 */
@Controller
public class ViewsController {
    /**
     * 显示用户登陆的页面
     * @return
     */
    @RequestMapping("/toLogin.action")
    public ModelAndView tologin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/login");
        return modelAndView;
    }

    /**
     * 显示用户注册的页面
     * @return
     */
    @RequestMapping("/toRegist.action")
    public ModelAndView toRegist(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/regist");
        return modelAndView;
    }

    /**
     * 显示主页
     * @return
     */
    @RequestMapping("/toIndex.action")
    public ModelAndView toIndex(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/jsp/index");
        return modelAndView;
    }
}
