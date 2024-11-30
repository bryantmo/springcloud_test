package org.example;

import com.sun.tools.attach.VirtualMachine;

public class Main {

    /**
     * 这个main方法可以多次被执行，在字节码层面，完成对JVM的多次热修改部署
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 获取当前 JVM 进程的 PID
        String pid = "12460";
//        String pid = Long.toString(ProcessHandle.current().pid());

        // 加载代理
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent("/Users/bryantmo/Downloads/code/springcloud_test/agent/target/agent.jar");
        vm.detach();
    }
}