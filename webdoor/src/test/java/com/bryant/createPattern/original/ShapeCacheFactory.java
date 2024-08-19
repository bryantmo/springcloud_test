package com.bryant.createPattern.original;

import java.util.Hashtable;

public class ShapeCacheFactory {

  //缓存池，保存多种图形的唯一对象。
  private static Hashtable<String, Shape> shapeMap
          = new Hashtable<String, Shape>();

  //结合工厂方法模式使用：当需要相同类型的图形时，从缓存池中取出对应的图形，然后复制一份给客户端。
  public static Shape getShape(String shapeId) {
    /**
     * 原型模式的核心，取出已有对象，并克隆
     */
    Shape cachedShape = shapeMap.get(shapeId);
    return (Shape) cachedShape.clone();
  }

  // 添加三种形状到缓存池：三角形、圆形、矩形
  public static void loadCache() {
    Circle circle = new Circle();
    circle.setType("Circle");
    shapeMap.put(circle.getType(),circle);

    Square square = new Square();
    square.setType("Square");
    shapeMap.put(square.getType(),square);

    Rectangle rectangle = new Rectangle();
    rectangle.setType("Rectangle");
    shapeMap.put(rectangle.getType(),rectangle);
  }

  public static void main(String[] args) {
    //initialization
    ShapeCacheFactory.loadCache();

    //Circle Square Rectangle
    Shape clonedShape = (Shape) ShapeCacheFactory.getShape("Circle");
    System.out.println("Shape : " + clonedShape.getType());

    Shape clonedShape2 = (Shape) ShapeCacheFactory.getShape("Square");
    System.out.println("Shape : " + clonedShape2.getType());

    Shape clonedShape3 = (Shape) ShapeCacheFactory.getShape("Rectangle");
    System.out.println("Shape : " + clonedShape3.getType());
  }
}

abstract class Shape implements Cloneable {
  protected String type;
  // 定义动作
  abstract void draw();
  public void setType(String type) {
    this.type = type;
  }
  public String getType(){
    return type;
  }
  //克隆方法
  public Object clone() {
    Object clone = null;
    try {
      clone = super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return clone;
  }
}
class Circle extends Shape {
  public Circle(){
    type = "Circle";
  }
  @Override
  public void draw() {
    System.out.println("Inside Circle::draw() method.");

  }
}
class Square extends Shape {
  public Square(){
    type = "Square";
  }
  @Override
  public void draw() {
    System.out.println("Inside Square::draw() method.");
  }
}
class Rectangle extends Shape {
  public Rectangle(){
    type = "Rectangle";
  }
  @Override
  public void draw() {
    System.out.println("Inside Rectangle::draw() method.");
  }
}