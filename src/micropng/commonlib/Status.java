package micropng.commonlib;

public class Status {
    private enum StatusID {
	OK, ERROR;
    }

    private String msg;
    private static final Status ok;
    
    static {
	ok = new Status(StatusID.OK);
	ok.msg = "OK";
    }
    
    private Status(StatusID id) {
    };

    public static Status ok() {
	return ok;
    }

    public static Status error(String msg) {
	Status res = new Status(StatusID.ERROR);
	res.msg = msg;
	return res;
    }

    public String message() {
	return msg;
    }
}
