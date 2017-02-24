function initializeDate(strObjId, strCurrentDate)
{
	var objInput = document.getElementById(strObjId);
	if (strCurrentDate == "")
	{
		var objDate = new Date();
		var strYear = objDate.getYear();
		var strMonth = objDate.getMonth() + 1;
		if (strMonth < 10)
			strMonth = "0" + strMonth;
		var strDate = objDate.getDate();
		if (strDate < 10)
			strDate = "0" + strDate;

		objInput.value = strYear + "-" + strMonth + "-" + strDate;
	}
	else
	{
		if (strCurrentDate.indexOf(" ") > 0)
			objInput.value = strCurrentDate.substring(0, strCurrentDate.indexOf(" "));
		else
			objInput.value = strCurrentDate;
	}
}

function initializeTime(strHourId, strHourValue, strMinuteId, strMinuteValue)
{
	var objHour = document.getElementById(strHourId);
	var objMinute = document.getElementById(strMinuteId);
	
	if (strHourValue == "")
	{
		var objDate = new Date();
		strHourValue = objDate.getHours();
		if (strHourValue < 10)
			strHourValue = "0" + strHourValue;
	}
			
	if (strMinuteValue == "")
	{
		var objDate = new Date();
		strMinuteValue = objDate.getMinutes();
		if (strMinuteValue < 10)
			strMinuteValue = "0" + strMinuteValue;
	}	
	
	for (var intLoop = 0; intLoop < 24; intLoop++)
	{
		var objOption = document.createElement("OPTION");
		if (intLoop < 10)
		{
			objOption.text = "0" + intLoop;
			objOption.value = "0" + intLoop;
		}
		else
		{
			objOption.text = intLoop;
			objOption.value = intLoop;
		}
		objHour.add(objOption);
	}
	objHour.selectedIndex = strHourValue;
					
	for (var intLoop = 0; intLoop < 60; intLoop++)
	{
		var objOption = document.createElement("OPTION");
		if (intLoop < 10)
		{
			objOption.text = "0" + intLoop;
			objOption.value = "0" + intLoop;
		}
		else
		{
			objOption.text = intLoop;
			objOption.value = intLoop;
		}
		objMinute.add(objOption);
	}
	objMinute.selectedIndex = strMinuteValue;
}

function getCurrentDate(strDel)
{
	var objDate = new Date();
	var strYear = objDate.getYear();
	var strMonth = objDate.getMonth() + 1;
	if (strMonth < 10)
		strMonth = "0" + strMonth;
	var strDate = objDate.getDate();
	if (strDate < 10)
		strDate = "0" + strDate;

	var strReturn = strYear + strDel + strMonth + strDel + strDate;
	return strReturn;
}

function getCurrentTime(strDel)
{
	var objDate = new Date();

	strHourValue = objDate.getHours();
	if (strHourValue < 10)
		strHourValue = "0" + strHourValue;

	strMinuteValue = objDate.getMinutes();
	if (strMinuteValue < 10)
		strMinuteValue = "0" + strMinuteValue;
	
	strSecondValue = objDate.getSeconds();
	if (strSecondValue < 10)
		strSecondValue = "0" + strSecondValue;
	
	var strReturn = strHourValue + strDel + strMinuteValue + strDel + strSecondValue;
	return strReturn;		
}

/*
function getDateDisplay(strDateTime, strFormat)
{
	var nFind = strDateTime.indexOf(" ");
	var strDate;
	if (nFind != -1)
		strDate = strDateTime.substring(0, nFind);
	else
		strDate = strDateTime;
	var arrDate = strDate.split("-");
	if (strFormat == "YYYY.MM.DD")
		strDate = arrDate[0] + "." + arrDate[2] + "." + arrDate[2];
	else if (strFormat == "YYYY/MM/DD")
		strDate = arrDate[0] + "/" + arrDate[1] + "/" + arrDate[2];
	else if (strFormat == "MM.DD")
		strDate = arrDate[1] + "." + arrDate[2];
	else if (strFormat == "MM/DD")
		strDate = arrDate[1] + "/" + arrDate[2];
	else if (strFormat == "MM-DD")
		strDate = arrDate[1] + "-" + arrDate[2];
	else
		strDate = arrDate[0] + "." + arrDate[1] + "." + arrDate[2];
	return strDate;
}
*/

/*
	strDateTime: YYYY-MM-DD HH:MM:SS
	strFormat:
		1. Y:년, M:월, D:일
		2. 년, 월, 일 별로 유효한 조합은 YY, YYYY, MM, DD
		3. Y, M, D 제외한 문자는 그대로 복사됨
*/
function getDateDisplay(strDateTime, strFormat)
{
	var strResult = "";
	if (strDateTime != "")
	{
		var nFind = strDateTime.indexOf(" ");
		var strDate;
		if (nFind != -1)
			strDate = strDateTime.substring(0, nFind);
		else
			strDate = strDateTime;
		var arrDate = strDate.split("-");

		var i = 0;
		while (i < strFormat.length)
		{
			if (strFormat.charAt(i) == 'Y')
			{
				strResult += arrDate[0].substring(2, 4);
				i += 2;
				if (strFormat.charAt(i) == 'Y')
				{
					strResult = strResult.substring(0, strResult.length - 2) + arrDate[0];
					i += 2;
				}
			}
			else if (strFormat.charAt(i) == 'M')
			{
				strResult += arrDate[1];
				i += 2;
			}
			else if (strFormat.charAt(i) == 'D')
			{
				strResult += arrDate[2];
				i += 2;
			}
			else
			{
				strResult += strFormat.charAt(i);
				i++;
			}
		}
	}
	return strResult;
}

function getTimeDisplay(strDateTime)
{
	var nFind = strDateTime.indexOf(" ");
	var strTime;
	if (nFind != -1)
		strTime = strDateTime.substring(nFind + 1, strDateTime.length);
	else
		strTime = strDateTime;

//	var arrDate = strDate.split(":");
	return strTime;
}

function initializePostDate(strObjId, strCurrentDate)
{
	var objInput = document.getElementById(strObjId);
	if (strCurrentDate == "")
	{
		var objDate = new Date();
		var varTime = objDate.getTime();
		varTime += 7 * (1000 * 60 * 60 * 24);
		objDate.setTime(varTime);

		var strYear = objDate.getYear();
		var strMonth = objDate.getMonth() + 1;
		var strDate = objDate.getDate();

		if (strMonth < 10)
			strMonth = "0" + strMonth;

		if (strDate < 10)
			strDate = "0" + strDate;

		objInput.value = strYear + "-" + strMonth + "-" + strDate;
	}
	else
	{
		if (strCurrentDate.indexOf(" ") > 0)
			objInput.value = strCurrentDate.substring(0, strCurrentDate.indexOf(" "));
		else
			objInput.value = strCurrentDate;
	}
}
