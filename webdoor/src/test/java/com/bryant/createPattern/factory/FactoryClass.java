package com.bryant.createPattern.factory;

public class FactoryClass {
    //使用 getShape 方法获取形状类型的对象
    public static Shape getShape(String shapeType){
        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        } else if(shapeType.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }

    public static void main(String[] args) {
        // 工厂方法调用，show的具体行为，交给子类去定制实现
        FactoryClass.getShape("CIRCLE").show();
        FactoryClass.getShape("RECTANGLE").show();
        FactoryClass.getShape("SQUARE").show();
    }

}

interface Shape {
    // 产品方法：显示信息
    void show();
}
class Circle implements Shape {
    @Override
    public void show() {
        System.out.println("i am circle..");
    }
}
class Rectangle implements Shape {
    @Override
    public void show() {
        System.out.println("i am Rectangle..");
    }
}
class Square implements Shape {
    @Override
    public void show() {
        System.out.println("i am Square..");
    }
}
