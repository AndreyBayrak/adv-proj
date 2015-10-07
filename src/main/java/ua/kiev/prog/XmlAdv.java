package ua.kiev.prog;


import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class XmlAdv extends DefaultHandler
{
    private Advertisement adv;
    private List<Advertisement> advList;
    private String nodeValue;


    public XmlAdv(List<Advertisement> advList) { this.advList = advList; }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts)
    {
        if ("advertisement".equals(qName))
            adv = new Advertisement();
    }


    @Override
    public void endElement(String uri, String localName, String qName)
    {
        if ("advertisement".equals(qName))
            advList.add(adv);

        else if ("name".equals(qName))
            adv.setName(nodeValue);

        else if ("shortDesc".equals(qName))
            adv.setShortDesc(nodeValue);

        else if ("longDesc".equals(qName))
            adv.setLongDesc(nodeValue);

        else if ("phone".equals(qName))
            adv.setPhone(nodeValue);

        else if ("price".equals(qName))
            adv.setPrice(Double.parseDouble(nodeValue));

        else if ("photo".equals(qName)) {
            try {
                adv.setPhoto(new Photo(nodeValue, Files.readAllBytes(new File(nodeValue).toPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void characters(char[] ch, int start, int length)
    {
        nodeValue = new String(ch, start, length);
    }
}
