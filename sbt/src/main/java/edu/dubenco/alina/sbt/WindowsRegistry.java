package edu.dubenco.alina.sbt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsRegistry
{
    public static void importSilently(String regFilePath) throws IOException,
            InterruptedException
    {
        if (!new File(regFilePath).exists())
        {
            throw new FileNotFoundException();
        }

        Process importer = Runtime.getRuntime().exec("reg import " + regFilePath);

        importer.waitFor();
    }
    
    public static boolean exists(String keyPath, String keyName) throws IOException, InterruptedException {
    	StringBuilder sb = new StringBuilder();
    	sb.append("reg query \"").append(keyPath).append("\"");
    	if(keyName != null && !keyName.trim().isEmpty()) {
    		sb.append(" /v \"").append(keyName).append("\"");
    	}
    	
    	Process proc = Runtime.getRuntime().exec(sb.toString());
        int retVal = proc.waitFor();
        if(retVal == 0) {
        	return true;
        } else {
            String readLine;
            StringBuffer errBuffer = new StringBuffer();
        	try(BufferedReader errRdr = new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
                while ((readLine = errRdr.readLine()) != null)
                {
                    errBuffer.append(readLine);
                }
        	}
        	System.out.println(errBuffer.toString());
        	return false;
        }
    }

    public static void overwriteValue(String keyPath, String keyName, String keyType,
            String keyValue) throws IOException, InterruptedException
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("reg add \"").append(keyPath).append("\"");
    	if(keyName != null) {
    		sb.append(" /v \"").append(keyName).append("\"");
    		if(keyValue != null) {
    			sb.append(" /d \"").append(keyValue).append("\"");
    			if(keyType != null) {
    				sb.append(" /t ").append(keyType);
    			}
    		}
    	}
    	sb.append(" /f");
        
    	Process proc = Runtime.getRuntime().exec(sb.toString());

        int retVal = proc.waitFor();
        if(retVal != 0) {
            String readLine;
            StringBuffer errBuffer = new StringBuffer();
        	try(BufferedReader errRdr = new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
                while ((readLine = errRdr.readLine()) != null)
                {
                    errBuffer.append(readLine);
                }
        	}
        	throw new IOException(errBuffer.toString());
        }
    }

    public static String getValue(String keyPath, String keyName, String keyType)
            throws IOException, InterruptedException
    {
        Process keyReader = Runtime.getRuntime().exec(
                "reg query \"" + keyPath + "\" /v \"" + keyName + "\"");

        BufferedReader outputReader;
        String readLine;
        StringBuffer outputBuffer = new StringBuffer();

        outputReader = new BufferedReader(new InputStreamReader(
                keyReader.getInputStream()));

        while ((readLine = outputReader.readLine()) != null)
        {
            outputBuffer.append(readLine);
        }

        String[] outputComponents = outputBuffer.toString().split("    ");

        keyReader.waitFor();

        return formatValue(outputComponents[outputComponents.length - 1], keyType);
    }
    
    public static void delete(String keyPath, String keyName) throws IOException, InterruptedException {
    	StringBuilder sb = new StringBuilder();
    	sb.append("reg delete \"").append(keyPath).append("\"");
    	if(keyName != null && !keyName.trim().isEmpty()) {
    		sb.append(" /v \"").append(keyName).append("\"");
    	}
    	sb.append(" /f");
    	
    	Process proc = Runtime.getRuntime().exec(sb.toString());
        int retVal = proc.waitFor();
        if(retVal != 0) {
            String readLine;
            StringBuffer errBuffer = new StringBuffer();
        	try(BufferedReader errRdr = new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
                while ((readLine = errRdr.readLine()) != null)
                {
                    errBuffer.append(readLine);
                }
        	}
        	throw new IOException(errBuffer.toString());
        }
    }
    
    public static String formatValue(String value, String type) {
    	if(value == null || value.isEmpty()) {
    		return "";
    	}
    	switch(type) {
    	case "REG_SZ":
    		return value;
    	case "REG_DWORD" :
    		try {
	    		Integer n = Integer.decode(value);
	    		return n.toString(); // Integer.valueOf(n.intValueExact()).toString();
    		}catch(NumberFormatException ex) {
    			return "NaN";
    		}
    	}
    	return value;
    }
}