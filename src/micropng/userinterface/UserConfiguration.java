package micropng.userinterface;

public class UserConfiguration {
    private boolean huffbox;
    private boolean idatbox;
    private String path;

    public UserConfiguration() {
	huffbox = true;
	idatbox = true;
	path = null;
    }

    public boolean getHuff() {
	return huffbox;
    }

    public void setHuff(boolean huffi) {
	huffbox = huffi;
    }

    public boolean getIda() {
	return idatbox;
    }

    public void setIda(boolean idati) {
	idatbox = idati;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String pathi) {
	path = pathi;
    }
}
