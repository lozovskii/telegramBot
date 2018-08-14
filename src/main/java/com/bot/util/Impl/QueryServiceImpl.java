package com.bot.util.Impl;

import com.bot.util.QueryService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Service
@PropertySource("classpath:queries.properties")
public class QueryServiceImpl implements QueryService {

    @Resource
    private Environment env;

    @Override
    public String getQuery(String name) {
        return this.env.getRequiredProperty(name);
    }
}
