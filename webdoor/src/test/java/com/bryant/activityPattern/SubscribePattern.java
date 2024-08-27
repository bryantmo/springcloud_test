package com.bryant.activityPattern;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 观察者模式:观察者模式是一种行为型设计模式，它定义了一种一对多的依赖关系，当一个对象的状态发生改变时，其所有依赖者都会收到通知并自动更新。
 *
 * 优点
 *      抽象耦合：观察者和主题之间是抽象耦合的。
 *      触发机制：建立了一套状态改变时的触发和通知机制。
 *
 * 缺点
 *      性能问题：如果观察者众多，通知过程可能耗时。
 *      循环依赖：可能导致循环调用和系统崩溃。
 *      缺乏变化详情：观察者不知道主题如何变化，只知道变化发生。
 */
@Slf4j
public class SubscribePattern {

    public static void main(String[] args) {
        VillageHead villageHead = new VillageHead();
        Villager villager1 = new Villager("美羊羊");
        Villager villager2 = new Villager("喜洋洋");
        Villager villager3 = new Villager("懒洋洋");
        villageHead.add(villager1);
        villageHead.add(villager2);
        villageHead.add(villager3);

        String action = "狼来了";
        villageHead.notifyVillager(action);
    }

}

/**
 * 村长：是观察者
 */
@Slf4j
class VillageHead {

    private List<Villager> villagerList = new ArrayList<Villager>();

    public void add(Villager villager) {
        villagerList.add(villager);
    }

    public void remove(Villager villager) {
        villagerList.remove(villager);
    }

    public void notifyVillager(String action) {
        if (action.equals("狼来了")) {
            log.info("村长通知所有村民：狼来了，村民快跑...");
            for (Villager villager : villagerList) {
                villager.goHome();
            }
        }
    }

}

/**
 * 村民：被观察者
 */
@Slf4j
class Villager {

    public Villager(String name) {
        this.name = name;
    }

    private String name;

    public void goHome() {
        log.info("村民" + name + "回家了");
    }
}