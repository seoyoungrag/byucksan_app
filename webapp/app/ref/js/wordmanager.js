// Define Word Control

// ///////////////////////////////////////
// Initialize Word Control
// ///////////////////////////////////////

// initializeWord HwpManager Object
// argument[0] : Target Div ID
// argument[1] : HwpCtrl의 사용목적 : document, enforce
function initializeHwp(location, type) {
	try {
		if (document.getElementById(location)) {
			var objectTag = "";
			if (type == "document") {
				if (document.getElementById("Document_HwpCtrl") == null) {
					objectTag = "<OBJECT id='Document_HwpCtrl' name='Document_HwpCtrl' width='100%' height='100%' classid='CLSID:7CF941E8-085D-47D8-BDDF-D7C124B697BB' codeBase='/ep/app/ref/cabfile/MSWord(1,0,0,11).cab#version=1,0,0,11'>"
						+ "	<PARAM name='_Version' value='65536'/>"
						+ "	<PARAM name='_ExtentX' value='20902'/>"
						+ "	<PARAM name='_ExtentY' value='12435'/>"
						+ "	<PARAM name='_StockProps' value='0'/>"
						+ "	<PARAM name='FILENAME' value=''/>'"
						+ "</OBJECT>";
		   		}
			} else if (type == "enforce") {
				if (document.getElementById("Enforce_HwpCtrl") == null) {
					objectTag = "<OBJECT id='Enforce_HwpCtrl' name='Enforce_HwpCtrl' width='100%' height='100%' classid='CLSID:7CF941E8-085D-47D8-BDDF-D7C124B697BB' codeBase='/ep/app/ref/cabfile/MSWord(1,0,0,11).cab#version=1,0,0,11'>"
						+ "	<PARAM name='_Version' value='65536'/>"
						+ "	<PARAM name='_ExtentX' value='20902'/>"
						+ "	<PARAM name='_ExtentY' value='12435'/>"
						+ "	<PARAM name='_StockProps' value='0'/>"
						+ "	<PARAM name='FILENAME' value=''/>'"
						+ "</OBJECT>";
		   		}
			} else if (type == "print") {
				if (document.getElementById("Print_HwpCtrl") == null) {
					objectTag = "<OBJECT id='Print_HwpCtrl' name='Print_HwpCtrl' width='100%' height='100%' classid='CLSID:7CF941E8-085D-47D8-BDDF-D7C124B697BB' codeBase='/ep/app/ref/cabfile/MSWord(1,0,0,11).cab#version=1,0,0,11'>"
						+ "	<PARAM name='_Version' value='65536'/>"
						+ "	<PARAM name='_ExtentX' value='20902'/>"
						+ "	<PARAM name='_ExtentY' value='12435'/>"
						+ "	<PARAM name='_StockProps' value='0'/>"
						+ "	<PARAM name='FILENAME' value=''/>'"
						+ "</OBJECT>";
		   		}
			}
			if (objectTag != "") {
				document.getElementById(location).innerHTML = objectTag;
			}
		}
	} catch (error) {
		errormessage("initialize");
	}
}


// return HwpCtrl_Object for Work
// argument[0] : HwpCtrl의 사용목적 : document, enforce
function selectModule(type) {
	if (type == "document")
		return document.getElementById("Document_HwpCtrl");
	else
		return document.getElementById("Enforce_HwpCtrl");
}


// remove Local_File_Access_Alert of HwpCtrl Object
// argument[0] : HwpCtrl Object
function registerModule(hwpCtrl) {
	try {
		/*if (hwpCtrl != null) {
			return hwpCtrl.RegisterModule("FilePathCheckDLL", "HwpLocalFileAccess");
		} else {
			errormessage();
		}*/
	} catch (error) {
		errormessage("registerModule");
	}
}


function checkCurrentVersion(hwpCtrl) {
//	var result = false;
//		var version = getCurrentVersion();
//		if(version > 14 || version < 9){
//			alert("작성기는 워드2010이하의 버전에서 사용가능합니다.");
//			window.close();
//			return result;
//		}else{
//			result = true;
//		}
	
	var result = true;

	return result;
}

//워드 버전 확인
function isAccessibleVersion(){
	var result = false;

	return result;
}


// check HwpCtrl Version
// argument[0] : HwpCtrl Object
function currentVersion(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var curVersion = hwpCtrl.Version;

			if ((curVersion & 0x08000000) == 0x08000000)
				return "2010";
			else if ((curVersion & 0x07000000) == 0x07000000)
				return "2007";
			else if ((curVersion & 0x06050000) == 0x06050000)
				return "2005";
			else if ((curVersion & 0x06000000) == 0x06000000)
				return "2004";
			else if ((curVersion & 0x05000000) == 0x05000000)
				return "2002";
			else
				return "0000";
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("currentVersion");
	}
}


// initialize HwpCtrl Toolbar
// argument[0] : HwpCtrl Object
function initToolbar(hwpCtrl, limit)	{
	try {
		if (hwpCtrl != null) {
			if (typeof(limit) == "undefined" || isNaN(parseInt(limit)))
				limit = 0;
			else
				limit = parseInt(limit);

			hwpCtrl.SetToolBar(-1, "TOOLBAR_MENU");
			hwpCtrl.SetToolBar(-1, "TOOLBAR_STANDARD");
			hwpCtrl.SetToolBar(-1, "TOOLBAR_FORMAT");
		//	hwpCtrl.SetToolBar(-1, "TOOLBAR_DRAW");
		//	hwpCtrl.SetToolBar(-1, "TOOLBAR_TABLE");
		//	hwpCtrl.SetToolBar(-1, "TOOLBAR_IMAGE");
		//	hwpCtrl.SetToolBar(-1, "TOOLBAR_HEADERFOOTER");

			// 찾기/바꾸기 Modeless Dialog에서 한글입력이 안되므로 한글입력이 되는 Modal Dialog로 변경
			hwpCtrl.ReplaceAction("FindDlg", "HwpCtrlFindDlg");
			hwpCtrl.ReplaceAction("ReplaceDlg", "HwpCtrlReplaceDlg");
			hwpCtrl.ReplaceAction("TableCreate", "HwpCtrlTableCreate");

			var lockCommandList = new Array(
				// 아래 Action은 OLE관련 문제로 인해 막아 놓는다.
				"OleCreateNew",
				"InsertTextArt",
				"InsertChart",
				"InsertVoice",

				// 아래 Action은 새창이나, 새탭을 사용하므로 막아 놓는다.
				"LabelTemplate", // 라벨 문서 만들기
				"ManuScriptTemplate", // 원고지 쓰기
				"IndexMark", // 찾아보기 표시
				"MakeIndex", // 찾아보기 만들기
				"MakeContents" // 차례만들기
			);

			var commandcount = lockCommandList.length;
			for (i = 0; i < commandcount; i++) {
				hwpCtrl.LockCommand(lockCommandList[i], true);
			}

			if ((limit & 1) == 1)
				hwpCtrl.LockCommand("Cut", true);
			if ((limit & 2) == 2)
				hwpCtrl.LockCommand("Copy", true);
			if ((limit & 4) == 4)
				hwpCtrl.LockCommand("Paste", true);
			if ((limit & 8) == 8)
				hwpCtrl.LockCommand("Print", true);

			var engineProperties = hwpCtrl.EngineProperties;
			engineProperties.SetItem("EnableAutoSpell", 1);
			hwpCtrl.EngineProperties = engineProperties;

			hwpCtrl.ShowStatusBar(1);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("initToolbar");
	}
}


// show HwpCtrl Toolbar
// argument[0] : HwpCtrl Object
function showToolbar(hwpCtrl, editMode) {
	try {
		if (hwpCtrl != null) {
			if (editMode == 0){
				hwpCtrl.EditMode = editMode;
				drawMenu(0);
			}
			else if ((editMode & 0x01) != 0 || (editMode & 0x02) != 0){
				hwpCtrl.EditMode = editMode;
				drawMenu(1);
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("showToolbar");
	}
}


// change Font
// argument[0] : HwpCtrl Object
// argument[1] : Font
// argument[2] : IsBold(true/false)
// argument[3] : Font Size
function setDefaultFont (hwpCtrl, font, bold, height) {
	try {
		if (hwpCtrl != null) {
		    var act = hwpCtrl.CreateAction("CharShape");
		    var set = hwpCtrl.CreateSet("CharShape");
		    act.GetDefault(set);
		    set.SetItem("FaceNameHangul", font);
		    set.SetItem("FaceNameLatin", font);
		    set.SetItem("FaceNameHanja", font);
		    set.SetItem("FaceNameJapanese", font);
		    set.SetItem("FaceNameOther", font);
		    set.SetItem("FaceNameSymbol", font);
		    set.SetItem("FaceNameUser", font);
		    set.SetItem("Bold", bold);
		    set.SetItem("Height", height);
		    act.Execute(set);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setDefaultFont");
	}
}


// set Margin of Page
// argument[0] : HwpCtrl Object
// argument[1] : Left Margin
// argument[2] : Right Margin
function setPageMargin(hwpCtrl, leftmargin, rightmargin) {
	try {
		if (hwpCtrl != null) {
		    var action = hwpCtrl.CreateAction("PageSetup");
			var set = action.CreateSet();
			action.GetDefault(set);

			var itemSet = set.CreateItemSet("PageDef","PageDef");
			set.SetItem("ApplyTo", 3);	//적용범위 : 문서전체

			//1mm = 283.465 HWPUNITs
			itemSet.SetItem("LeftMargin", leftmargin);
			itemSet.SetItem("RightMargin", rightmargin);

			action.Execute(set);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setPageMargin");
	}
}


// change Hwp frame scale
// argument[0] : Scale
// argument[1] : Left Frame
// argument[2] : Right Frame
function setFrameScale(scale, documentSide, enforceSide) {
	try {
		var documentframe = "documentSide";
		var enforceframe = "enforceSide";
		if (arguments.length == 3) {
			documentframe = documentSide;
			enforceframe = enforceSide;
		}

		var documentSide = $(documentframe);
		var enforceSide = $(enforceframe);

		if (documentSide != null) {
			var height = document.body.clientHeight - documentSide.getBoundingClientRect().top + 2;
			if (height > 0)
				documentSide.style.height = height;// - 50;	// for debug

			if (documentSide != null && enforceSide != null) {
				var nHeight = document.body.clientHeight - documentSide.getBoundingClientRect().top + 2;
				if (nHeight > 0)
					enforceSide.style.height = height;// - 50;		// for debug
			}

			if (scale == 0) {
				documentSide.style.display = "block";
				if (enforceSide != null)
					enforceSide.style.display = "none";
			} else if (scale == 1) {
				documentSide.style.display = "none";
				if (enforceSide != null)
					enforceSide.style.display = "block";
			} else if (scale == 2) {
				documentSide.style.display = "block";
				if (enforceSide != null)
					enforceSide.style.display = "block";
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setFrameScale");
	}
}


// clear HwpCtrl frame
// argument[0] : HwpCtrl Object
// argument[1] : Target Frame
function clearHwpCtrl(hwpCtrl, targetFrame) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.Clear(1);

			var targetSide = $(targetFrame);
			if (targetSide != null) {
				targetSide.innerHTML = "";
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearHwpCtrl");
	}
}


// clear Hwp Document
// argument[0] : HwpCtrl Object
function clearDocument(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.Clear(1);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearDocument");
	}
}


// get Edit mode
// argument[0] : HwpCtrl Object
function getEditMode(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			return hwpCtrl.EditMode;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getEditMode");
	}
}


// change Edit mode
// argument[0] : HwpCtrl Object
// argument[1] : EditMode - 0 : readonly, 1 : edit, 2 : edit(form)
// argument[2] : Guide Line - false : hide, true : show
function changeEditMode(hwpCtrl, editMode, guideline) {
	try {
		//jkkim added for 내용수정 완료 읽기모드 전환  : KB : 2008.05.19
		hwpCtrl.EditMode = editMode;
		var docState = $("#docState").val();
		var lobcode = $("#lobCode").val();
		if(docState == "APP100"&&lobcode == "LOB001"){
			if (g_DocWord.Sections.Count != 1)
			{
				protectSection();
			}
		}else if(editMode == 0){
			if (g_DocWord.ProtectionType != -1)
				g_DocWord.UnProtect();
			
			if (g_DocWord.ProtectionType == -1){
				g_DocWord.Protect(1);
			}
		}else if(editMode == 2){
			if (g_DocWord.ProtectionType != -1)
				g_DocWord.UnProtect();
		}

	} catch (error) {
		errormessage("changeEditMode");
	}
}


// change HwpCtrl View scale
// argument[0] : HwpCtrl Object
// argument[1] : ZoomType
// argument[2] : Ratio
function setHwpViewScale(hwpCtrl, zoomType, ratio)	{
	try {
		if (hwpCtrl != null) {
			var viewProp = hwpCtrl.ViewProperties;
			if (zoomType == 0)	{
				viewProp.SetItem("ZoomType", zoomType);
				viewProp.SetItem("ZoomRatio", ratio);
			} else {
				viewProp.SetItem("ZoomType", zoomType);
			}
			hwpCtrl.ViewProperties = viewProp;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setHwpViewScale");
	}
}

// initialize Menu for Approval
// argument[0] : HwpCtrl Object
// argument[1] : Font for Approval
function initializeMenu(hwpCtrl, font, limit, show) {
	try {
		if (hwpCtrl != null) {
			if (typeof(show) == "undefined")
				show = 1;
			changeEditMode(hwpCtrl, 1);
			setDefaultFont(hwpCtrl, font, 0, 1200);
			initToolbar(hwpCtrl, limit);
			showToolbar(hwpCtrl, show);
			setLockCommand(hwpCtrl,"Copy", false);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("initializeMenu");
	}
}


// change Window Full Size
function changeMaximumSize() {
	try {
		var width  = screen.availWidth;
		var height = screen.availHeight;
		window.resizeTo(width, height);
		setFrameScale(0);
	} catch (error) {
		errormessage("changeMaximumSize");
	}
}




// ///////////////////////////////////////
// Manage Document File
// ///////////////////////////////////////

// save Content
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function saveContent(hwpCtrl, filepath) {
	try {
		if (hwpCtrl != null) {
			selectBlock(hwpCtrl, HwpConst.Form.Content);

			var action = hwpCtrl.CreateAction("SaveBlockAction");
		    var set = hwpCtrl.CreateSet("FileSaveBlock");
		    action.GetDefault(set);
		    set.SetItem("FileName", filepath);
		    set.SetItem("Format", "HWP");
		    set.SetItem("Argument", "lock:FALSE");

			return (action.Execute(set) == 1) ? true : false;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("saveContent");
	}
}


// save Pub Content
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function savePubContent(hwpCtrl, filename) {
	try {
		if (hwpCtrl != null) {
			// check pubdoc filter for export
			if (!isPubFilter(hwpCtrl, 0)) {
				alert(HwpConst.Msg.NOT_INSTALLED_PUBFILTER);
				return false;
			}

			moveToPos(hwpCtrl, HwpConst.Form.Content);
			var fieldtype = "";
			if ((hwpCtrl.CurFieldState & 0xf) == 1)
				fieldtype = "cell";
			else if ((hwpCtrl.CurFieldState & 0xf) == 2)
				fieldtype = "clickhere";
			else {
				alert("Field '" + HwpConst.Form.Content + "' is not cell or clikhere object.");
				return false;
			}

			// UTF-8형식으로 저장됨
			var HwpArguments = "lock:FALSE;fieldtype:" + fieldtype + ";fieldname:" + HwpConst.Form.Content;
			if (filename == "")
				filename = "content.xml";

			var targetfile = FileManager.getlocaltempdir() + filename;
			if (hwpCtrl.SaveAs(targetfile, "PUBDOCBODY", HwpArguments)) {
				if (existSpanTag(targetfile)) {		// span 태그가 없어야 함
					alert(HwpConst.Msg.INCORRECT_PUBFILTER);
					return false;
				} else {
					return targetfile;
				}
			} else {
				return "";
			}
		} else {
			errormessage();
			return "";
		}
	} catch (error) {
		errormessage("savePubContent");
	}
}


// save Hwp Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function saveHwpDocument(hwpCtrl, filepath, lock) {
	var bResult = false;
	try {
		var nProtectionType = g_DocWord.ProtectionType;
		if (nProtectionType != -1)
			g_DocWord.UnProtect();
		if(!lock){
			bResult = saveAsDocument(hwpCtrl, filepath, lock);
		}else{
			hwpCtrl.SaveDocument("");	// Call OCX Methods - ""(NULL) Parameter Means Same Document as Open
		}
		if (nProtectionType != -1)
			g_DocWord.Protect(nProtectionType);
	} catch (error) {
		errormessage("saveHwpDocument");
	}
	return bResult;
}

// save As Hwp Document
// argument[0] : HwpCtrl Object
function saveAsHwpDocument(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			moveToPos(hwpCtrl, 2);

			var action = hwpCtrl.CreateAction("HwpCtrlFileSaveAs");
			var set = action.CreateSet();
			action.Execute(set);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("saveAsHwpDocument");
	}
}


// save Pub Document
// return pubdocument path with filename;
// argument[0] : HwpCtrl Object
// argument[1] : filename except localpath
// arugment[2] : boolean - yes or not for move to top of file
function savePubDocument(hwpCtrl, filename, moveTopOfFile) {
	try {
		if (hwpCtrl != null) {
			// check pubdoc filter for export
			if (!isPubFilter(hwpCtrl, 0)) {
				alert(HwpConst.Msg.NOT_INSTALLED_PUBFILTER);
				return false;
			}

			if (moveTopOfFile)
				moveToPos(hwpCtrl, 2);

			if (filename == "")
				filename = "content.xml";

			var targetfile = FileManager.getlocaltempdir() + filename;

			// UTF-8형식으로 저장됨
			var bReturn = hwpCtrl.SaveAs(targetfile, "PUBDOCBODY", "lock:FALSE");

			if (!bReturn || FileManager.getfilesize(targetfile) < 1) {
				targetfile = null;
			} else {
				if (existSpanTag(targetfile)) {		// span 태그가 없어야 함
					alert(HwpConst.Msg.INCORRECT_PUBFILTER);
					return false;
				}
			}

			return targetfile;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("savePubDocument");
	}
}


//save HTML Document
//argument[0] : HwpCtrl Object
//argument[1] : File Full Path
function saveHtmlDocument(hwpCtrl, filepath, lock) {
	try {
		var downloadPath = FileManager.getlocaltempdir();
		var strDocName = g_DocWord.Name;	// Embeded 문서이므로 "XXXX.doc의 문서"로 표시됨.
		var nIndex = strDocName.lastIndexOf(".");
		
		if (nIndex != -1)
			strDocName = strDocName.substring(0, nIndex) + ".doc";
		else
		{
			nIndex = strDocName.lastIndexOf("의 문서");
			if (nIndex != -1)
				strDocName = strDocName.substring(0, nIndex) + ".doc";
			else
				strDocName = strDocName + ".doc";
		}
		
		// 현재 편집 상태를 저장한다.
		//saveHwpDocument(hwpCtrl, downloadPath + strDocName, lock);
		if (downloadPath + strDocName != filepath){
			initializeHwp("mobilehwp", "print");  // 인쇄용 컨트롤 사용
			registerModule(Print_HwpCtrl);
			openWordDocument(Print_HwpCtrl, downloadPath + strDocName);
			
			//mobile용 html 생성
			//SaveAs (Object FileName, Object FileFormat, Object LockComments, Object Password, Object AddToRecentFiles, Object WritePassword,
			//		  Object ReadOnlyRecommended, Object EmbedTrueTypeFonts, Object SaveNativePictureFormat, Object SaveFormsData, Object SaveAsAOCELetter,
			//	      Object Encoding, Object InsertLineBreaks, Object AllowSubstitutions, Object LineEnding, Object AddBiDiMarks)
			
			var fileFormat = 8;			//wdFormatDocument : 0, wdFormatText : 2, wdFormatHTML : 8, wdFormatXML : 11
			var encoding = 51949;		//msoEncodingUSASCII : 20127, msoEncodingUTF8 : 65001, msoEncodingEUCKorean : 51949
			var lineEnding = 0;			//wdCRLF(Carriage return plus line feed) : 0, wdCROnly : 1, wdLFOnly : 2, wdLFCR : 3, wdLSPS(Not supported) : 4
			
			Print_HwpCtrl.WordDocument.SaveAs(filepath, fileFormat, false, "", true, "", false, false, true, true, false, encoding, false, false, lineEnding, false);
		}
		
	} catch (error) {
		errormessage("saveHtmlDocument");
	}
}

//SaveAs Document
//argument[0] : HwpCtrl Object
//argument[1] : File Full Path
function saveAsWordDocument(hwpCtrl, filepath, openfilename) {
	try {
		if(filepath !="" && filepath != null) {
			var fileFormat = 1;		// wdFormatTemplate : 1 (WORD)
			var nIndex = filepath.lastIndexOf(".");
			
			if (nIndex > 0) {
				var strFileType = filepath.substring(nIndex+1, filepath.length);
				
				if(strFileType == "html") {
					fileFormat = 8;		//wdFormatHTML : 8 (HTML)
				}else if(strFileType == "txt") {
					fileFormat = 2;		//wdFormatText : 2 (TEXT)
				}
			}
			
			var encoding = 51949;		//msoEncodingUSASCII : 20127, msoEncodingUTF8 : 65001, msoEncodingEUCKorean : 51949
			var lineEnding = 0;			//wdCRLF(Carriage return plus line feed) : 0, wdCROnly : 1, wdLFOnly : 2, wdLFCR : 3, wdLSPS(Not supported) : 4
			
			if(openfilename != "" && openfilename != null) {
				var downloadPath = FileManager.getlocaltempdir();
				
				initializeHwp("mobilehwp", "print");  // 인쇄용 컨트롤 사용
				registerModule(Print_HwpCtrl);
				openWordDocument(Print_HwpCtrl, downloadPath + openfilename);
				
				//SaveAs (Object FileName, Object FileFormat, Object LockComments, Object Password, Object AddToRecentFiles, Object WritePassword,
				//		  Object ReadOnlyRecommended, Object EmbedTrueTypeFonts, Object SaveNativePictureFormat, Object SaveFormsData, Object SaveAsAOCELetter,
				//	      Object Encoding, Object InsertLineBreaks, Object AllowSubstitutions, Object LineEnding, Object AddBiDiMarks)
				
				Print_HwpCtrl.WordDocument.SaveAs(filepath, fileFormat, false, "", true, "", false, false, true, true, false, encoding, false, false, lineEnding, false);
			}else {
				//SaveAs (Object FileName, Object FileFormat, Object LockComments, Object Password, Object AddToRecentFiles, Object WritePassword,
				//		  Object ReadOnlyRecommended, Object EmbedTrueTypeFonts, Object SaveNativePictureFormat, Object SaveFormsData, Object SaveAsAOCELetter,
				//	      Object Encoding, Object InsertLineBreaks, Object AllowSubstitutions, Object LineEnding, Object AddBiDiMarks)
				
				g_DocWord.SaveAs(filepath, fileFormat, false, "", true, "", false, false, true, true, false, encoding, false, false, lineEnding, false);
			}
		}

	} catch (error) {
		errormessage("saveAsDocument");
	}
}

//save HTML Document
//argument[0] : HwpCtrl Object
//argument[1] : File Full Path
function saveAsDocument(hwpCtrl, filepath, lock) {
	var bResult = false;
	try {
		// File System Copy하기 전에 현재 편집 상태를 저장한다.
		hwpCtrl.SaveDocument("");	
		var downloadPath = FileManager.getlocaltempdir();
		var strDocName = g_DocWord.Name;	// Embeded 문서이므로 "XXXX.doc의 문서"로 표시됨.
		var nIndex = strDocName.lastIndexOf(".");
		if (nIndex != -1)
			strDocName = strDocName.substring(0, nIndex) + ".doc";
		else
		{
			nIndex = strDocName.lastIndexOf("의 문서");
			if (nIndex != -1)
				strDocName = strDocName.substring(0, nIndex) + ".doc";
			else
				strDocName = strDocName + ".doc";
		}
		if (downloadPath + strDocName != filepath){
			//copyLocalFile(downloadPath + strDocName, filepath);
			document.getElementById("FileWizard").CopyLocalFile(downloadPath + strDocName, filepath);
			bResult = true;
		}
		
	} catch (error) {
		errormessage("saveHtmlDocument");
	}
	
	return bResult;
}

//open Hwp Document
//argument[0] : HwpCtrl Object
//argument[1] : File Full Path
function openHwpDocument(wordCtrl, filepath) {
	try {
		if (wordCtrl != null) {

			return wordCtrl.OpenDocument(typeOfPath(filepath));
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("openWordDocument");
	}
}

// open Hwp Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function openWordDocument(wordCtrl, filepath) {
	try {
		if (wordCtrl != null) {

			return wordCtrl.OpenDocument(typeOfPath(filepath));
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("openWordDocument");
	}
}


// open HTML Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function openHtmlDocument(hwpCtrl, resourcePath, movepos) {
	try {
		if (hwpCtrl != null) {
			if (arguments.length == 3)
				moveToPos(hwpCtrl, movepos);

			return hwpCtrl.Open(typeOfPath(resourcePath), "HTML", "lock:FALSE;code:ks");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("openHtmlDocument");
	}
}

//PC문서
//bodyfilepath : Local 파일 위치
function openLocalDocument(bodyfilepath){
	//한글 ActiveX 초기화
	initializeHwp("localhwp", "print");  // 인쇄용 컨트롤 사용
	
	registerModule(Print_HwpCtrl);
	openWordDocument(Print_HwpCtrl, bodyfilepath);

	// PC 파일에 본문 Cell이 없으면 내용전체를 가져온다. 
	if(!Print_HwpCtrl.WordDocument.Bookmarks.Exists(HwpConst.Form.Content + "1")) {
		insertFile(Document_HwpCtrl, bodyfilepath, HwpConst.Form.Content + "1");
	} else {
		// PC 파일의 본문 Cell 내용을 가져온다.
		var contentText = Print_HwpCtrl.WordDocument.Bookmarks(HwpConst.Form.Content + "1").Range.Cells(1).Range.Text;
		putFieldText(Document_HwpCtrl, HwpConst.Form.Content + "1", contentText);
	}
}

// open Text Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function openTextDocument(hwpCtrl, filepath) {
	try {
		if (hwpCtrl != null) {
			return hwpCtrl.Open(typeOfPath(filepath), "TEXT", "lock:FALSE;code:ks");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("openTextDocument");
	}
}


// open PubDoc Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function openPubDocument(hwpCtrl, filepath) {
	try {
		if (hwpCtrl != null) {
			// check pubdoc filter for import
			if (!isPubFilter(hwpCtrl, 1)) {
				alert(HwpConst.Msg.NOT_INSTALLED_PUBFILTER);
				return false;
			}

			return hwpCtrl.Open(typeOfPath(filepath), "PUBDOCBODY", "lock:FALSE");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("openPubDocument");
	}
}


// insert Hwp Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
// argument[2] : Move Position
function insertHwpDocument(hwpCtrl, filepath, movepos) {
	try {
		if (hwpCtrl != null) {
			if (arguments.length == 3)
				moveToPos(hwpCtrl, movepos);

			return hwpCtrl.insert(typeOfPath(filepath), "HWP", "lock:FALSE;versionwarning:FALSE");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertHwpDocument");
	}
}


// Insert HTML Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function insertHtmlDocument(hwpCtrl, resourcePath, movepos) {
	try {
		if (hwpCtrl != null) {
			if (arguments.length == 3)
				moveToPos(hwpCtrl, movepos);
			
			g_DocWord = Document_HwpCtrl.WordDocument;
		
			var myRange = g_DocWord.Bookmarks(HwpConst.Form.Content + "1").Range.Paragraphs(1).Range;
			myRange.Insertfile(resourcePath,"",false,false,false);

		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertHtmlDocument");
	}
}


// Insert Text Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function insertTextDocument(hwpCtrl, resourcePath, movepos) {
	try {
		if (hwpCtrl != null) {
			if (arguments.length == 3)
				moveToPos(hwpCtrl, movepos);

			g_DocWord = Document_HwpCtrl.WordDocument;
			
			var myRange = g_DocWord.Bookmarks(HwpConst.Form.Content + "1").Range.Paragraphs(1).Range;
			myRange.Insertfile(resourcePath,"",false,false,false);
			
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertTextDocument");
	}
}


// insert PubDoc Document
// argument[0] : HwpCtrl Object
// argument[1] : File Full Path
function insertPubDocument(hwpCtrl, filepath, movepos) {
	try {
		if (hwpCtrl != null) {
			if (arguments.length == 3)
				moveToPos(hwpCtrl, movepos);

			// check pubdoc filter for import
			if (!isPubFilter(hwpCtrl, 1)) {
				alert(HwpConst.Msg.NOT_INSTALLED_PUBFILTER);
				return false;
			}

			return hwpCtrl.insert(typeOfPath(filepath), "PUBDOCBODY", "lock:FALSE");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertPubDocument");
	}
}

function insertFile(hwpCtrl, resourcePath, movepos) {
	try {
		if (hwpCtrl != null) {
			g_DocWord = Document_HwpCtrl.WordDocument;
			
			//movepos Cell에 resourcePath 파일 insert
			var myRange = g_DocWord.Bookmarks(movepos).Range.Paragraphs(1).Range;
			myRange.Insertfile(resourcePath,"",false,false,false);
			
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertFile");
	}
}

// insert Text & save Document
// argument[0] : HwpCtrl Object
// argument[1] : filename for save
// argument[2] : Text Content for insert
function saveTextContent(hwpCtrl, sfilename, content) {
	try {
		if (hwpCtrl != null) {
			insertText(hwpCtrl, content);

			FileManager.setlocaltempdir();
			var downloadPath = FileManager.getlocaltempdir();
			sfilename = sfilename.substring(0, sfilename.length - 4) + ".hwp";
			if (saveHwpDocument(hwpCtrl, downloadPath + sfilename, false))
				return downloadPath + sfilename;
			else
				return null;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("saveTextContent");
	}
}


// check pubdoc filter if it is usabled
// argument[0] : HwpCtrl Object
// argument[1] : type, 0 : Export, 1 : Import
function isPubFilter(hwpCtrl, type) {
	try {
		if (hwpCtrl != null) {
			var action = hwpCtrl.CreateAction("GetDocFilters");
			var set = hwpCtrl.CreateSet("DocFilters");
			set.SetItem("Type", type);
			action.Execute(set);
			var filters = set.Item("DocFilters");

			return (filters.indexOf(HwpConst.Data.PubFilter) == -1) ? false : true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("isPubFilter");
	}
}


// print HwpCtrl
// argument[0] : HwpCtrl Object
function printHwpDocument(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			if (getCurrentVersion() < 9)
			{
			   alert("MS Word 97 에서는 프린트 기능을 지원하지 않습니다.\r\n저장하기 기능을 사용하여 로컬PC로 저장하신후 MS Word에서 인쇄하시기 바랍니다.");
			   return true;
			}
			hwpCtrl.FilePrint();
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("printHwpDocument");
	}
}


// delete Hwp Document
// argument[0] : HwpCtrl Object
function deleteDocument(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			runHwpAction(hwpCtrl, "Delete");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("deleteDocument");
	}
}


// get Approver Count
// argument[0] : HwpCtrl Object
function getConsiderCount(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "*" + HwpConst.Appr.Consider + loop)) {
					if (!existField(hwpCtrl, HwpConst.Appr.Consider + loop))
						break;
				}

				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getConsiderCount");
	}
}


// get Assistance Count
// argument[0] : HwpCtrl Object
function getAssistanceCount(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "*" + HwpConst.Appr.Assistance + loop)) {
					if (!existField(hwpCtrl, HwpConst.Appr.Assistance + loop))
						break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getAssistanceCount");
	}
}


//get Auditor Count
//argument[0] : HwpCtrl Object
function getAuditorCount(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "*" + HwpConst.Appr.Audit + loop)) {
					if (!existField(hwpCtrl, HwpConst.Appr.Audit + loop))
						break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getAuditCount");
	}
}


//get Approver Count per Line
//argument[0] : HwpCtrl Object
function getLineApproverCount(hwpCtrl, linenum) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "*" + HwpConst.Appr.Consider + linenum + "-" + loop)) {
					if (!existField(hwpCtrl, HwpConst.Appr.Consider + linenum + "-" + loop)) {
						break;
					}
				}

				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getLineApproverCount");
	}
}


