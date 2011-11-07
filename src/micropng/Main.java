package micropng;

import micropng.userinterface.ParametersEvaluator;

public class Main {
    public static void main(String[] args) {
	ParametersEvaluator parametersEvaluator = new ParametersEvaluator();
	parametersEvaluator.evaluate(args);
    }
}
