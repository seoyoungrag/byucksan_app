package com.sds.acube.app.ws.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * Class Name : FileVOs.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 25. <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  2011. 3. 25.
 * @version  1.0
 * @see  com.sds.acube.app.ws.server.vo.FileVO.java
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({AppFileVOs.class})
@XmlRootElement(name = "files")
@XmlType(name = "files")
public class AppFileVOs {
    /**
	 */
    @XmlElement(name="file", nillable = true)
    protected List<AppFileVO> fileVOs;

    /**
	 * @return  the fileVOs
	 */
    public List<AppFileVO> getFileVOs() {
        return fileVOs;
    }

    /**
	 * @param fileVOs  the fileVOs to set
	 */
    public void setFileVOs(List<AppFileVO> fileVOs) {
        this.fileVOs = fileVOs;
    }

}