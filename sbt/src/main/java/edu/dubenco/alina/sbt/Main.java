package edu.dubenco.alina.sbt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.json.JSONObject;

/**
* Main STB class
**/
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JFileChooser fc = new JFileChooser();
	private JToolBar toolBar;
	private JButton btnLoad;
	private JButton btnLoadJson;
	private JButton btnSave;
	private JButton btnSaveAs;
	private JButton btnExport;
	private JButton btnSelectAll;
	private JButton btnUnselectAll;
	private JTextField txtFilter;
	private JScrollPane scrl;
	private CustomItemModel itemModel;
	private JTable itemsTable;
	
	private Policy currentPolicy;
	

	public Main() {
		this.setTitle("Security Benchmarking Tool");
		this.setSize(1450, 815);
		this.setLayout(new BorderLayout(5, 5));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		fc.setCurrentDirectory(Paths.get("").toFile().getAbsoluteFile());
		
		toolBar = new JToolBar();
		this.add(toolBar, BorderLayout.NORTH);
		
		btnLoad = new JButton("Load audit file");
		toolBar.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFromFile();
			}
		});
		
		btnLoadJson = new JButton("Load JSON file");
		toolBar.add(btnLoadJson);
		btnLoadJson.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFromJsonFile();
			}
		});
		
		toolBar.addSeparator();
		
		btnSave = new JButton("Save");
		toolBar.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		
		btnSaveAs = new JButton("Save As");
		toolBar.add(btnSaveAs);
		btnSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		
		btnExport = new JButton("Export");
		toolBar.add(btnExport);
		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		
		toolBar.addSeparator();
		
		JLabel lbl = new JLabel("Filter:");
		toolBar.add(lbl);
		txtFilter = new JTextField();
		toolBar.add(txtFilter);
		txtFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				itemModel.filter(txtFilter.getText());
			}
		});
		
		btnSelectAll = new JButton("Select All");
		toolBar.add(btnSelectAll);
		btnSelectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleSelectAll(true);
			}
		});

		btnUnselectAll = new JButton("Unselect All");
		toolBar.add(btnUnselectAll);
		btnUnselectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleSelectAll(false);
			}
		});
		
		itemModel = new CustomItemModel(null);
		itemsTable = new JTable(itemModel);

		scrl = new JScrollPane(itemsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrl.setBounds(0, 0, 1400, 700);
		this.add(scrl, BorderLayout.CENTER);

		itemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		itemsTable.getColumn("selected").setPreferredWidth(60);
		itemsTable.getColumn("description").setPreferredWidth(500);
		itemsTable.getColumn("solution").setPreferredWidth(500);
		itemsTable.getColumn("info").setPreferredWidth(500);
		itemsTable.getColumn("see_also").setPreferredWidth(250);
		itemsTable.getColumn("type").setPreferredWidth(150);
		itemsTable.getColumn("value_type").setPreferredWidth(150);
		itemsTable.getColumn("value_data").setPreferredWidth(150);
		itemsTable.getColumn("right_type").setPreferredWidth(150);
		itemsTable.getColumn("reg_item").setPreferredWidth(150);
		itemsTable.getColumn("reg_key").setPreferredWidth(500);
		itemsTable.getColumn("reference").setPreferredWidth(500);
	}
	
	private void loadFromFile() {
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String fName = fc.getSelectedFile().getAbsolutePath();
			try {
				JSONObject json = new AuditParser().parseFile(fName);
				currentPolicy = new Policy(json);
				itemModel.setPolicy(currentPolicy);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void loadFromJsonFile() {
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String fName = fc.getSelectedFile().getAbsolutePath();
			try {
				byte[] bytes = Files.readAllBytes(Paths.get(fName));
				String content = new String(bytes);
				JSONObject  json = new JSONObject(content);
				currentPolicy = new Policy(json);
				itemModel.setPolicy(currentPolicy);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void saveToFile() {
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			String fName = fc.getSelectedFile().getAbsolutePath();
			List<String> contents = new ArrayList<String>();
			//TODO: populate contents
			try {
				Files.write(Paths.get(fName), contents);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void toggleSelectAll(boolean select) {
		for(int i = 0; i < itemModel.getRowCount(); i++) {
			itemModel.setValueAt(select, i, 0);
		}
		itemsTable.repaint();
	}
	
	public static void main(String[] args) {
		new Main().setVisible(true);
	}

}
