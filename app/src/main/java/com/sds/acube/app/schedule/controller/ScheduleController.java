package com.sds.acube.app.schedule.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.schedule.vo.ScheduleVO;

/** 
 *  Class Name  : ScheduleController.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 8. 1. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 8. 1.
 *  @version 1.0 
 *  @see  com.sds.acube.app.schedule.controller.ScheduleController.java
 */ 

@SuppressWarnings("serial")
@Controller("scheduleController")
@RequestMapping("/app/schedule/*.do")
public class ScheduleController extends BaseController {
    
    // 스케줄 관리
    @RequestMapping("/app/schedule/listSchedule.do")
    public ModelAndView listSchedule(HttpServletRequest request) throws Exception {
	ModelAndView mav = new ModelAndView("ScheduleController.listSchedule");
        
        List<ScheduleVO> scheduleVOs = new ArrayList<ScheduleVO>();
        Map<String, String> mapSFB = new HashMap<String, String>();
        Map<String, String> mapCTB = new HashMap<String, String>();
        Map<String, String> mapJFB = new HashMap<String, String>();
        
        InputStream f = ScheduleController.class.getResourceAsStream("/app/spring/context-schedule.xml");
        DocumentBuilder parser;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    
        factory.setValidating(false);
        factory.setNamespaceAware(false);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        parser = factory.newDocumentBuilder();
    
        Document document = parser.parse(f);
    
        if (document != null) {
            Element root = document.getDocumentElement();
            // cross site scripting data
            NodeList list = root.getElementsByTagName("bean");
            if (list != null) {
        	for (int loop = 0; loop < list.getLength(); loop++) {
        	    Element bean = (Element) list.item(loop);
        	    String beanClass = bean.getAttribute("class");
        	    if ("org.springframework.scheduling.quartz.SchedulerFactoryBean".equals(beanClass)) {
        		String beanId = bean.getAttribute("id");
        		if (beanId != null) {
        		    Node child = bean.getFirstChild();
        		    while (child != null) {
        			if ("property".equals(child.getNodeName())) {
                		    Node subchild = child.getFirstChild();
                		    while (subchild != null) {
                			if ("list".equals(subchild.getNodeName())) {
                        		    Node lastchild = subchild.getFirstChild();
                        		    while (lastchild != null) {
                        			if ("ref".equals(lastchild.getNodeName())) {
                        			    String name = ((Element) lastchild).getAttribute("local");
                        			    mapSFB.put(name, beanId);
                        			    break;
                        			}
                        			lastchild = lastchild.getNextSibling();
                        		    }
                			}
                			subchild = subchild.getNextSibling();
                		    }
        			}
        			child = child.getNextSibling();
        		    }
        		}
        	    } else if ("org.springframework.scheduling.quartz.CronTriggerBean".equals(beanClass)) {
        		String jobDetail = "";
        		String jobCron = "";
        		String beanId = bean.getAttribute("id");
        		if (beanId != null) {
        		    Node child = bean.getFirstChild();
        		    while (child != null) {
        			if ("property".equals(child.getNodeName())) {
        			    String name = ((Element) child).getAttribute("name");
        			    if ("jobDetail".equals(name)) {
        				jobDetail = ((Element) child).getAttribute("ref");
        			    } else if ("cronExpression".equals(name)) {
        				jobCron = ((Element) child).getAttribute("value");
        			    }
        			}
        			child = child.getNextSibling();
        		    }
        		}
        		mapCTB.put(jobDetail, beanId);
        		mapJFB.put(jobDetail, jobCron);
        	    } else if ("org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean".equals(beanClass)) {
        		String targetObject = "";
        		String targetMethod = "";
        		ScheduleVO scheduleVO = new ScheduleVO();
        		String beanId = bean.getAttribute("id");
        		if (beanId != null) {
        		    scheduleVO.setJobDetailFactoryBean(beanId);

        		    Node child = bean.getFirstChild();
        		    while (child != null) {
        			if ("description".equals(child.getNodeName())) {
        			    Node subchild =  child.getFirstChild();
        			    while (subchild != null) {
        				if (subchild.getNodeType() == 4 && "#cdata-section".equals(subchild.getNodeName())) {
        				    scheduleVO.setDescription(subchild.getNodeValue());
        				    break;
        				}
        				subchild = subchild.getNextSibling();
        			    }
        			} else if ("property".equals(child.getNodeName())) {
        			    String name = ((Element) child).getAttribute("name");
        			    if ("targetObject".equals(name)) {
        				Node subchild =  child.getFirstChild();
        				while (subchild != null) {
        				    if ("ref".equals(subchild.getNodeName())) {
        					targetObject = ((Element) subchild).getAttribute("bean");
        					break;
        				    }
        				    subchild = subchild.getNextSibling();
        				}
        			    } else if ("targetMethod".equals(name)) {
        				Node subchild =  child.getFirstChild();
        				while (subchild != null) {
        				    if ("value".equals(subchild.getNodeName())) {
        					Node lastchild =  subchild.getFirstChild();
        					while (lastchild != null) {
        					    if (lastchild.getNodeType() == 3 && "#text".equals(lastchild.getNodeName())) {
        						targetMethod = lastchild.getNodeValue();
        						break;
        					    }
        					    lastchild = lastchild.getNextSibling();
        					}
        				    }
        				    subchild = subchild.getNextSibling();
        				}
        			    }
        			}

        			child = child.getNextSibling();
        		    }

        		    scheduleVO.setTarget(targetObject + "." + targetMethod);
        		    scheduleVOs.add(scheduleVO);
        		}
        	    }
        	}
            }
        }
        
        int scheduleCount = scheduleVOs.size();
        for (int loop = 0; loop < scheduleCount; loop++) {
            ScheduleVO scheduleVO = scheduleVOs.get(loop);
            scheduleVO.setCronExpression(mapJFB.get(scheduleVO.getJobDetailFactoryBean()));
            scheduleVO.setCronTriggerBean(mapCTB.get(scheduleVO.getJobDetailFactoryBean()));
            scheduleVO.setScheduleFactoryBean(mapSFB.get(scheduleVO.getCronTriggerBean()));
        }
        mav.addObject("scheduleVOs", scheduleVOs);
        return mav;
    }
}

