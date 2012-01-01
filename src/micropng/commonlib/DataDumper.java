package micropng.commonlib;

public class DataDumper extends StreamFilter {
    private class DataDumperThread implements Runnable {

	@Override
	public void run() {
	    int in;
	    try {
		in = in();
		while (in != -1) {
		    System.err.print(in);
		    in = in();
		}
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}
    }

    private DataDumperThread dataDumperThread = new DataDumperThread();

    public DataDumper() {
	dataDumperThread = new DataDumperThread();
    }

    public void start() {
	new Thread(dataDumperThread).start();
    }
}