// check Arbitrary Field
// argument[0] : HwpCtrl Object
function existArbitraryField(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var result = false;
			if (isStandardForm(hwpCtrl)) {
				var loop = 1;		// start with 1.
				while(true) {
					if (existField(hwpCtrl, "@" + HwpConst.Appr.Consider + loop)) {
						if (existField(hwpCtrl, HwpConst.Appr.Consider + loop)) {
							loop++;
						} else {
							result = true;
							break;
						}
					} else {
						break;
					}
				}
			}

			return result;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("existArbitraryField");
	}
}


// clear Approver Table
// argument[0] : HwpCtrl Object
function clearApprTable(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			/*var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, HwpConst.Appr.Consider + loop))
					break;

				var fieldlist = "*" + HwpConst.Appr.Consider + loop + "\u0002"
					+ "@" + HwpConst.Appr.Consider + loop + "\u0002"
					+ "#" + HwpConst.Appr.Consider + loop + "\u0002"
					+ HwpConst.Appr.Consider + (loop++);
				var valuelist = "\u0002\u0002\u0002";
				putFieldText(hwpCtrl, fieldlist, valuelist);
			}
			loop = 1;
			while(true) {		// clear Assistance Cell
				if (!existField(hwpCtrl, HwpConst.Appr.Assistance + loop))
					break;

				var fieldlist = "*" + HwpConst.Appr.Assistance + loop + "\u0002"
				+ "@" + HwpConst.Appr.Assistance + loop + "\u0002"
				+ "#" + HwpConst.Appr.Assistance + loop + "\u0002"
				+ HwpConst.Appr.Assistance + (loop++);
				var valuelist = "\u0002\u0002\u0002";
				putFieldText(hwpCtrl, fieldlist, valuelist);
			}
			loop = 1;
			while(true) {		// clear Auditor Cell
				if (!existField(hwpCtrl, HwpConst.Appr.Audit + loop))
					break;

				var fieldlist = "*" + HwpConst.Appr.Audit + loop + "\u0002"
				+ "@" + HwpConst.Appr.Audit + loop + "\u0002"
				+ "#" + HwpConst.Appr.Audit + loop + "\u0002"
				+ HwpConst.Appr.Audit + (loop++);
				var valuelist = "\u0002\u0002\u0002";
				putFieldText(hwpCtrl, fieldlist, valuelist);
			}
			loop = 1;
			while(true) {		// clear Draft Dept Cell
				if (!existField(hwpCtrl, HwpConst.Appr.Consider + "1-" + loop))
					break;

				var fieldlist = "*" + HwpConst.Appr.Consider + "1-" + loop + "\u0002"
				+ "@" + HwpConst.Appr.Consider + "1-" + loop + "\u0002"
				+ "#" + HwpConst.Appr.Consider + "1-" + loop + "\u0002"
				+ HwpConst.Appr.Consider + "1-" + (loop++);
				var valuelist = "\u0002\u0002\u0002";
				putFieldText(hwpCtrl, fieldlist, valuelist);
			}
			loop = 1;
			while(true) {		// clear Exec Dept Cell
				if (!existField(hwpCtrl, HwpConst.Appr.Consider + "2-" + loop))
					break;

				var fieldlist = "*" + HwpConst.Appr.Consider + "2-" + loop + "\u0002"
				 + "@" + HwpConst.Appr.Consider + "2-" + loop + "\u0002"
				 + "#" + HwpConst.Appr.Consider + "2-" + loop + "\u0002"
				 + HwpConst.Appr.Consider + "2-" + (loop++);
				var valuelist = "\u0002\u0002\u0002";
				putFieldText(hwpCtrl, fieldlist, valuelist);
			}*/
			clearApprovalField();
			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearApprTable");
	}
}


// remove Approver Table
// argument[0] : HwpCtrl Object
function removeApprTable(hwpCtrl, fieldname) {
	try {
		if (hwpCtrl != null) {
			var dset = hwpCtrl.ViewProperties;
			var prevFlag = dset.Item("OptionFlag");
			dset.SetItem("OptionFlag", prevFlag | 2);
			hwpCtrl.ViewProperties = dset;
			if (typeof(fieldname) == "undefined")
				fieldname = "StandardForm";
			moveToPos(hwpCtrl, fieldname);	// 표준기안문 셀로 이동(StandardForm 셀(검토1, 협조1) -> 상위객체)
			moveToPos(hwpCtrl, 24);
			var parentCtrl = hwpCtrl.ParentCtrl;
			var PosSet = hwpCtrl.GetPosBySet();
			var nCurPara = PosSet.Item("Para");			// para
			var nCurPos = PosSet.Item("Pos");			// pos
			hwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);	// 표 선택
			hwpCtrl.Run("Delete");
			dset = hwpCtrl.ViewProperties;
			dset.SetItem("OptionFlag", prevFlag);
			hwpCtrl.ViewProperties = dset;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("removeApprTable");
	}
}


// remove Approver Table
// argument[0] : HwpCtrl Object
// argument[1] : location of table resource for sign
function insertApprTable(hwpCtrl, filepath) {
	try {
		if (hwpCtrl != null) {
			insertHwpDocument(hwpCtrl, filepath/* , HwpConst.Field.StandardForm*/);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertApprTable");
	}
}


//replace Approver Table
//argument[0] : HwpCtrl Object
//argument[1] : location of table resource for sign
function replaceApprTable(hwpCtrl, filepath, fieldname) {
	try {
		if (hwpCtrl != null) {
			if (isStandardForm(hwpCtrl)) {
				removeApprTable(hwpCtrl, "StandardForm");
				insertHwpDocument(hwpCtrl, filepath); /* HwpConst.Field.StandardForm */
			} else {
				if (typeof(fieldname) != "undefined") {
					if (existField(hwpCtrl, fieldname)) {
						if (fieldname == HwpConst.Field.ConsiderLine) {
							if (existField(hwpCtrl, HwpConst.Appr.Consider + "1")) {
								removeApprTable(hwpCtrl, HwpConst.Appr.Consider + "1");
							} else {
								moveToPos(hwpCtrl, HwpConst.Field.ConsiderLine);
							}
							insertHwpDocument(hwpCtrl, filepath); /* HwpConst.Appr.Consider */
						} else if (fieldname == HwpConst.Field.AssistanceLine) {
							if (existField(hwpCtrl, HwpConst.Appr.Assistance + "1")) {
								removeApprTable(hwpCtrl, HwpConst.Appr.Assistance + "1");
							} else {
								moveToPos(hwpCtrl, HwpConst.Field.AssistanceLine);
							}
							insertHwpDocument(hwpCtrl, filepath); /* HwpConst.Field.AssistanceLine */
						} else if (fieldname == HwpConst.Field.DraftDeptLine) {
							removeApprTable(hwpCtrl, HwpConst.Appr.Consider + "1-1");
							insertHwpDocument(hwpCtrl, filepath); /* HwpConst.Field.DraftDeptLine */
						} else if (fieldname == HwpConst.Field.ExecDeptLine) {
							removeApprTable(hwpCtrl, HwpConst.Appr.Consider + "2-1");
							insertHwpDocument(hwpCtrl, filepath); /* HwpConst.Field.ExecDeptLine */
						} else {
							clearApprTable(hwpCtrl);
						}
					} else {
						clearApprTable(hwpCtrl);
					}
				} else {
					clearApprTable(hwpCtrl);
				}
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("replaceApprTable");
	}
}


// remove Table Row
// argument[0] : HwpCtrl Object
// argument[1] : fieldname of row to remove
function removeTableRow(hwpCtrl, fieldname) {
	try {
		if (hwpCtrl != null) {
			moveToPos(hwpCtrl, fieldname);
			runHwpAction(hwpCtrl, "TableDeleteRow");
		} else {
			// errormessage();
		}
	} catch (error) {
		// errormessage("removeTableRow");
	}
}


// make Distribute Document
// argument[0] : HwpCtrl Object
// argument[1] : approvercount of Approvers
function makeArbitraryField(hwpCtrl, approvercount) {
	try {
		if (hwpCtrl != null) {
			// backup editmode
			var editmode = getEditMode(hwpCtrl);
			// change editmode. because we can merge table on edit type
			hwpCtrl.EditMode = 1;
		    selectBlock(hwpCtrl, "@" + HwpConst.Appr.Consider + approvercount);
		    selectBlock(hwpCtrl, HwpConst.Appr.Consider + approvercount);
		    runHwpAction(hwpCtrl, "TableMergeCell");
		    // restore editmode
			hwpCtrl.EditMode = editmode;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("makeArbitraryField");
	}
}


// make Distribute Document
// argument[0] : HwpCtrl Object
// argument[1] : Password for Distribute Document
function makeDistributeDocument(hwpCtrl, print, copy) {
	try {
		if (hwpCtrl != null) {
			if (typeof(print) == "undefined") {
				print = false;
			}
			if (typeof(copy) == "undefined") {
				copy = true;
			}
			var distributeDoc = false;
			var password = getRandomValue();
			var FSSAction = hwpCtrl.CreateAction("FileSetSecurity");
			var FSSSet = null;
			if (FSSAction)
				FSSSet = FSSAction.CreateSet();

			if (FSSAction && FSSSet)
			{
				FSSAction.GetDefault(FSSSet);

				if (password.length > 6)
				{
					FSSSet.SetItem("Password", password);
					FSSSet.SetItem("NoPrint", print);
					FSSSet.SetItem("NoCopy", copy);

					if (!FSSAction.Execute(FSSSet))
					{
						var msg = HwpConst.Msg.FAILED_MAKE_DISTRIBUTE_DOCUMENT;
						if (FSSSet.Item("Password").length <= 6)
							msg += "\n[" + HwpConst.Msg.TOO_SHORT_PASSWORD + "]";
			//			alert(msg);
					}

					distributeDoc = true;
				}
			} else {
				var msg = HwpConst.Msg.FAILED_MAKE_DISTRIBUTE_DOCUMENT;
				if (hwpCtrl.EditMode & 0x10) // 배포용 문서는 0x10 flag 를 포함한다.
					msg += "\n" + HwpConst.Msg.ALREADY_DISTRIBUTE_DOCUMENT + "\n" + HwpConst.Msg.ALREADY_DISTRIBUTE_DOCUMENT;
				else if (hwpCtrl.EditMode == 0)
					msg += "\n" + HwpConst.Msg.READONLY_DOCUMENT;
		//		alert(msg);
			}

			return distributeDoc;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("makeDistributeDocument");
	}
}


// save hwp text
// argument[0] : HwpCtrl Object
function setSavePoint(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			if (existField(hwpCtrl, HwpConst.Form.Title))
				HwpVariable.Title = getWordTextFile(hwpCtrl, HwpConst.Form.Title);

			if (existField(hwpCtrl, HwpConst.Form.Content))
				HwpVariable.Document = getWordTextFile(hwpCtrl, HwpConst.Form.Content);
			//else
			//	HwpVariable.Document = getWordTextFile(hwpCtrl);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setSavePoint");
	}
}


// get hwp text
// argument[0] : HwpCtrl Object
function getTextBody(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			if (existField(hwpCtrl, HwpConst.Form.Content))
				return getTextFile(hwpCtrl, HwpConst.Form.Content);
			else
				return getTextFile(hwpCtrl);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getTextBody");
	}
}


// compare current text with savepoint text
// argument[0] : HwpCtrl Object
function isChanged(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var changed = false;
			var currentItem = getCurrentItem();

			if (existField(hwpCtrl, HwpConst.Form.Title)) {
				var tempTitle	= $("#tempTitle", "#approvalitem" + currentItem).val();	// 임시 본문 제목, 본문이 저장될 때마다 본문의 제목 값 저장
				var bodyTitle 	= getFieldText(hwpCtrl,HwpConst.Form.Title);			// 본문 제목, 본문에서 제목 값을 읽어옴
		
				//제목 변경여부 확인
				if(bodyTitle != "") {
					if (tempTitle != bodyTitle) {
						changed = true;
					}
				}
			}

			return changed;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("isChanged");
	}
}

function getWordTextFile(hwpCtrl, fieldName){
	return g_DocWord.Bookmarks(fieldName).Range.Paragraphs(1).Range.Text;
}


// select Block
// argument[0] : HwpCtrl Object
// argument[1] : field name for copy
function selectBlock(hwpCtrl, movepos) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.MoveToField(movepos, true, true, true);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("selectBlock");
	}
}


