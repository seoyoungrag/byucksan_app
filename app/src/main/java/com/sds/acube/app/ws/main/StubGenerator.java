package com.sds.acube.app.ws.main;

import org.apache.axis2.util.CommandLineOptionParser;
import org.apache.axis2.wsdl.codegen.CodeGenerationEngine;
import org.apache.axis2.wsdl.codegen.CodeGenerationException;


/**
 * Class Name : StubGenerator.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 6. 1. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 6. 1.
 * @version 1.0
 * @see com.sds.acube.app.ws.main.StubGenerator.java
 */

public class StubGenerator {

    public static void generateStub(String wsdlUrl) throws CodeGenerationException {
	String[] args = new String[] { "-uri", wsdlUrl, "-p", "com.sds.acube.app.ws.client.document", "-or" };
	CommandLineOptionParser commandLineOptionParser = new CommandLineOptionParser(args);

	new CodeGenerationEngine(commandLineOptionParser).generate();
    }


    public static void main(String[] args) throws CodeGenerationException {
	String address = "http://10.1.20.123/rat";
	// String address = "http://10.5.115.124:8888/rat";

	StubGenerator.generateStub(address + "/services/DocumentService?wsdl");
	StubGenerator.generateStub(address + "/services/FolderService?wsdl");

    }
}
