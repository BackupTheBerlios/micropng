package micropng;

public class OutputChannel {

    public void error(String message) {
	System.err.println(message);
	System.exit(-1);
    }
    
    public void info(String message) {
	System.out.println(message);
    }
 
    public void debug(String message) {
	info(message);
    }
}
