function isIE() { 
 return ((navigator.appName == 'Microsoft Internet Explorer') || ((navigator.appName == 'Netscape') && (new RegExp("Trident/.*rv:([0-9]{1,}[\.0-9]{0,})").exec(navigator.userAgent) != null))); 
}

// Define FileManager Object
var FileManager = {
	
	// FileWizard Object initialize
	initialize: function(prefix, version, serverip, serverport, serverdir, compid, limitext, attmode) {		
		try {
			if (document.getElementById("FileWizard") == null) {
				var FC_Browser_Version = navigator.userAgent;
				var filewizardscript = "";
				/*if (new RegExp(/MSIE/).test(FC_Browser_Version)) {*/
				if(isIE()){
					filewizardscript = "<object id='FileWizard' style='width: 0px; height: 0px' name='FileWizard'"
						+ " serverip='" + serverip + "' serverport='" + serverport + "' serverdir='" + serverdir + "\\" + compid 
						+ "' prefixname='" + prefix + "' limitext='" + limitext + "' attmode='" + attmode + "' "
						+ "	classid='clsid:B84E12B0-B248-4371-A95A-EC943670DCFC'"
						+ "	codeBase='" + prefix + "/app/ref/cabfile/ACUBEFileCtrl.cab#version=" + version + "' "
						+ " viewastext>"
						+ "	<span style='color:red'>ActiveX control failed to load! -- Please check browser security settings.</span>"
						+ "	</object>";
				} else if (new RegExp(/Firefox/).test(FC_Browser_Version)) {
					filewizardscript = "<embed id='FileWizard' type='Application/AcubeFileCtrl' pluginspage='" + prefix + "/app/ref/cabfile/npAcubeFileCtrl.xpi' width=0 height=0>";
				} else if (new RegExp(/Chrome/).test(FC_Browser_Version)) {
					filewizardscript = "<embed id='FileWizard' type='Application/AcubeFileCtrl' pluginspage='" + prefix + "/app/ref/cabfile/npAcubeFileCtrl.msi' width=0 height=0>";
				} else if (new RegExp(/Safari/).test(FC_Browser_Version)) {
					filewizardscript = "<embed id='FileWizard' type='Application/AcubeFileCtrl' pluginspage='" + prefix + "/app/ref/cabfile/npAcubeFileCtrl.msi' width=0 height=0>";
				} else if (new RegExp(/Opera/).test(FC_Browser_Version)) {
					filewizardscript = "<embed id='FileWizard' type='Application/AcubeFileCtrl' pluginspage='" + prefix + "/app/ref/cabfile/npAcubeFileCtrl.msi' width=0 height=0>";
				}
		
		   		document.body.insertAdjacentHTML("beforeEnd", filewizardscript);

				// check FileWizard version
				try {
					var filewizard = document.getElementById("FileWizard");
					if (filewizard != null) {
						var invalid = false;
						var version = filewizard.GetCtrlVersion();
						var fwvs = version.split(".");

						if (parseInt(fwvs[0]) < 2) {
							invalid = true;
						} else if (parseInt(fwvs[0]) == 2) {
							if (parseInt(fwvs[1]) < 1) {
								invalid = true;
							} else if (parseInt(fwvs[1]) == 1) {
								if (parseInt(fwvs[2]) < 9) {
									invalid = true;
								} else if (parseInt(fwvs[2]) == 9) {
									if (parseInt(fwvs[3]) < 30)
										invalid = true;
								}
							}
						}
						if (invalid) {
							alert(FileConst.Error.UpgradeWizard);
						}

						FileManager.setlocaltempdir();
					} else {
						errormessage();
					}				
				} catch (error) {
					alert(FileConst.Error.NeedWizard);
				}
	   		}
		} catch (error) {
			FileManager.errormessage("initialize");
		}
	},
	
	
	// download File (from STOR to WAS)
	download: function(file, keepflag) {		
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				// filename - different name : 0 (Default), same name : 1
				if (arguments.length == 2 && (keepflag == true || keepflag == false))
					filewizard.setKeepLocalTmpFileName(keepflag ? 1 : 0);
				else
					filewizard.setKeepLocalTmpFileName(0);
					
				var url 		= $("#FileWizard").attr("prefixname") + "/app/appcom/attach/downloadAttach.do";
				var fileid 		= "";
				var filename 	= "";
				var displayname = "";
				var gubun 		= "";
				var docid 		= "";
				var type 		= "";
				
				// 파일다운로드시 로그를 기록하기 위해서 추가함.
				var iswritelog	= "";	// 로그를 기록할지 여부를 나타낼 필드 값
				var modulekey	= "";	// 로그를 기록시 인쇄/저장화면의 모듈 필드에 나타낼 필드 값
				var modulename	= "";	// 로그를 기록시 인쇄/저장화면의 제목 필드에 나타날 필드 값
								
				if (file instanceof Array) {
				
					for (var loop = 0; loop < file.length; loop++) {
						
						fileid 		+= file[loop].fileid 		+ String.fromCharCode(2);
						filename 	+= file[loop].filename 		+ String.fromCharCode(2);
						displayname += file[loop].displayname 	+ String.fromCharCode(2);
						gubun 		+= file[loop].gubun 		+ String.fromCharCode(2);
						docid 		+= file[loop].docid 		+ String.fromCharCode(2);
						type 		 = file[loop].type;
						
						// 로그구분자("Y" 이면 로그를 기록하고 기타는 로그를 기록하지 않는다) 
						if (file[loop].iswritelog != undefined && iswritelog == '')	iswritelog	= file[loop].iswritelog;
						if (file[loop].modulekey  != undefined && modulekey  == '')	modulekey	= file[loop].modulekey;
						if (file[loop].modulename != undefined && modulename == '')	modulename	= file[loop].modulename;
					}
					if( type == null || type == "" ){
						type = "save";
					}
				} else {

					fileid 		= file.fileid;
					filename 	= file.filename;
					displayname = file.displayname;
					gubun 		= file.gubun;
					docid 		= file.docid;
					type 		= file.type;

					// 로그구분자("Y" 이면 로그를 기록하고 기타는 로그를 기록하지 않는다)
					if (file.iswritelog != undefined) iswritelog	= file.iswritelog;
					if (file.modulekey 	!= undefined) modulekey		= file.modulekey;
					if (file.modulename != undefined) modulename	= file.modulename;					
										
				}
				
				var params = "fileid=" 	  + fileid 
						+ "&filename=" 	  + filename
						+ "&displayname=" + encodeURIComponent(displayname) 
						+ "&type=" 		  + type
						+ "&gubun=" 	  + gubun 
						+ "&docid=" 	  + docid
						+ "&iswritelog="  + iswritelog
						+ "&modulekey="   + modulekey
						+ "&modulename="  + modulename;

				$.post(url, params, function(data) {
						if (FileManager.complete) {
							FileManager.complete(data);
						}
					}, 'json');

/*
			var ajax = new Ajax.Request(
					url, 
					{
						method : 'post', 
						parameters : params,
						onComplete : FileManager.complete
					});					
*/	
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("download");
		}
	},
	
	// complete download File (from STOR to WAS)
	complete: function(filelist) {
		var file = new Array();
		if (filelist instanceof Array) {
			file = filelist;
		} else {
			file[0] = filelist;
		}
		
		var filewizard = document.getElementById("FileWizard");
		if (filewizard != null) {
			try {
				if (file[0].type == "upload" || file[0].type == "update") {
					if (typeof(file[0].fileid) == "undefined" || file[0].fileid == "") {
						alert(FileConst.Error.TransException);
						return false;
					}
				}
			} catch (error) {
				FileManager.errormessage("complete");
				return false;
			}
			
			if (file[0].type == "save" || file[0].type == "relay") {
				FileManager.savefile(file[0]);
			} else if (file[0].type == "open") {
				FileManager.openfile(file[0]);
			} else if (file[0].type == "savebody") {
				FileManager.savebodyfile(file[0], true);
			} else if (file[0].type == "upload") {
			} else if (file[0].type == "update") {
			}
		} else {
			errormessage();
		}
	},
	
	
	// download File (from WAS to Client)
	savefile: function(file, keepflag) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var fileinfo = "";
				if (file.filename.indexOf(String.fromCharCode(2)) != -1) {
					var filename = (file.filename).split(String.fromCharCode(2));
					var displayname = (file.displayname).split(String.fromCharCode(2));
					fileinfo = (filename.length) + String.fromCharCode(31);
					for (var loop = 0; loop < filename.length; loop++) {
						fileinfo += displayname[loop] + String.fromCharCode(29)
						+ $("#FileWizard").attr("serverdir") + "/" + filename[loop] + String.fromCharCode(31);
					}
				} else {
					fileinfo = "1" + String.fromCharCode(31) + file.displayname + String.fromCharCode(29)
						+ $("#FileWizard").attr("serverdir") + "/" + file.filename + String.fromCharCode(31);
				}
				filewizard.ClearFileInfos();		/* Clear All File Infomations in FileWiz Object */
				filewizard.SetLanguage('K');		/* K : Korean, Else : English */
				filewizard.SetServerAutoDelete(0);	/* 0 : Not Delete, 1 : Delete, Default : 1 */
				filewizard.SetFileUIMode((file.type == "relay") ? 0 : 1);		/* show Dialog, 0 : Not Show, 1 : Show, Default : 1 */				
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));		/* WAS Server IP, WAS Server Port */
				filewizard.SetLocalTempDirKeyName("APPTEMP");	/* Client Temp Directory Name */
				
				if ($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}
				
				if (arguments.length == 2 && (keepflag == true || keepflag == false))
					filewizard.setKeepLocalTmpFileName(keepflag ? 1 : 0);
				else
					filewizard.setKeepLocalTmpFileName(0);
				var result = filewizard.ViewFileEx("W", fileinfo);	/* W : Save File, F : Open File */
				if (result == -1) {
					file.localpath = "";
				}
				if (file.type == "relay") {
					if (window.saveunified != null && window.saveunified != "undefined") {
						window.saveunified(file);
					} else if (document.saveunified != null && document.saveunified != "undefined") {
						document.saveunified(file);
					} else {
						return file;
					}
				} else if (FileConst.Variable.Distribute == "Y") {
					FileConst.Variable.Distribute = "N";
					if (window.saveunified != null && window.saveunified != "undefined") {
						window.saveunified(result);
					} else if (document.saveunified != null && document.saveunified != "undefined") {
						document.saveunified(result);
					} else {
						return result;
					}
				}
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("savefile");
		}
	},


	// open File with Application
	openfile: function(file, keepflag) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var fileinfo = "1" + String.fromCharCode(31) + file.displayname + String.fromCharCode(29)
					+ $("#FileWizard").attr("serverdir") + "/" + file.filename + String.fromCharCode(31);
				filewizard.ClearFileInfos();		/* Clear All File Infomations in FileWiz Object */
				filewizard.SetLanguage('K');		/* K : Korean, Else : English */
				filewizard.SetServerAutoDelete(0);	/* 0 : Not Delete, 1 : Delete, Default : 1 */
				filewizard.SetFileUIMode(1);		/* show Dialog, 0 : Not Show, 1 : Show, Default : 1 */
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));		/* WAS Server IP, WAS Server Port */
				filewizard.SetLocalTempDirKeyName("APPTEMP");	/* Client Temp Directory Name */
				
				if($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}
				
				if (arguments.length == 2 && (keepflag == true || keepflag == false))
					filewizard.setKeepLocalTmpFileName(keepflag ? 1 : 0);
				else
					filewizard.setKeepLocalTmpFileName(0);
				var result = filewizard.ViewFileEx("F", fileinfo);	/* W : Save File, F : Open File */
				if (result != -1)
					file.localpath = result + "\\" + file.filename;
				else
					file.localpath = "";
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("openfile");
		}
	},
	

	// open File with Application
	openfileLocal: function(file) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				filewizard.ShellExecuteWeb(file.localpath);
			} else {
				this.errormessage();
			}
		} catch (error) {
			FileManager.errormessage("openfileLocal");
		}
	},


	// download File without Notification(from WAS to Client)
	savebodyfile: function(file, noticeflag, keepflag) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var filelist = new Array();
				var fileinfo = "";
				filewizard.SetLocalTempDirKeyName("APPTEMP");	/* Client Temp Directory Name */
				if (file.filename.indexOf(String.fromCharCode(2)) != -1) {
					var fileid = (file.fileid).split(String.fromCharCode(2));
					var filename = (file.filename).split(String.fromCharCode(2));
					var displayname = (file.displayname).split(String.fromCharCode(2));
					var docid = (file.docid).split(String.fromCharCode(2));

					fileinfo = (filename.length) + unescape('%1F');
					for (var loop = 0; loop < filename.length; loop++) {
						var f = new Object();
						f.fileid = fileid[loop];
						f.filename = filename[loop];
						f.displayname = displayname[loop];
						f.localpath = filewizard.GetLocalTempDir() + filename[loop];
						f.docid = docid[loop];
						filelist[loop] = f;
						// delete local file
						// FileManager.deleteLocalFile(f.localpath);

						fileinfo += String.fromCharCode(29) + f.localpath + String.fromCharCode(29) 
							+ String.fromCharCode(29) + $("#FileWizard").attr("serverdir") + "/" + f.filename + String.fromCharCode(29) + "1" + String.fromCharCode(29) + "0" 
							+ String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29)
							+ String.fromCharCode(29) + "N" + String.fromCharCode(29) + "0" + String.fromCharCode(29) + "NN"
							+ String.fromCharCode(29) + "FF" + String.fromCharCode(29) + "Y" + String.fromCharCode(29) + "0"
							+ String.fromCharCode(29) + String.fromCharCode(29) + "0" + String.fromCharCode(29) 
							+ String.fromCharCode(29) + "YI" + String.fromCharCode(31);
					}
				} else {
					if (file.localpathdest)
						file.localpath = file.localpathdest;
					else
						file.localpath = filewizard.GetLocalTempDir() + file.filename;

					// delete local file
					// FileManager.deleteLocalFile(file.localpath);
					fileinfo = "1" + unescape('%1F') + String.fromCharCode(29) + file.localpath + String.fromCharCode(29) 
						+ String.fromCharCode(29) + $("#FileWizard").attr("serverdir") + "/" + file.filename + String.fromCharCode(29) + "1" + String.fromCharCode(29) + "0" 
						+ String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29)
						+ String.fromCharCode(29) + "N" + String.fromCharCode(29) + "0" + String.fromCharCode(29) + "NN"
						+ String.fromCharCode(29) + "FF" + String.fromCharCode(29) + "Y" + String.fromCharCode(29) + "0"
						+ String.fromCharCode(29) + String.fromCharCode(29) + "0" + String.fromCharCode(29) 
						+ String.fromCharCode(29) + "YI" + String.fromCharCode(31);
				}
				filewizard.ClearFileInfos();		/* Clear All File Infomations in FileWiz Object */
				filewizard.SetLanguage('K');		/* K : Korean, Else : English */
				filewizard.SetShowCancel(0);		/* show Cancel Button, 0 : Not Show, 1 : Show */
				filewizard.SetUploadAfterMod("N");
				filewizard.SetServerAutoDelete(0);	/* 0 : Not Delete, 1 : Delete, Default : 1 */
				filewizard.SetFileUIMode(0);		/* show Dialog, 0 : Not Show, 1 : Show, Default : 1 */
				filewizard.SetMaxFileSize(0); 		/* File Maximum Size */
				filewizard.SetEncOption(0); 		/* File Encryption, 0 : Not Encrypt, 1 : Encrypt */
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));		/* WAS Server IP, WAS Server Port */
				filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
				
				if($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}
				
				if (arguments.length == 3 && (keepflag == true || keepflag == false))
					filewizard.setKeepLocalTmpFileName(keepflag ? 1 : 0);
				else
					filewizard.setKeepLocalTmpFileName(0);

				var result = filewizard.DownloadContents("2", fileinfo);	/* '' : save GUID File Name,  1 : save as Only Input File Name, 2 : save as Full Input File Name */
				if (result == null || result == "" || result == "undefined")
					file.localpath = "";
	
				if (typeof(noticeflag) != "undefined" && noticeflag 
					&& window.savenotice != null && window.savenotice != "undefined") {
				    // modified by 백영호. 20080417
				    // - file 정보를 알 수 있는 parameter를 추가
				    if (filelist.length > 0)
						window.savenotice(filelist);
					else
						window.savenotice(file);
				} else if (typeof(noticeflag) != "undefined" && noticeflag 
					&& document.savenotice != null && document.savenotice != "undefined") {
			                // - file 정보를 알 수 있는 parameter를 추가
				    if (filelist.length > 0)
						window.savenotice(filelist);
					else
						window.savenotice(file);
				} else {
				    if (filelist.length > 0)
						return filelist[0].localpath;
					else
						return file.localpath;
				}
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("savebodyfile");
		}
	},


	savefilelist: function(file) {
		
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var filelist = new Array();
				var fileinfo = "";
				filewizard.SetLocalTempDirKeyName("APPTEMP");	/* Client Temp Directory Name */
				if (file instanceof Array) {
					fileinfo = (file.length) + unescape('%1F');
					for (var loop = 0; loop < file.length; loop++) {
						var f = new Object();
						f.filename = file[loop].filename;
						f.displayname = file[loop].displayname;
						f.localpath = filewizard.GetLocalTempDir() + file[loop].filename;
						f.fileid = "";
						f.docid = "";
						filelist[loop] = f;
						alert(f);
						alert($("#FileWizard").attr("serverdir"));
						fileinfo += String.fromCharCode(29) + f.localpath + String.fromCharCode(29) 
							+ String.fromCharCode(29) + $("#FileWizard").attr("serverdir") + "/" + f.filename + String.fromCharCode(29) + "1" + String.fromCharCode(29) + "0" 
							+ String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29)
							+ String.fromCharCode(29) + "N" + String.fromCharCode(29) + "0" + String.fromCharCode(29) + "NN"
							+ String.fromCharCode(29) + "FF" + String.fromCharCode(29) + "Y" + String.fromCharCode(29) + "0"
							+ String.fromCharCode(29) + String.fromCharCode(29) + "0" + String.fromCharCode(29) 
							+ String.fromCharCode(29) + "YI" + String.fromCharCode(31);
					}
					filewizard.ClearFileInfos();		/* Clear All File Infomations in FileWiz Object */
					filewizard.SetLanguage('K');		/* K : Korean, Else : English */
					filewizard.SetShowCancel(0);		/* show Cancel Button, 0 : Not Show, 1 : Show */
					filewizard.SetUploadAfterMod("N");
					filewizard.SetServerAutoDelete(0);	/* 0 : Not Delete, 1 : Delete, Default : 1 */
					filewizard.SetFileUIMode(0);		/* show Dialog, 0 : Not Show, 1 : Show, Default : 1 */
					filewizard.SetMaxFileSize(0); 		/* File Maximum Size */
					filewizard.SetEncOption(0); 		/* File Encryption, 0 : Not Encrypt, 1 : Encrypt */
					filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));		/* WAS Server IP, WAS Server Port */
					filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
					
					if($("#FileWizard").attr("attmode") == "1") {
						filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
						filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
					}
					
					if (arguments.length == 3 && (keepflag == true || keepflag == false))
						filewizard.setKeepLocalTmpFileName(keepflag ? 1 : 0);
					else
						filewizard.setKeepLocalTmpFileName(0);
					var result = filewizard.DownloadContents("2", fileinfo);	/* '' : save GUID File Name,  1 : save as Only Input File Name, 2 : save as Full Input File Name */					
					return result;
				}
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("savefilelist");
		}
	},

	
	// download File(from WAS to Client)
	saveAsDesignated: function(file, designated, keepflag) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var filelist = new Array();
				var fileinfo = "";
				if (typeof(designated) == "undefined" || designated == null)
					designated = filewizard.SetLocalTempDirKeyName("APPTEMP");	/* Client Temp Directory Name */

				if (file instanceof Array) {
					fileinfo = (file.length) + unescape('%1F');
					for (var loop = 0; loop < file.length; loop++) {
						file[loop].localpath = designated + file[loop].filename;
						fileinfo += String.fromCharCode(29) + file[loop].localpath + String.fromCharCode(29) 
							+ String.fromCharCode(29) + $("#FileWizard").attr("serverdir") + "/" + file[loop].filename + String.fromCharCode(29) + "1" + String.fromCharCode(29) + "0" 
							+ String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29)
							+ String.fromCharCode(29) + "N" + String.fromCharCode(29) + "0" + String.fromCharCode(29) + "NN"
							+ String.fromCharCode(29) + "FF" + String.fromCharCode(29) + "Y" + String.fromCharCode(29) + "0"
							+ String.fromCharCode(29) + String.fromCharCode(29) + "0" + String.fromCharCode(29) 
							+ String.fromCharCode(29) + "YI" + String.fromCharCode(31);
					}
				} else {
					file.localpath = designated + file.filename;
					fileinfo = "1" + unescape('%1F') + String.fromCharCode(29) + file.localpath + String.fromCharCode(29) 
						+ String.fromCharCode(29) + $("#FileWizard").attr("serverdir") + "/" +  file.filename + String.fromCharCode(29) + "1" + String.fromCharCode(29) + "0" 
						+ String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29) + String.fromCharCode(29)
						+ String.fromCharCode(29) + "N" + String.fromCharCode(29) + "0" + String.fromCharCode(29) + "NN"
						+ String.fromCharCode(29) + "FF" + String.fromCharCode(29) + "Y" + String.fromCharCode(29) + "0"
						+ String.fromCharCode(29) + String.fromCharCode(29) + "0" + String.fromCharCode(29) 
						+ String.fromCharCode(29) + "YI" + String.fromCharCode(31);
				}
				
				filewizard.ClearFileInfos();		/* Clear All File Infomations in FileWiz Object */
				filewizard.SetLanguage('K');		/* K : Korean, Else : English */
				filewizard.SetShowCancel(0);		/* show Cancel Button, 0 : Not Show, 1 : Show */
				filewizard.SetUploadAfterMod("N");
				filewizard.SetServerAutoDelete(0);	/* 0 : Not Delete, 1 : Delete, Default : 1 */
				filewizard.SetFileUIMode(1);		/* show Dialog, 0 : Not Show, 1 : Show, Default : 1 */
				filewizard.SetMaxFileSize(0); 		/* File Maximum Size */
				filewizard.SetEncOption(0); 		/* File Encryption, 0 : Not Encrypt, 1 : Encrypt */
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));		/* WAS Server IP, WAS Server Port */
				filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
				
				if($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}
				
				if (arguments.length == 3 && (keepflag == true || keepflag == false))
					filewizard.setKeepLocalTmpFileName(keepflag ? 1 : 0);
				else
					filewizard.setKeepLocalTmpFileName(0);
	
				var result = filewizard.DownloadContents("2", fileinfo);	/* '' : save GUID File Name,  1 : save as Only Input File Name, 2 : save as Full Input File Name */
				if (result == null || result == "" || result == "undefined")
					file.localpath = "";
					
				return file;
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("saveAsDesignated");
		}
	},


	// get download path to save
	getdownloadpath: function() {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {		
				return filewizard.SelectLocalDownloadFolder();
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("getdownloadpath");
		}
	},


	// select download path to save
	selectdownloadpath: function(filename) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {		
				return filewizard.SelectLocalDownloadFolderEx(filename);
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("selectdownloadpath");
		}
	},


	// upload Body
	// return Array of FileObject{filename, displayname, localpath, serverpath, size, date, type}
	// argument : type - body, attach or file object : file(filename, displayname, localpath)
	// argument : stor - true/false;
	uploadbody: function(type, stor) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {		
				var filelist = new Array();
				if (typeof(type) == "object") {
					filewizard.ClearFileInfos();
					filewizard.SetLanguage('K');
					filewizard.SetShowCancel(0);
					filewizard.SetMaxFileSize(0);
					filewizard.SetEncOption(0);
					filewizard.SetUploadMode(1);
					filewizard.SetFileUIMode(0);
					filewizard.SetUploadAfterMod("N");
					filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));
					filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
	
					if (type instanceof Array) {
						for (var loop = 0; loop < type.length; loop++) {
							/* 
							fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
							ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
							*/						
							var fileinfo = "1" + String.fromCharCode(31) + type[loop].localpath + String.fromCharCode(31);
							fileinfo = filewizard.UploadContents("", fileinfo);
							var delimiter = FileManager.getdelimiter(fileinfo);
							if (delimiter == 0 || delimiter == -1)
								throw delimiter;
							var info = fileinfo.split(String.fromCharCode(delimiter));
							type[loop].filename = info[1].substring(info[1].lastIndexOf("/") + 1);
							type[loop].displayname = type[loop].localpath.substring(type[loop].localpath.lastIndexOf("\\") + 1);	
							type[loop].serverpath = info[1];
							type[loop].size = filewizard.GetLocalFileSize(type[loop].localpath);
							type[loop].date = filewizard.GetLocalFileDate(type[loop].localpath);
							filelist[loop] = type[loop];
						}
					} else {
						/* 
						fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
						ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
						*/						
						var fileinfo = "1" + String.fromCharCode(31) + type.localpath + String.fromCharCode(31);
						fileinfo = filewizard.UploadContents("", fileinfo);
						var delimiter = FileManager.getdelimiter(fileinfo);
						if (delimiter == 0 || delimiter == -1)
							throw delimiter;
						var info = fileinfo.split(String.fromCharCode(delimiter));
						type.filename = info[1].substring(info[1].lastIndexOf("/") + 1);
						type.displayname = type.localpath.substring(type.localpath.lastIndexOf("\\") + 1);
						type.serverpath = info[1];
						type.size = filewizard.GetLocalFileSize(type.localpath);
						type.date = filewizard.GetLocalFileDate(type.localpath);
						filelist[0] = type;
					}

					if (arguments.length == 2 && stor == true) {
					
						var url = $("#FileWizard").attr("prefixname") + "/app/appcom/attach/uploadBody.do";
						var params = "file=";
						var filelistlength = filelist.length;
						for (var loop = 0; loop < filelistlength; loop++) {
							var storfile = filelist[loop];
							params += "" + String.fromCharCode(2) + storfile.filename + String.fromCharCode(2) + storfile.displayname + String.fromCharCode(2) +
							"" + String.fromCharCode(2) + "" + String.fromCharCode(2) + "upload" + String.fromCharCode(4);
						}
						
						$.post(url, params, function(data) {

							if (data.result == "success") {
								var resultfile = data.filelist;
								var datalength = resultfile.length;
								for (var loop = 0; loop < datalength; loop++) {
									for (var pos = 0; pos < filelistlength; pos++) {
										if (filelist[pos].filename == resultfile[loop].filename) {
											filelist[pos].fileid = resultfile[loop].fileid;
											break;
										}
									}
								}
								if (FileManager.afterupload) {
									FileManager.afterupload(filelist);
								}
							} else if (data.result == "fail" && data.message == "toosmall" && retrycount < 5) {
								retrycount++;
								FileManager.uploadbody(type, stor);
							}
						}, 'json');		
					}	
				} else {
					if (type == null || type == "")
						type = "attach";
						
					if (type == "body") {
						filewizard.SetExtension("hwp");
					} else if (type == "image") {
						filewizard.SetExtension("jpg" + String.fromCharCode(29) + "gif");
					} else if (type == "attach") {
						filewizard.SetExtension("*" + String.fromCharCode(29) + "hwp" + String.fromCharCode(29) + "ppt" + String.fromCharCode(29) + "xls");
					} else if (type == "relay") {
						filewizard.SetExtension("xml");		// for relay
					}
					filewizard.ClearFileInfos();
					filewizard.SetLanguage('K');
					filewizard.SetMaxFileSize(0); 		/* File Maximum Size */
					filewizard.SetEncOption(0); 		/* File Encryption, 0 : Not Encrypt, 1 : Encrypt */
					filewizard.SetUploadMode(1);
					filewizard.SetFileUIMode(1);
					filewizard.SetUploadAfterMod("N");
					filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));		/* WAS Server IP, WAS Server Port */
					filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
					filewizard.SetMultiSelectMode((type == "body") ? 0 : 1);
					filewizard.SetMode("NEW");
					
					if($("#FileWizard").attr("attmode") == "1") {
						filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
						filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
					}

					/* 
					fileinfos = { fileinfo + (Char31) + fileinfo + (Char31) + ... + (Char31) + fileinfo }
					fileinfo =	{ displayname + (Char29) + localpath + (Char29) + serverpath + (Char29) + size + (Char29) + date }	
					ex) icon_todo_4.gif(Char29)C:\Pictures\icon_todo_4.gif(Char29/usropt/acube/app/temp///685E59C29D09436bB8E880B4E2787F99.gif(Char29)695(Char29)20081104103006
					*/
					var fileinfos = filewizard.AttachFileJs();
					if (fileinfos != "") {
						var prefix = $("#FileWizard").attr("prefixname");
						var fileinfo = fileinfos.split(String.fromCharCode(31));
						for (var loop = 0; loop < fileinfo.length; loop++) {
							var info = fileinfo[loop].split(String.fromCharCode(29));
							var file = new Object();
							file.displayname = info[1];
							file.localpath = info[2];
							file.serverpath = info[3].replace("//", "/");
							file.filename = file.serverpath.substring(file.serverpath.lastIndexOf("/") + 1);
							file.size = info[8];
							file.date = info[9];
							file.type = type;
							file.src = prefix + "/app/ref/image/attach/attach_" + this.getAttachIcon(file.filename) + ".gif";
							filelist[loop] = file;
						}
					}
				}

				return filelist;
			} else {
				errormessage();
				return null;
			}
		} catch (error) {
			if (error == 0 || error == -1)
				FileManager.errormessage("uploadbody", error);
			else
				FileManager.errormessage("uploadbody");
			
			return null;
		}
	},


	// upload File
	// return Array of FileObject{filename, displayname, localpath, serverpath, size, date, type}
	// argument : type - body, attach or file object : file(filename, displayname, localpath)
	// argument : stor - true/false;
	uploadfile: function(type, stor) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {		
				var filelist = new Array();
				if (typeof(type) == "object") {
					// prohibit executable file
					if (FileManager.searchSpecialfile(type)) {
						alert(FileConst.Error.ProhibitedFile);
						return null;
					}
					
					filewizard.ClearFileInfos();
					filewizard.SetLanguage('K');
					filewizard.SetShowCancel(0);
					filewizard.SetMaxFileSize(0);
					filewizard.SetEncOption(0);
					filewizard.SetUploadMode(1);
					filewizard.SetFileUIMode(0);
					filewizard.SetUploadAfterMod("N");
					filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));
					filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
	
					if (type instanceof Array) {
						for (var loop = 0; loop < type.length; loop++) {
							/* 
							fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
							ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
							*/						
							var fileinfo = "1" + String.fromCharCode(31) + type[loop].localpath + String.fromCharCode(31);
							fileinfo = filewizard.UploadContents("", fileinfo);
							var delimiter = FileManager.getdelimiter(fileinfo);
							if (delimiter == 0 || delimiter == -1)
								throw delimiter;
							var info = fileinfo.split(String.fromCharCode(delimiter));
							type[loop].filename = info[1].substring(info[1].lastIndexOf("/") + 1);
							type[loop].displayname = type[loop].localpath.substring(type[loop].localpath.lastIndexOf("\\") + 1);	
							type[loop].serverpath = info[1];
							type[loop].size = filewizard.GetLocalFileSize(type[loop].localpath);
							type[loop].date = filewizard.GetLocalFileDate(type[loop].localpath);
							filelist[loop] = type[loop];
						}
					} else {
						/* 
						fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
						ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
						*/	
						var fileinfo = "1" + String.fromCharCode(31) + type.localpath + String.fromCharCode(31);
						fileinfo = filewizard.UploadContents("", fileinfo);
						var delimiter = FileManager.getdelimiter(fileinfo);
						if (delimiter == 0 || delimiter == -1)
							throw delimiter;
						var info = fileinfo.split(String.fromCharCode(delimiter));
						type.filename = info[1].substring(info[1].lastIndexOf("/") + 1);
						type.displayname = type.localpath.substring(type.localpath.lastIndexOf("\\") + 1);
						type.serverpath = info[1];
						type.size = filewizard.GetLocalFileSize(type.localpath);
						type.date = filewizard.GetLocalFileDate(type.localpath);
						filelist[0] = type;
					}

					if (arguments.length == 2 && stor == true) {
						var url = $("#FileWizard").attr("prefixname") + "/app/appcom/attach/uploadAttach.do";
						var params = "file=";
						var filelistlength = filelist.length;
						for (var loop = 0; loop < filelistlength; loop++) {
							var storfile = filelist[loop];
							params += "" + String.fromCharCode(2) + storfile.filename + String.fromCharCode(2) + storfile.displayname + String.fromCharCode(2) +
							"" + String.fromCharCode(2) + "" + String.fromCharCode(2) + "upload" + String.fromCharCode(4);
						}

						$.post(url, params, function(data) {
							
							if (typeof(data) == "object" && data.result == "success") {
								var resultfile = data.filelist;
								var datalength = resultfile.length;
								for (var loop = 0; loop < datalength; loop++) {
									for (var pos = 0; pos < filelistlength; pos++) {
										
										if (filelist[pos].filename == resultfile[loop].filename) {
											filelist[pos].fileid = resultfile[loop].fileid;
											break;
										}
									}
								}
								if (FileManager.afterupload) {
									FileManager.afterupload(filelist);
								}
							}
						}, 'json');
					}
				} else {
					if (type == null || type == "") {
						type = "attach";
					}
						
					if (type == "body") {
						filewizard.SetExtension("hwp");					
					} else if (type == "hwp") {
						filewizard.SetExtension("hwp");
					} else if (type == "doc") {
						filewizard.SetExtension("doc");
					} else if (type == "html") {
						filewizard.SetExtension("html");
					} else if (type == "image") {
						filewizard.SetExtension("jpg" + String.fromCharCode(29) + "gif");
					} else if (type == "attach") {
						filewizard.SetExtension("*" + String.fromCharCode(29) + "hwp" + String.fromCharCode(29) + "ppt" + String.fromCharCode(29) + "xls");
					} else if (type == "relay") {
						filewizard.SetExtension("xml");		// for relay
					} else if (type == "pub") {
						filewizard.SetExtension("xml");		// for relay
					} else if (type == "all") {
						filewizard.SetExtension("hwp" + String.fromCharCode(29) + "doc" + String.fromCharCode(29) + "html");
					} else if (type.indexOf("show:") == 0) {
						filewizard.SetExtension(type.substring(5));
					}
					
					filewizard.ClearFileInfos();
					filewizard.SetLanguage('K');
					filewizard.SetMaxFileSize(0); 		/* File Maximum Size */
					filewizard.SetEncOption(0); 		/* File Encryption, 0 : Not Encrypt, 1 : Encrypt */
					filewizard.SetUploadMode(1);
					filewizard.SetFileUIMode(1);
					filewizard.SetUploadAfterMod("N");
					filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));		/* WAS Server IP, WAS Server Port */
					filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
					filewizard.SetMultiSelectMode((type == "body") ? 0 : 1);
					filewizard.SetMode("NEW");

					if ($("#FileWizard").attr("attmode") == "1") {
						filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
						filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
					}

					/* 
					fileinfos = { fileinfo + (Char31) + fileinfo + (Char31) + ... + (Char31) + fileinfo }
					fileinfo =	{ displayname + (Char29) + localpath + (Char29) + serverpath + (Char29) + size + (Char29) + date }	
					ex) icon_todo_4.gif(Char29)C:\Pictures\icon_todo_4.gif(Char29/usropt/acube/app/temp///685E59C29D09436bB8E880B4E2787F99.gif(Char29)695(Char29)20081104103006
					*/
					var fileinfos = filewizard.AttachFileJs();
					if (fileinfos != "") {
						var prefix = $("#FileWizard").attr("prefixname");
						var fileinfo = fileinfos.split(String.fromCharCode(31));
						for (var loop = 0; loop < fileinfo.length; loop++) {
							var info = fileinfo[loop].split(String.fromCharCode(29));
							var file = new Object();
							file.displayname = info[1];
							file.localpath = info[2];
							file.serverpath = info[3].replace("//", "/");
							file.filename = file.serverpath.substring(file.serverpath.lastIndexOf("/") + 1);
							file.size = info[8];
							file.date = info[9];
							file.type = type;
							file.src = prefix + "/app/ref/image/attach/attach_" + this.getAttachIcon(file.filename) + ".gif";
							
							/*
							alert("file.disaplyname -> " + file.displayname + "\n" +
								  "file.localpath   -> " + file.localpath + "\n" +
								  "file.serverpath  -> " + file.serverpath + "\n" +
								  "file.filename    -> " + file.filename + "\n" +
								  "file.type        -> " + file.type + "\n" +
								  "file.src         -> " + file.src);
							*/
							
 							filelist[loop] = file;
						}
					}

					// prohibit executable file
					if (FileManager.searchSpecialfile(filelist)) {
						alert(FileConst.Error.ProhibitedFile);
						return null;
					}
				}

				return filelist;
			} else {
				errormessage();
				return null;
			}
		} catch (error) {
			if (error == 0 || error == -1)
				FileManager.errormessage("uploadfile", error);
			else
				FileManager.errormessage("uploadfile");
			
			return null;
		}
	},


	updatebody: function(file) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {		
				filewizard.ClearFileInfos();
				filewizard.SetLanguage('K');
				filewizard.SetShowCancel(0);
				filewizard.SetMaxFileSize(0);
				filewizard.SetEncOption(0);
				filewizard.SetUploadMode(1);
				filewizard.SetFileUIMode(0);
				filewizard.SetUploadAfterMod("N");
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));
				filewizard.SetTempDir($("#FileWizard").attr("serverdir"));
				
				if($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}
				
