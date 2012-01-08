package micropng.commonlib;

public class DataDumper extends StreamFilter {
    private class DataDumperThread implements Runnable {

	@Override
	public void run() {
	    int in;
	    in = in();
	    while (in != -1) {
		//System.err.format("%03d ", in);
		in = in();
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
