package micropng;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class WinFrame extends JFrame implements ActionListener, ItemListener {
    Info inf = new Info();
    UserConfiguration config = new UserConfiguration();

    JMenuItem open = new JMenuItem("Öffnen");
    JMenuItem end = new JMenuItem("Beenden");
    JMenuItem info = new JMenuItem("Über");

    JButton action = new JButton("Ausführen");

    JLabel namefield = new JLabel();
    JLabel lengthfield = new JLabel();
    JLabel changedat = new JLabel();
    JLabel pathfield = new JLabel();

    JCheckBox idat = new JCheckBox("IDAT-Blöcke zusammenfassen", config.getIda());
    JCheckBox huff = new JCheckBox("Huffmanbäume optimieren", config.getHuff());

    // Konstruktor für das Hauptfenster
    public WinFrame() throws MalformedURLException {
	setTitle(inf.getName());
	setLocation(500, 200);
	setSize(500, 500);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container content = getContentPane();

	GridBagLayout grid = new GridBagLayout();
	GridBagConstraints cons = new GridBagConstraints();
	JPanel panel1 = new JPanel();
	panel1.setBackground(Color.WHITE);
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
	JPanel panel3 = new JPanel(new GridLayout(2, 1, 0, 0));
	panel1.setLayout(grid);

	// Toolbar
	JToolBar tool = new JToolBar();
	tool.setFloatable(false);
	tool.add(action);
	panel2.add(tool);

	// Dateinamen-Label
	buildConstraints(cons, 0, 0, 1, 1, 0, 0);
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.WEST;
	JLabel dname = new JLabel("Dateiname:");
	grid.setConstraints(dname, cons);
	panel1.add(dname);

	// Dateinamen-Feld
	buildConstraints(cons, 1, 0, 1, 1, 0, 0);
	cons.fill = GridBagConstraints.HORIZONTAL;
	grid.setConstraints(namefield, cons);
	panel1.add(namefield);

	// Dateigröße - Label
	buildConstraints(cons, 0, 2, 1, 1, 2, 0);
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.WEST;
	JLabel filelength = new JLabel("Dateigröße (Bytes):");
	grid.setConstraints(filelength, cons);
	panel1.add(filelength);

	// Dateigröße - Feld
	buildConstraints(cons, 1, 2, 1, 1, 50, 0);
	cons.fill = GridBagConstraints.HORIZONTAL;
	grid.setConstraints(lengthfield, cons);
	panel1.add(lengthfield);

	// Änderungsdatum - Label
	buildConstraints(cons, 0, 3, 1, 1, 2, 0);
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.WEST;
	JLabel filechange = new JLabel("Änderungsdatum: ");
	grid.setConstraints(filechange, cons);
	panel1.add(filechange);

	// Änderungdatum - Feld
	buildConstraints(cons, 1, 3, 1, 1, 50, 0);
	cons.fill = GridBagConstraints.HORIZONTAL;
	grid.setConstraints(changedat, cons);
	panel1.add(changedat);

	// Dateipfad - Label
	buildConstraints(cons, 0, 4, 1, 1, 2, 0);
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.WEST;
	JLabel filepath = new JLabel("Verzeichnis: ");
	grid.setConstraints(filepath, cons);
	panel1.add(filepath);

	// Dateipfad - Feld
	buildConstraints(cons, 1, 4, 1, 1, 50, 0);
	cons.fill = GridBagConstraints.HORIZONTAL;
	grid.setConstraints(pathfield, cons);
	panel1.add(pathfield);

	open.addActionListener(this);
	info.addActionListener(this);
	end.addActionListener(this);

	JMenu file = new JMenu("Datei");
	JMenu about = new JMenu("Info");
	file.add(open);
	file.addSeparator();
	file.add(end);
	about.add(info);

	JMenuBar bar = new JMenuBar();
	bar.add(file);
	bar.add(about);

	idat.addItemListener(this);
	huff.addItemListener(this);
	panel3.add(idat);
	panel3.add(huff);

	panel1.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
	content.add(panel1, BorderLayout.PAGE_START);
	content.add(panel2, BorderLayout.SOUTH);
	content.add(panel3, BorderLayout.EAST);

	setJMenuBar(bar);
	setLookAndFeel();
	setVisible(true);

    }

    private void buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
	gbc.gridx = gx;
	gbc.gridy = gy;
	gbc.gridwidth = gw;
	gbc.gridheight = gh;
	gbc.weightx = wx;
	gbc.weighty = wy;
    }
    
    private void setLookAndFeel() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    SwingUtilities.updateComponentTreeUI(this);
	} catch (Exception e) {
	    System.err.println("Das Aussehen konnte nicht angepasst werden: " + e);
	}
    }

    @Override
    public void actionPerformed(ActionEvent event) {
	Object source = event.getSource();
	if (source == open) {
	    JFileChooser fc = new JFileChooser();
	    fc.showOpenDialog(null);
	    File selFile = fc.getSelectedFile();

	    String filepath = selFile.getPath();
	    config.setPath(filepath);

	    // Dateiname
	    String filename = selFile.getName();
	    namefield.setText(filename);

	    // Dateigröße
	    long length = selFile.length();
	    String len = Long.toString(length);
	    lengthfield.setText(len);

	    // Änderungsdatum
	    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy | HH:mm:ss");
	    changedat.setText(sdf.format(selFile.lastModified()));

	    // Pfadangabe
	    String path = selFile.getParent();
	    pathfield.setText(path);

	} else if (source == info) {
	    JOptionPane.showMessageDialog(null, inf.getName() + "\n" + inf.getPurpose() + "\n"
		    + inf.getAuthor() + "\n" + inf.getCreation() + "\n" + inf.getLicence() + "\n"
		    + inf.getWeb(), "Über", DISPOSE_ON_CLOSE, inf.getLogo());
	} else if (source == end) {
	    System.exit(0);
	} else if (source == action) {
	    // TODO
	}
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
	if (idat.isSelected()) {
	    config.setIda(true);
	} else {
	    config.setIda(false);
	}
	if (huff.isSelected()) {
	    config.setHuff(true);
	} else {
	    config.setHuff(false);
	}
    }

    public static void main(String[] args) throws MalformedURLException {
	WinFrame wf = new WinFrame();
    }

}
