package com.atguigu;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class redis_test2 {
    public static void main(String[] args) {
//        yzhm("123321");
        jy(123321,"1972698094");
    }


    //验证码校验
    public static void jy(int hm,String yzm){
        Jedis jedis = new Jedis("192.168.1.8",6379);
        //验证码key
        String yzmkey="yzm"+hm;
        String s = jedis.get(yzmkey);
        if (s!=null){
            if (s.equals(yzm)){
                System.out.println("成功");
            }else{
                System.out.println("失败");
            }
        }else{
            System.out.println("失败");
        }

        jedis.close();
    }

    //验证只能一天访问三次
    //设置超时时间
    public static  void yzhm(String hm){
        Jedis jedis = new Jedis("192.168.1.8",6379);
        String hmkey="key"+hm;
        //验证码key
        String yzmkey="yzm"+hm;

        String s = jedis.get(hmkey);

        if (s == (null)){
            jedis.setex(hmkey,24*60*60,"1");
        }else if (Integer.parseInt(s)<3){
            //发送次数加一
            jedis.incr(hmkey);
        }else{
            System.out.println("今天已经发送三次验证码了");
            return;
        }
        //随机生成6位数验证码
        Random random = new Random();

        String yzm="";
        for (int i=1;i<=10;i++){
             int d = random.nextInt(10);
             yzm+=d;
        }
        jedis.setex(yzmkey,120,yzm);
        jedis.close();
    }
}
