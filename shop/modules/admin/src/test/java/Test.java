import java.util.Random;

public class Test {
    public static void main(String[] args) {
        RandomHan han = new RandomHan();
        System.out.print(han.getRandomHan());
    }
     
}
class RandomHan {
    private Random ran = new Random();
    private final static int delta = 0x9fa5 - 0x4e00 + 1;
      
    public char getRandomHan() {
        return (char)(0x4e00 + ran.nextInt(delta)); 
    }
}