//				var fileid = "";
//				var filename = "";
				var filelist = new Array();
				var params = "file=";

				// upload dato to was
				if (file instanceof Array) {
					for (var loop = 0; loop < file.length; loop++) {
						/* 
						fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
						ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
						*/						
						var fileinfo = "1" + String.fromCharCode(31) + file[loop].localpath + String.fromCharCode(31);
						fileinfo = filewizard.UploadContents("", fileinfo);
						var delimiter = FileManager.getdelimiter(fileinfo);
						if (delimiter == 0 || delimiter == -1)
							throw delimiter;
						var info = fileinfo.split(String.fromCharCode(delimiter));
						file[loop].filename = info[1].substring(info[1].lastIndexOf("/") + 1);
						file[loop].displayname = file[loop].localpath.substring(file[loop].localpath.lastIndexOf("\\") + 1);	
						file[loop].serverpath = info[1];
						filelist[loop] = file[loop];
//						fileid += file[loop].fileid + String.fromCharCode(2);
//						filename += file[loop].serverpath + String.fromCharCode(2);
						
						params += file[loop].fileid + String.fromCharCode(2) + file[loop].filename + String.fromCharCode(2) + file[loop].displayname + String.fromCharCode(2) +
						"" + String.fromCharCode(2) + "" + String.fromCharCode(2) + "update" + String.fromCharCode(2) + file[loop].docid + String.fromCharCode(4);
						
						// delete local file
//						FileManager.deleteLocalFile(file[loop].localpath);
					}
				} else {
					/* 
					fileinfo =	{ count + (Char31) + serverpath + (Char31) }
					ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
					*/						
					var fileinfo = "1" + String.fromCharCode(31) + file.localpath + String.fromCharCode(31);
					fileinfo = filewizard.UploadContents("", fileinfo);
					var delimiter = FileManager.getdelimiter(fileinfo);
					if (delimiter == 0 || delimiter == -1)
						throw delimiter;
					var info = fileinfo.split(String.fromCharCode(delimiter));
					file.filename = info[1].substring(info[1].lastIndexOf("/") + 1);
					file.displayname = file.localpath.substring(file.localpath.lastIndexOf("\\") + 1);
					file.serverpath = info[1];
					filelist[0] = file;
//					fileid += file.fileid + String.fromCharCode(2);
//					filename += file.serverpath + String.fromCharCode(2);

					params += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) +
					"" + String.fromCharCode(2) + "" + String.fromCharCode(2) + "update" + String.fromCharCode(2) + file.docid + String.fromCharCode(4);

					// delete local file
