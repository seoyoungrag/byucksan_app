// Select List Element
	var g_SelectBackground = "#f7f7f7";
	var g_bMultiSelect = "";
	var g_objSelected = null;
	var g_arrSelectedObject = new Array();
	var g_arrBackground = new Array();
	var g_iSelectCount = 0;
	var g_totalIndex = 0;
	var g_prevCtrlKey = false;

function onListClick()
{
	document.selection.empty();

	var element = window.event.srcElement;
	while (element.name != "selected")
	{
		element = element.parentElement;
		if (element.name == "selected")
		{
			selectElement(element, false);
			break;
		}
	}
}

function onListMouseMove()
{
	document.selection.empty();
}

function onListKeyDown()
{
	var keyCode = window.event.keyCode;

	switch (keyCode)
	{
		case 37:
		case 38:
		{
			var element = null;
			if (g_iSelectCount == 0)
				element = g_objSelected;
			else
			{
				var strIndex = g_objSelected.id;
				strIndex = strIndex.substring(5);
				var nIndex = parseInt(strIndex);
				if (nIndex > 0)
				{
					nIndex--;
					strIndex = "Index" + nIndex;
					element = document.getElementById(strIndex);
				}
			}
			if (element != null)
			{
				selectElement(element, true);
				element.firstChild.focus();
			}
			break;
		}
		case 39:
		case 40:
		{
			var element = null;
			if (g_iSelectCount == 0)
				element = g_objSelected;
			else
			{
				var strIndex = g_objSelected.id;
				strIndex = strIndex.substring(5);
				var nIndex = parseInt(strIndex);
				if (nIndex < g_totalIndex - 1)
				{
					nIndex++;
					strIndex = "Index" + nIndex;
					element = document.getElementById(strIndex);
				}
			}
			if (element != null)
			{
				selectElement(element, true);
				element.firstChild.focus();
			}
			break;
		}
		default:
			break;
	}
}

function onListDblClick()
{
	document.selection.empty();
	if (g_iSelectCount > 0)
	{
		var strImageId = g_arrSelectedObject[0].getAttribute("imageid");
		getSignatureImage(strImageId);
	}
}

function selectElement(element, bKeyboard)
{
	if (!g_bMultiSelect || window.event == null || (!window.event.shiftKey && !window.event.ctrlKey) || (window.event.shiftKey && window.event.ctrlKey))
	{
		clearSelectArray();

		g_arrSelectedObject[g_iSelectCount] = element;
		g_arrBackground[g_iSelectCount] = element.style.background;
		g_iSelectCount++;

		element.style.background = "#2984be";
		element.style.color = "white";
		g_objSelected = element;
		g_prevCtrlKey = ((window.event == null) ? false : window.event.ctrlKey);
	}
	else if (window.event.ctrlKey)
	{
		if (!bKeyboard)
		{
			var nFound = getSelectedIndexOf(element);
			if (nFound >= 0)	// already selected
			{
				element.style.color = "#58595b";
				element.style.background = g_arrBackground[nFound];
				shiftSelectArray(nFound);
			}
			else
			{
				g_arrSelectedObject[g_iSelectCount] = element;
				g_arrBackground[g_iSelectCount] = element.style.background;
				g_iSelectCount++;

				element.style.background = "#2984be";
				element.style.color = "white";
			}
			g_objSelected = element;
			g_prevCtrlKey = window.event.ctrlKey;
		}
	}
	else if (window.event.shiftKey)
	{
		var eleFirst;
		if (g_prevCtrlKey)
		{
			eleFirst = g_objSelected;
		}
		else
		{
			if (g_iSelectCount > 0)
				eleFirst = g_arrSelectedObject[0];
			else
				eleFirst = element;
		}
		var strFirst = eleFirst.id;
		strFirst = strFirst.substring(5);

		var eleLast = element;
		var strLast = eleLast.id;
		strLast = strLast.substring(5);

		var nFirst = parseInt(strFirst);
		var nLast = parseInt(strLast);

		var nStep = 0;
		if (nFirst < nLast)
			nStep = 1;
		else
			nStep = -1;

		clearSelectArray();

		for (var i = nFirst; i != nLast + nStep; i += nStep)
		{
			var str = "Index" + i;
			var ele = document.getElementById(str);
			if (ele != null)
			{
				g_arrSelectedObject[g_iSelectCount] = ele;
				g_arrBackground[g_iSelectCount] = ele.style.background;
				g_iSelectCount++;

				ele.style.background = "#2984be";
				ele.style.color = "white";
			}
		}
		g_objSelected = element;
		g_prevCtrlKey = window.event.ctrlKey;
	}
}

function clearSelectArray()
{
	if (g_iSelectCount == 0)
		return;

	for (var i = 0 ; i < g_iSelectCount ; i++)
	{
		var element = g_arrSelectedObject[i];
		var bgcolor = g_arrBackground[i];

		if (element != null)
		{
			element.style.background = bgcolor;
			element.style.color = "#58595b";
		}
	}

	g_iSelectCount = 0;
	g_arrSelectedObject = new Array();
}

function getSelectedIndexOf(element)
{
	for (var i = 0; i < g_iSelectCount; i++)
		if (g_arrSelectedObject[i] == element)
			return i;
	return -1;
}

function shiftSelectArray(nIndex)
{
	var arrSelectedObject = new Array();
	var arrBackground = new Array();
	var j = 0;

	for (var i = 0; i < g_iSelectCount; i++)
	{
		if (i != nIndex)
		{
			arrSelectedObject[j] = g_arrSelectedObject[i];
			arrBackground[j] = g_arrBackground[i];
			j++;
		}
	}
	g_arrSelectedObject = arrSelectedObject;
	g_arrBackground = arrBackground;
	g_iSelectCount--;
}
