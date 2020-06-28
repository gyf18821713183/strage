package SmtThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutDownDemo {
    private static ExecutorService service = Executors.newFixedThreadPool(10);
    //newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    //newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    //newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
    //newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
    public static void main(String[] args) throws  Exception{

        for (int i = 0; i < 100; i++) {
            service.submit(()->{
                System.out.println(Thread.currentThread().getName()+"正在执行任务");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("开始等待");
        service.shutdown();
        boolean b = service.awaitTermination(3, TimeUnit.SECONDS);
        // 与shutdown()方法结合使用时，尤其要注意的是shutdown()方法
        // 必须要在awaitTermination()方法之前调用，该方法才会生效。否则会造成死锁。
        System.out.println("等待完毕");
        if(b){
            System.out.println("分线程已经运行结束");
        } else {
            System.out.println(" 到达指定时间，还有线程没执行完，不再等待，关闭线程池!");
            service.shutdownNow();

        }
        System.out.println(Thread.currentThread().getName());
    }
}