//					FileManager.deleteLocalFile(file.localpath);
				}

				// update data in stor
				var url = $("#FileWizard").attr("prefixname") + "/app/appcom/attach/updateBody.do";
				$.post(url, params, function(data) {
					if (data.result == "success") {
						if (FileManager.afterupdate) {
							FileManager.afterupdate(data);
						}
					} else if (data.result == "fail" && data.message == "toosmall" && retrycount < 5) {
						retrycount++;
						FileManager.updatebody(file);
					}
				}, 'json');
			} else {
				errormessage();
			}
		} catch (error) {
			if (error == 0 || error == -1)
				FileManager.errormessage("updatebody", error);
			else
				FileManager.errormessage("updatebody");
		}
	},


	updatefile: function(file) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {		
				filewizard.ClearFileInfos();
				filewizard.SetLanguage('K');
				filewizard.SetShowCancel(0);
				filewizard.SetMaxFileSize(0);
				filewizard.SetEncOption(0);
				filewizard.SetUploadMode(1);
				filewizard.SetFileUIMode(0);
				filewizard.SetUploadAfterMod("N");
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));
				filewizard.SetTempDir($("#FileWizard").attr("serverdir"));

				if($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}

//				var fileid = "";
//				var filename = "";
				var filelist = new Array();
				var params = "file=";

				// upload dato to was
				if (file instanceof Array) {
					for (var loop = 0; loop < file.length; loop++) {
						/* 
						fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
						ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
						*/
						var fileinfo = "1" + String.fromCharCode(31) + file[loop].localpath + String.fromCharCode(31);
						fileinfo = filewizard.UploadContents("", fileinfo);
						var delimiter = FileManager.getdelimiter(fileinfo);
						if (delimiter == 0 || delimiter == -1)
							throw delimiter;
						var info = fileinfo.split(String.fromCharCode(delimiter));
						file[loop].filename = info[1].substring(info[1].lastIndexOf("/") + 1);
						file[loop].displayname = file[loop].localpath.substring(file[loop].localpath.lastIndexOf("\\") + 1);	
						file[loop].serverpath = info[1];
						filelist[loop] = file[loop];
//						fileid += file[loop].fileid + String.fromCharCode(2);
//						filename += file[loop].serverpath + String.fromCharCode(2);
						
						params += file[loop].fileid + String.fromCharCode(2) + file[loop].filename + String.fromCharCode(2) + file[loop].displayname + String.fromCharCode(2) +
						"" + String.fromCharCode(2) + "" + String.fromCharCode(2) + "update" + String.fromCharCode(4);
						
						// delete local file
//						FileManager.deleteLocalFile(file[loop].localpath);
					}
				} else {
					/* 
					fileinfo =	{ count + (Char31) + serverpath + (Char31) }
					ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
					*/						
					var fileinfo = "1" + String.fromCharCode(31) + file.localpath + String.fromCharCode(31);
					fileinfo = filewizard.UploadContents("", fileinfo);
					var delimiter = FileManager.getdelimiter(fileinfo);
					if (delimiter == 0 || delimiter == -1)
						throw delimiter;
					var info = fileinfo.split(String.fromCharCode(delimiter));
					file.filename = info[1].substring(info[1].lastIndexOf("/") + 1);
					file.displayname = file.localpath.substring(file.localpath.lastIndexOf("\\") + 1);
					file.serverpath = info[1];
					filelist[0] = file;
//					fileid += file.fileid + String.fromCharCode(2);
//					filename += file.serverpath + String.fromCharCode(2);

					params += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) +
					"" + String.fromCharCode(2) + "" + String.fromCharCode(2) + "update" + String.fromCharCode(4);

					// delete local file
