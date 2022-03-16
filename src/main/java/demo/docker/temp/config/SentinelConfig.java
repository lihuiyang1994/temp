package demo.docker.temp.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * lihui
 * 2022/3/15
 * SentinelConfig
 *
 * @description
 */
@Configuration
public class SentinelConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @PostConstruct
    private void initRules() throws Exception {
        FlowRule total = new FlowRule();
        total.setResource("message");
        total.setGrade(RuleConstant.FLOW_GRADE_QPS);
        total.setMaxQueueingTimeMs(5000);
        total.setCount(10);   // 每秒调用最大次数为 10 次

        FlowRule personRule = new FlowRule();
        //每个用户调用次数
        List<FlowRule> rules = new ArrayList<>();
        rules.add(total);
        rules.add(personRule);

        // 将控制规则载入到 Sentinel
        com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager.loadRules(rules);
    }
}
