package micropng.userinterface.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import micropng.userinterface.InternalConfiguration;
import micropng.userinterface.InternalConfiguration.Preset;

@SuppressWarnings("serial")
public class WinFrame extends JFrame implements ActionListener, ItemListener {
    private Info inf = new Info();
    private InternalConfiguration config = InternalConfiguration.createNewConfig(Preset.DEFAULT);

    private JMenuItem open = new JMenuItem("Öffnen");
    private JMenuItem end = new JMenuItem("Beenden");
    private JMenuItem info = new JMenuItem("Über");

    private JButton action = new JButton("Ausführen");

    private JLabel namefield = new JLabel();
    private JLabel lengthfield = new JLabel();
    private JLabel changedat = new JLabel();
    private JLabel pathfield = new JLabel();

//    private JCheckBox idat = new JCheckBox("IDAT-Blöcke zusammenfassen", config.doesAggregateIDAT());
//    private JCheckBox huff = new JCheckBox("Huffmanbäume optimieren", config.doesOptimizeHuffmanTrees());
    private JCheckBox idat = new JCheckBox("IDAT-Blöcke zusammenfassen", true);
    private JCheckBox huff = new JCheckBox("Huffmanbäume optimieren", true);

    // Konstruktor für das Hauptfenster
    public WinFrame() throws MalformedURLException {
	setTitle(inf.getName());
	setLocation(500, 200);
	setSize(500, 500);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	Container content = getContentPane();
	// Layout für Panel1 (Dateiinfo)
	GridBagLayout grid = new GridBagLayout();
	// Layout für den gesamten Container content
	GridBagLayout grid2 = new GridBagLayout();
	// Layout für die Checkboxen
	GridBagLayout grid3 = new GridBagLayout();
	GridBagConstraints cons = new GridBagConstraints();

	JPanel panel1 = new JPanel();
	panel1.setLayout(grid);

	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));

	JPanel panel3 = new JPanel();
	panel3.setLayout(grid3);

	// Toolbar
	JToolBar tool = new JToolBar();
	tool.setFloatable(false);
	tool.add(action);
	panel2.add(tool);

	// Dateinamen-Label
	JLabel dname = new JLabel("Dateiname:");
	cons.gridx = 0;
	cons.gridy = 0;
	cons.anchor = GridBagConstraints.WEST;
	grid.setConstraints(dname, cons);
	panel1.add(dname);

	// Dateinamen-Feld
	cons.gridx = 1;
	cons.gridy = 0;
	grid.setConstraints(namefield, cons);
	panel1.add(namefield);

	// Dateigröße - Label
	JLabel filelength = new JLabel("Dateigröße (Bytes):");
	cons.gridx = 0;
	cons.gridy = 1;
	grid.setConstraints(filelength, cons);
	panel1.add(filelength);

	// Dateigröße - Feld
	cons.gridx = 1;
	cons.gridy = 1;
	grid.setConstraints(lengthfield, cons);
	panel1.add(lengthfield);

	// Änderungsdatum - Label
	JLabel filechange = new JLabel("Änderungsdatum: ");
	cons.gridx = 0;
	cons.gridy = 2;
	grid.setConstraints(filechange, cons);
	panel1.add(filechange);

	// Änderungdatum - Feld
	cons.gridx = 1;
	cons.gridy = 2;
	grid.setConstraints(changedat, cons);
	panel1.add(changedat);

	// Dateipfad - Label
	JLabel filepath = new JLabel("Verzeichnis: ");
	cons.gridx = 0;
	cons.gridy = 3;
	grid.setConstraints(filepath, cons);
	panel1.add(filepath);

	// Dateipfad - Feld
	cons.gridx = 1;
	cons.gridy = 3;
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
	cons.gridx = 0;
	cons.gridy = 0;
	cons.anchor = GridBagConstraints.LINE_START;
	grid3.setConstraints(idat, cons);

	huff.addItemListener(this);
	panel3.add(idat);
	cons.gridx = 0;
	cons.gridy = 1;
	grid3.setConstraints(huff, cons);
	panel3.add(huff);

	panel1.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
	content.setLayout(grid2);
	cons.gridx = 0;
	cons.gridy = 0;
	cons.weightx = 10;
	cons.weighty = 10;
	cons.anchor = GridBagConstraints.FIRST_LINE_START;
	grid2.setConstraints(panel1, cons);
	content.add(panel1);

	cons.gridx = 0;
	cons.gridy = 2;
	cons.anchor = GridBagConstraints.LAST_LINE_END;
	grid2.setConstraints(panel2, cons);
	content.add(panel2);

	cons.gridx = 0;
	cons.gridy = 1;
	cons.anchor = GridBagConstraints.LINE_START;
	grid2.setConstraints(panel3, cons);
	content.add(panel3);

	setJMenuBar(bar);
	setLookAndFeel();
	setVisible(true);
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
	    int val = fc.showOpenDialog(null);

	    if (val == JFileChooser.APPROVE_OPTION) {
		File selFile = fc.getSelectedFile();

		String filepath = selFile.getPath();
		//config.setPath(filepath);

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

	    } else
		fc.cancelSelection();
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
	//config.setAggregateIDAT(idat.isSelected());
	//config.setOptimizeHuffmanTrees(huff.isSelected());
    }

    public static void main(String[] args) throws MalformedURLException {
	WinFrame wf = new WinFrame();
    }

}
