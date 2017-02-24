/**
 * 
 */
package com.sds.acube.app.exchange.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jconfig.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.exchange.AuthenticatedStubFactory;
import com.sds.acube.app.exchange.service.IFolderService;
import com.sds.acube.app.ws.client.document.FolderServiceStub;
import com.sds.acube.app.ws.client.document.FolderServiceStub.BindWS;
import com.sds.acube.app.ws.client.document.FolderServiceStub.Cleanup;
import com.sds.acube.app.ws.client.document.FolderServiceStub.CleanupResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.Create;
import com.sds.acube.app.ws.client.document.FolderServiceStub.CreateResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.Move;
import com.sds.acube.app.ws.client.document.FolderServiceStub.MoveResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.Order;
import com.sds.acube.app.ws.client.document.FolderServiceStub.OrderResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.Remove;
import com.sds.acube.app.ws.client.document.FolderServiceStub.RemoveResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.RemoveShare;
import com.sds.acube.app.ws.client.document.FolderServiceStub.RemoveShareResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.ResponseBean;
import com.sds.acube.app.ws.client.document.FolderServiceStub.Share;
import com.sds.acube.app.ws.client.document.FolderServiceStub.ShareResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.TestCleanup;
import com.sds.acube.app.ws.client.document.FolderServiceStub.TestCleanupResponse;
import com.sds.acube.app.ws.client.document.FolderServiceStub.Update;
import com.sds.acube.app.ws.client.document.FolderServiceStub.UpdateResponse;


/**
 * Class Name : FolderService.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 6. 16. <br> 수 정 자 : yucea <br> 수정내용 : <br>
 * @author  yucea
 * @since  2011. 6. 16.
 * @version  1.0
 * @see  com.sds.acube.app.ws.service.impl.FolderService.java
 */

@Service("folderService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class FolderService extends BaseService implements IFolderService {

    private static final long serialVersionUID = 9195968764539140163L;

    /**
	 */
    private FolderServiceStub stub = null;


    public FolderService() throws RemoteException {
	Category category = AppConfig.getCategory("docmgr");
	String userId = category.getProperty("account");
	String userPwd = category.getProperty("password");
	String baseUrl = category.getProperty("folder_wsdl");

	AuthenticatedStubFactory stubFactory = new AuthenticatedStubFactory(userId, userPwd);
	stub = stubFactory.getFolderServiceStub(baseUrl);
    }


    public boolean create(BindVO vo) throws Exception {
	BindWS ws = new BindWS();

	CommonUtil.copyObject(vo, ws);

	Create param = new Create();
	param.setWs(ws);

	CreateResponse res = stub.create(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return true;
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public boolean update(BindVO vo) throws Exception {
	BindWS ws = new BindWS();

	CommonUtil.copyObject(vo, ws);

	Update param = new Update();
	param.setWs(ws);

	UpdateResponse res = stub.update(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return true;
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public boolean move(BindVO vo, BindVO targetVO) throws Exception {
	BindWS ws = new BindWS();
	BindWS targetWS = new BindWS();

	CommonUtil.copyObject(vo, ws);
	CommonUtil.copyObject(targetVO, targetWS);

	Move param = new Move();
	param.setWs(ws);
	param.setTarget(targetWS);

	MoveResponse res = stub.move(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return true;
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public boolean remove(String compId, String deptId, String bindId) throws Exception {
	Remove param = new Remove();
	param.setCompId(compId);
	param.setDeptId(deptId);
	param.setBindId(bindId);

	RemoveResponse res = stub.remove(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return true;
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public boolean share(BindVO bindVO, String targetId) throws Exception {
	BindWS ws = new BindWS();
	CommonUtil.copyObject(bindVO, ws);

	Share param = new Share();
	param.setWs(ws);
	param.setTargetId(targetId);

	ShareResponse res = stub.share(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return true;
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public boolean removeShare(BindVO bindVO) throws Exception {
	BindWS ws = new BindWS();
	CommonUtil.copyObject(bindVO, ws);

	RemoveShare param = new RemoveShare();
	param.setWs(ws);

	RemoveShareResponse res = stub.removeShare(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return true;
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public boolean order(String compId, String deptId, BindVO[] vos) throws Exception {
	List<BindWS> list = new ArrayList<BindWS>();
	for (BindVO vo : vos) {
	    BindWS ws = new BindWS();
	    CommonUtil.copyObject(vo, ws);

	    list.add(ws);
	}

	Order param = new Order();
	param.setCompId(compId);
	param.setDeptId(deptId);
	param.setWss(list.toArray(new BindWS[0]));

	OrderResponse res = stub.order(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return true;
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public JSONArray testCleanup(String compId) throws Exception {
	TestCleanup clean = new TestCleanup();
	clean.setCompId(compId);

	TestCleanupResponse res = stub.testCleanup(clean);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    
	    return new JSONArray(bean.getMessage());
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    public JSONObject cleanup(String compId) throws Exception {
	Cleanup clean = new Cleanup();
	clean.setCompId(compId);

	CleanupResponse res = stub.cleanup(clean);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) {
	    return new JSONObject(bean.getMessage());
	} else {
	    throw new IllegalAccessException(bean.getMessage());
	}
    }
}
