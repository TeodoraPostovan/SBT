package edu.dubenco.alina.sbt;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

public class Backup {
	private Date time;
	private List<BackupItem> items;
	private File bkpFile;
	
	public Backup() {
		time = new Date();
		items = new ArrayList<>();

		File bkpDir = new File("backups");
		if(!bkpDir.exists()) {
			boolean success = bkpDir.mkdirs();
			if(!success) {
				throw new RuntimeException("Failed to create backups directory: " + bkpDir.getAbsolutePath());
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		bkpFile = new File(bkpDir, "bkp_" + df.format(time) + ".json");
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<BackupItem> getItems() {
		return items;
	}

	public void setItems(List<BackupItem> items) {
		this.items = items;
	}
	
	public synchronized void addItem(BackupItem item) {
		items.add(item);
		JSONObject json = new JSONObject(this);
		try {
			Files.write(bkpFile.toPath(), json.toString(2).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
