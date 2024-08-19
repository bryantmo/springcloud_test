package com.bryant.createPattern.abstractFactory;

/**
 * 抽象工厂
 */
public class AbstractFactory {

    public static void main(String[] args) {
        FolderFactoryCreator abstractFactory = new FolderFactoryCreator();

        // 获取不同的工厂方法
        LinuxSystemFolderFactory linuxSystemFolderFactory = abstractFactory.getLinuxFolderFactory();
        WindowsSystemFolderFactory windowsSystemFolderFactory = abstractFactory.getWindowsFolderFactory();

        // 工厂构建不同类型的子产品
        Folder linuxFolder = linuxSystemFolderFactory.createFolder();
        Folder windowsFolder = windowsSystemFolderFactory.createFolder();

        // 子产品不同行为
        System.out.println(linuxFolder.getPath());
        System.out.println(windowsFolder.getPath());
    }
}


interface SystemFolderFactoryCreator {
    WindowsSystemFolderFactory getWindowsFolderFactory();
    LinuxSystemFolderFactory getLinuxFolderFactory();
}
/**
 * <p>
 *     抽象工厂类
 * </p>
 */

class FolderFactoryCreator implements SystemFolderFactoryCreator {
    @Override
    public WindowsSystemFolderFactory getWindowsFolderFactory() {
        return new WindowsSystemFolderFactory();
    }

    @Override
    public LinuxSystemFolderFactory getLinuxFolderFactory() {
        return new LinuxSystemFolderFactory();
    }
}

/**
 * 下面是工厂
 */

interface FolderFactory {
    Folder createFolder();
}
class LinuxSystemFolderFactory implements FolderFactory{
    @Override
    public Folder createFolder() {
        return new LinuxFolder();
    }
}
class WindowsSystemFolderFactory implements FolderFactory{
    @Override
    public Folder createFolder() {
        return new WindowFolder();
    }
}


/**
 * 下面是产品
 */

 interface Folder {
    public String getPath();
}
 class LinuxFolder implements Folder{
    private String path = "/usr/bin";

    @Override
    public String getPath() {
        return this.path;
    }
}
 class WindowFolder implements Folder{
    private String path = "/C:/file";

     @Override
     public String getPath() {
        return this.path;
    }
}