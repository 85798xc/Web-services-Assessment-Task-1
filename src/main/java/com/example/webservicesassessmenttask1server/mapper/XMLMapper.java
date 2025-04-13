package com.example.webservicesassessmenttask1server.mapper;

import com.example.webservicesassessmenttask1server.entity.Student;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.StringWriter;

@Component
public class XMLMapper {
    public String transformToXML(Student student) throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(student, writer);
        return writer.toString();
    }

    public Student transformToPOJO(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Student) unmarshaller.unmarshal(new StringReader(xml));
    }
}
