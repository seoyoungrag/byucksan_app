package com.sds.acube.app.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.core.convert.ConversionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.anyframe.util.StringUtil;


/**
 * Class Name : Transform.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 24. <br>
 * 수 정 자 : Timothy <br>
 * 수정내용 : <br>
 * 
 * @author Timothy
 * @since 2011. 3. 24.
 * @version 1.0
 * @see com.sds.acube.app.common.util.Transform.java
 */
public class Transform {

    protected static Log logger = LogFactory.getLog(Transform.class);

    private static final String BYINT = "ByInt";
    private static final String SET = "set";
    private static final String JAVABYTE = "[B";
    private static final String JAVAINT = "int";
    private static final String JAVABOOLEAN = "boolean";
    private static final String JAVASTRING = "java.lang.String";
    private static final String JAVAINTEGER = "java.lang.Integer";
    private static final String JAVAOBJECT = "java.lang.Object";
    private static final String JAVALIST = "java.util.List";
    private static final String JAVAARRAYLIST = "java.util.ArrayList";
    private static final String NEWLINE = "\r\n";


    // transfrom From Request Object To Domain Model Object
    public static Object transformToDmo(/* DMO name */String className, /*
					                                 * Source
					                                 * object
					                                 */Object object) throws SecurityException,
	    IllegalArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
	    InvocationTargetException, JSONException {

	if (object instanceof HttpServletRequest) {
	    return transformFromRequest(className, (HttpServletRequest) object);
	} else if (object instanceof JSONObject) {
	    return transfromFromJson(className, (JSONObject) object);
	} else {
	    return null;
	}
    }


    @SuppressWarnings("unchecked")
    private static Object transformFromRequest(/* DMO name */String className, /*
					                                        * HttpServletRequest
					                                        * object
					                                        */HttpServletRequest request)
	    throws InstantiationException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException {

	Class target = Class.forName(className);
	Method[] methods = target.getDeclaredMethods();
	// Field[] fields = target.getDeclaredFields();
	Object instance = target.newInstance();

	for (int i = 0; i < methods.length; i++) {
	    if (methods[i].getName().indexOf(SET) == 0 && !methods[i].getName().endsWith(BYINT)) {
		String methodName = methods[i].getName().substring(3);
		methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		Class[] param = methods[i].getParameterTypes();

		Method method = target.getMethod(methods[i].getName(), param);
		Object[] params = null;
		if (param[0].toString().indexOf(JAVASTRING) != -1) {
		    params = new Object[] { StringUtils.trimToEmpty(request.getParameter(methodName)) };
		} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
		    if ("".equals(StringUtils.trimToEmpty(request.getParameter(methodName))))
			continue;
		    params = new Object[] { new Integer(StringUtils.trimToEmpty(request.getParameter(methodName))) };
		} else {
		    continue;
		}
		method.invoke(instance, params);
	    }
	}

