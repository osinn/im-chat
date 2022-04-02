//package com.gitee.osinn.im.sys;
//
//import com.gitee.osinn.im.constants.Const;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * 描述
// *
// * @author wency_cai
// */
//@Controller
//public class LoginController {
//
//    @Autowired
//    @Qualifier(value = "imUserService")
//    private IImUserService imUserService;
//
//    @Autowired
//    private RedisService redisService;
//
//    @RequestMapping("")
//    public String login() {
//        return "login";
//    }
//
//    @RequestMapping("/toLogin")
//    public String toLogin(@RequestParam("name") String name, @RequestParam("password")  String password, ModelMap map) {
//        ImUser imUser = imUserService.getByLoginName(name);
//        if(imUser == null) {
//            return "login.html";
//        }
//        map.addAttribute("token", Const.TOKEN_CACHE_PREFIX + imUser.getId());
//        map.addAttribute("username", imUser.getLoginName());
//        redisService.set(Const.TOKEN_CACHE_PREFIX + imUser.getId(), imUser);
//        return "index";
//    }
//
//}