// select Block
// argument[0] : HwpCtrl Object
// argument[1] : field name for start position
// argument[2] : field name for end position
function selectBlockEx(hwpCtrl, startpos, endpos) {
	try {
		if (hwpCtrl != null) {
			// move to start position
			moveToPos(hwpCtrl, startpos);
			moveToPos(hwpCtrl, 26);
			moveToPos(hwpCtrl, 10);
			var action = hwpCtrl.CreateAction("DocumentInfo");
			var set = action.CreateSet();
			action.Execute(set);
			var spara = set.Item("CurPara");
			var spos = set.Item("CurPos");
			// move to end position
			moveToPos(hwpCtrl, endpos);
			moveToPos(hwpCtrl, 26);
			action = hwpCtrl.CreateAction("DocumentInfo");
			set = action.CreateSet();
			action.Execute(set);
			var epara = set.Item("CurPara");
			var epos = set.Item("CurPos");
			selectText(hwpCtrl, spara, spos, epara, epos);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("selectBlockEx");
	}
}


// select Text
// argument[0] : HwpCtrl Object
// argument[1] : Start Paragraph
// argument[2] : Start Position
// argument[3] : End Paragraph
// argument[4] : End Position
function selectText(hwpCtrl, spara, spos, epara, epos) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.SelectText(spara, spos, epara, epos);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("selectText");
	}
}


// copy Block
// argument[0] : HwpCtrl Object
// argument[1] : field name for copy
function copyBlock(hwpCtrl, movepos) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.Run("Copy");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("copyBlock");
	}
}


// cut Block
// argument[0] : HwpCtrl Object
// argument[1] : field name for cut
function cutBlock(hwpCtrl, movepos) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.Run("Cut");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("cutBlock");
	}
}


// paste Block
// argument[0] : HwpCtrl Object
// argument[1] : field name for select
function pasteBlock(hwpCtrl, movepos) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.Run("Paste");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("pasteBlock");
	}
}


// delete Table
// argument[0] : HwpCtrl Object
// argument[1] : field name for select
function selectTable(hwpCtrl, movepos) {
	try {
		if (hwpCtrl != null) {
			moveToPos(hwpCtrl, movepos);
			moveToPos(hwpCtrl, 26);
			var posSet = hwpCtrl.GetPosBySet();
			var para = posSet.Item("Para");
			var pos = posSet.Item("Pos");
			selectText(hwpCtrl, para, pos, para, pos + 8);	// 표 선택
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("selectTable");
	}
}


// delete Table
// argument[0] : HwpCtrl Object
// argument[1] : field name for move
function deleteTable(hwpCtrl, movepos) {
	try {
		if (hwpCtrl != null) {
			selectTable(hwpCtrl, movepos);
			runHwpAction(hwpCtrl, "Delete");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("deleteTable");
	}
}


// ///////////////////////////////////////
// Control Document
// ///////////////////////////////////////

// insert Text
// argument[0] : HwpCtrl Object
// argument[1] : Text Content
function insertText(hwpCtrl, content) {
	try {
		if (hwpCtrl != null && content != "") {
			var action = hwpCtrl.CreateAction("InsertText");
			var set = action.CreateSet();
			set.SetItem("Text", content);
			action.Execute(set);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertText");
	}
}


// move Position
// argument[0] : HwpCtrl Object
// argument[1] : Move Position
function moveToPos(position, text, start, select) {
	try {
//		if (Document_HwpCtrl != null) {
//			if (arguments.length == 2) {
//				text = true;
//				start = true;
//				select = false;
//			}
//
//			if (isNaN(parseInt(position)))
//				return Document_HwpCtrl.MoveToField(position, text, start, select);
//			else
//				return Document_HwpCtrl.MovePos(position);
//		} else {
//			errormessage();
//		}
	} catch (error) {
		errormessage("moveToPos");
	}
}


// get Position
// argument[0] : HwpCtrl Object
function getPos(hwpCtrl) {
	try {
		var pos = hwpCtrl.GetPosBySet();
		HwpVariable.Position.List = pos.Item("List");
		HwpVariable.Position.Para = pos.Item("Para");
		HwpVariable.Position.Pos = pos.Item("Pos");
	} catch (error) {
		HwpVariable.Position.List = "";
		HwpVariable.Position.Para = "";
		HwpVariable.Position.Pos = "";

//		errormessage("getPos");
	}
}


// set Position
// argument[0] : HwpCtrl Object
function setPos(hwpCtrl) {
	try {
		if (HwpVariable.Position.List != "" && HwpVariable.Position.Para != ""
				&& HwpVariable.Position.Pos != "") {
			var pos = hwpCtrl.CreateSet("ListParaPos");
			pos.SetItem("List", 0 + HwpVariable.Position.List);
			pos.SetItem("Para", 0 + HwpVariable.Position.Para);
			pos.SetItem("Pos", 0 + HwpVariable.Position.Pos);
			hwpCtrl.SetPosBySet(pos);

			HwpVariable.Position.List = "";
			HwpVariable.Position.Para = "";
			HwpVariable.Position.Pos = "";
		}
	} catch (error) {
		HwpVariable.Position.List = "";
		HwpVariable.Position.Para = "";
		HwpVariable.Position.Pos = "";

//		errormessage("setPos");
	}
}


// break Hwp Page
// argument[0] : HwpCtrl Object
function breakPage(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			return hwpCtrl.Run("BreakPage");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("breakPage");
	}
}


// protect Cell
// argument[0] : HwpCtrl Object
// argument[1] : Field for protect
function protectCell(hwpCtrl, field) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, field))
				return;

			moveToPos(hwpCtrl, field);
			var tp = hwpCtrl.CreateSet("Table");
			var cp = tp.CreateItemSet("Cell", "Cell");
			cp.SetItem("Protected", 1);
			hwpCtrl.CellShape = tp;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("protectCell");
	}
}


// check Empty Cell
// argument[0] : HwpCtrl Object
// argument[1] : Cell Name
function isEmptyCell(hwpCtrl, field) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, field))
				return true;

			// 문단의 끝의 위치가 아니면 해당 셀은 비어있는 셀이 아닌 것으로 간주
			/*var pos = 0;
			moveToPos(hwpCtrl, field);
			hwpCtrl.MovePos(7, 0, 0); //moveEndOfPara
			pos = hwpCtrl.GetPosBySet();

			if (pos.Item("Pos") > 0)
				return false;*/
			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("isEmptyCell");
	}
}


// check Empty ClickHere
// argument[0] : HwpCtrl Object
// argument[1] : ClickHere Name
function isEmptyClickHere(hwpCtrl, field) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, field))
				return true;

			// 누름틀의 내용길이가 0이고 문단 끝의 위치가 16인 경우 본문이 없는 것으로 간주
			var pos = 0;
			var contents = getFieldText(hwpCtrl, field);
			if (contents.length == 0) {
            moveToPos(hwpCtrl, field);
				hwpCtrl.MovePos(7, 0, 0); //moveEndOfPara
				pos = hwpCtrl.GetPosBySet();

				if (pos.Item("Pos") == 16)
					return true;
			}
			return false;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("isEmptyClickHere");
	}
}


// check Exist Document
// argument[0] : HwpCtrl Object
function isExistDocument(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			return scanDocument(hwpCtrl);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("isExistDocument");
	}
}

// Scan Document
// argument[0] : HwpCtrl Object
// argument[1] : Keyword for Scan
function scanDocument(hwpCtrl, keyword) {
	try {
		/*if (hwpCtrl != null) {
			HwpVariable.IsExist = false;
			hwpCtrl.InitScan(0xff, 0x77, 0, 0, 0, 0);
			while (getText(hwpCtrl, keyword) != 1) {};
			hwpCtrl.ReleaseScan();

			return HwpVariable.IsExist;
		} else {
			errormessage();
		}*/
	} catch (error) {
		errormessage("scanDocument");
	}
}


// Get Text
// argument[0] : HwpCtrl Object
// argument[1] : Keyword for Scan
function getText(hwpCtrl, keyword) {
	try {
		if (hwpCtrl != null) {
			var message = "";
			var text = "";
			var TextSet = hwpCtrl.CreateSet("GetText");
			var result = hwpCtrl.GetTextBySet(TextSet);

			switch(result) {
			case 0:
				message = "텍스트정보 없음";
				break;
			case 1:
			 	message = "리스트의 끝";
				break;
			case 2:
				message = "일반 텍스트";
				text = TextSet.Item("Text");
				break;
			case 3:
				message = "다음 문단";
				text = TextSet.Item("Text");
				break;
			case 4:
				message = "제어문자 내부로 들어감";
				text = "{\n";
				break;
			case 5:
				message = "제어 문자를 빠져 나옴";
				text = "}\n";
				break;
			case 101:
				message = "초기화 안됨. (InitScan() 실패 또는 InitScan()를 실행하지 않은 경우.)";
				break;
			case 102:
				message = "텍스트 변환 실패";
				break;
			}

			if (typeof(keyword) == "undefined" || keyword == "") {
				if (text != "" && text != "\r" && text != "\n" && text != "\r\n") {
					HwpVariable.IsExist = true;
					result = 1;
				}
			} else {
				if (text == keyword || text == keyword + "\r" || text == keyword + "\n" || text == keyword + "\r\n") {
					HwpVariable.IsExist = true;
					result = 1;
				}
			}

			return result;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getText");
	}
}


// check Hyperlink
// argument[0] : HwpCtrl Object
function existHyperlink(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var result = false;
			var ctrlcode = hwpCtrl.HeadCtrl;
			while(ctrlcode != null) {
			    if (ctrlcode.CtrlID == "%hlk") {
			    	result = true;
			    	break;
			    }
			    ctrlcode = ctrlcode.next;
			}

			return result;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("existHyperlink");
	}
}


// remove Tab & Space
// argument[0] : HwpCtrl Object
function removeSpace(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			moveToPos(hwpCtrl, HwpConst.Form.Content);

			while(true) {
				var content = getFieldText(hwpCtrl, HwpConst.Form.Content);
				if (content.indexOf(" ") == 0 || content.indexOf("	") == 0 || content.indexOf("\r\n") == 0) {
					runHwpAction(hwpCtrl, "MoveSelNextWord");
					runHwpAction(hwpCtrl, "Delete");
				} else {
					break;
				}
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("removeSpace");
	}
}


