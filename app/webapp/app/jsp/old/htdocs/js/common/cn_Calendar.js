function onClickCalendar(strBaseUrl, strObjId)
{
	var objText = document.getElementById(strObjId);
	var strCurrentDate = objText.value;

	if (strCurrentDate == "")
	{
		var currentDate = new Date();
		var currentYear = currentDate.getYear();
		var currentMonth = currentDate.getMonth() + 1;
		var currentDay = currentDate.getDate();
		if (parseInt(currentMonth) < 10)
			currentMonth = "0" + currentMonth;
		if (parseInt(currentDay) < 10)
			currentDay = "0" + currentDay;

		strCurrentDate = currentYear + "-" + currentMonth + "-" + currentDay;
	}

	var strURL = strBaseUrl + "common/jsp/CN_Calendar.jsp?currentdate=" + strCurrentDate + "&objid=" + strObjId;
	window.open(strURL, "newCalendar", " status=yes,width=265,height=300");
}

function setDate(strObjId, strObjValue)
{
	var objText = document.getElementById(strObjId);
	objText.value = strObjValue;
}

