package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {
    /**
     * @return a {@link JAXBContext} initialized with the classes in the
     * com.cooksys.socket.assignment.model package
     */
    public static JAXBContext createJAXBContext() {
    	
    	JAXBContext jaxb = null;
    	
		try {
			jaxb = JAXBContext.newInstance(Config.class, RemoteConfig.class, LocalConfig.class, Student.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	return jaxb;
    }


    public static Config loadConfig(String configFilePath, JAXBContext jaxb) throws JAXBException {
    	
    	File f = new File(configFilePath);
    	Unmarshaller unmarshaller = jaxb.createUnmarshaller();
    	Config config = (Config)unmarshaller.unmarshal(f);
    	
    	return config;

    }
}
