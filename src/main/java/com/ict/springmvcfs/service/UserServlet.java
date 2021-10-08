package com.ict.springmvcfs.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("userServlet")
public class UserServlet extends HttpServlet {
    private  MongoClient mongoClient;


    public UserServlet() {
        try{
            mongoClient = MongoClients.create("mongodb://192.168.42.128:27017");
            System.out.println("数据库连接成功");}
        catch (Exception e){
            System.out.println("数据库连接失败");
        }
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        //设置请求和响应的字符编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/html");

        //获得请求数据
        String name=request.getParameter("name");
        String age=request.getParameter("age");

        PrintWriter out = response.getWriter();

        if(name!=null&&age!=null)
        {
            try{
                MongoDatabase db = mongoClient.getDatabase("test");
                MongoCollection<Document> user = db.getCollection("user");
                Document d=new Document();

                d.put("name",name);
                d.put("age",Integer.parseInt(age));

                user.insertOne(d);
                out.write("新增成功");
            }catch (Exception e){
                out.write("新增失败");
            }
        }
        else{
            out.write("新增失败，缺少姓名或者年龄信息");
        }

    }







    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置请求和响应的字符编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{

        //获得请求数据
        String name=request.getParameter("name");
        String age=request.getParameter("age");


        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> user = db.getCollection("user");
        //使用过滤器设置条件
        BasicDBObject filter = new BasicDBObject();

        if (name != null || age != null) {
            if (name != null) {
                filter.append("name", new BasicDBObject("$regex", name));
            }
            if (age != null) {
                filter.append("age",Integer.parseInt(age));
            }

            MongoCursor<Document> cursor = user.find(filter).iterator();

            while (cursor.hasNext()) {
                Document d=cursor.next();
                out.write("name:" + d.get("name") + ",");
                out.write("age:" + d.get("age") + "\n");
            }


        }
        else{
            out.write("条件不足，无法查询");
        }}
        catch (Exception e){
            out.write("获取数据失败！");
        }
    }

    public void destroy() {
        //断开数据库连接
        if(null!=mongoClient){
            mongoClient.close();
            mongoClient=null;
        }
    }

}