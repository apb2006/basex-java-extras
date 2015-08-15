package org.apb.modules;

/*
 * XSD utilities
 * 
 */
import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Xsd {
	static SchemaFactory factory = SchemaFactory
			.newInstance("http://www.w3.org/2001/XMLSchema");

	public static String validate(String xmlPath, String schemaPath) {
		try {

			Schema schema = factory.newSchema(new File(schemaPath));

			Validator validator = schema.newValidator();

			Source source = new StreamSource(new File(xmlPath));

			validator.validate(source);

			return "";

		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
}