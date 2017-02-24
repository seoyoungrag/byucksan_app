<%--	// System Call madn with parameters
	// argv[0] = madn execution program
	// argv[1] = Download User Id
	// argv[2] = Source Filenam, Not include Path because Config is same
	// argv[3] = Version information 1.0 - 9.9
	// argv[4] = Save Option (0 : DENY / 1 : PERMIT)
	// argv[5] = Edit Option (0 : DENY / 1 : PERMIT)
	// argv[6] = Print Option (00 : DENY / 99 : PERMIT)
	// argv[7] = EXPORT Option (0 : DENY / 1 : PERMIT) - Not USE
	// argv[8] = Expired Date (000 - 999 Day)
	// argv[9] ... argv[12] Set 0
	// argv[13] = MADDS IP Address
	// argv[14] = MADDS PORT Number (40001)

	// argv[15] ... argv[17] = Content Reference information
	// Argv[15]	= Content ID (Primary key for content query): ���� �ĺ� ���̵� (Maximum 10Char)
	// Argv[16]	= File Index ID: �ϳ��� ��ϵ� ������ ���� ���� ÷�� ������ �ִ� ��� �и��ϱ� ���� ��� (Maximum 5 Char)
	// Argv[17]	= TABLE INDEX: ContentID�� TABLE������ �и��� ��� (Maximum 1 Char)
--%>
<%@ include file="/jsp/drm/SC_MarkAnyExt.inc"%>
<%
   System.out.println("inside MADN");
	// Query from dms_acl
	// modify by junhyuk 2002-07-05 ///////////////
	String sDrmUserId;
   String DrmURL;//=uiBean.arrInfo[32];
   String DrmPort;//=uiBean.arrInfo[33];
   String DrmProduct;//=uiBean.arrInfo[34];
   String DrmTarget;//=uiBean.arrInfo[35];
   String DrmTempPort;// = DrmPort;
   String DrmDmLogUse;// = uiBean.arrInfo[36];
	try
	{
		String sInfo[] = uiBean.getOriginArrInfo();
		DrmURL = sInfo[32];
		DrmPort = sInfo[33];
		DrmProduct = sInfo[34];
		DrmTarget = sInfo[35];
		DrmTempPort = sInfo[33];
		DrmDmLogUse = sInfo[36];

		sDrmUserId = uiBean.getUserID();
		DrmTempPort = DrmTempPort.substring(1);
	}
	catch (Exception e)
	{
		DrmURL="";
		DrmPort="";
		DrmProduct="";
		DrmTarget="";
		DrmTempPort = "";
		DrmDmLogUse = "";

		sDrmUserId = "";
	}	
	///////////////////////////////////////////////

   if(!DrmTarget.equals("0"))                      //DRM �� ����Ǵ� ���
   {
      if(DrmProduct!=null && DrmProduct.equals("2"))
      {
   		String param = null;
   		String szFileName = null;
   		String szSave,szBlock,szPrintCount;    
   		String szACapFile;
         String szFullName;
   		// DM ������ MarkAny ���ѿ� �µ��� ���� �� �Է�
   		if(Integer.parseInt(sMaxPrg) < 20)         //View���� ���� ���
   		{
      		szSave = "0";
     		   szBlock = "0";
      		szPrintCount = "0";
   		}
   		else
   		{
      		if(Integer.parseInt(sMaxPrg) < 30)     // �μ���Ѹ� ���� ���
      		{
         		szSave = "0";
         		szBlock = "0";
         		szPrintCount = "99";
      		}
      		else                                   // ���� + �μ���� ���� ���
      		{
         		szSave = "1";
         		szBlock = "1";
         		szPrintCount = "99";
      		}
   		}

			// DM ������ tmp ���丮�� �ӽ÷� ����� temp���ϵ��� ��Ű¡
			for (int i = 0; i < Integer.parseInt(sFileCnt) ; i++)
			{
 				String szExtentionName;
            szACapFile = varFile[i][3];
            szExtentionName = szACapFile.substring(szACapFile.lastIndexOf(".")+1);
            if(checkACapFile(szExtentionName,arrParam).equals("0"))
				{
   			param = "/user1/markany/SAFER/bin/MA_DN" + " " + sDrmUserId + " " + varFile[i][2] + " 1.0 " + szSave + " " + szBlock + " " + szPrintCount + " 00 " + sDrmExpire + " "; 
   		   for (int j = 0; j<4 ;j++)
				{
					param += DrmDmLogUse + " ";	
				}
   			param += DrmURL + " " + DrmTempPort + " ";
   			//param += "70.7.34.114 " + "40001 ";
				param += sDocId + " " + varFleId[i] + " " + "F";
   			szFileName = varFleTtl[i];
            szFullName = varFile[i][2];
   			System.out.println("param : " + param);
   
   			// ��������(MADN) �����Ű��
   			Runtime runtime = Runtime.getRuntime();
   			Process x = runtime.exec(param);
   			x.waitFor();
  
    			// MADN�� Return �� ������ (retVal)
   			InputStream Input;
   			InputStreamReader In2r;
   			BufferedReader br;

   			Input = x.getInputStream();
   			In2r = new InputStreamReader(Input);
   			br = new BufferedReader(In2r);

   			String retVal = br.readLine();
            if(!retVal.equals("0"))
				{
					System.out.println("MarkAny ���� ��ȣȭ�� �����߻�");
				}
   			System.out.println("retVal : " +retVal);
   			br.close();
   			In2r.close();
   			Input.close();

		      //szExtentionName = szACapFile.substring(szACapFile.lastIndexOf(".")+1);
		      szACapFile = szACapFile.substring(0,szACapFile.lastIndexOf("."));
		      szACapFile += ".ACap";
		      varFile[i][3] = szACapFile;
            
            //szExtentionName = szFullName.substring(szFullName.lastIndexOf(".")+1);
            szFullName = szFullName.substring(0,szFullName.lastIndexOf("."));
            szFullName += ".ACap";
            varFile[i][2] = szFullName;
				}
			}
		}
	}
%>
<%!
	String checkACapFile(String extName,String[] arrParam)
	{
		for ( int i = 0 ; i < arrParam.length ; i++)
		{
	   	if(extName.toLowerCase().equals(arrParam[i].toLowerCase()))
			{
				return "0";
			}
		}
		return "-1";	
	}
%>
