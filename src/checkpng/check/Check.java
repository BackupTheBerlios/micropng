package checkpng.check;

import checkpng.check.Result.Status;

public abstract class Check {
    private CheckTask task;

    public Check(CheckTask task) {
	this.task = task;
    }

    public void incompatible(String message) {
	task.setResult(new Result(Status.INCOMPATIBLE, message));
	System.err.println("Program is not able to correctly process this png file.");
	System.exit(-1);
    }
    
    public void error(String message) {
	task.setResult(new Result(Status.ERROR, message));
	System.err.println("File not valid. Exit.");
	System.exit(-1);
    }

    public void warn(String message) {
	task.setResult(new Result(Status.WARNING, message));
	System.err.println(message);
    }

    public void ok() {
	task.setResult(new Result(Status.OK, "ok"));
    }
}
