package com.huajie.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/solr")
@RestController
public class SolrController {

    @Autowired
    private SolrClient client;

    @RequestMapping("/testSolr")
    public String testSolr(String param) throws IOException, SolrServerException {
        try {
            //查询参数对象，继承了SolrParams抽象类
            ModifiableSolrParams params =new ModifiableSolrParams();
            //查询条件
            params.add("q","companyName:*");
            //这里的分页和mysql分页一样
            params.add("start","0");
            params.add("rows","10");
            QueryResponse query = client.query(params);
            //查询结果
            SolrDocumentList results = query.getResults();
            //将查询结果直接转化为List，这里有个坑，对象每个属性必须要加 @Field("id") 属性，包为import org.apache.solr.client.solrj.beans.Field;
            //如果不加属性的话，会返回相等长度的的List，但是List里面每个对象的值均为空
//            List<SimplePublicCustomer> beans = query.getBeans(SimplePublicCustomer.class);
            return results.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