//					FileManager.deleteLocalFile(file.localpath);
				}

				// update data in stor
				var url = $("#FileWizard").attr("prefixname") + "/app/appcom/attach/updateAttach.do";
				$.post(url, params, function(data) {
					if (FileManager.afterupdate) {
						FileManager.afterupdate(data);
					}
				}, 'json');
			} else {
				errormessage();
			}
		} catch (error) {
			if (error == 0 || error == -1)
				FileManager.errormessage("updatefile", error);
			else
				FileManager.errormessage("updatefile");
		}
	},


	// complete Update File (from WAS to STOR)
	afterupdate: function(data) {
		var filewizard = document.getElementById("FileWizard");
		if (filewizard != null) {
			if (window.nextprocess != null && window.nextprocess != "undefined") {
				window.nextprocess(data);
			} else if (document.nextprocess != null && document.nextprocess != "undefined") {
				document.nextprocess(data);
			}
		} else {
			errormessage();
		}
	},


	// complete Upload File (from WAS to STOR)
	afterupload: function(data) {
		var filewizard = document.getElementById("FileWizard");
		if (filewizard != null) {
			if (window.nextprocess != null && window.nextprocess != "undefined") {
				window.nextprocess(data);
			} else if (document.nextprocess != null && document.nextprocess != "undefined") {
				document.nextprocess(data);
			}
		} else {
			errormessage();
		}
	},

	// select xml file for relay
	// return type : filecount | localpath 1 | localpath 2 | ... | localpath n
	selectxmlfile: function() {
		var filemanager = null;
		
		try {
			filemanager = new ActiveXObject("FileBroker.FileManager");
		} catch (error) {
			alert(FileConst.Error.NeedBroker);
			return null;
		}

		try {
			return filemanager.selectXmlFiles();
		} catch (error) {
			FileManager.errormessage("selectxmlfile");
		}
	},
	
	
	// upload xml file for relay
	// return Array of FileObject{filename, displayname, localpath, serverpath, size, date, type}
	// argument : filecount | localpath 1 | localpath 2 | ... | localpath n
	uploadxmlfile: function(fileinfo) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var filelist = new Array();
				filewizard.ClearFileInfos();
				filewizard.SetLanguage('K');
				filewizard.SetShowCancel(0);
				filewizard.SetMaxFileSize(0);
				filewizard.SetEncOption(0);
				filewizard.SetUploadMode(1);
				filewizard.SetFileUIMode(1);
				filewizard.SetUploadAfterMod("N");
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));
				filewizard.SetTempDir($("#FileWizard").attr("serverdir"));

				if($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}

				fileinfo = fileinfo.replace(/\|/g, String.fromCharCode(31));
				var localfile = fileinfo.split(String.fromCharCode(31));
				
				/* 
				fileinfos = { count + (Char31) + serverpath + (Char31) + ... + (Char31) + serverpath + (Char31) }
				ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31/usropt/acube/app/temp///1D8D4F5BC0A4C034085628aAC2C94E4a.hwp(Char31)
				*/						
				var fileinfos = filewizard.UploadContents("", fileinfo);
				var delimiter = FileManager.getdelimiter(fileinfo);
				if (delimiter == 0 || delimiter == -1)
					throw delimiter;
				if (fileinfos != "") {
					var wasfile = fileinfos.split(String.fromCharCode(delimiter));
					for (var loop = 1; loop <= wasfile[0]; loop++) {
						var file = new Object();
						file.filename = wasfile[loop].substring(wasfile[loop].lastIndexOf("/") + 1);
						file.displayname = localfile[loop].substring(localfile[loop].lastIndexOf("\\") + 1);
						file.localpath = localfile[loop];
						file.serverpath = wasfile[loop];
						file.size = filewizard.GetLocalFileSize(file.localpath);
						file.date = "";
						file.type = "relay";
						filelist[loop - 1] = file;
					}
				}
				
				return filelist;
			} else {
				errormessage();
			}
		} catch (error) {
			if (error == 0 || error == -1)
				FileManager.errormessage("uploadxmlfile", error);
			else
				FileManager.errormessage("uploadxmlfile");
		}
	},
	
	// get ClientSide Fileinfo
	// return FileObject{filename, displayname, localpath, serverpath, size, date, type}
	// argument : location - localpath with filename
	getfileinfo: function(location) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var fileinfo = filewizard.GetLocalFileInfos(location);
				if (fileinfo != "") {
					var info = fileinfo.split(String.fromCharCode(29));
					var file = new Object();
					file.filename = info[1].substring(info[1].lastIndexOf("\\")+1);
					file.displayname = info[0];
					file.localpath = info[1];
	//				file.serverpath = info[2];
					file.size = info[2];
					file.date = info[3];
	//				file.type = type;
					return file;
				}
				return null;
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("getfileinfo");
		}
	},
	
	
	// check Server File
	// return 0 : exist, -1 : not exist
	// argument[0] : serverpath with filename
	// argument[1] : accessmode - 0
	checkserverfile: function(filepath /*, accessmode */) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				if (filepath.indexOf($("#FileWizard").attr("serverdir")) != -1) {
					return filewizard.CheckExistServerFile(filepath, 0);
				} else {
					return filewizard.CheckExistServerFile($("#FileWizard").attr("serverdir") + "/" + filepath, 0);
				}
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("checkserverfile");
		}
	},
	
	
	// Delete Temp Folder
	// argument : time for delete 
	deletefolder: function(deletetime) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				if (typeof(deletetime) == "undefined" || isNaN(parseInt(deletetime))) {
					filewizard.SetLocalTempDirKeyName("Hnc\\BinData");
					filewizard.DeleteLocalTempDir();
					filewizard.SetLocalTempDirKeyName("APPTEMP");
					filewizard.DeleteLocalTempDir();
				} else {
					filewizard.SetLocalTempDirKeyName("Hnc\\BinData");
					filewizard.DeleteLocalTempDirEx(parseInt(deletetime));
					filewizard.SetLocalTempDirKeyName("APPTEMP");
					filewizard.DeleteLocalTempDirEx(parseInt(deletetime));
				}
			} else {
				errormessage();
			}
		} catch (error) {
//			FileManager.errormessage("deletefolder");
		}
	},
		
	
	// set File Extension
	// argument : filetype - file extension
	setExtension: function(filetype) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				if (filetype == null || filetype == "")
					filetype = "*" + String.fromCharCode(29) + "hwp" + String.fromCharCode(29) + "ppt"
						+ String.fromCharCode(29) + "xls" + String.fromCharCode(29) + "doc"
						+ String.fromCharCode(29) + "htm" + String.fromCharCode(29) + "txt";
				filewizard.SetExtension(filetype);
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("setExtension");
		}
	}, 	


	// get LocalTempDirectory
	// return APPTEMP path
	getlocaltempdir: function() {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				return filewizard.GetLocalTempDir();
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("getlocaltempdir");
		}
	},
	
	
	// set LocalTempDirectory
	// augument : location
	setlocaltempdir: function(location) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				if (typeof(location) == "undefined")
					filewizard.SetLocalTempDirKeyName("APPTEMP");
				else
					filewizard.SetLocalTempDirKeyName(location);
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("setlocaltempdir");
		}
	},


	// get ClientSide FileSize
	// return filesize
	// argument : localpath with filename
	getfilesize: function(location) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				return filewizard.GetLocalFileSize(location);
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("setlocaltempdir");
		}
	}, 	

	
	// get ClientSide FileDate
	getfiledate: function(location) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				return filewizard.GetLocalFileDate(location);
			} else {
				errormessage();
			}
		} catch (error) {
			FileManager.errormessage("getfiledate");
		}
	},	


	// set Local-file Information
	setfileinfo: function(file) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				var filelist = new Array();
				
				filewizard.ClearFileInfos();
				filewizard.SetLanguage('K');
				filewizard.SetShowCancel(0);
				filewizard.SetMaxFileSize(0);
				filewizard.SetEncOption(0);
				filewizard.SetUploadMode(1);
				filewizard.SetFileUIMode(1);
				filewizard.SetUploadAfterMod("N");
				filewizard.SetServerInfo($("#FileWizard").attr("serverip"), $("#FileWizard").attr("serverport"));
				filewizard.SetTempDir($("#FileWizard").attr("serverdir"));

				if($("#FileWizard").attr("attmode") == "1") {
					filewizard.SetTransferMode(1);	/* 0 : SFTP(Default), 1 : SHTTP */
					filewizard.SetSHTTPMode("", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfileupload", "", $("#FileWizard").attr("prefixname") + "/servlet/dmfile");
				}

				if (file instanceof Array) {
					for (var loop = 0; loop < file.length; loop++) {
						/* 
						fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
						ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
						*/						
						var fileinfo = "1" + String.fromCharCode(31) + file[loop].localpath + String.fromCharCode(31);
						fileinfo = filewizard.UploadContents("", fileinfo);
						var delimiter = FileManager.getdelimiter(fileinfo);
						if (delimiter == 0 || delimiter == -1)
							throw delimiter;
						var info = fileinfo.split(String.fromCharCode(delimiter));
						file[loop].filename = info[1].substring(info[1].lastIndexOf("/")+1);
						if (typeof(file[loop].displayname) == "undefined" || file[loop].displayname == "")
							file[loop].displayname = file[loop].filename;
						file[loop].serverpath = info[1];
						file[loop].size = filewizard.GetLocalFileSize(file[loop].localpath);
						filelist[loop] = file[loop];
					}
				} else {
					/* 
					fileinfo =	{ count + (Char31) + serverpath + (Char31) }	
					ex) 1(Char31/usropt/acube/app/temp/F5B08562C94E4a8aAC2C0A41D8D4C034.hwp(Char31)
					*/						
					var fileinfo = "1" + String.fromCharCode(31) + file.localpath + String.fromCharCode(31);
					fileinfo = filewizard.UploadContents("", fileinfo);
					var delimiter = FileManager.getdelimiter(fileinfo);
					if (delimiter == 0 || delimiter == -1)
						throw delimiter;
					var info = fileinfo.split(String.fromCharCode(delimiter));
					file.filename = info[1].substring(info[1].lastIndexOf("/")+1);
					if (typeof(file.displayname) == "undefined" || file.displayname == "")
						file.displayname = file.filename;
					file.serverpath = info[1];
					file.size = filewizard.GetLocalFileSize(file.localpath);
					filelist[0] = file;
				}
				
				return filelist;
			} else {
				errormessage();
			}
		} catch (error) {
			if (error == 0 || error == -1)
				FileManager.errormessage("setfileinfo", error);
			else
				FileManager.errormessage("setfileinfo");
		}
 	},


	deleteLocalFile: function(location) {
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				filewizard.DeleteLocalFile(location);
			} else {
				errormessage();
			} 
		} catch (error) {
			FileManager.errormessage("deleteLocalFile");
		}
	},
	
	
	getdelimiter: function(fileinfo) {
		try {
			var dcode = 0;
			var codecount = 0;
			
			for (var code = 1; code < 32; code++) {
				if (fileinfo.indexOf(String.fromCharCode(code)) != -1) {
					codecount++;
					dcode = code;
				}
			}
			
			if (codecount > 1)
				return -1;
			else
				return dcode;
			 
		} catch (error) {
			FileManager.errormessage("getdelimiter");
		}
	},
	
	
	searchSpecialfile: function(fileinfo) {
		try {
			var isexist = false;
			var filename = "";
			var limitext = $("#FileWizard").attr("limitext");
			if (fileinfo instanceof Array) {
				for (var loop = 0; loop < fileinfo.length; loop++) {
					if (typeof(fileinfo[loop].type) == "undefined" || fileinfo[loop].type != "body") {
						filename = fileinfo[loop].localpath.toLowerCase();
						var pos = filename.lastIndexOf(".");
						if (pos != -1 && pos < filename.length) {
							var ext = filename.substring(pos + 1);
							if (limitext.indexOf(ext) != -1) {
								isexist = true;
								break;
							}
						}
					}
				}
			} else {
				if (typeof(fileinfo.type) == "undefined" || fileinfo.type != "body") {
					filename = fileinfo.localpath.toLowerCase();
					var pos = filename.lastIndexOf(".");
					if (pos != -1 && pos < filename.length) {
						var ext = filename.substring(pos + 1);
						if (limitext.indexOf(ext) != -1) {
							isexist = true;
						}
					}
				}
			}
			
			return isexist;
			
		} catch (error) {
			FileManager.errormessage("searchSpecialfile");
		}
	},


	getAttachIcon: function(filename) {
		try {
			var extension = filename.substring(filename.lastIndexOf(".") + 1);
			if (extension != "bc" && extension != "bmp" && extension != "dl" && extension != "doc" && extension != "docx" && extension != "exe" 
				&& extension != "gif" && extension != "gul" && extension != "htm" && extension != "html" && extension != "hwp" 
				&& extension != "ini" && extension != "jpg"	&& extension != "mgr" && extension != "mpg" && extension != "pdf" 
				&& extension != "ppt" && extension != "pptx" && extension != "print" && extension != "report" && extension != "tif" && extension != "txt" 
				&& extension != "wav" && extension != "xls" && extension != "xlsx" && extension != "xls02" && extension != "xml" && extension != "gif") {
				extension = "etc";
			}
		} catch (error) {
			extension = "etc";
		}

		return extension;
	},
	

	// display Error Message
	errormessage: function(methodname, errormessage) {
		if (typeof(methodname) == "undefined")
			alert(FileConst.Error.NeedWizard);
		else if (typeof(errormessage) == "undefined")
			alert(FileConst.Error.Method + "(ERROR:FileManager." + methodname + ")");
		else
			alert(FileConst.Error.Method + "(ERROR:FileManager." + methodname + "[" + errormessage + "])");

		return null;
	}
};

