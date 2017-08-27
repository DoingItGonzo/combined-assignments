package com.cooksys.ftd.assignments.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client extends Utils {
	

    public static void main(String[] args) throws JAXBException, UnknownHostException, IOException {
    	
    	JAXBContext jaxb = createJAXBContext();
    	
    	Config config = loadConfig("C:/Users/ftd-16/workspace/combined-assignments/4-socket-io-serialization/config/config.xml", jaxb);
    	RemoteConfig remCon = config.getRemote();
    	
    	Socket socket = new Socket(remCon.getHost(), remCon.getPort());
    	InputStream in = socket.getInputStream();
    	Unmarshaller unmarshaller = jaxb.createUnmarshaller();
    	Student student = (Student)unmarshaller.unmarshal(in);
    	
    	System.out.println(student.getFirstName());
    	System.out.println(student.getLastName());
    	System.out.println(student.getFavoriteIDE());
    	System.out.println(student.getFavoriteLanguage());
    	System.out.println(student.getFavoriteParadigm());
		socket.close();
    }
    
}
