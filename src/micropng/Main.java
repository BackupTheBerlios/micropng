package micropng;

import micropng.userinterface.InvocationLineEvaluator;

public class Main {
    public static void main(String[] args) {
	InvocationLineEvaluator parametersEvaluator = new InvocationLineEvaluator();
	parametersEvaluator.evaluate(args);
    }
}
