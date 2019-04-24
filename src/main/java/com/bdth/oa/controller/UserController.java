package com.bdth.oa.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdth.oa.domain.User;
import com.bdth.oa.service.UserService;
import com.bdth.oa.utils.BaseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
@RestController
@RequestMapping("/mgr")
public class UserController {


    @Autowired
    private UserService userService;

    @RequiresPermissions("/mgr/list")
    @GetMapping("/list")
    public BaseResult queryPage(@RequestParam Long page, @RequestParam Long size) {
        IPage<User> pageResult = userService.page(new Page<>(page, size));
        return BaseResult.ok(pageResult.getRecords(),
                BaseResult.Cursor.builder()
                        .page(pageResult.getCurrent())
                        .size(pageResult.getSize())
                        .total(pageResult.getTotal())
                        .build());
    }


    /**
     * 跳转到首页
     * @return
     */
    @GetMapping({"/","/index"})
    public String showIndex() {
        return "index";
    }
}
