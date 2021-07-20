package parentson;

public class Parent implements Cloneable{
    int i = 1;
    char ch;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static class Son extends Parent{
//        int i = 2;
    }

    public static void main(String[] args) {
        Parent p = new Son();
        p.i = 3;
        System.out.println(p.ch);
        System.out.println(((Son)p).i);
    }
}
