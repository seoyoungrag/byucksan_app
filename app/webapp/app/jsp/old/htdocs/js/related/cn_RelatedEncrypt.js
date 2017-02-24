function encodeValue(strValue)
{
	objEnc.Encode = strValue;
	strValue =objEnc.Encode;

	return strValue;
}

function decodeValue(strValue)
{
	objEnc.Decode = strValue;
	strValue =objEnc.Decode;

	return strValue;
}
