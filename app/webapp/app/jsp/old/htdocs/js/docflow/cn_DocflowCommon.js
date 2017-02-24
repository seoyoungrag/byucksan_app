function transferApos(strInput)
{
	var strOutput = "";
	
	for (var intLoop = 0; intLoop < strInput.length; intLoop++)
	{
		if (strInput.charAt(intLoop) == "'")
			strOutput += "''";
		else if (strInput.charAt(intLoop) == "_")
			strOutput += "[_]";
		else if (strInput.charAt(intLoop) == "[")
			strOutput += "[[]";
		else
			strOutput += strInput.charAt(intLoop);
	}
	
	return strOutput;
}

function getRidOfBlank(strInput)
{
	while (true)
	{
		if (strInput.charAt(0) == " ")
			strInput = strInput.substring(1);
		else
			break;
	}
	
	return strInput;
}

function getRidOfZero(strInput)
{
	while (true)
	{
		if (strInput.charAt(0) == "0")
			strInput = strInput.substring(1);
		else
			break;
	}
	
	return strInput;
}