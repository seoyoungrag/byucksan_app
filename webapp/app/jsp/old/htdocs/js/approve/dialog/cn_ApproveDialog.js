function getValueByName(strName)
{
	var objEle = document.getElementsByName(strName);
	var strValue = "";
	for (var i = 0; i < objEle.length; ++i)
	{
		if (objEle[i].checked == true)
		{
			strValue = objEle[i].value;
			break;
		}
	}
	return strValue;
}

function setValueByName(strValue, strName)
{
	var objEle = document.getElementsByName(strName);
	for (var i = 0; i < objEle.length; ++i)
	{
		if (objEle[i].value == strValue)
		{
			objEle[i].checked = true;
			break;
		}
	}
}

function getValueById(strId)
{
	var objEle = document.getElementById(strId);
	var strValue = objEle.value;
	return strValue;
}

function setValueById(strValue, strId)
{
	var objEle = document.getElementById(strId);
	objEle.value = strValue;
}

function getValueById4Combo(strId)
{
	var objCombo = document.getElementById(strId);
	var nIndex = objCombo.selectedIndex;
	return objCombo.options[nIndex].value;
}