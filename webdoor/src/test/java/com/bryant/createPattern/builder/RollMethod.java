package com.bryant.createPattern.builder;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     驱动模式抽象类：steamed vermicelli roll 肠粉制作工艺
 * </p>
 */
public abstract class RollMethod {
  protected volatile static List<String> steps = new ArrayList<>();
  public RollMethod addStep(String puzzle){
    steps.add(puzzle);
    return this;
  }
  public void listSteps(String driveMethodType){
    System.out.println(driveMethodType + ": \n " + StringUtils.join(steps,","));
  }

  public static void main(String[] args) {
    RollMethod guangzhou = new GuangzhouRollMethod()
            .addStep("1.容器中放入肠粉，加入水调成粉浆")
            .addStep("2.蒸盘刷油，倒入粉浆，加入葱花，鸡蛋液")
            .addStep("3.水开上锅蒸一分钟。出锅后装盘，淋上蒜香豉油汁即可")
            ;

    RollMethod chaozhou = new ChaozhouRollMethod()
            .addStep("1.按粉和水1：2比例（或1：2.5比例），调好粉浆，适量粉加入水，搅拌成粉浆。粉和水的比例，这个看个人口感和经验来调，如果喜欢吃硬点的肠粉就适当减少水量，如果喜欢软滑的口感就调稀一点，调制粉浆的时候水要慢慢加，不要一下全倒进去，然后有时间，静置30分钟")
            .addStep("2.在蒸盘上刷上一层油，薄薄一层就好，用正方形盘来蒸比圆盘形蒸更容易弄起")
            .addStep("3.舀适量粉浆在盘子里，搅匀，添加您喜欢的辅料，可以放上自己想吃的肉末，鸡蛋或青菜")
            .addStep("4.把蒸盘放进锅隔水蒸（水要提前烧开），盖上盖子，上蒸锅大火蒸3分钟，揭开锅盖时肠粉起大泡就是熟了。")
            .addStep("5.刮板抹上油，然后刮板把肠粉往一边卷起放盘子里")
            .addStep("6.给肠粉淋上酱汁，就可以慢慢享用，美味的肠粉就做好了！（我这里是蒸了一张鸡蛋肉末肠粉）")
            ;

    guangzhou.listSteps("guangzhou");
    chaozhou.listSteps("chaozhou");
  }
}




/**
 * 广式肠粉的步骤
 */
class GuangzhouRollMethod extends RollMethod {

  public RollMethod addStep(String step) {
    return super.addStep(step);
  }
}

/**
 * 潮州肠粉的步骤
 */
class ChaozhouRollMethod extends RollMethod {

  public RollMethod addStep(String step) {
    return super.addStep(step);
  }
}