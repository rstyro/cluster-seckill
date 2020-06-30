package top.lrshuai.skill.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.lrshuai.skill.base.BaseController;
import top.lrshuai.skill.commons.result.Result;
import top.lrshuai.skill.user.entity.Users;
import top.lrshuai.skill.user.service.IUsersService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/user/users")
public class UsersController extends BaseController {

    @Autowired
    private IUsersService usersService;

    @GetMapping("/list")
    public Result list(){
        return  Result.ok(usersService.list());
    }

    @GetMapping("/add")
    public Result add(@RequestBody Users users){
        return  Result.ok(usersService.save(users));
    }

    @GetMapping("/del")
    public Result del(String id){
        return  Result.ok(usersService.removeById(id));
    }

}
