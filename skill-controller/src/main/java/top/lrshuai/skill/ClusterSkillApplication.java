package top.lrshuai.skill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling// 定时任务
@EnableDiscoveryClient
@MapperScan({"top.lrshuai.skill.*.mapper"})
@SpringBootApplication
public class ClusterSkillApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ClusterSkillApplication.class, args);
    }

    /**
     * 打war 包需要重写
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // TODO Auto-generated method stub
        return builder.sources(this.getClass());
    }

}
