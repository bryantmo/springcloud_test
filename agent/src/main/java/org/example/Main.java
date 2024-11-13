package org.example;

import com.sun.tools.attach.VirtualMachine;

public class Main {
    public static void main(String[] args) throws Exception {
        // 获取当前 JVM 进程的 PID
        String pid = Long.toString(ProcessHandle.current().pid());

        // 加载代理
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent("/Users/bryantmo/Downloads/code/springcloud_test/agent/target/agent.jar");
        vm.detach();
    }
}