// Insert Header & Footer
// argument[0] : HwpCtrl Object
// argument[1] : Password for Distribute Document
// argument[2] : Align Type
// argument[3] : Contents for Insert
// argument[4] : Version of Hwp Ctrl
// argument[4] : Boolean form Page Number
function insertHeaderFooter(hwpCtrl, ctrlType, alignType, contents, version, displaypage) {
	try {
		if (hwpCtrl != null) {
			if (typeof(displaypage) == "undefined")
				displaypage = false;

			var act = hwpCtrl.CreateAction("HeaderFooter");
			var set = act.CreateSet();
			act.GetDefault(set);

			if (version >= "2005") {
				set.SetItem("HeaderFooterStyle", 0);
				set.SetItem("HeaderFooterCtrlType", ctrlType);
			} else {
				set.SetItem("DialogOption", 0);     // 편집버튼 표시여부 : 1 - 표시함 , 기타 - 표시안함
				set.SetItem("CtrlType", ctrlType);  // 머리말꼬리말 타입 : 0 - 머리말, 1 - 꼬리말
				set.SetItem("Type", 0);             // 적용범위 : 양쪽
			}
			act.Execute(set);

			// 0. 가운데 정렬 : ParagraphShapeAlignCenter
			// 1. 왼쪽 정렬 : ParagraphShapeAlignLeft
			// 2. 오른쪽 정렬 : ParagraphShapeAlignRight
			// 3. 양쪽 정렬 : ParagraphShapeAlignJustify
			if (alignType == 0)
				runHwpAction(hwpCtrl, "ParagraphShapeAlignCenter");
			else if (alignType == 1)
				runHwpAction(hwpCtrl, "ParagraphShapeAlignLeft");
			else if (alignType == 2)
				runHwpAction(hwpCtrl, "ParagraphShapeAlignRight");
			else if (alignType == 3)
				runHwpAction(hwpCtrl, "ParagraphShapeAlignJustify");

			act = hwpCtrl.CreateAction("InsertText");
			set = act.CreateSet();
			act.GetDefault(set);
			if (displaypage)
				set.SetItem("Text", contents + "  ");
			else
				set.SetItem("Text", contents);
			act.Execute(set);

			if (displaypage) {
				if (version >= "2005")
					runHwpAction(hwpCtrl, "InsertCpTpNo");
				else
					runHwpAction(hwpCtrl, "InsertPageNum");
			}

			runHwpAction(hwpCtrl, "Close");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertHeaderFooter");
	}
}


// insert Page Number
// argument[0] : HwpCtrl Object
// argument[1] : number format
// argument[2] : number position
// argument[3] : number prefix
// argument[4] : number suffix
function insertPageNumber(hwpCtrl, format, pos, prefix, suffix) {
	try {
		if (hwpCtrl != null) {
/*
			var version = currentVersion(hwpCtrl);
			if (version == "2002" || version == "2004") {
				runHwpAction(hwpCtrl, "InsertPageNum");
			} else if (version == "2005" || version == "2007"){
				runHwpAction(hwpCtrl, "InsertCpTpNo");
			}
*/
			var act = hwpCtrl.CreateAction("PageNumPos");
			var set = act.CreateSet();
			act.GetDefault(set);
	    	set.SetItem("NumberFormat", format);  // 번호모양
			// 0 = NONE, 1 = TOP_LEFT, 2 = TOP_CENTER, 3 = TOP_RIGHT, 4 = BOTTOM_LEFT, 5 = BOTTOM_CENTER
			// 6 = BOTTOM_RIGHT, 7 = OUTSIDE_TOP, 8 = OUTSIDE_BOTTOM, 9 = INSIDE_TOP, 10 = INSIDE_BOTTOM
	    	set.SetItem("DrawPos", pos);     // 번호위치
	    	set.SetItem("PrefixChar", prefix);             // 앞장식 문자
	    	set.SetItem("SuffixChar", suffix);             // 뒷장식 문자

			return act.Execute(set);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertPageNumber");
	}
}


// adjust PageDef for Print
// argument[0] : HwpCtrl Object
function adjustPageDef(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var action = hwpCtrl.CreateAction("PageSetup");
			var set = action.CreateSet();
			action.GetDefault(set);

			set.SetItem("ApplyTo", 3);
			// 1mm = 283.465 HWPUNITs
			var topmargin = 0;
			var headerlength = 0;
			var bottommargin = 0;
			var footerlength = 0;

			// Header
			if (set.Item("PageDef").Item("TopMargin") != null)
				topmargin = Math.round(set.Item("PageDef").Item("TopMargin") / HwpConst.Form.HWPUNITs);
			if (set.Item("PageDef").Item("HeaderLen") != null)
				headerlength = Math.round(set.Item("PageDef").Item("HeaderLen") / HwpConst.Form.HWPUNITs);

			// Footer
			if (set.Item("PageDef").Item("BottomMargin") != null)
				bottommargin = Math.round(set.Item("PageDef").Item("BottomMargin") / HwpConst.Form.HWPUNITs);
			if (set.Item("PageDef").Item("FooterLen") != null)
				footerlength = Math.round(set.Item("PageDef").Item("FooterLen") / HwpConst.Form.HWPUNITs);

			var pset = set.CreateItemSet("PageDef","PageDef");
			if (topmargin + headerlength >= 10) {
				pset.SetItem("TopMargin", Math.round(HwpConst.Form.HWPUNITs * 10));
				pset.SetItem("HeaderLen", Math.round(HwpConst.Form.HWPUNITs * (topmargin + headerlength - 10)));
			}

			if (bottommargin + footerlength >= 5) {
				pset.SetItem("BottomMargin", Math.round(HwpConst.Form.HWPUNITs * 5));
				pset.SetItem("FooterLen", Math.round(HwpConst.Form.HWPUNITs * (bottommargin + footerlength - 5)));
			}

			if (topmargin + headerlength >= 10 || bottommargin + footerlength >= 5)
				return action.Execute(set);
			else
				return true;

		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("adjustPageDef");
	}
}


// get Field List
// argument[0] : HwpCtrl Object
// argument[1] : Field Name
function isDuplicated(hwpCtrl, field) {
	try {
		if (hwpCtrl != null) {
			var fieldlist = getFieldList(hwpCtrl);
			if(fieldlist != null){
			var fields = fieldlist.split("\u0002");
			var fieldcount = 0;
			var flength = fields.length;
			for (var loop = 0; loop < flength; loop++) {
				if (fields[loop] == field)
					fieldcount++;
			}

			return fieldcount;
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("isDuplicated");
	}
}


// get Field List
// argument[0] : HwpCtrl Object
function getFieldList(hwpCtrl) {
	try {
//		if (hwpCtrl != null) {
//			return hwpCtrl.GetFieldList();
//		} else {
//			errormessage();
//		}
		//alert(g_DocWord.Bookmarks.size());
	} catch (error) {
		errormessage("getFieldList");
	}
}


// get Field Text
// argument[0] : HwpCtrl Object
// argument[1] : Field Name
function getFieldText(hwpCtrl,strFieldName)
{
	try {
		var nStartPos = "";
		var nEndPos = "";
		if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2)
			g_DocWord.UnProtect();
		if(g_DocWord.Bookmarks.Exists(strFieldName)){
			nStartPos = g_DocWord.Bookmarks(strFieldName).Range.Cells(1).Range.Start;
			nEndPos = g_DocWord.Bookmarks(strFieldName).Range.Cells(1).Range.End;
			var strFieldText = "";
			if (nStartPos < nEndPos - 1)
				strFieldText = g_DocWord.Range(nStartPos, nEndPos-1).Text;
		}

		if (Document_HwpCtrl.EditMode == 0)
			g_DocWord.Protect(1);
	   else if(Document_HwpCtrl.EditMode == 1){
			protectSection();
		}

		return strFieldText;
	} catch (error) {
		errormessage("getFieldText");
	}
}


// get Field Text
// argument[0] : HwpCtrl Object
// argument[1] : Field Name
function getFieldTextEx(hwpCtrl, field) {
	try {
		if (hwpCtrl != null) {
			var data = new Object();
			data.field = field;
			data.value = hwpCtrl.GetFieldText(field);
			return data;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getFieldTextEx");
	}
}


function putFieldText(hwpCtrl,strFieldList,strValueList)
{
	if(strFieldList != undefined && strValueList != undefined) {
		var nLoop = 0;
		var arrFieldList = strFieldList.split("\u0002");
		var arrValueList = strValueList.split("\u0002");
		var nLength = arrFieldList.length;
		var tempdir = FileManager.getlocaltempdir();

		//hjjung 2008.03.13 : worddoc 존재여부 확인

		if (g_DocWord != null)
		{
			// 2008.04.21. KB : JKKIM added by 별도의 워드 파일에 대한 화면 락 해결. started
			g_DocWord.Activate();
			// -- ended
			if (g_DocWord.ProtectionType != -1)
				g_DocWord.UnProtect();
			while (nLoop < nLength)
			{
				var strField = arrFieldList[nLoop];

				if (strField != "" && g_DocWord.Bookmarks.Exists(strField))
				{
					var myRange = g_DocWord.Bookmarks(strField).Range.Cells(1).Range;
					var strValue = arrValueList[nLoop];
					var strRecordText = readTempRecordText(strField);
					if (strRecordText != strValue)
					{
						myRange.Delete(1,1);
						if(strValue.indexOf(tempdir) == -1){
							myRange.Cells(1).Range.Text = strValue;
						}else{
							setRecordImageFromFile(strField,strValue);
						}
					}
				}
				nLoop++;
			}
			if (g_ProcessCase != 1 && g_DocWord.ProtectionType == -1)
			{
				if (g_strEditType != 0)
					g_DocWord.Protect(1);
			}
			// added by jkkim 수정시 섹션 유지 처리
			 if(Document_HwpCtrl.EditMode == 1){
					protectSection();
			  }
		}
	}
}

function readTempRecordText(strApprovalField)
{
	var strRecordText = "";
	try {
		//hjjung 2008.03.13 : worddoc 존재여부 확인
		if (g_DocWord != null) {
			if (g_DocWord.ProtectionType == 1)
				g_DocWord.UnProtect();
			if (g_DocWord.Bookmarks.Exists(strApprovalField) == true)
			{
				if (g_bIsFormData == true)
				{
					if (strApprovalField.indexOf(HwpConst.Field.Body) != -1)
					{
						var nProposal = getItemCount(); 
						var nTable = g_DocWord.Bookmarks(HwpConst.Field.Body + nProposal).Range.Tables.Count;
						if (nTable == 0)
						{
							strRecordText = g_DocWord.Bookmarks(HwpConst.Field.Body + g_CaseNumber).Range.Paragraphs(1).Range.Text;
						}
						else
						{
							var startloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
							var endloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
							if (startloc < endloc - 1)
								strRecordText = g_DocWord.Range(startloc, endloc-1).Text;
						}
					}
					else
					{
						var startloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
						var endloc = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
						if (startloc < endloc - 1)
							strRecordText = g_DocWord.Range(startloc, endloc-1).Text;
					}
				}
	
				else
					strRecordText = g_DocWord.Bookmarks(strApprovalField).Range.Text;
			}
		}
	
		return strRecordText;
	 }catch(error){
		 errormessage("readTempRecordText");
	 }
}


function setRecordImageFromFile(strApprovalField, strSignUrl)
{
	clearRecordImage(strApprovalField);

	if (g_DocWord.Bookmarks.Exists(strApprovalField) == true)
	{
		g_DocWord.Activate();
		var objPicture = g_DocWord.Bookmarks(strApprovalField).Range.InlineShapes.AddPicture(strSignUrl,false,true);
		// 이미지 사이즈를 필드에 맞게 재조정
		var nCellWidth = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Width;
		var nCellHeight = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Height;
		if (objPicture != null && nCellWidth != null && nCellHeight != null && nCellHeight < 9999999)
		{
			var nPictureWidth = objPicture.Width;
			var nPictureHeight = objPicture.Height;

			if ((nPictureWidth / nCellWidth) > 0.8 || (nPictureHeight / nCellHeight) > 0.8)
			{	// 이미지가 셀의 8/10 보다 큰경우
				if (nPictureWidth / nCellWidth > 0.8)
				{
					nPictureWidth = (nCellWidth * 8) / 10;
					nPictureHeight = (nPictureHeight * nCellWidth * 8) / (nPictureWidth * 10);
				}
				if (nPictureHeight / nCellHeight > 0.8)
				{
					nPictureWidth = (nPictureWidth * nCellHeight * 8) / (nPictureHeight  * 10);
					nPictureHeight = (nCellHeight * 8) / 10;
				}
			}
			else
			{	// 이미지가 셀의 8/10 보다 작은 경우
				if ((nPictureWidth / nCellWidth) > (nPictureHeight / nCellHeight))
				{
					nPictureWidth = (nCellWidth * 8) / 10;
					nPictureHeight = (nPictureHeight * nCellWidth * 8) / (nPictureWidth * 10);
				}
				else
				{
					nPictureWidth = (nPictureWidth * nCellHeight * 8) / (nPictureHeight  * 10);
					nPictureHeight = (nCellHeight * 8) / 10;
				}
			}
			objPicture.Width = nPictureWidth;
			objPicture.Height = nPictureHeight;
		}

		var mySelection = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range;
		var strRecordText = readTempRecordText(strApprovalField);

		if (g_bIsFormData == true)
		{

			if (strRecordText.length > 3)
			{
				mySelection.InlineShapes(1).ConvertToShape();
				mySelection.ShapeRange.WrapFormat.Type = 3;		//wdWrapSquare=0, wdWrapTight=1, wdWrapThrough=2, wdWrapNone=3, wdWrapTopBottom=4
				mySelection.ShapeRange.WrapFormat.Side = 3;		//wdWrapBoth=0, wdWrapLeft=1, wdWrapRight=2, wdWrapLargest=3
				mySelection.ShapeRange.ZOrder(5);
			}
		}
		else
		{
			if (strRecordText.length > 3)
			{
				mySelection.InlineShapes(1).ConvertToShape();
				mySelection.ShapeRange.WrapFormat.Type = 3;
				mySelection.ShapeRange.ZOrder(5);				//msoSendBehindText
			}
		}
	}
}


function clearRecordImage(strApprovalField)
{
	if (g_DocWord.ProtectionType  == 1 || g_DocWord.ProtectionType == 2)
		g_DocWord.UnProtect();

	if (g_DocWord.Bookmarks.Exists(strApprovalField))
	{
		var nImage = g_DocWord.Bookmarks(strApprovalField).Range.InlineShapes.Count;
		var strRecordText = readTempRecordText(strApprovalField);

		if (g_bIsFormData == true)
		{
			g_DocWord.Activate();
			if ((strRecordText == "") || (strRecordText.length <= 1) || (nImage != 0))
				g_DocWord.Bookmarks(strApprovalField).Range.Delete(1,1);
			else
				g_DocWord.Bookmarks(strApprovalField).Range.Delete(1,strRecordText.length);

			var mySelection = g_DocWord.Bookmarks(strApprovalField).Range.Cells(1).Range;

//			if (mySelection.Borders(-8).LineStyle == 1)
//				mySelection.Borders(-8).LineStyle = 0;
		}
		else
		{
			if (strRecordText.length <= 1)
				g_DocWord.Bookmarks(strApprovalField).Range.Delete(1,1);
		}
	}
}

// put Field Text
// argument[0] : HwpCtrl Object
// argument[1] : FieldList
// argument[2] : ValueList
// argument[3] : delimiter
/*function putFieldText(hwpCtrl, fieldList, valueList, delimiter) {
	
	try {
		//alert(fieldList+"|||"+valueList);
		if (hwpCtrl != null) {
			if (arguments.length == 3)
				delimiter = "\u0002";

			if (typeof(fieldList) == "undefined" || fieldList == "" || fieldList == "null")
				return false;
			
			var tempdir = FileManager.getlocaltempdir();

			var pos = hwpCtrl.GetPosBySet();
			HwpVariable.Position.List = pos.Item("List");
			HwpVariable.Position.Para = pos.Item("Para");
			HwpVariable.Position.Pos = pos.Item("Pos");
			
			if (fieldList.indexOf(delimiter) == -1) {
				var fieldvalue = typeOf(valueList);
				if (fieldvalue == "" || fieldvalue == "null")
					fieldvalue = "\u0002";

				if (existField(hwpCtrl, fieldList)) { // 필드가 존재할 경우만
					if (fieldList == HwpConst.Form.EnforceDate || fieldList == HwpConst.Form.ReceiveDate) {
						moveToPos(hwpCtrl, fieldList);
						var retry = 0;
						var data = typeOfDate("date", fieldvalue);
						hwpCtrl.PutFieldText(fieldList, data);
						while (data != "\u0002" && data != hwpCtrl.GetFieldText(fieldList) && retry < 5) {
							hwpCtrl.PutFieldText(fieldList, data);
							retry++;
						}
					} else {
						moveToPos(hwpCtrl, fieldList);
						if (fieldList == HwpConst.Form.OrganName || fieldList == HwpConst.Form.SenderName) {
							var retry = 0;
							var data = typeOfSender(fieldvalue);
							hwpCtrl.PutFieldText(fieldList, data);
							while (data != "\u0002" && data != hwpCtrl.GetFieldText(fieldList) && retry < 5) {
								hwpCtrl.PutFieldText(fieldList, data);
								retry++;
							}
						} else {
							if (fieldvalue.indexOf(tempdir) == -1) {
								var retry = 0;
								var data = typeOfRC(fieldvalue);
								hwpCtrl.PutFieldText(fieldList, data);
								while (data != "\u0002" && data != hwpCtrl.GetFieldText(fieldList) && retry < 5) {
									hwpCtrl.PutFieldText(fieldList, data);
									retry++;
								}
							} else {
								if (isEmptyCell(hwpCtrl, fieldList)) {
									insertPersonalSign(hwpCtrl, fieldList, fieldvalue);
								}
							}
						}

						// 발신기관명, 발신명의, 수신처참조 장평조절
						if (fieldList == HwpConst.Form.OrganName) {
							changeLetterRatio(hwpCtrl, fieldList, 20);
						} else if (fieldList == HwpConst.Form.SenderName) {
							changeLetterRatio(hwpCtrl, fieldList, 18);
						} else if (fieldList == HwpConst.Form.ReceiverRef) {
							changeLetterRatio(hwpCtrl, fieldList, 10);
						}
					}
				}
			} else {
				var fields = fieldList.split(delimiter);
				var values = valueList.split(delimiter);

			    var fieldCount = fields.length;

				for (var loop = 0; loop < fieldCount; loop++) {
					if (fields[loop] != null && fields[loop] != "") {
						var fieldvalue = typeOf(values[loop]);
						if (fieldvalue == "" || fieldvalue == "null")
							fieldvalue = "\u0002";

						if (existField(hwpCtrl, fields[loop])) { // 필드가 존재할 경우만
							if (fields[loop] == HwpConst.Form.EnforceDate || fields[loop] == HwpConst.Form.ReceiveDate) {
								moveToPos(hwpCtrl, fields[loop]);
								var retry = 0;
								var data = typeOfDate("date", fieldvalue);
								hwpCtrl.PutFieldText(fields[loop], data);
								while (data != "\u0002" && data != hwpCtrl.GetFieldText(fields[loop]) && retry < 5) {
									hwpCtrl.PutFieldText(fields[loop], data);
									retry++;
								}
							} else {
								moveToPos(hwpCtrl, fields[loop]);
								if (fields[loop] == HwpConst.Form.OrganName || fields[loop] == HwpConst.Form.SenderName) {
									var retry = 0;
									var data = typeOfSender(fieldvalue);
									hwpCtrl.PutFieldText(fields[loop], data);
									while (data != "\u0002" && data != hwpCtrl.GetFieldText(fields[loop]) && retry < 5) {
										hwpCtrl.PutFieldText(fields[loop], data);
										retry++;
									}
								} else {
									if (fieldvalue.indexOf(tempdir) == -1) {
										var retry = 0;
										var data = typeOfRC(fieldvalue);
										hwpCtrl.PutFieldText(fields[loop], data);
										while (data != "\u0002" && data != hwpCtrl.GetFieldText(fields[loop]) && retry < 5) {
											hwpCtrl.PutFieldText(fields[loop], data);
											retry++;
										}
									} else {
										if (isEmptyCell(hwpCtrl, fields[loop])) {
											insertPersonalSign(hwpCtrl, fields[loop], fieldvalue);
										}
									}
								}

								// 발신기관명, 발신명의 장평조절
								if (fields[loop] == HwpConst.Form.OrganName) {
									changeLetterRatio(hwpCtrl, fields[loop], 20);
								} else if (fields[loop] == HwpConst.Form.SenderName) {
									changeLetterRatio(hwpCtrl, fields[loop], 18);
								} else if (fields[loop] == HwpConst.Form.ReceiverRef) {
									changeLetterRatio(hwpCtrl, fields[loop], 10);
								}
							}
						}
					}
				}
			}
			setPos(hwpCtrl);
			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("putFieldText");
	}
}*/


// Insert Field Text
// argument[0] : HwpCtrl Object
// argument[1] : data(field, value)
function putFieldTextEx(hwpCtrl, data) {
	try {
		if (hwpCtrl != null) {
			var retry = 0;
			hwpCtrl.PutFieldText(data.field, data.value);
			while (data.value != "\u0002" && data.value != hwpCtrl.GetFieldText(data.field) && retry < 5) {
				hwpCtrl.PutFieldText(data.field, data.value);
				retry++;
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("putFieldTextEx");
	}
}


// Insert Field Text repeatly
// argument[0] : HwpCtrl Object
// argument[1] : Field List
// argument[2] : Value List
// argument[3] : Row List
// argument[4] : Column List
// argument[5] : Delimiter - Default : "\u0002"
function repeatFieldText(hwpCtrl, fieldList, valueList, rowList, columnList, delimiter) {
	try {
		if (hwpCtrl != null) {
			if (arguments.length < 6)
				delimiter = "\u0002";

			if (typeof(fieldList) == "undefined" || fieldList == "" || fieldList == "null")
				return false;

			if (fieldList.indexOf(delimiter) == -1)
				return false;

			var fields = fieldList.split(delimiter);
			var values = valueList.split(delimiter);
			var rows = rowList.split(delimiter);
			var columns = columnList.split(delimiter);

			var fieldscount = fields.length;
			if (fieldscount != values.length || fieldscount != rows.length || fieldscount != columns.length)
				return false;

			if (fields[fields - 1] == null || fields[fields - 1] == "")
				fieldscount--;
			var rowcount = parseInt(rows[fieldscount - 1]);
			var columncount = parseInt(columns[fieldscount - 1]);

			// Put Non-Repeated Data into Table Field
			var index = 0;
			while (true) {
				if (index < fieldscount && (rows[index] == "0" || columns[index] == "0")) {
					var fieldvalue = typeOf(values[index]);
					if (fieldvalue == "" || fieldvalue == "null")
						fieldvalue = "\u0002";

					if (existField(hwpCtrl, fields[index])) { 	// if exist field
						moveToPos(hwpCtrl, fields[index]);
						var retry = 0;
						var data = typeOfRC(fieldvalue);
						hwpCtrl.PutFieldText(fields[index], data);
						while (data != "\u0002" && data != hwpCtrl.GetFieldText(fields[index]) && retry < 5) {
							hwpCtrl.PutFieldText(fields[index], data);
							retry++;
						}
					}
					
					index++;
				} else {
					break;
				}
			}

			if (fieldscount != rowcount * columncount + index) {
				alert(HwpConst.Msg.NOT_MATCH_FIELD_AND_DATA);
				return false;
			}
			
			// Append Table Row
			for (var loop = 1; loop < rowcount; loop++) {
				moveToPos(hwpCtrl, fields[index]);
				moveToPos(hwpCtrl, 107);
				runHwpAction(hwpCtrl, "TableAppendRow");

				for (var pos = 0; pos < columncount; pos++) {
					changeFieldName(hwpCtrl, fields[columncount * loop + pos + index]);
					moveToPos(hwpCtrl, 101);
				}
			}

			// Put Repeated Data into Table Field
			for (var loop = index; loop < fieldscount; loop++) {
				var fieldvalue = typeOf(values[loop]);
				if (fieldvalue == "" || fieldvalue == "null")
					fieldvalue = "\u0002";

				if (existField(hwpCtrl, fields[loop])) { 	// if exist field
					moveToPos(hwpCtrl, fields[loop]);
					var retry = 0;
					var data = typeOfRC(fieldvalue);
					hwpCtrl.PutFieldText(fields[loop], data);
					while (data != "\u0002" && data != hwpCtrl.GetFieldText(fields[loop]) && retry < 5) {
						hwpCtrl.PutFieldText(fields[loop], data);
						retry++;
					}
				}
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("repeatFieldText");
	}
}


// get HwpCtrl Text
// argument[0] : HwpCtrl Object
// argument[1] : field name to get
function getTextFile(hwpCtrl, field) {
	try {
		var text = "";
		if (typeof(field) == "undefined") {
			text = hwpCtrl.GetTextFile("TEXT", "");
		} else {
			selectBlock(hwpCtrl, field);
			text = hwpCtrl.GetTextFile("TEXT", "saveblock");
			moveToPos(hwpCtrl, field);
		}
		return text;
	} catch (error) {
		errormessage("getTextFile");
	}
}


// check Field
// argument[0] : HwpCtrl Object
// argument[1] : field name to find in Document
function existField(hwpCtrl,field) {
	try {
		if (g_DocWord != null) {
			return g_DocWord.Bookmarks.Exists(field);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("existField : "+field);
	}
}


// change Field
// argument[0] : HwpCtrl Object
// argument[1] : field name to change in Document
function changeFieldName (hwpCtrl, field) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.SetCurFieldName(field);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("changeFieldName");
	}
}


// rename Field
// argument[0] : HwpCtrl Object
// argument[1] : old field name
// argument[2] : new field name
function renameField(hwpCtrl, oldfield, newfield) {
	try {
		if (hwpCtrl != null) {
			//hwpCtrl.RenameField(oldfield, newfield);
			var myRange = g_DocWord.Bookmarks(oldfield).Range.Cells(1).Range;

			g_DocWord.Bookmarks.Add(newfield, myRange);
			g_DocWord.Bookmarks(oldfield).Delete();
			
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("renameField");
	}
}


// insert Image
// argument[0] : HwpCtrl Object
// argument[1] : fieldname to find in Document
// argument[2] : image filepath with filename to insert Document
// argument[3] : width of Image
// argument[4] : height of Image
function insertImage(hwpCtrl, field, filepath, imagewidth, imageheight) {
	try {
		setRecordImageFromFile(field, filepath);
//		if (hwpCtrl != null) {
//			if (!existField(hwpCtrl, field))
//				return false;
//
//			if (typeof(filepath) == "undefined" || (filepath.indexOf("\\") == -1 && filepath.indexOf("/") == -1))
//				return false;
//
//			moveToPos(hwpCtrl, field);
//			// 한글버그로 인하여 sizeoption 설정. 셀 넓이가 20.00mm인 경우 3번 옵션값이 정상동작하지 않음, 가로 <> 세로
//			var action = hwpCtrl.CreateAction("TablePropertyDialog");
//			var set = action.CreateSet();
//			action.GetDefault(set);
//			var cellSet = set.Item("ShapeTableCell");
//			
//			var sizeoption = 3;
//			var width = Math.round(cellSet.Item("Width")/HwpConst.Form.HWPUNITs);
//			var height = Math.round(cellSet.Item("Height")/HwpConst.Form.HWPUNITs);
//			if (width == 20 || width == height) {
//				sizeoption = 2;
//			}
//			
//			runHwpAction(hwpCtrl, "SelectAll");
//			runHwpAction(hwpCtrl, "Delete");
//			var result;
//			if (arguments.length == 3) {
//				result =hwpCtrl.InsertPicture(filepath, true, sizeoption, false, false, 0);
//			} else if (arguments.length == 5) {
//				// 0 : original size, 1 : appointed size, 2 : cell size, 3 : cell size with same ratio
//				if (imagewidth == "" || imagewidth == "0" || imageheight == "" || imageheight == "0") {
//					result =hwpCtrl.InsertPicture(filepath, true, sizeoption, false, false, 0);
//				} else {
//					result =hwpCtrl.InsertPicture(filepath, true, 1, false, false, 0, imagewidth, imageheight);
//				}
//			}
//			if(typeof("object"))
//				return true;
//		} else {
//			errormessage();
//		}
	} catch (error) {
		errormessage("insertImage");
	}
}


// insert Image
// argument[0] : HwpCtrl Object
// argument[1] : data(field, filepath, width, height)
function insertImageEx(hwpCtrl, data) {
	try {
		if (hwpCtrl != null) {
			// not exist field
			if (!existField(hwpCtrl, data.field))
				return;
			// not exist image
			if (typeof(data.value) == "undefined" || (data.value.indexOf("\\") == -1 && data.value.indexOf("/") == -1))
				return;

			clearImage(hwpCtrl, data.field);

			if (data.width == "" || data.width == "0" || data.height == "" || data.height == "0") {
				// 한글버그로 인하여 sizeoption 설정. 셀 넓이가 20.00mm인 경우 3번 옵션값이 정상동작하지 않음
				var action = hwpCtrl.CreateAction("TablePropertyDialog");
				var set = action.CreateSet();
				action.GetDefault(set);
				var cellSet = set.Item("ShapeTableCell");
				
				var sizeoption = 3;
				var width = Math.round(cellSet.Item("Width")/HwpConst.Form.HWPUNITs);
				var height = Math.round(cellSet.Item("Height")/HwpConst.Form.HWPUNITs);
				if (width == 20 || width == height) {
					sizeoption = 2;
				}
				
				hwpCtrl.InsertPicture(data.value, true, sizeoption, false, false, 0);
			} else {
				// 0 : original size, 1 : appointed size, 2 : cell size, 3 : cell size with same ratio
				hwpCtrl.InsertPicture(data.value, true, 1, false, false, 0, data.width, data.height);
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertImageEx");
	}
}


//insertPersonalSign
//argument[0] : HwpCtrl Object
//argument[1] : fieldname to find in Document
//argument[2] : image filepath with filename to insert Document
//argument[3] : width of Image
//argument[4] : height of Image
function insertPersonalSign(hwpCtrl, field, filepath) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, field))
				return;

			moveToPos(hwpCtrl, field);
			// 한글버그로 인하여 sizeoption 설정. 셀 넓이가 20.00mm인 경우 3번 옵션값이 정상동작하지 않음
			var action = hwpCtrl.CreateAction("TablePropertyDialog");
			var set = action.CreateSet();
			action.GetDefault(set);
			var cellSet = set.Item("ShapeTableCell");
			
			var sizeoption = 3;
			var width = Math.round(cellSet.Item("Width")/HwpConst.Form.HWPUNITs);
			var height = Math.round(cellSet.Item("Height")/HwpConst.Form.HWPUNITs);
			if (width == 20 || width == height) {
				sizeoption = 2;
			}

			runHwpAction(hwpCtrl, "SelectAll");
			runHwpAction(hwpCtrl, "Delete");
			hwpCtrl.InsertPicture(filepath, true, sizeoption, false, false, 0);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertPersonalSign");
	}
}


// export Image
// argument[0] : HwpCtrl Object
// argument[1] : fieldname
// argument[2] : filename
// failed function - it needs more guide...
function exportImage(hwpCtrl, field, filename) {
	try {
		if (hwpCtrl != null) {
			var ext = "JPG";
			var pos = filename.lastIndexOf(".");
			if (pos != -1 && pos < filename.length) {
				ext = filename.substring(pos + 1).toUpperCase();
			}

			moveToPos(hwpCtrl, field);
			hwpCtrl.Run("SelectCtrlFront");		// 개체 선택

			action = hwpCtrl.CreateAction("ShapeObjSaveAsPicture");
			set = action.CreateSet();
			action.GetDefault(set);
			set.SetItem("Path", filename);
			set.SetItem("Ext", ext);

			action.Execute(set);		
			hwpCtrl.Run("Cancel");	// pHwpCtrl.UnSelectCtrl(); 코드 대체
		} else {
			errormessage();
		}
	} catch (error) {
		alert("exportImage Error ");
		errormessage("exportImage");
	}
}

// clear Image
// argument[0] : HwpCtrl Object
// argument[1] : fieldname to find in Document
function clearImage(hwpCtrl, field) {
	try {
		clearRecordImage(field);
//		if (hwpCtrl != null) {
//			if (!existField(hwpCtrl, field))
//				return;
//
//			var repeatcount = 0;
//			selectBlock(hwpCtrl, field);
//			while (hwpCtrl.SelectionMode() == 0 && repeatcount < 5) {
//				selectBlock(hwpCtrl, field);
//				repeatcount++;
//			}
//		//	moveToPos(hwpCtrl, field);
//		//	runHwpAction(hwpCtrl, "SelectAll");
//		//	runHwpAction(hwpCtrl, "Delete"); 
//			putFieldText(hwpCtrl, field, "\u0002");
//		} else {
//			errormessage();
//		}
	} catch (error) {
		errormessage("clearImage");
	}
}


// adjust Seal Image Position
// argument[0] : HwpCtrl Object
// argument[1] : targetfield - field name of image
// argument[2] : sourcefield - field name of sendername
function adjustSealPos(hwpCtrl, targetfield, sourcefield, width) {
	try {
		if (hwpCtrl != null) {
			var unifiedHwp = false;
			moveToPos(hwpCtrl, sourcefield);
			moveToPos(hwpCtrl, 24);
			var parentCtrl = hwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				unifiedHwp = true;

			if (!unifiedHwp)
				return false;

			moveToPos(hwpCtrl, sourcefield);
			var sendername = hwpCtrl.GetFieldText(sourcefield);
			sendername.replace(/[ \t]*$/, "");
			if (sendername.length == 0) {
				sendername = hwpCtrl.GetFieldText(sourcefield);
				sendername.replace(/[ \t]*$/, "");
			}
			var textsize = 0;
			for (var i = 0; i < sendername.length; i++)
			{
				if ((sendername.charCodeAt(i) & 0xff00) != 0)
					textsize += 2;
				else
					textsize += 1;
			}
			var charOffset = ((textsize > 34) ? 34 : textsize) / 2  - 1 ; //서명인은 마지막글자 뒤로  // jth8172 2012 신결재 TF
			if (charOffset <= 3) {
				charOffset = 17;
			}
			var fontWidth = HwpConst.Font.SenderName * 50;
			var horizontalAdjust = Math.round(charOffset * fontWidth  + width * 100 + 1000) ; // 서명이미지 가로사이즈 만큼 계산 // jth8172 2012 신결재 TF

			moveToPos(hwpCtrl, targetfield);
		
			var tableAction = hwpCtrl.CreateAction("TablePropertyDialog");
			var tableSet = tableAction.CreateSet();
			if (tableSet == null)
				return false;

			tableAction.GetDefault(tableSet);
			// horizontal align relative to enclosing table
			if (tableSet.Item("HorzRelTo") != 3)
				tableSet.SetItem("HorzRelTo", 3);

			tableSet.SetItem("HorzAlign", 1); //서명인은 마지막글자 뒤로 왼쪽 정렬 // jth8172 2012 신결재 TF
			tableSet.SetItem("HorzOffset", horizontalAdjust);
			tableAction.Execute(tableSet);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("adjustSealPos");
	}
}


//adjust Stamp Image Position  // jth8172 2012 신결재 TF
//argument[0] : HwpCtrl Object
//argument[1] : targetfield - field name of image
//argument[2] : sourcefield - field name of sendername
function adjustStampPos(hwpCtrl, targetfield, sourcefield) {
	try {
		if (hwpCtrl != null) {
			var unifiedHwp = false;
			moveToPos(hwpCtrl, sourcefield);
			moveToPos(hwpCtrl, 24);
			var parentCtrl = hwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				unifiedHwp = true;

			if (!unifiedHwp)
				return false;

			moveToPos(hwpCtrl, sourcefield);
			var sendername = hwpCtrl.GetFieldText(sourcefield);
			sendername.replace(/[ \t]*$/, "");
			if (sendername.length == 0) {
				sendername = hwpCtrl.GetFieldText(sourcefield);
				sendername.replace(/[ \t]*$/, "");
			}
			var textsize = 0;
			for (var i = 0; i < sendername.length; i++)
			{
				if ((sendername.charCodeAt(i) & 0xff00) != 0)
					textsize += 2;
				else
					textsize += 1;
			}
			var charOffset = ((textsize > 34) ? 34 : textsize) / 2 - 1;
			if (charOffset <= 1) {
				charOffset = 8;
			}
			var fontWidth = HwpConst.Font.SenderName * 50;
			var horizontalAdjust = Math.round(charOffset * fontWidth);

			moveToPos(hwpCtrl, targetfield);
			var tableAction = hwpCtrl.CreateAction("TablePropertyDialog");
			var tableSet = tableAction.CreateSet();
			if (tableSet == null)
				return false;

			tableAction.GetDefault(tableSet);

			// horizontal align relative to enclosing table
			if (tableSet.Item("HorzRelTo") != 3)
				tableSet.SetItem("HorzRelTo", 3);
			// center alignment
			tableSet.SetItem("HorzAlign", 1);
			tableSet.SetItem("HorzOffset", horizontalAdjust);
			tableAction.Execute(tableSet);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("adjustStampPos");
	}
}


// adjust Seal Image Position
// argument[0] : HwpCtrl Object
// argument[1] : targetfield - field name of image
function adjustSealPosEx(hwpCtrl, targetfield, width) {
	try {
		if (hwpCtrl != null) {
			adjustSealPos(hwpCtrl, targetfield, HwpConst.Form.SenderName, width);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("adjustSealPosEx");
	}
}

//adjust Stamp Image Position  // jth8172 2012 신결재 TF
//argument[0] : HwpCtrl Object
//argument[1] : targetfield - field name of image
function adjustStampPosEx(hwpCtrl, targetfield) {
	try {
		if (hwpCtrl != null) {
			adjustStampPos(hwpCtrl, targetfield, HwpConst.Form.SenderName);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("adjustStampPosEx");
	}
}

// change Letter Ratio
// argument[0] : HwpCtrl Object
function changeLetterRatio(hwpCtrl, fieldname, fontsize) {
	try {
		var ratio = 100;
		var fontsize = 1000;
		var textsize = 0;

		var fieldtext = hwpCtrl.GetFieldText(fieldname);
		fieldtext.replace(/[ \t]*$/, "");
		for (var i = 0; i < fieldtext.length; i++) {
			if ((fieldtext.charCodeAt(i) & 0xff00) != 0)
				textsize += 2;
			else
				textsize += 1;
		}

		if (fieldname == HwpConst.Form.ReceiverRef) {
			var considercount = Math.ceil(getConsiderCount(hwpCtrl) / 4);
			var assistancecount = Math.ceil(getAssistanceCount(hwpCtrl) / 4);
			var linecount = 25;

			// get linecount by consider & assistance count
			if (considercount == 1 && assistancecount == 1) {
				linecount = 39;
			} else if (considercount == 1 && assistancecount == 2) {
				linecount = 37;
			} else if (considercount == 1 && assistancecount == 3) {
				linecount = 35;
			} else if (considercount == 2 && assistancecount == 1) {
				linecount = 36;
			} else if ((considercount == 1 && assistancecount == 4) || (considercount == 2 && assistancecount == 2)) {
				linecount = 34;
			} else if ((considercount == 2 && assistancecount == 3) || (considercount == 3 && assistancecount == 1)) {
				linecount = 33;
			} else if ((considercount == 2 && assistancecount == 4) || (considercount == 3 && assistancecount == 2)) {
				linecount = 31;
			} else if ((considercount == 3 && assistancecount == 3) || (considercount == 4 && assistancecount == 1)) {
				linecount = 30;
			} else if ((considercount == 3 && assistancecount == 4) || (considercount == 4 && assistancecount == 2)) {
				linecount = 28;
			} else if (considercount == 4 && assistancecount == 3) {
				linecount = 27;
			} else if (considercount == 4 && assistancecount == 4) {
				linecount = 25;
			}

			// get ratio by text size
			if (textsize > 148 * linecount) {
				var line = 0;
				if (linecount >= 34)
					line = linecount + 4;
				else
					line = linecount + 3;

				if (textsize > 165 * line) {
					if (linecount >= 39)
						line = linecount + 10;
					else if (linecount >= 35)
						line = linecount + 9;
					else if (linecount >= 31)
						line = linecount + 8;
					else
						line = linecount + 7;

					if (textsize > 184 * line)
						fontsize = 700;		// Letter Size : Font 7
					else
						fontsize = 800;		// Letter Size : Font 8
				} else {
					fontsize = 900;			// Letter Size : Font 9
				}

				ratio = 60;
			} else if (textsize > 137 * linecount) {
				ratio = 60;
			} else if (textsize > 128 * linecount) {
				ratio = 65;
			} else if (textsize > 120 * linecount) {
				ratio = 70;
			} else if (textsize > 111 * linecount) {
				ratio = 75;
			} else if (textsize > 105 * linecount) {
				ratio = 80;
			} else if (textsize > 99 * linecount) {
				ratio = 85;
			} else if (textsize > 94 * linecount) {
				ratio = 90;
			} else if (textsize > 89 * linecount) {
				ratio = 95;
			}
		} else {
			var basesize = 34;

			if (fieldname == HwpConst.Form.OrganName)
				basesize = 37;

			if (textsize > basesize) {
				ratio = Math.floor(basesize * 100 / textsize);
				ratio = (ratio > 60) ? ratio : 60;
			}
		}

		selectBlock(hwpCtrl, fieldname);

		var act = hwpCtrl.CreateAction("CharShape");
		var set = hwpCtrl.CreateSet("CharShape");
		if (set.Item("RatioHangul") != ratio) {
			set.SetItem("RatioHangul", ratio);
			set.SetItem("RatioLatin", ratio);
			set.SetItem("RatioHanja", ratio);
			set.SetItem("RatioJapanese", ratio);
			set.SetItem("RatioOther", ratio);
			set.SetItem("RatioSymbol", ratio);
			set.SetItem("RatioUser", ratio);

			// change fontsize
			if (fieldname == HwpConst.Form.ReceiverRef)
				set.SetItem("Height", fontsize);

			act.Execute(set);
		}

		moveToPos(hwpCtrl, fieldname);
	} catch (error) {
	//	오류발생 시에도 에러메세지 표시 안함
	//	errormessage("changeLetterRatio");
	}
}


// seal Omit Stamp(Text)
// argument[0] : HwpCtrl Object
// argument[1] : value
// argument[2] : type
// argument[3] : width
// argument[4] : setflag - 0 : remove seal, 1 : append seal
function setOmitField(hwpCtrl, value, type, width, setflag) {
	try {
		
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, HwpConst.Form.Omit))
				return false;

			moveToPos(hwpCtrl, HwpConst.Form.Omit);
			
			var omitRange = Document_HwpCtrl.WordDocument.Bookmarks(HwpConst.Form.Omit).Range.Cells(1).Range;
			
			if (setflag) {
				putFieldText(hwpCtrl, HwpConst.Form.Omit, value);
				
				omitRange.Cells(1).Range.Borders.Enable = 1;
				omitRange.Cells(1).Range.Bold = 1;
				
				//Border Line 두께 설정 wdLineWidth150pt(1.50point 1.00point가 default)
				omitRange.cells(1).Range.Borders.OutsidelineWidth = 12;
				
				//Text 가운데 정렬(0 : Left-aligned, 1 : Center-aligned, 2 : Right-aligned)
				omitRange.Paragraphs.Format.Alignment = 1;
				
				//Cell 높이에 따라 자동 Font Size Setting
				omitRange.Cells(1).Range.Font.Size = omitRange.Cells(1).Range.Application.PixelsToPoints(omitRange.cells(1).Height/2);
			} else {
				putFieldText(hwpCtrl, HwpConst.Form.Omit, "\u0002");
				
				//Border Disable
				omitRange.Cells(1).Range.Borders.Enable = 0;
			}

			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("sealOmitStamp");
	}
}


// show omit stamp text(omit field)
// argument[0] : HwpCtrl Object
function showOmitStamp(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
        clearStamp(hwpCtrl);
			return setOmitField(hwpCtrl, HwpConst.Form.OmitStamp, true, true, true);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("showOmitStamp");
	}
}


// show signature text(omit field)
// argument[0] : HwpCtrl Object
function showSignature(hwpCtrl, signature) {
	try {
		if (hwpCtrl != null) {
			var result = false;
	        clearStamp(hwpCtrl);
			moveToPos(hwpCtrl, HwpConst.Form.Omit);
			setDefaultFont(hwpCtrl, HwpConst.Font.Goongseoche, 0, 1800);
			result = setOmitField(hwpCtrl, signature, false, false, true);
			setDefaultFont(hwpCtrl, HwpConst.Font.Gulimche, 0, 1800);

			return result;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("showSignature");
	}
}


// show omit signature text(omit field)
// argument[0] : HwpCtrl Object
function showOmitSignature(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
        clearStamp(hwpCtrl);
			return setOmitField(hwpCtrl, HwpConst.Form.OmitSignature, true, true, true);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("showOmitSignature");
	}
}


// hide omit text(omit field)
// argument[0] : HwpCtrl Object
function hideOmitSeal(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			return setOmitField(hwpCtrl, "", false, false, false);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("hideOmitSeal");
	}
}


// insert Stamp(Image)
// argument[0] : HwpCtrl Object
// argument[1] : stamp table to change
function changeStampTable(hwpCtrl, filepath) {
	try {
		if (hwpCtrl != null) {
			renameField(hwpCtrl, HwpConst.Form.Seal, HwpConst.Form.Empty);
			moveToPos(hwpCtrl, HwpConst.Field.SenderName);
			var tp = hwpCtrl.CreateSet("Table");
			var cp = tp.CreateItemSet("Cell", "Cell");
			cp.SetItem("Height", 8300);
			hwpCtrl.CellShape = tp;

			moveToPos(hwpCtrl, HwpConst.Field.StandardForm);
			insertHwpDocument(hwpCtrl, filepath);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("changeStampTable");
	}
}


// insert Stamp(Image)
// argument[0] : HwpCtrl Object
// argument[1] : image filepath with filename to insert Document
// argument[2] : width of Stamp
// argument[3] : height of Stamp
function insertStamp(hwpCtrl, filepath, width, height) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, HwpConst.Form.Seal)) {
				if (existField(hwpCtrl, HwpConst.Form.OfficialSeal))
					renameField(hwpCtrl, HwpConst.Form.OfficialSeal, HwpConst.Form.Seal);
				else
					return false;
			}

			//clearStamp(hwpCtrl);
			if (arguments.length == 2) {
				insertImage(hwpCtrl, HwpConst.Form.Seal, filepath);
			} else {
				if (isStandardForm(hwpCtrl)) {
					var cellwidth = (width > 54) ? 54 : Math.ceil(width);
					var cellheight = (height > 54) ? 54 : Math.ceil(height);
					cellwidth = (cellwidth < 10) ? 10 : cellwidth;
					cellheight = (cellheight < 30) ? 30 : cellheight;
					//resizeStamp(hwpCtrl, cellwidth, cellheight);
				}
				insertImage(hwpCtrl, HwpConst.Form.Seal, filepath, width, height);
			}

			//adjustStampPosEx(hwpCtrl, HwpConst.Form.Seal);

			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertStamp");
	}
}



//insert Seal(Image)  // jth8172 2012 신결재 TF
//argument[0] : HwpCtrl Object
//argument[1] : image filepath with filename to insert Document
//argument[2] : width of Stamp
//argument[3] : height of Stamp
function insertSeal(hwpCtrl, filepath, width, height) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, HwpConst.Form.Seal)) {
				if (existField(hwpCtrl, HwpConst.Form.OfficialSeal))
					renameField(hwpCtrl, HwpConst.Form.OfficialSeal, HwpConst.Form.Seal);
				else
					return false;
			}

			clearStamp(hwpCtrl);
			if (arguments.length == 2) {
				insertImage(hwpCtrl, HwpConst.Form.Seal, filepath);
			} else {
				if (isStandardForm(hwpCtrl)) {
					var cellwidth = (width > 54) ? 54 : Math.ceil(width);
					var cellheight = (height > 54) ? 54 : Math.ceil(height);
					cellwidth = (cellwidth < 10) ? 10 : cellwidth;
					cellheight = (cellheight < 30) ? 30 : cellheight;
					resizeStamp(hwpCtrl, cellwidth, cellheight);
				}
				insertImage(hwpCtrl, HwpConst.Form.Seal, filepath, width, height);
			}

			adjustSealPosEx(hwpCtrl, HwpConst.Form.Seal, width);

			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertStamp");
	}
}