	return instance;
    }


    @SuppressWarnings("unchecked")
    private static Object transfromFromJson(/* DMO name */String className, /*
					                                     * JSONObject
					                                     * object
					                                     */JSONObject json) throws InstantiationException,
	    ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
	    InvocationTargetException, JSONException {

	Class target = Class.forName(className);
	Method[] methods = target.getDeclaredMethods();
	Object instance = target.newInstance();

	for (int i = 0; i < methods.length; i++) {
	    if (methods[i].getName().indexOf(SET) == 0 && !methods[i].getName().endsWith(BYINT)) {
		String methodName = methods[i].getName().substring(3);
		methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		Class[] param = methods[i].getParameterTypes();

		Method method = target.getMethod(methods[i].getName(), param);
		Object[] params = null;
		if (param[0].toString().indexOf(JAVASTRING) != -1) {
		    params = new Object[] { StringUtils.trimToEmpty((String) json.get(methodName)) };
		} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
		    if ("".equals(StringUtils.trimToEmpty((String) json.get(methodName))))
			continue;
		    params = new Object[] { new Integer(StringUtils.trimToEmpty((String) json.get(methodName))) };
		} else {
		    continue;
		}
		method.invoke(instance, params);
	    }
	}

	return instance;
    }


    // transfrom From Request Object To Domain Model Object List
    public static Object transformList(/* DMO name */String className, /*
				                                        * Request
				                                        * object
				                                        */Object object, /*
						                                          * List
						                                          * structure
						                                          */String[] order) throws InstantiationException,
	    ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
	    InvocationTargetException {
	String[] delimiter = { "\u0002", "\u0004" };
	return transformList(className, object, order, delimiter);
    }


    // transfrom From Request Object To Domain Model Object List
    @SuppressWarnings("unchecked")
    public static Object transformList(/* DMO name */String className, /*
				                                        * Request
				                                        * object
				                                        */Object object, /*
						                                          * List
						                                          * structure
						                                          */String[] order, String[] delimiter)
	    throws InstantiationException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException,
	    IllegalAccessException, InvocationTargetException {

	if (delimiter.length == 1)
	    delimiter[1] = "\u0004";

	ArrayList objectList = new ArrayList();
	String dmoName = className;
	if (className.lastIndexOf(".") != -1)
	    dmoName = className.substring(className.lastIndexOf(".") + 1).toLowerCase();

	Class target = Class.forName(className);
	Method[] methods = target.getDeclaredMethods();
	@SuppressWarnings("unused")
	Field[] fields = target.getDeclaredFields();

	HttpServletRequest request = (HttpServletRequest) object;
	String data = StringUtils.trimToEmpty(request.getParameter(dmoName));
	String[] fieldList = data.split(delimiter[1]);

	for (int loop = 0; loop < fieldList.length; loop++) {
	    Object instance = target.newInstance();
	    String[] field = fieldList[loop].split(delimiter[0]);

	    for (int pos = 0; pos < order.length; pos++) {
		for (int i = 0; i < methods.length; i++) {
		    String methodName = methods[i].getName().substring(3);
		    methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		    if (order[pos].equals(methodName)) {
			if (methods[i].getName().indexOf(SET) == 0 && !methods[i].getName().endsWith(BYINT)) {
			    Class[] param = methods[i].getParameterTypes();

			    Method method = target.getMethod(methods[i].getName(), param);
			    Object[] params = null;
			    if (param[0].toString().indexOf(JAVASTRING) != -1) {
				params = new Object[] { field[pos] };
			    } else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
				params = new Object[] { new Integer(field[pos]) };
			    } else if (param[0].toString().indexOf(JAVABYTE) != -1) {
				params = new Object[] { field[pos] };
			    } else if (param[0].toString().indexOf(JAVAINT) != -1) {
				params = new Object[] { new Integer(field[pos]) };
			    } else if (param[0].toString().indexOf(JAVABOOLEAN) != -1) {
				params = new Object[] { new Boolean(field[pos]) };
			    } else if (param[0].toString().indexOf(JAVAOBJECT) != -1) {
				params = new Object[] { field[pos] };
			    } else {
				continue;
			    }

			    method.invoke(instance, params);
			}
		    }
		}
	    }
	    objectList.add(instance);
	}

	return objectList;
    }


    // transfrom From Domain Model Object To XML Type String
    @SuppressWarnings("unchecked")
    public static Object transformToXml(/* DMO object */Object object) throws InstantiationException, ClassNotFoundException,
	    SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

	StringBuffer xml = new StringBuffer();
	if (object != null) {

	    logger.debug("# object class name : " + object.getClass().getName());
	    String className = object.getClass().getName().toLowerCase();
	    if (className.lastIndexOf(".") != -1)
		className = className.substring(className.lastIndexOf(".") + 1);

	    xml.append("<" + className + ">" + NEWLINE);
	    if (object instanceof List || object instanceof ArrayList) {
		int objectsize = ((ArrayList) object).size();
		for (int loop = 0; loop < objectsize; loop++) {
		    xml.append(transformToXml(((ArrayList) object).get(loop)));
		}
	    } else {
		Method[] methods = object.getClass().getDeclaredMethods();

		for (int i = 0; i < methods.length; i++) {
		    if (methods[i].getName().indexOf(SET) == 0 && !methods[i].getName().endsWith(BYINT)) {

			String methodName = methods[i].getName().substring(3);
			Class[] param = methods[i].getParameterTypes();
			Method method = object.getClass().getMethod("get" + methodName, null);
			@SuppressWarnings("unused")
			Object result = method.invoke(object, null);
			if (param[0].toString().indexOf(JAVAARRAYLIST) != -1 || param[0].toString().indexOf(JAVALIST) != -1) {
			    ArrayList list = (ArrayList) method.invoke(object, null);
			    if (list != null) {
				xml.append("<" + methodName.toLowerCase() + ">" + NEWLINE);
				for (int loop = 0; loop < list.size(); loop++) {
				    xml.append(transformToXml(list.get(loop)));
				}
				xml.append("</" + methodName.toLowerCase() + ">" + NEWLINE);
			    }
			} else if (param[0].toString().indexOf(JAVASTRING) != -1) {
			    xml.append("<" + methodName.toLowerCase() + ">");
			    if (method.invoke(object, null) != null) {
				xml.append("<![CDATA[" + method.invoke(object, null).toString() + "]]>");
			    }
			    xml.append("</" + methodName.toLowerCase() + ">" + NEWLINE);
			} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
			    xml.append("<" + methodName.toLowerCase() + ">");
			    if (method.invoke(object, null) != null) {
				xml.append(method.invoke(object, null).toString());
			    }
			    xml.append("</" + methodName.toLowerCase() + ">" + NEWLINE);
			} else if (param[0].toString().indexOf(JAVABYTE) != -1) {
			    xml.append("<" + methodName.toLowerCase() + ">");
			    if (method.invoke(object, null) != null) {
				xml.append("<![CDATA[" + method.invoke(object, null).toString() + "]]>");
			    }
			    xml.append("</" + methodName.toLowerCase() + ">" + NEWLINE);
			} else if (param[0].toString().indexOf(JAVAINT) != -1) {
			    xml.append("<" + methodName.toLowerCase() + ">");
			    if (method.invoke(object, null) != null) {
				xml.append(method.invoke(object, null).toString());
			    }
			    xml.append("</" + methodName.toLowerCase() + ">" + NEWLINE);
			} else if (param[0].toString().indexOf(JAVABOOLEAN) != -1) {
			    xml.append("<" + methodName.toLowerCase() + ">");
			    if (method.invoke(object, null) != null) {
				xml.append(method.invoke(object, null).toString());
			    }
			    xml.append("</" + methodName.toLowerCase() + ">" + NEWLINE);
			} else if (param[0].toString().indexOf(JAVAOBJECT) != -1) {
			    xml.append("<" + methodName.toLowerCase() + ">");
			    if (method.invoke(object, null) != null) {
				xml.append("<![CDATA[" + method.invoke(object, null).toString() + "]]>");
			    }
			    xml.append("</" + methodName.toLowerCase() + ">" + NEWLINE);
			}
		    }
		}
	    }

	    xml.append("</" + className + ">" + NEWLINE);
	} else {
	    xml.append("<object>object is null</object>");
	}

	return xml.toString();
    }


    // transfrom From XML Node(Element) XML Type String
    public static String transformToXml(/* Node object */Object object, String indentation) {
	Node node = null;
	if (object instanceof Node)
	    node = (Node) object;
	else if (object instanceof Element)
	    node = (Element) object;
	else
	    return "";

	node.normalize();
	StringBuffer xml = new StringBuffer();
	String childIndentation = indentation + "  ";
	short nNodeType = node.getNodeType();

	if (nNodeType == 1) {
	    xml.append(indentation);
	    xml.append('<');
	    xml.append(node.getNodeName());
	    NamedNodeMap attributeMap = node.getAttributes();
	    if (attributeMap != null) {
		int nAttributeCount = attributeMap.getLength();
		for (int j = 0; j < nAttributeCount; j++) {
		    Node attribute = attributeMap.item(j);
		    xml.append(" " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
		}
	    }
	    NodeList childList = node.getChildNodes();
	    int nChildCount = childList.getLength();
	    if (nChildCount <= 0) {
		xml.append("/>");
	    } else {
		xml.append('>');
		if (childList.item(0).getNodeType() == 1)
		    xml.append('\n');
		else if (childList.item(0).getNodeType() == 3) {
		    String strText = childList.item(0).getNodeValue().trim();
		    if (nChildCount > 1 && childList.item(1).getNodeType() == 1 && strText.equals(""))
			xml.append('\n');
		}
		for (int i = 0; i < nChildCount; i++) {
		    Node childNode = childList.item(i);
		    short nChildNodeType = childNode.getNodeType();
		    if (nChildNodeType == 1) {
			xml.append(transformToXml(childNode, childIndentation));
			xml.append('\n');
		    } else if (nChildNodeType == 3) {
			String strText = childNode.getNodeValue().trim();
			if (childNode.getPreviousSibling() == null && childNode.getNextSibling() == null && !strText.equals(""))
			    // strXML = strXML + "<![CDATA[" + strText + "]]>";
			    xml.append(strText);
		    } else if (nChildNodeType == 4) {
			String strText = childNode.getNodeValue().trim();
			if (!strText.equals(""))
			    xml.append("<![CDATA[" + strText + "]]>");
			// strXML += strText;
		    }
		}

		if (childList.item(0).getNodeType() == 1)
		    xml.append(indentation);
		else if (childList.item(0).getNodeType() == 3) {
		    String strText = childList.item(0).getNodeValue().trim();
		    if (nChildCount > 1 && childList.item(1).getNodeType() == 1 && strText.equals(""))
			xml.append(indentation);
		}
		xml.append("</" + node.getNodeName() + ">");
	    }
	}

	return xml.toString();
    }


    // transfrom From Domain Model Object To XML Type String
    @SuppressWarnings("unchecked")
    public static Object transformToJson(/* DMO object */Object object) throws SecurityException, IllegalArgumentException, JSONException,
	    InstantiationException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

	JSONObject json = new JSONObject();

	Method[] methods = object.getClass().getDeclaredMethods();

	for (int i = 0; i < methods.length; i++) {
	    if (methods[i].getName().indexOf(SET) == 0 && !methods[i].getName().endsWith(BYINT)) {

		String methodName = methods[i].getName().substring(3);
		Class[] param = methods[i].getParameterTypes();
		Method method = object.getClass().getMethod("get" + methodName, null);
		if (param[0].toString().indexOf(JAVAARRAYLIST) != -1) {
		    ArrayList list = (ArrayList) method.invoke(object, null);
		    if (list != null) {
			JSONArray jsonarray = new JSONArray();
			int listsize = list.size();
			for (int loop = 0; loop < listsize; loop++) {
			    jsonarray.put(transformToJson(list.get(loop)));
			}
			json.put(methodName.toLowerCase(), jsonarray);
		    }
		} else if (param[0].toString().indexOf(JAVASTRING) != -1) {
		    if (method.invoke(object, null) != null)
			json.put(methodName.toLowerCase(), method.invoke(object, null).toString());
		} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
		    if (method.invoke(object, null) != null)
			json.put(methodName.toLowerCase(), method.invoke(object, null).toString());
		} else if (param[0].toString().indexOf(JAVABYTE) != -1) {
		    if (method.invoke(object, null) != null)
			json.put(methodName.toLowerCase(), method.invoke(object, null).toString());
		} else if (param[0].toString().indexOf(JAVAINT) != -1) {
		    if (method.invoke(object, null) != null)
			json.put(methodName.toLowerCase(), method.invoke(object, null).toString());
		} else if (param[0].toString().indexOf(JAVABOOLEAN) != -1) {
		    if (method.invoke(object, null) != null)
			json.put(methodName.toLowerCase(), method.invoke(object, null).toString());
		} else if (param[0].toString().indexOf(JAVAOBJECT) != -1) {
		    if (method.invoke(object, null) != null)
			json.put(methodName.toLowerCase(), method.invoke(object, null).toString());
		}
	    }
	}

	return json;
    }


    /**
     * 오브젝트의 값을 다른 오브젝트에 복사한다.
     * 
     * @param className
     *            생성할 클래스명
     * @param source
     *            복사할 클래스
     * @return 복사된 Object
     * @throws InstantiationException
     */
    public static Object transformDMO(String className, Object source) throws ClassNotFoundException, IllegalAccessException,
	    InvocationTargetException, NumberFormatException, IllegalArgumentException, ConversionException, NoSuchMethodException,
	    InstantiationException {
	Class target = Class.forName(className);
	Method[] methods = target.getDeclaredMethods();
	Object instance = target.newInstance();

	for (int i = 0; i < methods.length; i++) {
	    if (methods[i].getName().indexOf(SET) == 0 && !methods[i].getName().endsWith(BYINT)) {
		String methodName = methods[i].getName().substring(3);
		Class[] param = methods[i].getParameterTypes();
		Method sourcemethod = source.getClass().getMethod("get" + methodName, null);
		Method targetmethod = target.getMethod(methods[i].getName(), param);
		Object[] params = null;
		if (param[0].toString().indexOf(JAVASTRING) != -1) {
		    if (sourcemethod.invoke(source, null) != null)
			params = new Object[] { sourcemethod.invoke(source, null).toString() };
		} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
		    if (!"".equals(sourcemethod.invoke(source, null).toString()))
			params = new Object[] { new Integer(sourcemethod.invoke(source, null).toString()) };
		} else if (param[0].toString().indexOf(JAVABYTE) != -1) {
		    if (sourcemethod.invoke(source, null) != null)
			params = new Object[] { sourcemethod.invoke(source, null) };
		} else if (param[0].toString().indexOf(JAVAINT) != -1) {
		    params = new Object[] { new Integer(sourcemethod.invoke(source, null).toString()) };
		} else if (param[0].toString().indexOf(JAVABOOLEAN) != -1) {
		    params = new Object[] { new Boolean(sourcemethod.invoke(source, null).toString()) };
		} else if (param[0].toString().indexOf(JAVAOBJECT) != -1) {
		    if (sourcemethod.invoke(source, null) != null)
			params = new Object[] { sourcemethod.invoke(source, null) };
		} else {
		    continue;
		}
		if (params != null)
		    targetmethod.invoke(instance, params);
	    }
	}

	return instance;
    }


    /**
     * 오브젝트의 값을 다른 오브젝트에 복사한다.
     * 
     * @param source
     *            복사할 소스 클래스
     * @param target
     *            복사된 소스 클래스
     * @throws InstantiationException
     */
    public static void duplicateDMO(Object source, Object target) throws ClassNotFoundException, IllegalAccessException,
	    InvocationTargetException, NumberFormatException, IllegalArgumentException, ConversionException, NoSuchMethodException,
	    InstantiationException {
	Method[] methods = target.getClass().getDeclaredMethods();

	for (int i = 0; i < methods.length; i++) {
	    if (methods[i].getName().indexOf(SET) == 0 && !methods[i].getName().endsWith(BYINT)) {
		String methodName = methods[i].getName().substring(3);
		Class[] param = methods[i].getParameterTypes();
		Method sourcemethod = source.getClass().getMethod("get" + methodName, null);
		Method targetmethod = target.getClass().getMethod(methods[i].getName(), param);
		Object[] params = null;
		if (param[0].toString().indexOf(JAVASTRING) != -1) {
		    if (sourcemethod.invoke(source, null) != null)
			params = new Object[] { sourcemethod.invoke(source, null).toString() };
		} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
		    if (!"".equals(sourcemethod.invoke(source, null).toString()))
			params = new Object[] { new Integer(sourcemethod.invoke(source, null).toString()) };
		} else if (param[0].toString().indexOf(JAVABYTE) != -1) {
		    if (sourcemethod.invoke(source, null) != null)
			params = new Object[] { sourcemethod.invoke(source, null) };
		} else if (param[0].toString().indexOf(JAVAINT) != -1) {
		    params = new Object[] { new Integer(sourcemethod.invoke(source, null).toString()) };
		} else if (param[0].toString().indexOf(JAVABOOLEAN) != -1) {
		    params = new Object[] { new Boolean(sourcemethod.invoke(source, null).toString()) };
		} else if (param[0].toString().indexOf(JAVAOBJECT) != -1) {
		    if (sourcemethod.invoke(source, null) != null)
			params = new Object[] { sourcemethod.invoke(source, null) };
		} else {
		    continue;
		}
		if (params != null)
		    targetmethod.invoke(target, params);
	    }
	}
    }

    
    /**
     * 오브젝트의 값을 다른 오브젝트에 복사한다.
     * 
     * @param getObj
     *            읽어올 VO
     * @param setObj
     *            저장될 VO
     * @return 변경된 Object
     */
    public static Object transformVO(Object getObj, Object setObj) throws ClassNotFoundException, IllegalAccessException,
	    InvocationTargetException, NumberFormatException, IllegalArgumentException, ConversionException, NoSuchMethodException {
	String[] names = getListName(getObj.getClass());
	String value = "";
	int size = names.length;
	for (int i = 0; i < size; i++) {
	    value = BeanUtils.getProperty(getObj, names[i]);
	    value = StringUtil.null2str(value).trim();
	    if (value != null && !"".equals(value)) {
		// logger.debug("name::::::::::::"+names[i]+"value::::::::"+value);
		BeanUtils.setProperty(setObj, names[i], value);
	    }
	}
	return setObj;
    }


    /**
     * 클래스의 선언된 Attribute 이름을 배열로 넘긴다.
     * 
     * @param klass
     * @return Attribute 이름을 배열
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static String[] getListName(Class klass) throws ClassNotFoundException {
	List<Object> result = new ArrayList();
	Field[] fields = klass.getDeclaredFields();

	int size = fields.length;
	for (int i = 0; i < size; i++) {
	    result.add(fields[i].getName());
	}

	Class superClass = klass.getSuperclass();
	if (!superClass.getName().equals("java.lang.Object")) {
	    String[] reArray = getListName(superClass);

	    int arrSize = reArray.length;
	    for (int i = 0; i < arrSize; i++) {
		result.add(reArray[i]);
	    }
	}
	String[] resultArray = new String[result.size()];
	result.toArray(resultArray);
	return resultArray;
    }


    /**
     * <pre> 
     *  xml형태의 string을 node별 list로 변경함
     * </pre>
     * 
     * @param xmlstr
     * @param nodeName
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @see
     */
    public static List<Map> transferXmlToMap(String xmlstr, String nodeName) {

	Map rsltMap = new HashMap();
	List rsltList = new ArrayList();
	
	try {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();

	    ByteArrayInputStream is = new ByteArrayInputStream(xmlstr.getBytes("UTF-8"));

	    Document doc = db.parse(is);
	    doc.getDocumentElement().normalize();

	    // 결재문서정보 노드리스트
	    NodeList nodeList = null;
	    if(nodeName.toUpperCase().equals("LEGACY")) {
	    	nodeList = doc.getDocumentElement().getElementsByTagName("*");
	    } else {
	    	nodeList = doc.getDocumentElement().getElementsByTagName(nodeName);
	    }
	    // Node node = (Node) nodeList.item(0);

	    int rsize = nodeList.getLength();
	    Node node;
	    NodeList cnList;
	    String name = "";
	    String context = "";

	    for (int i = 0; i < rsize; i++) {
			rsltMap = new HashMap();
			node = (Node) nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
			    // 하위노드리스트
			    cnList = node.getChildNodes();
			    for (int j = 0; j < cnList.getLength(); j++) {
					Node cnode = (Node) cnList.item(j);
					if (cnode.getNodeType() == Node.ELEMENT_NODE) {
					    name = cnode.getNodeName();
					    context = cnode.getTextContent();
					    rsltMap.put(name.trim(), context);
					}
			    }
			    rsltList.add(rsltMap);
	
			}
	    }

	    // return rsltList;

	} catch (ParserConfigurationException e) {

	} catch (IOException e) {

	} catch (SAXException e) {

	}
	return rsltList;
    }

}
