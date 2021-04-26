package com.jason.web.redis;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class GameRankSample {
    static int TOTAL_SIZE = 20;
    public static void main(String[] args)
    {
        //Redis数据库连接地址
        String host = "r-wz9880cd5fed07f4pd.redis.rds.aliyuncs.com";
        //连接密码
        String password = "Ry7Xd0Mu3G";
        int port = 6379;
        Jedis jedis = new Jedis(host, port);
        try {
            String authString = jedis.auth(password);
            if (!authString.equals("OK"))
            {
                System.err.println("AUTH Failed: " + authString);
                return;
            }
            //Key(键)
            String key = "游戏名：奔跑吧，阿里！";
            //清除可能的已有数据
            jedis.del(key);
            //模拟生成若干个游戏玩家
            List<String> playerList = new ArrayList<String>();
            for (int i = 0; i < TOTAL_SIZE; ++i)
            {
                //随机生成每个玩家的ID
                playerList.add(UUID.randomUUID().toString());
            }
            System.out.println("输入所有玩家 ");
            //记录每个玩家的得分
            for (int i = 0; i < playerList.size(); i++)
            {
                //随机生成数字，模拟玩家的游戏得分
                int score = (int)(Math.random()*5000);
                String member = playerList.get(i);
                System.out.println("玩家ID：" + member + "， 玩家得分: " + score);
                //将玩家的ID和得分，都加到对应key的SortedSet中去
                jedis.zadd(key, score, member);
            }
            //输出打印全部玩家排行榜
            System.out.println();
            System.out.println("       "+key);
            System.out.println("       全部玩家排行榜                    ");
            //从对应key的SortedSet中获取已经排好序的玩家列表
            Set<Tuple> scoreList = jedis.zrevrangeWithScores(key, 0, -1);
            for (Tuple item : scoreList) {
                System.out.println("玩家ID："+item.getElement()+"， 玩家得分:"+Double.valueOf(item.getScore()).intValue());
            }
            //输出打印Top5玩家排行榜
            System.out.println();
            System.out.println("       "+key);
            System.out.println("       Top 玩家");
            scoreList = jedis.zrevrangeWithScores(key, 0, 4);
            for (Tuple item : scoreList) {
                System.out.println("玩家ID："+item.getElement()+"， 玩家得分:"+Double.valueOf(item.getScore()).intValue());
            }
            //输出打印特定玩家列表
            System.out.println();
            System.out.println("         "+key);
            System.out.println("          积分在1000至2000的玩家");
            //从对应key的SortedSet中获取已经积分在1000至2000的玩家列表
            scoreList = jedis.zrangeByScoreWithScores(key, 1000, 2000);
            for (Tuple item : scoreList) {
                System.out.println("玩家ID："+item.getElement()+"， 玩家得分:"+Double.valueOf(item.getScore()).intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            jedis.quit();
            jedis.close();
        }
    }
}