package top.lrshuai.skill.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReloadSkillGoods {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void reloadGoods(){
        //todo
    }
}
