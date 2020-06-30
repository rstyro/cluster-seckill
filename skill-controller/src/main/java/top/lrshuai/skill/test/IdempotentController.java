package top.lrshuai.skill.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.skill.commons.annotation.Idempotent;
import top.lrshuai.skill.commons.result.Result;

@Slf4j
@RestController
@RequestMapping("/idempotent")
public class IdempotentController {


    @GetMapping("/save")
    @Idempotent
    public Object save() throws InterruptedException {
        System.out.println("保存操作");
        Thread.sleep(2*1000);
        System.out.println("保存成功");
        return Result.ok();
    }

    @GetMapping("/update")
    @Idempotent
    public Object update() throws InterruptedException {
        System.out.println("更新操作");
        Thread.sleep(3*1000);
        System.out.println("更新成功");
        return Result.ok();
    }
}
