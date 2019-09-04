package com.bot.util.impl;

import com.bot.util.PropertyService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource({"classpath:queries.properties", "classpath:keyboard.properties"})
public class PropertyServiceImpl implements PropertyService {

    @Resource
    private Environment env;

    @Override
    public String getProperty(String name) {
        return this.env.getRequiredProperty(name);
    }

    @Override
    public List<String> getProperties(String name) {
        return Arrays.stream(getProperty(name).split(",")).collect(Collectors.toList());
    }

}