var AttachManager = {
	appendAttach: function(listid, attachid, checkid, checkname, type, file, editable) {
		try {
			if (type == "attach") {
				document.getElementById(listid).insertAdjacentHTML("beforeEnd", AttachManager.createAttach(attachid, checkid, checkname, file, editable));
			} else if (type == "body") {
				document.getElementById(listid).insertAdjacentHTML("afterBegin", AttachManager.createAttach(attachid, checkid, checkname, file));
			}
		} catch (error) {
			AttachManager.errormessage("appendAttach");
		}
	},

	createAttach: function(attachid, checkid, checkname, file, editable) {
		try {
			var prefix = $("#FileWizard").attr("prefixname");
			var itemnum;
			var securityYn;
			var isDuration;
			
			// 현재 안건번호 function의 유무 확인
			if(typeof(window["getCurrentItem"]) != "undefined") {
				itemnum = getCurrentItem();
			}
			/*
			// 현재 안건번호 function의 유무 확인 (jQuery)
			if ($('body').getCurrentItem) {
				itemnum = getCurrentItem();
			}
			*/
			// securityYn 필드 유무 확인
			if($("#securityYn").val() != "undefined") {
				securityYn = $("#securityYn", "#approvalitem" + itemnum).val();
			}
			// isDuration 필드 유무 확인
			if($("#isDuration").val() != "undefined") {
				isDuration = $("#isDuration", "#approvalitem" + itemnum).val();
			}

			if (file.filetype == FileConst.FileType.BizAttach) {
				return "<table id=\"" + attachid + "\" width=\"100%\" filename=\"" + file.filename + "\" filetype=\"" + file.filetype + "\" displayname=\"" + file.displayname + "\" localpath=\"" + file.localpath + "\" filesize=\"" + file.size + "\" filedate=\"" + file.date + "\" regdate=\"" + ((typeof(file.regdate) == "undefined") ? "" : file.regdate) + "\" fileid=\"" + ((typeof(file.fileid) == "undefined") ? "" : file.fileid) +  "\" modflag=\"false\" border=0 cellpadding=0 cellspacing=0><tr>"
					+ "<td width=\"100%\" class=\"tb_left\"><input type=\"checkbox\" id=\"" + checkid + "\" name=\"" + checkname + "\">&nbsp;"
					+ "<img src=\"" + file.src + "\" width=16 height=16></img>&nbsp;"
					+ "<a href=\"#\" onclick=\"viewAttach('" + attachid + "', '" + securityYn + "', '" + isDuration + "');return(false);\">[" + FileConst.Rsc.BizAttach + "]" + file.displayname + "</a> [" + ((file.size < 512) ? 1 : Math.round(file.size / 1024)) + "KB]</td>"
					+ "</tr></table>";
			} else {
				if (typeof(editable) != "undefined" && editable == true) {
					return "<table id=\"" + attachid + "\" width=\"100%\" filename=\"" + file.filename + "\" filetype=\"" + file.filetype + "\" displayname=\"" + file.displayname + "\" localpath=\"" + file.localpath + "\" filesize=\"" + file.size + "\" filedate=\"" + file.date + "\" regdate=\"" + ((typeof(file.regdate) == "undefined") ? "" : file.regdate) + "\" fileid=\"" + ((typeof(file.fileid) == "undefined") ? "" : file.fileid) +  "\" modflag=\"false\" border=0 cellpadding=0 cellspacing=0><tr>"
					+ "<td class=\"tb_left\"><input type=\"checkbox\" id=\"" + checkid + "\" name=\"" + checkname + "\">&nbsp;"
					+ "<img src=\"" + file.src + "\" width=16 height=16></img>&nbsp;"
					+ "<a href=\"#\" onclick=\"viewAttach('" + attachid + "', '" + securityYn + "', '" + isDuration + "');return(false);\">"
					+ "<span id=\"display" + checkid + "\">" + file.displayname + "</span></a>"
					+ "<span id=\"modify" + checkid + "\" style=\"display:none;\"><input type=\"text\" id=\"txt" + checkid + "\" size=\"35\" value=\"" + file.displayname + "\" class=\"input\" onkeyup=\"checkInputMaxLength(this,'',256)\" onblur=\"changeDisplayName('" + checkid + "', 'off');return(false);\"/></span> [" + ((file.size < 512) ? 1 : Math.round(file.size / 1024)) + "KB]"
					+ "<span>&nbsp<a href=\"#\" style=\"text-decoration:none;color:black;\" onclick=\"changeDisplayName('" + checkid + "', 'on');return(false);\"><img src=\"" + prefix + "/app/ref/image/icon_change_filename.gif\" border=\"0\" style=\"cursor:pointer;\" alt=\"" + FileConst.Rsc.ModifyFileName + "\" width=16 height=16></img></a></td></tr></table>";
				} else {
					return "<table id=\"" + attachid + "\" width=\"100%\" filename=\"" + file.filename + "\" filetype=\"" + file.filetype + "\" displayname=\"" + file.displayname + "\" localpath=\"" + file.localpath + "\" filesize=\"" + file.size + "\" filedate=\"" + file.date + "\" regdate=\"" + ((typeof(file.regdate) == "undefined") ? "" : file.regdate) + "\" fileid=\"" + ((typeof(file.fileid) == "undefined") ? "" : file.fileid) +  "\" modflag=\"false\" border=0 cellpadding=0 cellspacing=0><tr>"
					+ "<td class=\"tb_left\"><input type=\"checkbox\" id=\"" + checkid + "\" name=\"" + checkname + "\">&nbsp;"
					+ "<img src=\"" + file.src + "\" width=16 height=16></img>&nbsp;"
					+ "<a href=\"#\" onclick=\"viewAttach('" + attachid + "', '" + securityYn + "', '" + isDuration + "');return(false);\">"
					+ file.displayname + "</a> [" + ((file.size < 512) ? 1 : Math.round(file.size / 1024)) + "KB]</td></tr></table>";
				}
			}
		} catch (error) {
			AttachManager.errormessage("createAttach");
		}
	},
		
	modifyAttach: function(selectedid) {
		try {
			var changeflag = false;
			var file = new Object();
			var source = $("#" + selectedid);
			file.displayname = file.filename = source.attr("filename");
			if (AttachManager.existInTemp(source.attr("localpath")) == true) {
				if (source.attr("modflag") == true) {
					if (AttachManager.isModified(source.attr("localpath"), source.attr("filesize"), source.attr("filedate")) == true) {
						// 붙임파일 업로드
						var filelist = new Array();
						file.localpath = source.attr("localpath");
						filelist = FileManager.uploadfile(file);
						// 업로드한 파일로 정보 업데이트
						source.attr("filename", filelist[0].filename);
						source.attr("localpath", filelist[0].localpath);
						source.attr("filesize", filelist[0].size);
						source.attr("filedate", filelist[0].date);
						// 다운로드할 파일 정보셋팅
						file.displayname = file.filename = source.attr("filename");
						FileManager.openfile(filelist[0], true);
						source.attr("filedate", FileManager.getfiledate(source.attr("localpath")));
						source.attr("localpath", filelist[0].localpath);
						
						changeflag = true;
					} else {
						// 수정버튼을 클릭하고 수정하지 않은 경우 로컬파일만 오픈
						FileManager.openfile(file, true);
						source.attr("localpath", file.localpath);
					}
				} else {
					// 로컬파일 오픈
					FileManager.openfile(file, true);
					source.attr("localpath", file.localpath);
				}
			} else {
				// 로컬파일이 없는 경우
				// 와스에 있는 파일 다운로드 후 오픈
				FileManager.openfile(file, true);
				source.attr("localpath", file.localpath);
			}
	
			source.attr("modflag", true);
			
			return changeflag;
		} catch (error) {
			AttachManager.errormessage("modifyAttach");
		}
	},
		
	removeAttach: function(selectedid) {
		try {
			var source = document.getElementById(selectedid);
			var parent = source.parentElement;
			parent.removeChild(source);
		} catch (error) {
			AttachManager.errormessage("removeAttach");
		}
	},
	
	saveAttach: function(filelist) {
		FileManager.download(filelist);
	},
	
	viewAttach: function(selectedid) {
		try {
			var changeflag = false;
			var file = new Object();
			var source = $("#" + selectedid);
			file.fileid = source.attr("fileid");
			file.displayname = source.attr("displayname");
			file.filename = source.attr("filename");
			file.localpath = source.attr("localpath");
			file.type = "open";
			if (AttachManager.existInTemp(source.attr("localpath"))) {
				if (source.attr("modflag") == true) {
					if (AttachManager.isModified(source.attr("localpath"), source.attr("filesize"), source.attr("filedate"))) {
						// 붙임파일 업로드
						var filelist = new Array();
						file.localpath = source.attr("localpath");
						filelist = FileManager.uploadfile(file);
						// 업로드한 파일로 정보 업데이트
						source.attr("filename", filelist[0].filename);
						source.attr("localpath", filelist[0].localpath);
						source.attr("filesize", filelist[0].size);
						source.attr("filedate", filelist[0].date);
						// 다운로드할 파일 정보셋팅
						file.displayname = source.attr("displayname");
						file.filename = source.attr("filename");
						if (file.displayname.lastIndexOf(".") == -1 && file.filename.lastIndexOf(".") != -1) {
							file.displayname += file.filename.substring(file.filename.lastIndexOf("."));
						}
						FileManager.openfile(filelist[0], true);
						source.attr("filedate", FileManager.getfiledate(source.attr("localpath")));
						
						changeflag = true;
					} else {
						// 수정버튼을 클릭하고 수정하지 않은 경우 로컬파일만 오픈
//						FileManager.openfile(file, false);
						FileManager.openfileLocal(file);
						source.attr("localpath", file.localpath);
					}
				} else {
					// 로컬파일 오픈
//						FileManager.openfile(file, false);
						file.filename = source.attr("localpath");
						FileManager.openfileLocal(file);
						source.attr("localpath", file.localpath);
				}
			} else {
				if (file.displayname.lastIndexOf(".") == -1 && file.filename.lastIndexOf(".") != -1) {
					file.displayname += file.filename.substring(file.filename.lastIndexOf("."));
				}
				// 로컬파일이 없는 경우
				if (typeof(file.fileid) == "undefined" || file.fileid == "") {
					// 와스에 있는 파일 다운로드 후 오픈
					FileManager.openfile(file, false);
					source.attr("localpath", file.localpath);
				} else {
					// 저장서버에 있는 파일 다운로드 후 오픈
					FileManager.download(file);
				}
			}
			
			// 다운로드 한 붙임파일 정보로 변경
			source.attr("modflag", false);
			
			return changeflag;
		} catch (error) {
			AttachManager.errormessage("viewAttach");
		}
	},
	
	moveUpAttach: function(selectedid) {
		try {
			var source = document.getElementById(selectedid);
			var target = source.previousSibling;
			if (target == null) {
				alert(FileConst.Error.NoPlaceToMove);
				return false;
			}
			
			var parent = source.parentElement;
			parent.removeChild(source);
			parent.insertBefore(source, target);
			
			return true;
		} catch (error) {
			AttachManager.errormessage("moveUpAttach");
		}
	},
	
	moveDownAttach: function(selectedid) {
		try {
			var source = document.getElementById(selectedid);
			var target = source.nextSibling;
			if (target == null) {
				alert(FileConst.Error.NoPlaceToMove);
				return false;
			}
			
			var parent = source.parentElement;
			parent.removeChild(source);
			if (target.nextSibling == null)
				parent.insertAdjacentElement("beforeEnd", source);
			else
				parent.insertBefore(source, target.nextSibling);
				
			return true;
		} catch (error) {
			AttachManager.errormessage("moveDownAttach");
		}
	},
		
	existInTemp: function(localpath) {
		try {
			return (FileManager.getfileinfo(localpath) == null) ? false : true;
		} catch (error) {
			AttachManager.errormessage("existInTemp");
		}
	},
	
	isModified: function(localpath, size, date) {
		try {
			var localsize = FileManager.getfilesize(localpath);
			var localdate = FileManager.getfiledate(localpath);
			
			return (localsize == size && localdate == date) ? false : true;
		} catch (error) {
			AttachManager.errormessage("isModified");
		}
	},
	makeLocalFile: function(sFilePath, contents){
		 //FileWizard.MakeLocalFile(sFilePath,  sContents)
		try {
			var filewizard = document.getElementById("FileWizard");
			if (filewizard != null) {
				filewizard.MakeLocalFile(sFilePath, contents);
			} else {
				errormessage();
			} 
		} catch (error) {
			FileManager.errormessage("makeLocalFile");
		}
	},

	// display Error Message
	errormessage: function(methodname) {
		if (typeof(methodname) == "undefined")
			alert(FileConst.Error.NeedWizard);
		else
			alert(FileConst.Error.Method + "(ERROR:AttachManager." + methodname + ")");

		return null;
	}
};


