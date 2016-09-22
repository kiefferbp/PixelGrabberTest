/**
 * Created by Brian on 9/22/2016.
 */
public class Timer {
    private long startTime;
    private long endTime;
    private String startMessage;
    private String endMessage;

    public Timer(String startMessage, String endMessage) {
        this.startMessage = startMessage;
        this.endMessage = endMessage;
    }

    public void start() {
        startTime = System.nanoTime();
        System.out.println(startMessage);
    }

    public void end() {
        endTime = System.nanoTime();
        System.out.println(endMessage + " (" + (int)((endTime - startTime)/1000000) + " ms)");
    }
}