// resize Stamp Cell(Image)
// argument[0] : HwpCtrl Object
// argument[1] : width of Stamp to resize
// argument[2] : height of Stamp to resize
function resizeStamp(hwpCtrl, width, height) {
	try {
		if (hwpCtrl != null) {
			// resize height of SenderName Field
			/*moveToPos(hwpCtrl, HwpConst.Field.SenderName);
			var tp = hwpCtrl.CreateSet("Table");
			var cp = tp.CreateItemSet("Cell", "Cell");
			cp.SetItem("Height", Math.ceil((9.3 + (5 / 6) * (height - 30)) * HwpConst.Form.HWPUNITs));
			hwpCtrl.CellShape = tp;

			// resize width, height of Seal Field
			moveToPos(hwpCtrl, HwpConst.Form.Seal);
			var action = hwpCtrl.CreateAction("TablePropertyDialog");
			var set = action.CreateSet();
			action.GetDefault(set);
			// unlock to resize
			set.SetItem("ProtectSize", 0);
			action.Execute(set);
			// resize stamp cell
			tp = hwpCtrl.CreateSet("Table");
			cp = tp.CreateItemSet("Cell", "Cell");
			cp.SetItem("Width", Math.ceil(width * HwpConst.Form.HWPUNITs));
			cp.SetItem("Height", Math.ceil(height * HwpConst.Form.HWPUNITs));
			hwpCtrl.CellShape = tp;
			// lock to resize
			set.SetItem("ProtectSize", 1);
			action.Execute(set);*/
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("resizeStamp");
	}
}


// remove Stamp(Image)
// argument[0] : HwpCtrl Object
function removeStamp(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, HwpConst.Form.Seal))
				return;

			clearImage(hwpCtrl, HwpConst.Form.Seal);

		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("removeStamp");
	}
}


// insert Omit Stamp(Image)
// argument[0] : HwpCtrl Object
// argument[1] : image filepath with filename to insert Document
function insertOmitStamp(hwpCtrl, filepath) {
	try {
		if (hwpCtrl != null) {
			//if (!existField(hwpCtrl, HwpConst.Form.Omit))
			//	return false;

			clearStamp(hwpCtrl);
			insertImage(hwpCtrl, HwpConst.Form.Omit, filepath, 30, 10);

			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertOmitStamp");
	}
}


// remove Omit Stamp(Image)
// argument[0] : HwpCtrl Object
function removeOmitStamp(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, HwpConst.Form.Omit))
				return;

			clearImage(hwpCtrl, HwpConst.Form.Omit);

		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("removeOmitStamp");
	}
}


// remove Seal or Omit Stamp (Image)
// argument[0] : HwpCtrl Object
// argument[1] : stampType (HwpConst.Form.Seal or HwpConst.Form.Omit)
function clearStamp(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			// remove Seal image(Seal field)
			removeStamp(hwpCtrl);
			// remove Omit image(Omit field)
			removeOmitStamp(hwpCtrl);
			// hide Omit text(Omit field)
			hideOmitSeal(hwpCtrl);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearStamp");
	}
}

