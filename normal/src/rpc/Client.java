package rpc;

public class Client {

    public static void main(String[] args) throws Exception {
        //服务调用者只需要设置依赖
        AobingService serviceGet = rpcUtil.refer(AobingService.class, "127.0.0.1", 2333);
        System.out.println(serviceGet.hello("111"));
    }
}