function appendAttach() {
	var filelist = FileManager.uploadfile();
	
	if (filelist != null) {
		var filecount = filelist.length;
		var totalsize = getAttachSize();
		for (var loop = 0; loop < filecount; loop++) {
			// 첨부사이즈 체크
			if (!isNaN(parseInt(filelist[loop].size))) {
				totalsize += parseInt(filelist[loop].size);
			}
			if (FileConst.FileType.MaxSize != 0) {
				if (FileConst.FileType.MaxSize < totalsize) {
					alert(FileConst.Error.ExceedLimitedSize);
					return false;
				}
			}
			
			var attachid = "attach_" + index;
			var checkid = "attach_cid_" + index;
			var checkname = "attach_cname";
			AttachManager.appendAttach("divattach", attachid, checkid, checkname, "attach", filelist[loop], true);
			index++;
		}
		
		arrangeAttach();
	}
}

function removeAttach() {
	var count = 0;
	var attachid = "";
	var checkname = "attach_cname";
	var checkboxes = document.getElementsByName(checkname);
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = checkboxes.length - 1; loop >= 0; loop--) {
			if (checkboxes[loop].checked) {
				attachid = checkboxes[loop].id;
				attachid = attachid.replace("attach_cid_", "attach_");
				var attach = $("#" + attachid);
				if (attach.attr("filetype") == FileConst.FileType.BizAttach) {
					alert(FileConst.Error.CannotDeleteFromBiz + " [" + attach.attr("displayname") + "]");
					checkboxes[loop].checked = false;
				} else {				
					AttachManager.removeAttach(attachid);
				}
				count++;
			}
		}
	}

	if (count == 0) {
		alert(FileConst.Error.NoAttachFileSelected);
	}
	
	arrangeAttach();
}