// run HWP Action or HWP Set
// argument[0] : HwpCtrl Object
// argument[1] : name of action or set
function runHwpAction(hwpCtrl, action) {
	try {
		if (hwpCtrl != null) {
			hwpCtrl.Run(action);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("runHwpAction");
	}
}


// ///////////////////////////////////////
// Composite Function
// ///////////////////////////////////////


// set Enforce Form
// argument[0] : HwpCtrl Object
// argument[1] : 001 : width, 002 : page, 006 : 70%, 005 : 80%, 004 : 90%, 003 : 100%
function setDefaultView(hwpCtrl, ratio) {
	try {
		if (hwpCtrl != null) {

			var viewtype = 1;
			if (ratio == "002")
        		viewtype = 2;
	        else if (ratio == "003")
	        	viewtype = 120;
	        else if (ratio == "004")
	        	viewtype = 110;
	        else if (ratio == "005")
	        	viewtype = 100;
	        else if (ratio == "006")
	        	viewtype = 90;
	        else if (ratio == "007")
	        	viewtype = 80;
	        else if (ratio == "008")
	        	viewtype = 70;

			if (viewtype == 1 || viewtype == 2)
				setHwpViewScale(hwpCtrl, viewtype, 0);
			else
				setHwpViewScale(hwpCtrl, 0, viewtype);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setDefaultView");
	}
}


// set Enforce Form
// argument[0] : HwpCtrl Object
// argument[1] : dataArray - Array with Text data(type, field, value) or Image data(type, field, value, width, height)
function setEnforceForm(hwpCtrl, dataArray) {
	try {
		if (hwpCtrl != null) {
			var datacount = dataArray.length;
			for (var loop = 0; loop < datacount; loop++) {
				var data = dataArray[loop];
				if (data.type == "image")
					insertImageEx(hwpCtrl, data);
				else
					putFieldTextEx(hwpCtrl, data);
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setEnforceForm");
	}
}


// set Document Form
// argument[0] : HwpCtrl Object
// argument[1] : docinfo info - docinfo(headercampaign, organname, receiver, via, title, content, sendername, receiverlist,
//										enforcenumber, enforcedate, receivenumber, receivedate, publicbound, footercampaign)
function setEnfDocument(hwpCtrl, docinfo) {
	try {
		if (hwpCtrl != null) {
			var fieldlist = HwpConst.Form.HeaderCampaign + "\u0002" + HwpConst.Form.OrganName + "\u0002" + HwpConst.Form.Receiver + "\u0002"
				+ HwpConst.Form.Via + "\u0002" + HwpConst.Form.Title + "\u0002" + HwpConst.Form.SenderName + "\u0002" + HwpConst.Form.EnforceNumber + "\u0002"
				+ HwpConst.Form.EnforceDate + "\u0002" + HwpConst.Form.ReceiveNumber + "\u0002" + HwpConst.Form.ReceiveDate + "\u0002"
				+ HwpConst.Form.PublicBound + "\u0002" + HwpConst.Form.FooterCampaign;
			var valuelist = docinfo.headercampaign + "\u0002" + docinfo.organname + "\u0002" + docinfo.receiver + "\u0002"
				+ docinfo.via + "\u0002" + docinfo.title + "\u0002" + docinfo.sendername + "\u0002" + docinfo.enforcenumber + "\u0002" + typeOfDate("", docinfo.enforcedate)
				+ "\u0002" + docinfo.receivenumber + "\u0002" + typeOfDate("", docinfo.receivedate) + "\u0002"
				+ docinfo.publicbound + "\u0002" + docinfo.footercampaign;
			putFieldText(hwpCtrl, fieldlist, valuelist);

			// [20080616.MODIFY.백영호]-START.
			// - docinfo.content, docinfo.receiverlist 값이 없을 경우에는 수행하지 않도록 처리
			// - '' 일 경우는 체크안함 (값을 지우기 위해 일부러 '' 값을 셋팅하는 경우도 있을것 같아서)
			if ((docinfo.content != undefined) && (docinfo.content != null)) {
			    putFieldText(hwpCtrl, HwpConst.Form.Content, docinfo.content);
			}
        if ((docinfo.receiverlist != undefined) && (docinfo.receiverlist != null) && (docinfo.receiverlist != "")) {
            // [20080627]-백영호. 본문하단의 수신자 셀에 "수신자" 라는 텍스트 표시
			    putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle, HwpConst.Data.Receiver);   // 셀[수신자]
			    putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, docinfo.receiverlist);          // 셀[수신처참조]
			}
        // [20080616.MODIFY.백영호]-END.
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setEnfDocument");
	}
}


// set Drafter Form
// argument[0] : HwpCtrl Object
// argument[1] : drafter info - drafter(zipcode, address, homepage, telephone, fax, email)
function setDrafter(hwpCtrl, drafter, isdraft) {
	try {
		if (hwpCtrl != null) {
			var zipcode = "";
			var len = drafter.zipcode.length;
			for (var loop = 0; loop < len; loop++) {
				if ("1234567890".indexOf(drafter.zipcode.substring(loop, loop+1)) != -1)
					zipcode += drafter.zipcode.substring(loop, loop+1);
			}
			drafter.zipcode = zipcode;

			var message = "";
			var fieldlist = HwpConst.Form.Zipcode + "\u0002" + HwpConst.Form.Address + "\u0002" + HwpConst.Form.Homepage + "\u0002"
				+ HwpConst.Form.Telephone + "\u0002" + HwpConst.Form.Fax + "\u0002" + HwpConst.Form.Email;
			var valuelist = (drafter.zipcode.substring(0, 3) + "-" + drafter.zipcode.substring(3, 6)) + "\u0002" + drafter.address + "\u0002" + drafter.homepage + "\u0002"
				+ drafter.telephone + "\u0002" + drafter.fax + "\u0002" + drafter.email;
			putFieldText(hwpCtrl, fieldlist, valuelist);

			if (arguments.length == 3 && typeof(isdraft) != "undefined" && isdraft) {
				if (typeof(drafter.zipcode) == "undefined" || drafter.zipcode == "" || drafter.zipcode == "null") {
					if (message == "")
						message += HwpConst.Form.Zipcode;
					else
						message += ", " + HwpConst.Form.Zipcode;
				}
				if (typeof(drafter.address) == "undefined" || drafter.address == "" || drafter.address == "null") {
					if (message == "")
						message += HwpConst.Form.Address;
					else
						message += ", " + HwpConst.Form.Address;
				}
				if (typeof(drafter.homepage) == "undefined" || drafter.homepage == "" || drafter.homepage == "null") {
					if (message == "")
						message += HwpConst.Form.OrgHomepage;
					else
						message += ", " + HwpConst.Form.OrgHomepage;
				}
				if (typeof(drafter.telephone) == "undefined" || drafter.telephone == "" || drafter.telephone == "null") {
					if (message == "")
						message += HwpConst.Form.Telephone;
					else
						message += ", " + HwpConst.Form.Telephone;
				}
				if (typeof(drafter.fax) == "undefined" || drafter.fax == "" || drafter.fax == "null") {
					if (message == "")
						message += HwpConst.Form.DeptFax;
					else
						message += ", " + HwpConst.Form.DeptFax;
				}
				if (typeof(drafter.email) == "undefined" || drafter.email == "" || drafter.email == "null") {
					if (message == "")
						message += HwpConst.Form.Email;
					else
						message += ", " + HwpConst.Form.Email;
				}

				if (message != "") {
				//	alert(HwpConst.Msg.NOT_SETTING_DRAFTERINFO.replace("%s", message));
					return false;
				}
			}

			return true;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setDrafter");
	}
}


// set Approver Form
// argument[0] : HwpCtrl Object
// argument[1] : approvers info - approver list - approver(position, name, date, type, opinion, specialrole)
function setApprover(hwpCtrl, approvers, linecount, doctype, assistantlinetype, auditlinetype) {
	try {
		if (hwpCtrl != null) {
			if (isStandardForm(hwpCtrl)) {
				var finalflag = false;
				var apprcount = 1;
				var assistcount = 1;
				var agreecount = 1;
				var auditcount = 1;
				var fieldlist = "";
				var valuelist = "";
				if (linecount == 1) {
					var approvercount = approvers.length;
					for (var loop = 0; loop < approvercount; loop++) {
						var approver = approvers[loop];
						var apprtype = getApprovalCode(approver.type, approver.linenum, doctype, assistantlinetype, auditlinetype);      // transfer appr-code to appr-codename
						if (apprtype != "") {
							// check special role
							if (approver.approverrole.indexOf("OPT051")>-1) {  // 발의자
								approver.position = HwpConst.Field.Proposer + approver.position;   // 발의자
								if (approver.approverrole.indexOf("OPT052")>-1) { 
									approver.position = HwpConst.Field.Reporter + approver.position;   // 발의자&보고자
								}
							} else	if (approver.approverrole.indexOf("OPT052")>-1) { // 발의자&보고자
									approver.position = HwpConst.Field.Reporter + approver.position;   // 보고자
							}
							//협조
							if (apprtype == HwpConst.Appr.Assistance || apprtype == HwpConst.Appr.ParellelAssistance) {		// assistcount
								fieldlist += "*" + HwpConst.Form.Assistance + assistcount + "\u0002" + HwpConst.Form.Assistance + (assistcount++) + "\u0002";
							//합의(결재양식에서는 협조란을 함께 사용한다.)
							}else if (apprtype == HwpConst.Appr.Agree || apprtype == HwpConst.Appr.ParellelAgree) {		// agreecount
								fieldlist += "*" + HwpConst.Form.Assistance + assistcount + "\u0002" + HwpConst.Form.Assistance + (assistcount++) + "\u0002";
							} else if (apprtype == HwpConst.Appr.Audit) {		// auditcount
								fieldlist += "*" + HwpConst.Form.Auditor + auditcount + "\u0002" + HwpConst.Form.Auditor + (auditcount++) + "\u0002";
							} else {																// approver
								fieldlist += "*" + HwpConst.Form.Consider + apprcount + "\u0002@" + HwpConst.Form.Consider + apprcount + "\u0002" + HwpConst.Form.Consider + (apprcount++) + "\u0002";
							}
							
							
							if (apprtype == HwpConst.Appr.Assistance || apprtype == HwpConst.Appr.ParellelAssistance ||
							    apprtype == HwpConst.Appr.Agree      || apprtype == HwpConst.Appr.ParellelAgree      || apprtype == HwpConst.Appr.Audit) {
	//							if (typeof(approver.opinion) == "undefined" || approver.opinion == "")
									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
										valuelist += approver.position + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.name) + "\u0002";
									else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
										valuelist += HwpConst.Form.DecideFor + approver.reppos + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.repname) + "\u0002";
									else
										valuelist += approver.position + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), approver.signfile) + "\u0002"; //isApproved(approver.date, typeOfER(approver.absreason)) + "\u0002";
									
	//							else
	//								if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
	//									valuelist += approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + isApproved(approver.date, approver.name) + "\u0002";
	//								else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
	//									valuelist += approver.reppo + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + isApproved(approver.date, approver.repname) + "\u0002";
	//								else
	//									valuelist += approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + isApproved(approver.date, typeOfER(approver.absreason)) + "\u0002";
							} else if (apprtype != HwpConst.Appr.ReadAfter) {
								if (!finalflag && ((apprtype == HwpConst.Appr.Submit && approvercount == 1) || (apprtype == HwpConst.Appr.Approver || apprtype == HwpConst.Appr.Arbitrary || apprtype == HwpConst.Appr.DecideFor || apprtype == HwpConst.Appr.DecideForArbitrary))) {
	//								if (typeof(approver.opinion) == "undefined" || approver.opinion == "")
										if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
											valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.name) + "\u0002";
										else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
											valuelist += HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.repname) + "\u0002";
										else
											valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), approver.signfile) + "\u0002"; //isApproved(approver.date, typeOfER(approver.absreason)) + "\u0002";
	//								else
	//									valuelist += approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + isApproved(approver.date, approver.name) + "\u0002";
										finalflag = true;
								} else {
	//								if (typeof(approver.opinion) == "undefined" || approver.opinion == "") {
										if (finalflag && apprtype == HwpConst.Appr.Arbitrary) {
											if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
												valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002\u0002";
											else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
												valuelist += HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + "\u0002\u0002";
											else
												valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002\u0002";
										} else {
											if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
												valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.name) + "\u0002";
											else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
												valuelist += HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.repname) + "\u0002";
											else
												valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), approver.signfile) + "\u0002"; //isApproved(approver.date, typeOfER(approver.absreason)) + "\u0002";
										}
	//								} else {
	//									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
	//										valuelist += approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) + "\u0002" + isApproved(approver.date, approver.name) + "\u0002";
	//									else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
	//										valuelist += HwpConst.Form.DecideFor + approver.reppos + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) + "\u0002" + isApproved(approver.date, approver.repname) + "\u0002";
	//									else
	//										valuelist += approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) + "\u0002" + isApproved(approver.date, typeOfER(approver.absreason)) + "\u0002";
	//								}
								}
							}
						}
					} //for
				} else {  // linecount != 1
					var draftdept = 0;
					var execdept = 0;
					var approvercount = approvers.length;
					for (var loop = 0; loop < approvercount; loop++) {
						var approver = approvers[loop];
						var apprtype = getApprovalCode(approver.type, approver.linenum, doctype, assistantlinetype, auditlinetype);      // transfer appr-code to appr-codename
						if (apprtype != "") {
							if (approver.linenum == "1") {
								draftdept++;
								fieldlist += "*" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002@" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002#" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002";
							} else if (approver.linenum == "2") {
								execdept++;
								fieldlist += "*" + HwpConst.Form.Consider + "2-" + execdept + "\u0002@" + HwpConst.Form.Consider + "2-" + execdept + "\u0002#" + HwpConst.Form.Consider + "2-" + execdept + "\u0002" + HwpConst.Form.Consider + "2-" + execdept + "\u0002";
							}
							if (apprtype == HwpConst.Appr.Approver || apprtype == HwpConst.Appr.Arbitrary || apprtype == HwpConst.Appr.DecideFor || apprtype == HwpConst.Appr.DecideForArbitrary) {
								if (typeof(approver.absreason) == "undefined" || approver.absreason == "") {
									valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.name) + "\u0002";
								} else if (typeof(approver.repname) != "undefined" && approver.repname != "") {								
									valuelist += HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.repname + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.repname) + "\u0002";
								} else {
									valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), approver.signfile) + "\u0002"; //isApproved(approver.date, typeOfER(approver.absreason)) + "\u0002";
								}
							} else {
								if (typeof(approver.absreason) == "undefined" || approver.absreason == "") {
									valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.name) + "\u0002";
								} else if (typeof(approver.repname) != "undefined" && approver.repname != "") {								
									valuelist += HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.repname + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002"; //isApproved(approver.date, approver.repname) + "\u0002";
								} else {
									valuelist += approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), approver.signfile) + "\u0002"; //isApproved(approver.date, typeOfER(approver.absreason)) + "\u0002";
								}
							}
						}
					} //for
				}
				putFieldText(hwpCtrl, fieldlist, valuelist);
			} else {   // is not standard form
				var finalflag = false;
				var apprcount = 1;
				var assistcount = 1;
				var auditcount = 1;
				var fieldlist = "";
				var valuelist = "";
				if (linecount == 1) {
					var approvercount = approvers.length;
					for (var loop = 0; loop < approvercount; loop++) {
						var approver = approvers[loop];
						var apprtype = getApprovalCode(approver.type, approver.linenum, doctype, assistantlinetype, auditlinetype);      // transfer appr-code to appr-codename
						if (apprtype != "") {
							// check special role
/*
							if (!isNaN(parseInt(approver.specialrole)) && (parseInt(approver.specialrole) & 0x01) == 1) {
								approver.position = HwpConst.Field.Proposer + approver.position;   // 발의자
							}
							if (!isNaN(parseInt(approver.specialrole)) && (parseInt(approver.specialrole) & 0x02) == 2) {
								approver.position = HwpConst.Field.Reporter + approver.position;   // 보고자
							}
*/
							// check special role
							if (approver.approverrole.indexOf("OPT051")>-1) {  // 발의자
								approver.position = HwpConst.Field.Proposer + approver.position;   // 발의자
								if (approver.approverrole.indexOf("OPT052")>-1) { 
									approver.position = HwpConst.Field.Reporter + approver.position;   // 발의자&보고자
								}
							} else	if (approver.approverrole.indexOf("OPT052")>-1) { // 발의자&보고자
									approver.position = HwpConst.Field.Reporter + approver.position;   // 보고자
							}
							
			
							if (apprtype == HwpConst.Appr.Assistance || apprtype == HwpConst.Appr.ParellelAssistance) {		// assistcount
								fieldlist += "^" + HwpConst.Form.Assistance + assistcount + "\u0002*" + HwpConst.Form.Assistance + assistcount + "\u0002#" + HwpConst.Form.Assistance + assistcount + "\u0002$" + HwpConst.Form.Assistance + assistcount + "\u0002" + HwpConst.Form.Assistance + (assistcount++) + "\u0002";
							} else if (apprtype == HwpConst.Appr.Agree || apprtype == HwpConst.Appr.ParellelAgree) {		// assistcount
									fieldlist += "^" + HwpConst.Form.Assistance + assistcount + "\u0002*" + HwpConst.Form.Assistance + assistcount + "\u0002#" + HwpConst.Form.Assistance + assistcount + "\u0002$" + HwpConst.Form.Assistance + assistcount + "\u0002" + HwpConst.Form.Assistance + (assistcount++) + "\u0002";
							} else if (apprtype == HwpConst.Appr.Audit) {		// auditcount
								fieldlist += "^" + HwpConst.Form.Auditor + auditcount + "\u0002*" + HwpConst.Form.Auditor + auditcount + "\u0002#" + HwpConst.Form.Auditor + auditcount + "\u0002$" + HwpConst.Form.Auditor + auditcount + "\u0002" + HwpConst.Form.Auditor + (auditcount++) + "\u0002";
							} else {																// approver
								fieldlist += "^" + HwpConst.Form.Consider + apprcount + "\u0002*" + HwpConst.Form.Consider + apprcount + "\u0002@" + HwpConst.Form.Consider + apprcount + "\u0002#" + HwpConst.Form.Consider + apprcount + "\u0002$" + HwpConst.Form.Consider + apprcount + "\u0002" + HwpConst.Form.Consider + (apprcount++) + "\u0002";
							}
		
							if (apprtype == HwpConst.Appr.Assistance || apprtype == HwpConst.Appr.ParellelAssistance ||
								apprtype == HwpConst.Appr.Agree || apprtype == HwpConst.Appr.ParellelAgree ||
								apprtype == HwpConst.Appr.Audit) {
	//							if (typeof(approver.opinion) == "undefined" || approver.opinion == "")
									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
									else if (typeof(approver.repname) != "undefined" && approver.repname != "")	
										valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
									else
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
	//							else
	//								if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
	//									valuelist += apprtype + "\u0002" + approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
	//								else if (typeof(approver.repname) != "undefined" && approver.repname != "")	
	//									valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + approver.name + "\u0002" + approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
	//								else
	//									valuelist += apprtype + "\u0002" + approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
							} else if (apprtype != HwpConst.Appr.ReadAfter) {
								if (!finalflag && ((apprtype == HwpConst.Appr.Submit && approvercount == 1) || (apprtype == HwpConst.Appr.Approver || apprtype == HwpConst.Appr.Arbitrary || apprtype == HwpConst.Appr.DecideFor || apprtype == HwpConst.Appr.DecideForArbitrary))) {
	//								if (typeof(approver.opinion) == "undefined" || approver.opinion == "")
									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
									else if (typeof(approver.repname) != "undefined" && approver.repname != "")	
										valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
									else
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
	//								else
	//									valuelist += apprtype + "\u0002" + approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
										finalflag = true;
								} else {
	//								if (typeof(approver.opinion) == "undefined" || approver.opinion == "") {
										if (finalflag && apprtype == HwpConst.Appr.Arbitrary) {
											if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
												valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002\u0002";
											else if (typeof(approver.repname) != "undefined" && approver.repname != "")	
												valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002\u0002";
											else
												valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002\u0002";
										} else {
											if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
												valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
											else if (typeof(approver.repname) != "undefined" && approver.repname != "")	
												valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
											else
												valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
										}
	//								} else {
	//									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
	//										valuelist += apprtype + "\u0002" + approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.dept + "\u0002" + approver.name + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
	//									else if (typeof(approver.repname) != "undefined" && approver.repname != "")	
	//										valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.dept + "\u0002" + approver.name + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
	//									else
	//										valuelist += apprtype + "\u0002" + approver.position + "(" + HwpConst.Form.ExistOpinion + ")\u0002" + typeOfAppr(apprtype) +"\u0002" + approver.dept +  "\u0002" + approver.name + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
	//								}
								}
							}
						}
					}  //for
				} else {
					var draftdept = 0;
					var execdept = 0;
					var approvercount = approvers.length;
					for (var loop = 0; loop < approvercount; loop++) {
						var approver = approvers[loop];
						var apprtype = getApprovalCode(approver.type, approver.linenum, doctype, assistantlinetype, auditlinetype);      // transfer appr-code to appr-codename
						if (apprtype != "") {
							if (approver.linenum == "1") {
								draftdept++;
								fieldlist += "^" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002*" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002@" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002#" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002$" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002" + HwpConst.Form.Consider + "1-" + draftdept + "\u0002";
							} else if (approver.linenum == "2") {
								execdept++;
								fieldlist += "^" + HwpConst.Form.Consider + "2-" + execdept + "\u0002*" + HwpConst.Form.Consider + "2-" + execdept + "\u0002@" + HwpConst.Form.Consider + "2-" + execdept + "\u0002#" + HwpConst.Form.Consider + "2-" + execdept + "\u0002$" + HwpConst.Form.Consider + "2-" + execdept + "\u0002" + HwpConst.Form.Consider + "2-" + execdept + "\u0002";
							}
							if (apprtype == HwpConst.Appr.Approver || apprtype == HwpConst.Appr.Arbitrary || apprtype == HwpConst.Appr.DecideFor || apprtype == HwpConst.Appr.DecideForArbitrary) {
								if (typeof(approver.absreason) == "undefined" || approver.absreason == "") {
									valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" +  approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
								} else if (typeof(approver.repname) != "undefined" && approver.repname != "") {
									valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.repname + "\u0002" +  approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
								} else {
									valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" +  approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
								}
							} else {
								if (typeof(approver.absreason) == "undefined" || approver.absreason == "") {
									valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
								} else if (typeof(approver.repname) != "undefined" && approver.repname != "") {
									valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
								} else {
									valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" +approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
								}
							}
						}
					} // for
				}
				
				putFieldText(hwpCtrl, fieldlist, valuelist);
			}  // if standard form
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("setApprover");
	}
}


