package parentson;

public class Parent {
    int i = 1;

    public static class Son extends Parent{
//        int i = 2;
    }

    public static void main(String[] args) {
        Parent p = new Son();
        p.i = 3;
        System.out.println(p.i);
        System.out.println(((Son)p).i);
    }
}
