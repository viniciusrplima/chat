import org.junit.Test;

public class GeneralTest {

    @Test
    public void testMessageSplit() {
        String message = "Vinicius;;Olá como vai?";

        String[] splitted = message.split(";;");

        System.out.println(splitted[0]);
        System.out.println(splitted[1]);
    }
}
