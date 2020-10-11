package edu.dubenco.alina.sbt;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Executer {
	private Backup backup;
	
	private List<String> IMPLEMENTED_ITEMS = Arrays.asList(
			"Access data sources across domains - Internet Zone", 
			"Access data sources across domains - Restricted Sites Zone",
			"Allow Basic authentication - Client - AllowBasic",
			"Allow Basic authentication - Service - AllowBasic",
			"Allow META REFRESH",
			"Interactive logon: Smart card removal behavior"
			);
	
	public Executer() {
		backup = new Backup();
	}
	
	public ExecutionResult check(CustomItem item) {
		switch(item.getType().toUpperCase()) {
		case "REGISTRY_SETTING":
			return checkRegistryValue(item);
		case "USER_RIGHTS_POLICY":
			//TODO: https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/secedit
		case "LOCKOUT_POLICY":
		case "REG_CHECK":
		default:
			return new ExecutionResult(ExecutionStatus.UNKNOWN, null);
		}
	}

	public ExecutionResult enforce(CustomItem item) {
		if(!IMPLEMENTED_ITEMS.contains(item.getDescription())) {
			return new ExecutionResult(ExecutionStatus.UNKNOWN, "Not implemented");
		}
		
		switch(item.getType().toUpperCase()) {
		case "REGISTRY_SETTING":
			return updateRegistryValue(item);
		case "USER_RIGHTS_POLICY":
			//TODO: https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/secedit
		case "LOCKOUT_POLICY":
		case "REG_CHECK":
		default:
			return new ExecutionResult(ExecutionStatus.UNKNOWN, null);
		}
	}
	
	public void restore(BackupItem item) {
		switch(item.getType()) {
		case Registry:
			restoreRegistry(item);
			break;
		case UserRights:
			break;
		}
	}
	
	private ExecutionResult checkRegistryValue(CustomItem item) {
		try {
			String value = WindowsRegistry.getValue(item.getRegKey(), item.getRegItem(), polycyValueTypeToRegType(item.getValueType()));
			ExecutionStatus status;
			if(Objects.equals(item.getValueData(), value)) {
				status = ExecutionStatus.PASSED;
			} else {
				status = ExecutionStatus.FAILED;
			}
			return new ExecutionResult(status, value);
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecutionResult(ExecutionStatus.ERROR, e.getMessage());
		}
	}
	
	private ExecutionResult updateRegistryValue(CustomItem item) {
		try {
			String regKey = item.getRegKey();
			String regItem = item.getRegItem();
			String regValue = null;
			String regType = polycyValueTypeToRegType(item.getValueType());
			boolean exists = WindowsRegistry.exists(regKey, regItem);
			if(exists) {
				regValue = WindowsRegistry.getValue(item.getRegKey(), item.getRegItem(), regType);
			} else {
				exists = WindowsRegistry.exists(regKey, null);
				while(!exists) {
					regKey = regKey.substring(0, regKey.lastIndexOf('\\'));
					exists = WindowsRegistry.exists(regKey, null);
				}
			}
			BackupItem bkpItem = new BackupItem(BackupItem.Type.Registry, regKey, regItem, regValue, regType, item.getRegKey());
			getBackup().addItem(bkpItem);
			
			WindowsRegistry.overwriteValue(item.getRegKey(), item.getRegItem(), regType,  item.getValueData());
			
			return new ExecutionResult(ExecutionStatus.PASSED, item.getValueData());
		
		} catch (Exception e) {
			e.printStackTrace();
			return new ExecutionResult(ExecutionStatus.ERROR, e.getMessage());
		}
	}
	
	private void restoreRegistry(BackupItem item) {
		String regKey = item.getKey();
		String newKey = item.getNewKey();
		String regItem = item.getItem();
		String regValue = item.getValue();
		String regType = item.getValueType();
		try {
			if(regValue != null && !regValue.isEmpty()) {
				WindowsRegistry.overwriteValue(regKey, regItem, regType, regValue);
			} else {
				boolean exists = WindowsRegistry.exists(regKey, regItem);
				if(exists) {
					WindowsRegistry.delete(regKey, regItem);
				}
			}
			if(newKey.length() > regKey.length()) {
				String subKey = newKey;
				while(subKey.length() > regKey.length()) {
					boolean exists = WindowsRegistry.exists(subKey, null);
					if(exists) {
						WindowsRegistry.delete(subKey, null);
					} else {
						break;
					}
					subKey = subKey.substring(0, subKey.lastIndexOf('\\'));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String polycyValueTypeToRegType(String dataType) {
		switch(dataType) {
		case "POLICY_DWORD": 
			return "REG_DWORD";
		case "POLICY_TEXT":
			return "REG_SZ";
		default:
			return "REG_SZ";
		}
	}

	public synchronized Backup getBackup() {
		return backup;
	}
	
}
