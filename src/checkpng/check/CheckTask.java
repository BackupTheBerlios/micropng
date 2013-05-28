package checkpng.check;

public class CheckTask {
    private Result result;

    public void setResult(Result result) {
	if (this.result != null) {
	    throw new RuntimeException("Result already set. This is a bug.");
	}
	this.result = result;
    }

    public Result getResult() {
	return result;
    }
}