function saveAttach() {
	var count = 0;
	var attachid = "";
	var checkname = "attach_cname";
	var checkboxes = document.getElementsByName(checkname);
	var filelist = new Array();
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = checkboxes.length - 1; loop >= 0; loop--) {
			if (checkboxes[loop].checked) {
				attachid = checkboxes[loop].id;
				attachid = attachid.replace("attach_cid_", "attach_");
				var attach = $("#" + attachid);
				var file = new Object();
				file.fileid = attach.attr("fileid");
				file.filename = attach.attr("filename");
				file.displayname = attach.attr("displayname");
				if (file.displayname.lastIndexOf(".") == -1 && file.filename.lastIndexOf(".") != -1) {
					file.displayname += file.filename.substring(file.filename.lastIndexOf("."));
				}
				file.gubun = "";
				file.docid = "";
				file.type = "save";
				filelist[count++] = file;
			}
		}
	}

	if (count == 0) {
		alert(FileConst.Error.NoAttachFileSelected);
	} else {
		AttachManager.saveAttach(filelist);
	}
}


function viewAttach(selectedid, securityYn, isDuration) {
	if ((arguments.length == 3) && (securityYn == "Y") && (isDuration == "true")) {
		insertDocPassword4Attach(selectedid);
		return;
	}
	AttachManager.viewAttach(selectedid);
}


