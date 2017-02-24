<%@ page import="java.util.Locale" %>
<%
	String calSelectedLanguage = "ko";
	if((Locale)session.getAttribute("LANG_TYPE") != null) {
	    calSelectedLanguage = ((Locale)session.getAttribute("LANG_TYPE")).getLanguage();  // Selected language
	}
%>
<SCRIPT language='JavaScript'>

var SELECTED_LANGUAGE = '<%=calSelectedLanguage%>';

var YEAR_NAME = '';

var MONTH_NAMES = new Array(
	"<spring:message code='calendar.month.full1'/>", 
	"<spring:message code='calendar.month.full2'/>", 
	"<spring:message code='calendar.month.full3'/>", 
	"<spring:message code='calendar.month.full4'/>", 
	"<spring:message code='calendar.month.full5'/>", 
	"<spring:message code='calendar.month.full6'/>", 
	"<spring:message code='calendar.month.full7'/>", 
	"<spring:message code='calendar.month.full8'/>", 
	"<spring:message code='calendar.month.full9'/>", 
	"<spring:message code='calendar.month.full10'/>", 
	"<spring:message code='calendar.month.full11'/>", 
	"<spring:message code='calendar.month.full12'/>", 
	"<spring:message code='calendar.month.short1'/>", 
	"<spring:message code='calendar.month.short2'/>", 
	"<spring:message code='calendar.month.short3'/>", 
	"<spring:message code='calendar.month.short4'/>", 
	"<spring:message code='calendar.month.short5'/>", 
	"<spring:message code='calendar.month.short6'/>", 
	"<spring:message code='calendar.month.short7'/>", 
	"<spring:message code='calendar.month.short8'/>", 
	"<spring:message code='calendar.month.short9'/>", 
	"<spring:message code='calendar.month.short10'/>", 
	"<spring:message code='calendar.month.short11'/>", 
	"<spring:message code='calendar.month.short12'/>");

var DAY_NAMES = new Array(
	"<spring:message code='calendar.week.full1'/>", 
	"<spring:message code='calendar.week.full2'/>", 
	"<spring:message code='calendar.week.full3'/>", 
	"<spring:message code='calendar.week.full4'/>", 
	"<spring:message code='calendar.week.full5'/>", 
	"<spring:message code='calendar.week.full6'/>", 
	"<spring:message code='calendar.week.full7'/>", 
	"<spring:message code='calendar.week.short1'/>", 
	"<spring:message code='calendar.week.short2'/>", 
	"<spring:message code='calendar.week.short3'/>", 
	"<spring:message code='calendar.week.short4'/>", 
	"<spring:message code='calendar.week.short5'/>", 
	"<spring:message code='calendar.week.short6'/>", 
	"<spring:message code='calendar.week.short7'/>");

var G_MONTH_NAMES = new Array(
	"<spring:message code='calendar.month.full1'/>", 
	"<spring:message code='calendar.month.full2'/>", 
	"<spring:message code='calendar.month.full3'/>", 
	"<spring:message code='calendar.month.full4'/>", 
	"<spring:message code='calendar.month.full5'/>", 
	"<spring:message code='calendar.month.full6'/>", 
	"<spring:message code='calendar.month.full7'/>", 
	"<spring:message code='calendar.month.full8'/>", 
	"<spring:message code='calendar.month.full9'/>", 
	"<spring:message code='calendar.month.full10'/>", 
	"<spring:message code='calendar.month.full11'/>", 
	"<spring:message code='calendar.month.full12'/>");
                          
var G_MONTHABBREVIATIONS = new Array(
	"<spring:message code='calendar.month.short1'/>", 
	"<spring:message code='calendar.month.short2'/>", 
	"<spring:message code='calendar.month.short3'/>", 
	"<spring:message code='calendar.month.short4'/>", 
	"<spring:message code='calendar.month.short5'/>", 
	"<spring:message code='calendar.month.short6'/>", 
	"<spring:message code='calendar.month.short7'/>", 
	"<spring:message code='calendar.month.short8'/>", 
	"<spring:message code='calendar.month.short9'/>", 
	"<spring:message code='calendar.month.short10'/>", 
	"<spring:message code='calendar.month.short11'/>", 
	"<spring:message code='calendar.month.short12'/>");

var	G_DAYHEADERS = new Array(
	"<spring:message code='calendar.week.char1'/>", 
	"<spring:message code='calendar.week.char2'/>", 
	"<spring:message code='calendar.week.char3'/>", 
	"<spring:message code='calendar.week.char4'/>", 
	"<spring:message code='calendar.week.char5'/>", 
	"<spring:message code='calendar.week.char6'/>", 
	"<spring:message code='calendar.week.char7'/>");

</SCRIPT>

<SCRIPT LANGUAGE="javascript" SRC="<%=webUri%>/app/ref/js/calendarPopup.js"></SCRIPT>

<SCRIPT language='JavaScript'>

document.write("<DIV ID='calendarDiv' STYLE='position:absolute;visibility:hidden;background-color:white;layer-background-color:white;'></DIV>");

document.write(getCalendarStyles());

var cal = new CalendarPopup('calendarDiv');

</SCRIPT>
