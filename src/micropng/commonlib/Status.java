package micropng.commonlib;

public class Status {
    public enum StatusType {
	OK, ERROR;
    }

    private String msg;
    private static final Status ok;
    private StatusType statusType;

    static {
	ok = new Status(StatusType.OK);
	ok.msg = "OK";
    }

    private Status(StatusType statusType) {
	this.statusType = statusType;
    }

    public static Status ok() {
	return ok;
    }

    public static Status error(String msg) {
	final Status res = new Status(StatusType.ERROR);
	res.msg = msg;
	return res;
    }

    public String message() {
	return msg;
    }

    public StatusType getStatusType() {
	return statusType;
    }
}
