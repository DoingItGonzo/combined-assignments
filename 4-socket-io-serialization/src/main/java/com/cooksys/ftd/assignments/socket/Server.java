package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     * 
     * @throws JAXBException 
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException {
    	
    	File f = new File(studentFilePath);
    	Unmarshaller unmarshaller = jaxb.createUnmarshaller();
    	Student student = (Student)unmarshaller.unmarshal(f);
    	
    	return student;
    	
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     * @throws JAXBException 
     * @throws IOException 
     */
    public static void main(String[] args) throws Exception {
    	
    	JAXBContext jaxb = createJAXBContext();
    	
    	Config config = loadConfig("C:/Users/ftd-16/workspace/combined-assignments/4-socket-io-serialization/config/config.xml", jaxb);
    	LocalConfig localConfig = config.getLocal();
        int port = localConfig.getPort();
        System.out.println(port);
        
        ServerSocket serveSocket = new ServerSocket(port);
        System.out.println("Server waiting");
        
        Socket client = serveSocket.accept();
        System.out.println("client connected!");

        
        Student student = loadStudent("C:/Users/ftd-16/workspace/combined-assignments/4-socket-io-serialization/config/student.xml", jaxb);
        
    	OutputStream out = client.getOutputStream();

        Marshaller marshaller = jaxb.createMarshaller();
        marshaller.marshal(student, out);
        
        serveSocket.close();
        client.close();
        
        
//        try (ServerSocket serverSocket = new ServerSocket()) {
//        Socket client = serverSocket.accept();
//        BufferedReader buffRead = new BufferedReader(new InputStreamReader(client.getInputStream()));
//        } catch (Exception e) {
//        	e.printStackTrace;
//        }
//    	InputStream incoming = socket.getInputStream(); 
//    	OutputStream out = socket.getOutputStream();
}
}
