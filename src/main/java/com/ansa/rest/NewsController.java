package com.ansa.rest;

import com.ansa.dto.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@RestController
@RequestMapping(value = "/rest/news")
public class NewsController {
    private static List<Data> dataList = new CopyOnWriteArrayList<Data>();

    static {
        dataList.add(new Data(1L, "test", "test description"));
        dataList.add(new Data(2L, "name", "name description"));

    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Data> getData(){

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return dataList;
    }
}
