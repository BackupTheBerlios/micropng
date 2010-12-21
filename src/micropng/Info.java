package micropng;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Info {
    private String author, name, creation, licence, purpose;
    private ImageIcon logo;
    private URL web;

    public Info() throws MalformedURLException {
	name = "micropng";
	author = "Katharina Titz, Martin Walch";
	creation = "2009-2010";
	licence = "GPLv3";
	purpose = "PNG-Dateien verkleinern";
	logo = new ImageIcon();
	web = new URL("http://micropng.berlios.de/");
    }

    public String getAuthor() {
	return author;
    }

    public String getName() {
	return name;
    }

    public String getCreation() {
	return creation;
    }

    public String getLicence() {
	return licence;
    }

    public String getPurpose() {
	return purpose;
    }

    public ImageIcon getLogo() {
	return logo;
    }

    public URL getWeb() {
	return web;
    }
}