// set Approver Form
// argument[0] : HwpCtrl Object
function clearApprover(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			// Consider
			var considercount = getConsiderCount(hwpCtrl);
			for (var loop = 1; loop <= considercount; loop++) {
				clearImage(hwpCtrl, HwpConst.Appr.Consider + loop);
			}
			// Assistance
			var assistancecount = getAssistanceCount(hwpCtrl);
			for (var loop = 1; loop <= assistancecount; loop++) {
				clearImage(hwpCtrl, HwpConst.Appr.Assistance + loop);
			}
			// Draft Dept Consider
			var approvercount = getLineApproverCount(hwpCtrl, 1);
			for (var loop = 1; loop <= approvercount; loop++) {
				clearImage(hwpCtrl, HwpConst.Appr.Consider + "1-" + loop);
			}
			// Exec Dept Consider
			approvercount = getLineApproverCount(hwpCtrl, 2);
			for (var loop = 1; loop <= approvercount; loop++) {
				clearImage(hwpCtrl, HwpConst.Appr.Consider + "2-" + loop);
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearApprover");
	}
}


//set Approver Form
//argument[0] : HwpCtrl Object
//argument[1] : approvers info - approver list - approver(position, name, date, type, opinion, specialrole)
function resetApprover(hwpCtrl, approvers, linecount, doctype, assistantlinetype, auditlinetype) {
	try {
		clearApprover(hwpCtrl);
		if (typeof(doctype) == "undefined") {
			doctype = "";
		}
		//setApprover(hwpCtrl, approvers, linecount, doctype, assistantlinetype, auditlinetype);
		 setApprovalFlow(hwpCtrl,approvers, linecount);
		
		var docinfo = new Object();
		var firstApprover = getFirstApprover(approvers);
		var lastApprover = getLastApprover(approvers);
		if (firstApprover == null) {
			docinfo.drafterpos = "";
			docinfo.draftername = "";
			docinfo.draftdept = "";
			docinfo.draftdate =  "";
		} else {
			docinfo.drafterpos = firstApprover.position;
			docinfo.draftername = firstApprover.name;
			docinfo.draftdept = firstApprover.dept;
			docinfo.draftdate =  typeOfDate("ledger", firstApprover.date);
		}
		if (lastApprover == null) {
			docinfo.approverpos = "";
			docinfo.approvername = "";
			docinfo.approvaldept = "";
			docinfo.approvaldate = "";
		} else {
			docinfo.approverpos = (lastApprover.repname == "") ? lastApprover.position : lastApprover.reppos;
			docinfo.approvername = (lastApprover.repname == "") ? lastApprover.name : lastApprover.repname;
			docinfo.approvaldept = (lastApprover.repname == "") ? lastApprover.dept : lastApprover.repdept;
			docinfo.approvaldate = typeOfDate("ledger", lastApprover.date);
		}
		setExtraInfo(hwpCtrl, docinfo);
	} catch (error) {
		errormessage("resetApprover");
	}
}


// set Approver Form
// argument[0] : HwpCtrl Object
// argument[1] : Document Number (Deptname + reginumber)
// argument[2] : Approval Date
function putRegiInfo(hwpCtrl, number, date) {
	try {
		if (hwpCtrl != null) {
			var result = false;
			// Document Number
			if (existField(hwpCtrl, HwpConst.Form.EnforceNumber)) {
				putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, number);
				result = true;
			}
			if (existField(hwpCtrl, HwpConst.Form.RegiNumber)) {
				putFieldText(hwpCtrl, HwpConst.Form.RegiNumber, number);
				result = true;
			}
			if (existField(hwpCtrl, HwpConst.Form.DocNumber)) {
				putFieldText(hwpCtrl, HwpConst.Form.DocNumber, number);
				result = true;
			}
			// Approval Date
			if (existField(hwpCtrl, HwpConst.Form.RegiDate)) {
				putFieldText(hwpCtrl, HwpConst.Form.RegiDate, typeOfDate("ledger", date));
				result = true;
			}
			if (existField(hwpCtrl, HwpConst.Form.ApprovalDate)) {
				putFieldText(hwpCtrl, HwpConst.Form.ApprovalDate, typeOfDate("ledger", date));
				result = true;
			}

			return result;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("putRegiInfo");
	}
}


// clear RegiInfo
// argument[0] : HwpCtrl Object
function clearRegiInfo(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			// Document Number, Approval Date, Enforce Number, Enforce Date, Receive Number, Receive Date
			var fieldlist = HwpConst.Form.EnforceNumber + "\u0002" + HwpConst.Form.EnforceDate + "\u0002"
							+ HwpConst.Form.RegiNumber + "\u0002" + HwpConst.Form.RegiDate + "\u0002"
							+ HwpConst.Form.DocNumber + "\u0002" + HwpConst.Form.ApprovalDate + "\u0002"
							+ HwpConst.Form.ReceiveNumber + "\u0002" + HwpConst.Form.ReceiveDate + "\u0002"
							+ HwpConst.Field.Opinion;
			var fieldvalue = "\u0002\u0002\u0002\u0002\u0002\u0002\u0002\u0002";

			putFieldText(hwpCtrl, fieldlist, fieldvalue);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearRegiInfo");
	}
}


//set Document Form
//argument[0] : HwpCtrl Object
//argument[1] : docinfo info - docinfo(headercampaign, organname, receiver, via, title, content, sendername, receiverlist,
//										enforcenumber, enforcedate, receivenumber, receivedate, publicbound, footercampaign)
function setExtraInfo(hwpCtrl, docinfo) {
	try {
		if (hwpCtrl != null) {
			var fieldlist = HwpConst.Field.DraftDept + "\u0002" + HwpConst.Field.DraftPos + "\u0002" 
			+ HwpConst.Field.DraftName + "\u0002" + HwpConst.Field.DraftDate + "\u0002" 
			+ HwpConst.Field.ApprovalDept + "\u0002" + HwpConst.Field.ApprovalPos + "\u0002" 
			+ HwpConst.Field.ApprovalName + "\u0002" + HwpConst.Field.ApprovalDate;
			
			var valuelist = typeOf(docinfo.draftdept) + "\u0002" + typeOf(docinfo.drafterpos) + "\u0002" 
			+ typeOf(docinfo.draftername) + "\u0002" + typeOf(docinfo.draftdate) + "\u0002"
			+ typeOf(docinfo.approvaldept) + "\u0002" + typeOf(docinfo.approverpos) + "\u0002" 
			+ typeOf(docinfo.approvername) + "\u0002" + typeOf(docinfo.approvaldate);
	
			putFieldText(hwpCtrl, fieldlist, valuelist);
		}
	} catch (error) {
		errormessage("setExtraInfo");
	}
}


