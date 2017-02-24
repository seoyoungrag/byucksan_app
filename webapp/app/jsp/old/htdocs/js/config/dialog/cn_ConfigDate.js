function isSequential(strEDate, strSDate)
{
	var START_AM = "00:00:00";
	var START_PM = "12:00:00";
	var END_AM = "11:59:59";
	var END_PM = "23:59:59";

	var arrEDate = strEDate.split(" ");
	var strEDay = arrEDate[0];
	var strETime = arrEDate[1];
	var arrSDate = strSDate.split(" ");
	var strSDay = arrSDate[0];
	var strSTime = arrSDate[1];

	if ((strETime != END_AM) && (strETime != END_PM))
		return false;

	if ((strSTime != START_AM) && (strSTime != START_PM))
		return false;

	var nDiffDays = getDaysFromTo(strSDay, strEDay);
	if ((nDiffDays > 2) || (nDiffDays < 1))
		return false;
	else
	{
		if (nDiffDays == 2)
		{
			if ((strETime == END_PM) && (strSTime == START_AM))
				return true;
			else
				return false;
		}
		else
		{
			if ((strETime == END_AM) && (strSTime == START_PM))
				return true;
			else
				return false;
		}
	}
}

function getDaysFromTo(s1, s2)
{
	var y1 = getDaysFrom21Century4String(s1);
	var y2 = getDaysFrom21Century4String(s2);
	return y1 - y2 + 1;
}

function getDaysFrom21Century4String(s)
{
	var d, m, y;
	y = parseInt(s.substring(0, 4), 10);
	m = parseInt(s.substring(5, 7), 10);
	d = parseInt(s.substring(8), 10);
	return getDaysFrom21Century(d, m, y);
}

function getDaysFrom21Century(d, m, y)
{
	var sum = 0;
	if (y >= 2000)
	{
		sum = getDaysFromYearFirst(d, m, y);
		for (var j = y - 1; j >= 2000; j--)
			sum += getDaysInYear(j);
	}
	else if (y > 0 && y < 2000)
	{
		sum = getDaysFromYearFirst(d, m, y);
		for (var j = 1999; j >= y; j--)
			sum -= getDaysInYear(y);
	}
	return (sum - 1);
}

function getDaysFromYearFirst(d, m, y)
{
	var sum = 0;
	var max = getDaysInMonth(m, y);
	if (d >= 1 && d <= max)
	{
		sum = d;
		for (var j = 1; j < m; j++)
			sum += getDaysInMonth(j, y);
	}
	return sum;
}

function getDaysInMonth(m, y)
{
	var b = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if (m != 2 && m >= 1 && m <= 12 && y != 1582)
		return b[m - 1];
	if (m != 2 && m >= 1 && m <= 12 && y == 1582)
	{
		if (m != 10)
			return b[m - 1];
		else
			return b[m - 1] - 10;
	}

	if (m != 2)
		return 0;

	if (y > 1582)
	{
		if (y % 400 == 0)
			return 29;
		else if (y % 100 == 0)
			return 28;
		else if (y % 4 == 0)
			return 29;
		else
			return 28;
	}
	else if (y == 1582)
		return 28;
	else if (y > 4)
	{
		if (y % 4 == 0)
			return 29;
		else
			return 28;
	}
	else
		return 28;
}

function getDaysInYear(y)
{
	if (y > 1582)
	{
		if (y % 400 == 0)
			return 366;
		else if (y % 100 == 0)
			return 365;
		else if (y % 4 == 0)
			return 366;
		else
			return 365;
	}
	else if (y == 1582)
		return 355;
	else if (y > 4)
	{
		if (y % 4 == 0)
			return 366;
		else
			return 365;
	}
	else if (y > 0)
		return 365;
	else
		return 0;
}
