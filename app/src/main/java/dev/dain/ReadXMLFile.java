package dev.dain;

import android.provider.DocumentsContract;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by davidha on 2015. 2. 28..
 */
public class ReadXMLFile {
    String xml;
    String mText;

    public ReadXMLFile(String Text) {
    


        try

        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            Element order = doc.getDocumentElement();
            NodeList items = order.getElementsByTagName("item");
            String Result = "";
            for(int i=0; i<items.getLength(); i++)
            {
                Node item=items.item(i);
                Node text=item.getFirstChild();
                String IteName = text.getNodeValue();
                Result+= IteName+":";

                NamedNodeMap Attrs = item.getAttributes();
                for(int j=0; j<Attrs.getLength(); j++)
                {
                    Node attr = Attrs.item(j);
                    Result += (attr.getNodeName() + "=" + attr.getNodeValue()+ " ");
                }
                Result+="\n";
            }

        } catch (
                Exception e
                )

        {

        }
    }

}
