package wtp;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlUtil {
	public String convertToXml(Object source, Class... type ) {
		String result;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext carContext = JAXBContext.newInstance(type);
            Marshaller carMarshaller = carContext.createMarshaller();
            carMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            carMarshaller.marshal(source, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
	}
	
	public UserWS loadXMLFromString(String xml) throws Exception
	{
//	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	    DocumentBuilder builder = factory.newDocumentBuilder();
//	    InputSource is = new InputSource(new StringReader(xml));
//	    return builder.parse(is);
		return JAXB.unmarshal(new StringReader(xml), UserWS.class);
	}
}
