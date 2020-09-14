package edu.dubenco.alina.stb;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.JSONObject;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JFileChooser fc = new JFileChooser();
	private JButton btnLoad;
	private JButton btnSave;
	private JPanel pnlData;
	private JScrollPane scrl;
	private JTextArea txtArea;
	
	private JSONObject currentPolicy;
	

	public Main() {
		this.setTitle("Security Benchmarking Tool");
		this.setSize(1450, 815);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		fc.setCurrentDirectory(Paths.get("").toFile().getAbsoluteFile());
		
		btnLoad = new JButton("Load from audit file");
		btnLoad.setBounds(10, 10, 230, 25);
		this.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFromFile();
			}
		});
		
		btnLoad = new JButton("Load from JSON file");
		btnLoad.setBounds(250, 10, 230, 25);
		this.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFromJsonFile();
			}
		});
		
		btnSave = new JButton("Save to JSON file");
		btnSave.setBounds(490, 10, 230, 25);
		this.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		
		pnlData = new JPanel();
		pnlData.setBounds(10, 65, 1400, 700);
		pnlData.setBackground(Color.BLACK);
		pnlData.setLayout(null);
		this.add(pnlData);

		txtArea = new JTextArea();

		scrl = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrl.setBounds(0, 0, 1400, 700);
		pnlData.add(scrl);


		
		
	}
	
	private void loadFromFile() {
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String fName = fc.getSelectedFile().getAbsolutePath();
			try {
				currentPolicy = new AuditParser().parseFile(fName);
				txtArea.setText(currentPolicy.toString(4));
				txtArea.setCaretPosition(0);
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
				currentPolicy = new JSONObject(content);
				txtArea.setText(currentPolicy.toString(4));
				txtArea.setCaretPosition(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void saveToFile() {
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			String fName = fc.getSelectedFile().getAbsolutePath();
			List<String> contents = new ArrayList<String>();
			contents.add(txtArea.getText());
			try {
				Files.write(Paths.get(fName), contents);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Main().setVisible(true);
	}

}