// [20090317.백영호] - saveSignBodyFile를 호출하도록 수정
// save BodyFile without Stamp
// argument[0] : HwpCtrl Object
// argument[1] : Original File Fullpath
// argument[2] : TRUE/FALSE to make a Distribution Document
function saveBodyFile(hwpCtrl, filepath, distribute) {
	try {
		if (hwpCtrl != null) {
			return saveSignBodyFile(hwpCtrl, filepath, distribute, false);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("saveBodyFile");
	}
}


// [20090317.백영호]-서명인,관인을 모두 포함하는 본문저장 method 추가
// save BodyFile
// argument[0] : HwpCtrl Object
// argument[1] : Original File Fullpath
// argument[2] : TRUE/FALSE to make a Distribution Document 
// argument[3] : TRUE/FALSE to include stamp
// argument[4] : new filepath to Save
function saveSignBodyFile(hwpCtrl, filepath, distribute, sign, localpath) {
	try {
		if (hwpCtrl != null) {

			// 서명인, 관인제거 여부 체크
			if (!sign) {
				// 서명정보를 제거한다.
				clearApprover(hwpCtrl);
				// 관인(서명인)/생략인정보를 제거한다.
				clearStamp(hwpCtrl);
			}

			// 문서의 최상위로 이동한다.
			moveToPos(hwpCtrl, 2);

			// 문서를 저장한다.
			if (arguments.length == 4) {
			    saveAsHwpDocument(hwpCtrl);
			} else {
			    saveHwpDocument(hwpCtrl, localpath, true);
			}

			if (distribute)
				makeDistributeDocument(hwpCtrl, false, true);

			// 원문을 다시 오픈한다.
			return openHwpDocument(hwpCtrl, filepath);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("saveSignBodyFile");
	}
}


// make Document Form for Save or Print
// insert Form Resource
// argument[0] : HwpCtrl Object
// argument[1] : Form Resource path
// argument[2] : Position to insert
function insertFormResource(hwpCtrl, filepath, movepos) {
	try {
		if (hwpCtrl != null) {
			if (arguments.length == 3)
				moveToPos(hwpCtrl, movepos);

			insertHwpDocument(hwpCtrl, filepath + "documentcard.hwp");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertFormResource");
	}
}


// insert Document Headline
// argument[0] : HwpCtrl Object
// argument[1] : Field Name
// argument[2] : Field Data
// argument[3] : Data Font
// argument[4] : Data Fontsize
function insertHeadline(hwpCtrl, field, data, font, fontsize) {
	try {
		if (hwpCtrl != null) {
			if (font == null)
				font = HwpConst.Font.Gulimche;
			if (fontsize == null)
				fontsize = 1200;

			moveToPos(hwpCtrl, field);
			data = data.replace(/&quot;/g, "\"");
			data = data.replace(/&amp;/g, "&");
			data = data.replace(/&lt;/g, "<");
			data = data.replace(/&gt;/g, ">");
			data = data.replace(/\u0003/g, "\r\n");
			data = data.replace(/DEA11/g, HwpConst.Appr.Submit);
			data = data.replace(/DEA21/g, HwpConst.Appr.Consider);
			data = data.replace(/DEA31/g, HwpConst.Appr.Assistance);
			data = data.replace(/DEA32/g, HwpConst.Appr.ParellelAssistance);
			data = data.replace(/DEA41/g, HwpConst.Appr.Approver);
			data = data.replace(/DEA51/g, HwpConst.Appr.Arbitrary);
			data = data.replace(/DEA61/g, HwpConst.Appr.DecideFor);
			data = data.replace(/DEA71/g, HwpConst.Appr.DecideForArbitrary);
			var datalist = data.split("\u0004");
			var datacount = datalist.length;
			for (var loop = 0; loop < datacount - 1; loop++)
			{
				if (datalist[loop] == "[" + HwpConst.Field.Title + "]"
					|| datalist[loop] == "[" + HwpConst.Field.Summary + "]"
					|| datalist[loop] == "[" + HwpConst.Field.ReportPath + "]") {
					setDefaultFont(hwpCtrl, font, 1, fontsize);
				} else {
					setDefaultFont(hwpCtrl, font, 0, fontsize);
				}

				if (loop == datacount - 2)
					insertText(hwpCtrl, datalist[loop]);
				else
					insertText(hwpCtrl, datalist[loop] + "\r\n");
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("insertHeadline");
	}
}

//양식내 의견필드 추가
function insertOpinionTbl(hwpCtrl, filepath, totalOpinion)
{
	var nEditMode = hwpCtrl.EditMode;
	if ((nEditMode & 0x01) != 0)
		hwpCtrl.EditMode = 0x02;

	/**
	if (!existField(hwpCtrl, HwpConst.Field.Opinion))	//필드 존재하지 않을 경우 필드 생성
	{
		g_DocWord = Document_HwpCtrl.WordDocument;
		
		var contentCell = g_DocWord.Bookmarks(HwpConst.Form.Content + "1").Range.Cells(1);
		var cellHeight = contentCell.Height;
		
		//본문 cell 높이조정(wdRowHeightAuto : 0, wdRowHeightAtLeast : 1, wdRowHeightExactly : 2)
		contentCell.HeightRule = 0; 
		
		//본문 cell 나누기(의견 cell 생성)
		contentCell.Split(2,1);	
		
		//의견 Bookmark 생성
		g_DocWord.Bookmarks.Add(HwpConst.Field.Opinion, contentCell.Next.Range);
		var opinionCell = g_DocWord.Bookmarks(HwpConst.Field.Opinion).Range.Cells(1);
		
		//현재본문cell 높이
		var resizeCellHeight = contentCell.Height;
		
		if(cellHeight >= (resizeCellHeight * 2)) {
			//의견 cell 높이조정 : 전체Cell 높이 - 본문cell) 
			opinionCell.Height = cellHeight - (resizeCellHeight * 2);
		}
	}
	**/
	
	hwpCtrl.EditMode = nEditMode;
	
	totalOpinion = typeOfRC(totalOpinion);		//엔터키 변환
	putFieldText(hwpCtrl, HwpConst.Field.Opinion, totalOpinion);
	
}

//양식내 의견필드 제거
function deleteOpinionTbl(hwpCtrl)
{
	if (existField(hwpCtrl, HwpConst.Field.Opinion))
	{
		putFieldText(Document_HwpCtrl, HwpConst.Field.Opinion, "\u0002");	//의견 초기화
		
		var nEditMode = hwpCtrl.EditMode;
	
		// 문서 편집모드로 변경
		hwpCtrl.EditMode = 0x01;

		var opinionBookmark = g_DocWord.Bookmarks(HwpConst.Field.Opinion);
		
		//의견 cell 삭제 (wdDeleteCellsShiftLeft : 0, wdDeleteCellsShiftUp : 1, wdDeleteCellsEntireRow : 2, wdDeleteCellsEntireColumn : 3)
		//opinionBookmark.Range.Cells(1).Delete(2);
		
		//의견 삭제
		opinionBookmark.Range.Cells(1).Range.Text = "";

		// 양식모드로 변경
		hwpCtrl.EditMode = nEditMode;
	}
}

function deleteDocumentLine(hwpCtrl, nLineCount)
{
	for (var nLoop = 0; nLoop < nLineCount; nLoop++)
	{
		//20120510 기존함수로 호출시 스크립트 오류 발생하여 변경  kj.yang
		//runHwpAction("DeleteBack");
		runHwpAction(hwpCtrl, "DeleteBack");	
	}
}

// check stardard form
// argument[0] : HwpCtrl Object
function isStandardForm(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			return existField(hwpCtrl, HwpConst.Form.Title);
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("isStandardForm");
	}
}


// transfer appr-code to appr-codename
function getApprCodeName (apprcode) {
	try {
	    if (apprcode == "DEA11")
	        return HwpConst.Appr.Submit;
	    else if (apprcode == "DEA21")
	        return HwpConst.Appr.Consider;
	    else if (apprcode == "DEA31")
	        return HwpConst.Appr.Assistance;
	    else if (apprcode == "DEA32")
	        return HwpConst.Appr.ParellelAssistance;
	    else if (apprcode == "DEA41")
	        return HwpConst.Appr.Approver;
	    else if (apprcode == "DEA51")
	        return HwpConst.Appr.Arbitrary;
	    else if (apprcode == "DEA61")
	        return HwpConst.Appr.DecideFor;
	    else if (apprcode == "DEA71")
	        return HwpConst.Appr.DecideForArbitrary;
	    else
	        return apprcode;
	} catch (error) {
		errormessage("getApprCodeName");
	}
}


// check the state of Approval
function isApproved(date, name) {
	try {
		if (typeof(date) == "undefined" || date == "undefined" || date == "" || date == "9999-12-31 23:59:59")
			return "";
		else
			return name;
	} catch (error) {
		errormessage("isApproved");
	}
}


function isSigned(date, name, signfile) {
	try {
		if (typeof(date) == "undefined" || date == "undefined" || date == "" || date == "9999-12-31 23:59:59") {
			return "";
		} else {
			if (signfile == "" || FileManager.getfileinfo(FileManager.getlocaltempdir() + signfile) == null) {
				return name;
			} else {
				return FileManager.getlocaltempdir() + signfile;
			}
		}
	} catch (error) {
		errormessage("isSigned");
	}
}


// transfer Data Type
function typeOfAppr(type) {
	try {
		// DecideFor, DecideForArbitrary -> DecideFor
		// Arbitrary -> Arbitrary
		// Others -> ""
		if (type == HwpConst.Appr.DecideFor || type == HwpConst.Appr.DecideForArbitrary)
			return HwpConst.Appr.DecideFor;
		else if (type == HwpConst.Appr.Arbitrary)
			return HwpConst.Appr.Arbitrary;
		else
			return "";
	} catch (error) {
		errormessage("typeOfAppr");
	}
}


// transfer Sender
function typeOfSender(value) {
	if (typeof(value) != "undefined") {
		var space = "";

		if (value.length == 3) {
			space = "      ";			// 6 spaces
		} else if (value.length == 4) {
			space = "    ";				// 4 spaces
		} else if (value.length == 5) {
			space = "  ";				// 2 spaces
		} else if (value.length == 6) {
			space = " ";				// 1 spaces
		}

		if (space != "") {
			var newvalue = "";

			for (var loop = 0; loop < value.length; loop++) {
				newvalue += value.substring(loop, loop+1);

				if (loop < value.length - 1)
					newvalue += space;
			}

			value = newvalue;
		}
	}

	return value;
}


// transfer RC
function typeOfRC(value) {
	var pos = value.indexOf("\u0003");
	while(pos != -1) {
		value = value.replace("\u0003", "\r\n");
		pos = value.indexOf("\u0003");
	}

	return value;
}


// transfer Date
function typeOfDate(type, date) {
	try {
		if (date == "9999-12-31 23:59:59")
			return "";
		
		if (type == "approval") {
			if ((date.length == 10) || (date.length == 19))
				return date.substring(0,4) + "." + date.substring(5,7) + "." + date.substring(8,10) + ".";
			else if (date.length == 14)
				return date.substring(0,4) + "." + date.substring(4,6) + "." + date.substring(6,8) + ".";
			else
				return date;
		} else if (type == "ledger") {
			var delimeter = "-";
			if ((HwpVariable.DateFormat).length > 8) {
				delimeter = (HwpVariable.DateFormat).substring(4,5);
			}
			if ((date.length == 10) || (date.length == 19))
				return date.substring(0,4) + delimeter + date.substring(5,7) + delimeter + date.substring(8,10);
			else if (date.length == 14)
				return date.substring(0,4) + delimeter + date.substring(4,6) + delimeter + date.substring(6,8);
			else
				return date;
		} else {
			if ((date.length == 10) || (date.length == 19))
				return "(" + date.substring(0,4) + ". " + date.substring(5,7) + ". " + date.substring(8,10) + ".)";
			else if (date.length == 14)
				return "(" + date.substring(0,4) + ". " + date.substring(4,6) + ". " + date.substring(6,8) + ".)";
			else
				return date;
		}
	} catch (error) {
		errormessage("typeOfDate");
	}
}


// transfer ER
function typeOfER(value) {
	if (value == "DAB01")
		return HwpConst.EmptyReason.DAB01;
	else if (value == "DAB02")
		return HwpConst.EmptyReason.DAB02;
	else if (value == "DAB03")
		return HwpConst.EmptyReason.DAB03;
	else if (value == "DAB04")
		return HwpConst.EmptyReason.DAB04;
	else if (value == "DAB05")
		return HwpConst.EmptyReason.DAB05;
	else if (value == "DAB06")
		return HwpConst.EmptyReason.DAB06;
	else if (value == "DAB07")
		return HwpConst.EmptyReason.DAB07;
	else if (value == "DAB08")
		return HwpConst.EmptyReason.DAB08;
	else if (value == "DAB09")
		return HwpConst.EmptyReason.DAB09;
	else if (value == "DAB10")
		return HwpConst.EmptyReason.DAB10;
	else if (value == "DAB11")
		return HwpConst.EmptyReason.DAB11;
	else if (value == "DAB12")
		return HwpConst.EmptyReason.DAB12;
	else if (value == "DAB13")
		return HwpConst.EmptyReason.DAB13;
	else if (value == "DAB14")
		return HwpConst.EmptyReason.DAB14;
	else
		return value;
}


// transfer Path
function typeOfPath(value) {
	return value.replace(String.fromCharCode(29), "").replace(String.fromCharCode(31), "");
}


// change undefined value to blank value
function typeOf(value) {
	return (typeof(value) == "undefined" || value == "undefined") ? "" : value;
}


// create new random value with date/time
function getRandomValue(depth) {
	var now = new Date();
	var year = "" + now.getYear();
	var month = (now.getMonth() + 1 < 10) ? "0" + (now.getMonth() + 1) : (now.getMonth() + 1);
	var day = (now.getDate() < 10) ? "0" + now.getDate() : now.getDate();
	var hour = (now.getHours() < 10) ? "0" + now.getHours() : now.getHours();
	var minute = (now.getMinutes() < 10) ? "0" + now.getMinutes() : now.getMinutes();
	var second = (now.getSeconds() < 10) ? "0" + now.getSeconds() : now.getSeconds();
	var millisecond = now.getMilliseconds();
	var random = "";

	if (typeof(depth) == "undefined" || isNaN(parseInt(depth)) || depth > 7)
		random = year + month + day + hour + minute + second + millisecond + (Math.random() * 100000000000000000);
	else if (depth == 7)
		random = year + month + day + hour + minute + second + millisecond;
	else if (depth == 6)
		random = year + month + day + hour + minute + second;
	else if (depth == 5)
		random = year + month + day + hour + minute;
	else if (depth == 4)
		random = year + month + day + hour;
	else if (depth == 3)
		random = year + month + day;
	else if (depth == 2)
		random = year + month;
	else if (depth == 1)
		random = year;
	else if (depth < 1)
		random = Math.round(Math.random() * 100000);

	return random;
}


// display Error Message
function errormessage(methodname) {
	if (typeof(methodname) == "undefined")
		alert(HwpConst.Msg.NOT_INSTALLED_ACTIVEX);
	else
		alert(HwpConst.Msg.OCCURRED_ERROR_REQUEST_MANAGER + "(ERROR:" + methodname + ")");
	return false;
}


// check span tag
// argument[0] : pubdoc.xml localpath
function existSpanTag(localpath) {
	// not support IE security policy
	return false;

	try {
		var xmlDom = new ActiveXObject("MSXML.DOMDocument");
		xmlDom.async = false;
		xmlDom.validateOnParse = false;
		if (xmlDom.load(localpath)) {
			if (xmlDom.selectNodes("//span").length > 0)
				return true;
			else if (xmlDom.selectNodes("//SPAN").length > 0)
				return true;
			else if (xmlDom.selectNodes("//Span").length > 0)
				return true;
			else
				return false;
		}
	} catch (error) {
		return false;
	}
}

//body configuration to block "copy"
function setLockCommand(hwpCtrl,strCommand, bSet)
{
	hwpCtrl.LockCommand(strCommand, bSet);
}

//added by jkkim word add work
function getTitle(){
	 var wordCtrl = selectModule("document");
	 var title = getFieldText(wordCtrl,HwpConst.Form.Title);
	 
	 if(title == undefined) {
		 title = "";
	 }
	 
	 return title;
}

function isFormData()
{
	var bIsFormData = false;

	if (g_DocWord == null) { //hjjung 2008.03.13 : worddoc 존재여부 확인
		bIsFormData = false;
	}
	else if (g_DocWord.Bookmarks.Exists(HwpConst.Field.Title))
	{
		bIsFormData = true;
	}

	return bIsFormData;
}

function setApprovalFlow(hwpCtrl,approvers, linecount)
{
	try {
			var finalflag = false;
			var apprcount = 1;
			var assistcount = 1;
			var agreecount = 1;
			var auditcount = 1;
			var fieldlist = "";
			var valuelist = "";
			if (linecount == 1) {
				var approvercount = approvers.length;
				for (var loop = 0; loop < approvercount; loop++) {
					var approver = approvers[loop];
					var apprtype = getApprovalCode(approver.type, approver.linenum, "doc", undefined, undefined);      // transfer appr-code to appr-codename
			
					if (apprtype != "") {
						// check special role
						if (approver.approverrole.indexOf("OPT051")>-1) {  // 발의자
							approver.position = HwpConst.Field.Proposer + approver.position;   // 발의자
							if (approver.approverrole.indexOf("OPT052")>-1) { 
								approver.position = HwpConst.Field.Reporter + approver.position;   // 발의자&보고자
							}
						} else	if (approver.approverrole.indexOf("OPT052")>-1) { // 발의자&보고자
								approver.position = HwpConst.Field.Reporter + approver.position;   // 보고자
						}
						
						//P_검토1,N_검토1,DN_검토1,D_검토1,검토1 : 직위,이름,부서명,서명날짜,서명
						
						//협조
						if (apprtype == HwpConst.Appr.Assistance || apprtype == HwpConst.Appr.ParellelAssistance) {		// assistcount
							fieldlist += "^" + HwpConst.Form.Assistance + assistcount + "\u0002P_" + HwpConst.Form.Assistance + assistcount + "\u0002N_" + HwpConst.Form.Assistance + assistcount + "\u0002DN_" + HwpConst.Form.Assistance + assistcount + "\u0002" + HwpConst.Form.Assistance + (assistcount++) + "\u0002";
						//합의
						} else if (apprtype == HwpConst.Appr.Agree || apprtype == HwpConst.Appr.ParellelAgree) {		// agreecount
							fieldlist += "^" + HwpConst.Form.Assistance + assistcount + "\u0002P_" + HwpConst.Form.Assistance + assistcount + "\u0002N_" + HwpConst.Form.Assistance + assistcount + "\u0002DN_" + HwpConst.Form.Assistance + assistcount + "\u0002" + HwpConst.Form.Assistance + (assistcount++) + "\u0002";
						//감사
						} else if (apprtype == HwpConst.Appr.Audit) {		// auditcount
							fieldlist += "^" + HwpConst.Form.Auditor + auditcount + "\u0002P_" + HwpConst.Form.Auditor + auditcount + "\u0002N_" + HwpConst.Form.Auditor + auditcount + "\u0002DN_" + HwpConst.Form.Auditor + auditcount + "\u0002" + HwpConst.Form.Auditor + (auditcount++) + "\u0002";
						/**
						//기안	
						}else if(apprtype == HwpConst.Appr.Submit){
							fieldlist += HwpConst.Form.Submit + "\u0002" + HwpConst.Form.Submit + "1\u0002N_" + HwpConst.Form.Submit + "1\u0002DN_" + HwpConst.Form.Submit + "\1u0002D_" + HwpConst.Form.Submit + "1\u0002";
						//결재,전결,대결
						} else if (apprtype == HwpConst.Appr.Approver || apprtype == HwpConst.Appr.Arbitrary || apprtype == HwpConst.Appr.DecideFor) {	
							fieldlist += "^" + HwpConst.Form.Approver + "\u0002P_" + HwpConst.Form.Approver + "1\u0002N_" + HwpConst.Form.Approver + "1\u0002DN_" + HwpConst.Form.Approver + "\1u0002D_" + HwpConst.Form.Approver + "\u0002";
						**/
						//기안,검토,결재
						} else {	
							if(apprtype != HwpConst.Appr.ReadAfter) {
								fieldlist += "^" + HwpConst.Form.Consider + apprcount + "\u0002P_" + HwpConst.Form.Consider + apprcount + "\u0002N_" + HwpConst.Form.Consider + apprcount + "\u0002DN_" + HwpConst.Form.Consider + apprcount + "\u0002D_" + HwpConst.Form.Consider + apprcount + "\u0002" + HwpConst.Form.Consider + (apprcount++) + "\u0002";
							}
						}
						
						if (apprtype == HwpConst.Appr.Assistance || apprtype == HwpConst.Appr.ParellelAssistance ||
						    apprtype == HwpConst.Appr.Agree      || apprtype == HwpConst.Appr.ParellelAgree      || apprtype == HwpConst.Appr.Audit) {
								if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
									valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";   //isApproved(approver.date, approver.name) + "\u0002";
								else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
									valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
								else
									valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
						} else if (apprtype != HwpConst.Appr.ReadAfter) {
							if (!finalflag && ((apprtype == HwpConst.Appr.Submit && approvercount == 1) || (apprtype == HwpConst.Appr.Approver || apprtype == HwpConst.Appr.Arbitrary || apprtype == HwpConst.Appr.DecideFor || apprtype == HwpConst.Appr.DecideForArbitrary))) {
									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
									else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
										valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
									else
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";

									finalflag = true;
							} else {
								if (finalflag && apprtype == HwpConst.Appr.Arbitrary) {
									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + typeOfAppr(apprtype) + "\u0002\u0002";
									else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
										valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + typeOfAppr(apprtype) + "\u0002\u0002";
									else
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + typeOfAppr(apprtype) + "\u0002\u0002";
								} else {
									if (typeof(approver.absreason) == "undefined" || approver.absreason == "")
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + typeOfAppr(apprtype) + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
									else if (typeof(approver.repname) != "undefined" && approver.repname != "")									
										valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + typeOfAppr(apprtype) + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
									else
										valuelist += apprtype + "\u0002" + approver.position + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + typeOfAppr(apprtype) + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
								}
							} //else end
						}
					}
				} //for end
			}else {	//이중결재
				var draftdept = 0;
				var execdept = 0;
				var approvercount = approvers.length;
				for (var loop = 0; loop < approvercount; loop++) {
					var approver = approvers[loop];
					var apprtype = getApprovalCode(approver.type, approver.linenum, "doc", undefined, undefined);      // transfer appr-code to appr-codename
					if (apprtype != "") {
						if (approver.linenum == "1") {
							draftdept++;
							fieldlist += "^" + HwpConst.Form.Consider + "1_" + draftdept + "\u0002P_" + HwpConst.Form.Consider + "1_" + draftdept + "\u0002D_" + HwpConst.Form.Consider + "1_" + draftdept + "\u0002N_" + HwpConst.Form.Consider + "1_" + draftdept + "\u0002DN_" + HwpConst.Form.Consider + "1_" + draftdept + "\u0002" + HwpConst.Form.Consider + "1_" + draftdept + "\u0002";
						} else if (approver.linenum == "2") {
							execdept++;
							fieldlist += "^" + HwpConst.Form.Consider + "2_" + execdept + "\u0002P_" + HwpConst.Form.Consider + "2_" + execdept + "\u0002D_" + HwpConst.Form.Consider + "2_" + execdept + "\u0002N_" + HwpConst.Form.Consider + "2_" + execdept + "\u0002DN_" + HwpConst.Form.Consider + "2_" + execdept + "\u0002" + HwpConst.Form.Consider + "2_" + execdept + "\u0002";
						}
						if (apprtype == HwpConst.Appr.Approver || apprtype == HwpConst.Appr.Arbitrary || apprtype == HwpConst.Appr.DecideFor || apprtype == HwpConst.Appr.DecideForArbitrary) {
							if (typeof(approver.absreason) == "undefined" || approver.absreason == "") {
								valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" +  approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
							} else if (typeof(approver.repname) != "undefined" && approver.repname != "") {
								valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.repname + "\u0002" +  approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
							} else {
								valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + " " + typeOfDate("approval", approver.date) + "\u0002" + approver.name + "\u0002" +  approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
							}
						} else {
							if (typeof(approver.absreason) == "undefined" || approver.absreason == "") {
								valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" + approver.dept + "\u0002" + isSigned(approver.date, approver.name, approver.signfile) + "\u0002";
							} else if (typeof(approver.repname) != "undefined" && approver.repname != "") {
								valuelist += apprtype + "\u0002" + HwpConst.Form.DecideFor + approver.reppos + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.repname + "\u0002" + approver.repdept + "\u0002" + isSigned(approver.date, approver.repname, approver.signfile) + "\u0002";
							} else {
								valuelist += apprtype + "\u0002" + approver.position + "\u0002" + typeOfAppr(apprtype) + "\u0002" + approver.name + "\u0002" +approver.dept + "\u0002" + isSigned(approver.date, typeOfER(approver.absreason), "") + "\u0002";
							}
						}
					}
				} // for end
			} // else end

			putFieldText(hwpCtrl,fieldlist, valuelist);

	} catch (error) {
		errormessage("setApprover");
	}
}


function setDataToForm()
{
	var strFieldList = "";
	var strValueList = "";
	
	// JKKIM added for 기안일시
	if (g_DocWord.Bookmarks.Exists(HwpConst.Field.DraftTime)==true)
	{
		strFieldList += HwpConst.Field.DraftTime + "\u0002";
		strValueList += draftdate + "\u0002";
	}
	// JKKIM added for 기안일시
	if (g_DocWord.Bookmarks.Exists(HwpConst.Form.DocNumber)==true)
	{
		var g_approvalNumber = deptcategory+"-"+serialnumber;
		strFieldList += HwpConst.Form.DocNumber + "\u0002";
		strValueList += g_approvalNumber + "\u0002";
	}
	if (strFieldList.length > 0)
	{
		strFieldList = strFieldList.substring(0, strFieldList.length - 1);
		strValueList = strValueList.substring(0, strValueList.length - 1);
		putFieldText(g_DocWord, strFieldList, strValueList);
	}


	// JKKIM added for 보호모드 처리
	if (g_DocWord.ProtectionType  == 1 || g_DocWord.ProtectionType == 2)
			g_DocWord.UnProtect();

	protectSection();
}

function setApprLineForWord(apprline, isinit, doubleYn){
	if (typeof(isinit) == "undefined") {
		isinit = false;
	}

	var line = getApproverList(apprline);
	var itemCount = getItemCount();
	var lineCount = 1;

	if(doubleYn == "Y") {
		lineCount = 2;
	}

	for (var loop = 0; loop < itemCount; loop++) {
		var itemnum = loop + 1;
		$("#appLine", "#approvalitem" + itemnum).val(apprline);
		setApprovalFlow(Document_HwpCtrl,getApproverUser(line), lineCount);
	}

}

function setApprLineForWordSend(apprline, isinit, doubleYn){
	if (typeof(isinit) == "undefined") {
		isinit = false;
	}

	var line = getApproverList(apprline);
	var lineCount = 1;
	
	if(doubleYn == "Y") {
		lineCount = 2;
	}
	
	$("#appLine", "#approvalitem").val(apprline);
	setApprovalFlow(Document_HwpCtrl,getApproverUser(line), lineCount);
}


function getCurrentVersion()
{
	return parseFloat(g_DocWord.Application.Version);
}


function clearApprovalField()
{
	var TITLE_APPROVE						= "결재";
	var FIELD_TITLE							= "제목";
	var FIELD_LAST							= "결재";
	var FIELD_DRAFTER						= "기안";
	var FIELD_CONSIDER						= "검토";
	var FIELD_COOPERATE						= "협조";
	var FIELD_PRIOR							= "선결";
	var FIELD_CHARGER						= "담당자";
	var FIELD_PUBLIC						= "공람";
	var FIELD_DEPART_INSPECT				= "감사";
	
	var nLoop = 1;
	var strFieldName = "";
	var strFieldList = "";

	//이름
    if (g_DocWord.Bookmarks.Exists("N_" + FIELD_CONSIDER + "1") == true || 
    		g_DocWord.Bookmarks.Exists("N_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("N_" +FIELD_CONSIDER + "2_1") == true)
	{
    	if(g_DocWord.Bookmarks.Exists("N_" + FIELD_CONSIDER + "1") == true) {
    		while(true)
    		{
    			strFieldName = "N_" + FIELD_DRAFTER + (nLoop++);
    			if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    				break;
    			strFieldList += strFieldName + "\u0002";
    		}

    		nLoop = 1;
    		while(true)
    		{
    			strFieldName = "N_" + FIELD_CONSIDER + (nLoop++);
    			if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    				break;
    			strFieldList += strFieldName + "\u0002";
    		}

    		nLoop = 1;
    		while(true)
    		{
    			strFieldName = "N_" + FIELD_COOPERATE + (nLoop++);
    			if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    				break;
    			strFieldList += strFieldName + "\u0002";
    		}

    		nLoop = 1;
    		while(true)
    		{
    			strFieldName = "N_" + FIELD_LAST + (nLoop++);
    			if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    				break;
    			strFieldList += strFieldName + "\u0002";
    		}

    		nLoop = 1;
    		while(true)
    		{
    			strFieldName = "N_" + FIELD_DEPART_INSPECT + (nLoop++);
    			if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    				break;
    			strFieldList += strFieldName + "\u0002";
    		}
    	}else if(g_DocWord.Bookmarks.Exists("N_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("N_" +FIELD_CONSIDER + "2_1") == true) {	//이중결재
    		for (var nPos = 1; nPos < 3; nPos++) 
    		{
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "N_" + FIELD_DRAFTER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}

    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "N_" + FIELD_CONSIDER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}

    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "N_" + FIELD_COOPERATE + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "N_" + FIELD_LAST + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    		
    		} //for end
    	} //else if end
	}
	else if (g_DocWord.Bookmarks.Exists(FIELD_CHARGER) == true)
	{
		strFieldName = FIELD_CHARGER;
		if (g_DocWord.Bookmarks.Exists(strFieldName) == true)
			strFieldList += strFieldName + "\u0002";

		nLoop = 1;
		while(true)
		{
			strFieldName = FIELD_PRIOR + (nLoop++);
			if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
				break;
			strFieldList += strFieldName + "\u0002";
		}

		nLoop = 1;
		while(true)
		{
			strFieldName = FIELD_PUBLIC + (nLoop++);
			if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
				break;
			strFieldList += strFieldName + "\u0002";
		}
	}

    //직위
	if (g_DocWord.Bookmarks.Exists("P_"+FIELD_CONSIDER+"1") == true ||
			g_DocWord.Bookmarks.Exists("P_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("P_" +FIELD_CONSIDER + "2_1") == true)
	{
		if(g_DocWord.Bookmarks.Exists("P_"+FIELD_CONSIDER+"1") == true) {
			/*nLoop = 1;
			while(true)
			{
				strFieldName = "P_" + FIELD_DRAFTER + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}*/

			nLoop = 1;
			while(true)
			{
				strFieldName = "P_" + FIELD_CONSIDER + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}

			nLoop = 1;
			while(true)
			{
				strFieldName = "P_" + FIELD_COOPERATE + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
			
			nLoop = 1;
			while(true)
			{
				strFieldName = "P_" + FIELD_LAST + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
			
			nLoop = 1;
			while(true)
			{
				strFieldName = "P_" + FIELD_DEPART_INSPECT + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
		}else if(g_DocWord.Bookmarks.Exists("P_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("P_" +FIELD_CONSIDER + "2_1") == true) {	//이중결재
    		for (var nPos = 1; nPos < 3; nPos++) 
    		{
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "P_" + FIELD_DRAFTER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "P_" + FIELD_CONSIDER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "P_" + FIELD_COOPERATE + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}

    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "P_" + FIELD_LAST + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    		} //for end
		} //else if end
	}
	
	//서명일자
	if (g_DocWord.Bookmarks.Exists("D_"+FIELD_CONSIDER+"1") == true ||
			g_DocWord.Bookmarks.Exists("D_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("D_" +FIELD_CONSIDER + "2_1") == true)
	{
		if (g_DocWord.Bookmarks.Exists("D_"+FIELD_CONSIDER+"1") == true) {
			nLoop = 1;
			while(true)
			{
				strFieldName = "D_" + FIELD_DRAFTER + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}

			nLoop = 1;
			while(true)
			{
				strFieldName = "D_" + FIELD_CONSIDER + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}

			nLoop = 1;
			while(true)
			{
				strFieldName = "D_" + FIELD_COOPERATE + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
			
			nLoop = 1;
			while(true)
			{
				strFieldName = "D_" + FIELD_LAST + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
			
			nLoop = 1;
			while(true)
			{
				strFieldName = "D_" + FIELD_DEPART_INSPECT + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
		}else if(g_DocWord.Bookmarks.Exists("D_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("D_" +FIELD_CONSIDER + "2_1") == tru) {
    		for (var nPos = 1; nPos < 3; nPos++) 
    		{
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "D_" + FIELD_DRAFTER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "D_" + FIELD_CONSIDER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "D_" + FIELD_COOPERATE + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}

    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "D_" + FIELD_LAST + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    		} //for end
		} //else if end
	}
	
	//부서명
	if (g_DocWord.Bookmarks.Exists("DN_"+FIELD_CONSIDER+"1") == true || 
			g_DocWord.Bookmarks.Exists("DN_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("DN_" +FIELD_CONSIDER + "2_1") == true)
	{
		if (g_DocWord.Bookmarks.Exists("DN_"+FIELD_CONSIDER+"1") == true) {
			nLoop = 1;
			while(true)
			{
				strFieldName = "DN_" + FIELD_DRAFTER + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}

			nLoop = 1;
			while(true)
			{
				strFieldName = "DN_" + FIELD_CONSIDER + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}

			nLoop = 1;
			while(true)
			{
				strFieldName = "DN_" + FIELD_COOPERATE + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
			
			nLoop = 1;
			while(true)
			{
				strFieldName = "DN_" + FIELD_LAST + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
			
			nLoop = 1;
			while(true)
			{
				strFieldName = "DN_" + FIELD_DEPART_INSPECT + (nLoop++);
				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
					break;
				strFieldList += strFieldName + "\u0002";
			}
		}else if(g_DocWord.Bookmarks.Exists("DN_" + FIELD_CONSIDER + "1_1") == true || g_DocWord.Bookmarks.Exists("DN_" +FIELD_CONSIDER + "2_1") == true) {
    		for (var nPos = 1; nPos < 3; nPos++) 
    		{
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "DN_" + FIELD_DRAFTER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "DN_" + FIELD_CONSIDER + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "DN_" + FIELD_COOPERATE + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}

    			nLoop = 1;
    			while(true)
    			{
    				strFieldName = "DN_" + FIELD_LAST + nPos + "_" + (nLoop++);
    				if (g_DocWord.Bookmarks.Exists(strFieldName) == false)
    					break;
    				strFieldList += strFieldName + "\u0002";
    			}
    			
    		} //for end
		}//else if end
	}

	if (g_DocWord.ProtectionType != -1)
		g_DocWord.UnProtect();

	var arrFieldList = strFieldList.split("\u0002");
	for (var nLoop = 0; nLoop < arrFieldList.length; nLoop++)
	{
		strFieldName = arrFieldList[nLoop];
		if (strFieldName.length > 0)
		{
			if (strFieldName.indexOf(FIELD_DRAFTER) != -1 || strFieldName.indexOf(FIELD_COOPERATE) != -1 ||
				strFieldName.indexOf(FIELD_DEPART_INSPECT) != -1)
			{
				clearFieldData("DN_" + strFieldName, 0);
			}

			clearFieldData(strFieldName, 0);

		}
	}

	return true;
}

function clearFieldData(strFieldName, nType)
{
	// 2008.04.21. KB : JKKIM added by 결재화면 외에 워드파일에 대한 화면 락 현상 해결. start
	if (g_DocWord != null)
		g_DocWord.Activate();
	// end
	if (g_DocWord.Bookmarks.Exists(strFieldName))
	{
		if (nType == 0)	// Text
		{
			var myRange = g_DocWord.Bookmarks(strFieldName).Range.Cells(1).Range;
			myRange.Delete(1,1);
		}
		else			// Image
		{
			var nImage = g_DocWord.Bookmarks(strFieldName).Range.InlineShapes.Count;
			var strRecordText = readTempRecordText(strFieldName);

			if ((strRecordText == "") || (strRecordText.length <= 1) || (nImage != 0))
				g_DocWord.Bookmarks(strFieldName).Range.Delete(1,1);
			else
				g_DocWord.Bookmarks(strFieldName).Range.Delete(1,strRecordText.length);
		}
	}
}




