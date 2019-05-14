package com.qxiangy.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author  YQX
 * @time 2019年3月18日14:57:42
 * @content DOM解析XML文件
		<configure>
		  <baseurl>1234</baseurl> 
		</configure>
 * */
public class DomConfig
{
	
	private static DomConfig instance = null;
	
	private DomConfig(){}
	
	public static DomConfig getInstall(){
		
		if(null==instance){
			
			instance = new DomConfig();
		}
		return instance;
	}
	
	// strXmlFile 文件路径   xmlContent 节点名
	public static String getXml(String strXmlFile,String xmlContent)
	{
		// 从xml中初始化
		String xmlFile = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try
		{
			db = factory.newDocumentBuilder();
			try
			{
				Document doc = db.parse(new File(strXmlFile));
				Element ele = doc.getDocumentElement();
				NodeList list = null;
				
				if (ele != null)
				{
					list = ele.getChildNodes();
					if (list != null)
					{
						for (int i = 0; i < list.getLength(); i++)
						{
							Node node = list.item(i);
							if (node.getNodeType() != Node.ELEMENT_NODE) continue;

							String nodeName = node.getNodeName();
							if (nodeName.equals(xmlContent)) 
								
								xmlFile = node.getTextContent();
						}
					}

				}
			}
			catch (SAXException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// e.printStackTrace();
			}
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}

		return xmlFile;
	}
}

