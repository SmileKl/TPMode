package com.example.vegetables;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.example.vegetables.param.TreeNodes;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class VegetablesApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,100L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8));
    private Integer pits = 500;


    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
            executor.execute(this::safe);
        }
    }

    public synchronized void safe(){
        if (pits < 0){
            System.out.println("票已卖完！");
            return;
        }
        System.out.println("目前票数>>"+pits);
        pits-=1;
        System.out.println("成功卖出一张票,剩余票数>>"+pits);
    }

    @Test
    void test005(){
        Map<String,Object> map = new HashMap();
        map.put("name","大帅哥");
        map.put("age","18");
        stringRedisTemplate.opsForHash().putAll("map",map);
        stringRedisTemplate.opsForHash().entries("map");
        System.out.println(stringRedisTemplate.opsForHash().entries("map"));
        System.out.println(stringRedisTemplate.opsForHash().get("map", "name"));
    }

    @Test
    public void test1(){
        List<TreeNodes> nodeList = new ArrayList<>();
        TreeNodes TreeNode =new TreeNodes();
        TreeNode.setParentId(0L);
        TreeNode.setId(111L);
        TreeNode.setName("父节点");
        TreeNode.setPath("/0/111");
        nodeList.add(TreeNode);

        TreeNodes TreeNode11 =new TreeNodes();
        TreeNode11.setParentId(111L);
        TreeNode11.setId(222L);
        TreeNode11.setName("第二层");
        TreeNode11.setPath("/0/111/222");
        nodeList.add(TreeNode11);
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("id");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", treeNodeConfig,
                (TreeNodes, tree) -> {
                    tree.setId(String.valueOf(TreeNode.getId()));
                    tree.setParentId(String.valueOf(TreeNode.getParentId()));
                    tree.setName(TreeNode.getName());
                    // 扩展属性 ...
                    tree.putExtra("path",TreeNode.getPath());
                });
        System.out.println(JSON.toJSONString(treeNodes));
    }

}
