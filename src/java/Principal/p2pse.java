/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Principal;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
/**
 *
 * @author aluno
 */
public class p2pse {
    
    public static void main(String[] args) throws Exception{
        DocumentBuilderFactory factory;
        factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        
        DocumentBuilder  builder;
        builder = factory.newDocumentBuilder();
        
        
        builder.parse("getFileResponse.xml");
    }
    
}
