package micropng.userinterface;

public class InputConfigurationOptionProperties {

    public InputConfigurationOptionProperties(char shortName, String longName, String shortHelp,
	    String longHelp) {
	this.shortName = shortName;
	this.longName = longName;
	this.shortHelp = shortHelp;
	this.longHelp = longHelp;
    }
    private char shortName;
    private String longName;
    private String shortHelp;
    private String longHelp;

    public char getShortName() {
        return shortName;
    }
    public String getLongName() {
        return longName;
    }
    public String getShortHelp() {
        return shortHelp;
    }
    public String getLongHelp() {
        return longHelp;
    }

}
