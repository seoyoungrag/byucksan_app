function drawProposal(strId)
{
	var nBodyCount = opener.getBodyCount();						// 결재의 안건수

	if (nBodyCount > 1)
	{
		var objCombo = document.getElementById(strId);
		objCombo.length = nBodyCount;

		for (var i = 1 ; i < nBodyCount ; i++)
		{
			objCombo.options[i].text = (i + 1) + PROPOSAL;
			objCombo.options[i].value = i + 1;
		}
	}
}

function drawSelectProposal(strId, nStart, nEnd)
{
	var nBodyCount = opener.getBodyCount();						// 결재의 안건수
	if (nBodyCount > 1)
	{
		var objCombo = document.getElementById(strId);
		objCombo.length = (nEnd - nStart) + 1;

		var nIndex = 0;
		for (var i = nStart ; i <= nEnd ; i++)
		{
			objCombo.options[nIndex].text = i + PROPOSAL;
			objCombo.options[nIndex].value = i;

			nIndex++;
		}
	}
}
