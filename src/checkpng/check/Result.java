package checkpng.check;

public class Result {
    public enum Status {
	OK, WARNING, ERROR, INCOMPATIBLE;
    }

    private Status status;
    private String message;

    public Result(Status status, String message) {
	this.status = status;
	this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