function openAttach(docid, fileid, filename, displayname) {
	if (arguments.length == 1) {
		alert(docid);
	} else {
		alert(fileid);
	}
}

function selectedCheck() {
	var count = 0;
	var checkboxes = document.getElementsByName("attach_cname");
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = 0; loop < checkboxes.length; loop++) {
			if (checkboxes[loop].checked) {
				selectedid = checkboxes[loop].id;
				count++;
			}
		}
	}

	return count;
}

function moveUpAttach() {
	var count = selectedCheck();

	if (count == 0) {
		alert(FileConst.Error.NoAttachFileSelected);
		return;
	} else if (count > 1) {
		alert(FileConst.Error.SelectOnlyOneAttach);
		return;
	}

	AttachManager.moveUpAttach(selectedid.replace("attach_cid_", "attach_"));
	arrangeAttach();
}

function moveDownAttach() {
	var count = selectedCheck();

	if (count == 0) {
		alert(FileConst.Error.NoAttachFileSelected);
		return;
	} else if (count > 1) {
		alert(FileConst.Error.SelectOnlyOneAttach);
		return;
	}

	AttachManager.moveDownAttach(selectedid.replace("attach_cid_", "attach_"));
	arrangeAttach();
}

function arrangeAttach() {	
	var itemnum = "";
	//현재 안건번호 function의 유무 확인
	if(typeof(window["getCurrentItem"]) != "undefined") {
		itemnum = getCurrentItem();
	}else{
		itemnum = 1;
	}
	var checkboxes = document.getElementsByName("attach_cname");
	if (checkboxes != null && checkboxes.length != 0) {
		var attachinfo = "";
		var length = checkboxes.length;
		for (var loop = 0; loop < length; loop++) {
			attachid = checkboxes[loop].id;
			var attach = $("#" + attachid.replace("attach_cid_", "attach_"));
			if (attach.attr("filetype") == FileConst.FileType.BizAttach) {
				attachinfo += attach.attr("fileid") + String.fromCharCode(2) + attach.attr("filename") + String.fromCharCode(2) + attach.attr("displayname") + String.fromCharCode(2) + 
				FileConst.FileType.BizAttach + String.fromCharCode(2) + attach.attr("filesize") + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    (loop+1) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + attach.attr("regdate") + String.fromCharCode(2) + 
			    attach.attr("localpath") + String.fromCharCode(4);
			} else {
				attachinfo += attach.attr("fileid") + String.fromCharCode(2) + attach.attr("filename") + String.fromCharCode(2) + attach.attr("displayname") + String.fromCharCode(2) + 
				FileConst.FileType.Attach + String.fromCharCode(2) + attach.attr("filesize") + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    (loop+1) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + attach.attr("regdate") + String.fromCharCode(2) + 
			    attach.attr("localpath") + String.fromCharCode(4);
			}
		}
		$("#attachFile", "#approvalitem" + itemnum).val(attachinfo);
	} else {
		$("#attachFile", "#approvalitem" + itemnum).val("");
	}
}

function clearAttach() {
	document.getElementById("divattach").innerHTML = "";
}

function loadAttach(fileinfo, editable) {
	index = 0;
	var filelist = transferFileList(fileinfo);
	var filecount = filelist.length;
	for (var loop = 0; loop < filecount; loop++) {
		var attachid = "attach_" + index;
		var checkid = "attach_cid_" + index;
		var checkname = "attach_cname";
		AttachManager.appendAttach("divattach", attachid, checkid, checkname, "attach", filelist[loop], editable);
		index++;
	}
}

function reloadAttach(itemnum, editable) {
	var fileinfo = $("#attachFile", "#approvalitem" + itemnum).val();
	clearAttach();
	loadAttach(fileinfo, editable);
}

function getAttachSize() {
	var filesize = 0;
	var checkboxes = document.getElementsByName("attach_cname");
	if (checkboxes != null && checkboxes.length != 0) {
		var length = checkboxes.length;
		for (var loop = 0; loop < length; loop++) {
			attachid = checkboxes[loop].id;
			var attach = document.getElementById(attachid.replace("attach_cid_", "attach_"));
			if (!isNaN(parseInt(attach.filesize))) {
				filesize += parseInt(attach.filesize);
			}
		}
	}
	return filesize;	
}

function transferFileList(attachinfo) {
	var attachlist = new Array();
	var attachs = attachinfo.split(String.fromCharCode(4));
	var prefix = $("#FileWizard").attr("prefixname");
		
	var attachlength = attachs.length;
	for (var loop = 0; loop < attachlength; loop++) {
		if (attachs[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = attachs[loop].split(String.fromCharCode(2));
			var file = new Object();
			file.fileid = info[0];
			file.filename = info[1];
			file.displayname = info[2];
			file.filetype = info[3];
			file.size = info[4];
			file.fileorder = info[7];
			file.regdate = info[10];
			file.src = prefix + "/app/ref/image/attach/attach_" + FileManager.getAttachIcon(file.filename) + ".gif";
			file.localpath = info[11];
			
			attachlist[loop] = file;
		}
	}
	return attachlist;
}

function transferFileInfo(attachlist) {
	var attachinfo = "";
	var attachlength = attachlist.length;
	for (var loop = 0; loop < attachlength; loop++) {
		var file = attachlist[loop];
		attachinfo += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		file.filetype + String.fromCharCode(2) + file.size + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		file.fileorder + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + file.regdate + String.fromCharCode(2) + 
		file.localpath + String.fromCharCode(4);
	}
	return attachinfo;
}

function changeDisplayName(selectedid, act) {
	if (act == "on") {
		$("#display" + selectedid).hide();
		$("#modify" + selectedid).show();
	} else {
		var displayname = escapeFilename($("#txt" + selectedid).val());
		$("#txt" + selectedid).val(displayname);
		var attach = $("#" + selectedid.replace("attach_cid_", "attach_"));
		if (displayname != attach.attr("displayname")) {
			attach.attr("displayname", displayname);
			arrangeAttach();
			$("#display" + selectedid).text(displayname);
		}
		$("#display" + selectedid).show();
		$("#modify" + selectedid).hide();
	}
}
