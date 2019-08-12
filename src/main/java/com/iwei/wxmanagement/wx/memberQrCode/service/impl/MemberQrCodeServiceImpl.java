package com.iwei.wxmanagement.wx.memberQrCode.service.impl;

import com.iwei.wxmanagement.wx.dao.MemberQrCodeMapper;
import com.iwei.wxmanagement.wx.memberQrCode.dto.MemberQrCodeDTO;
import com.iwei.wxmanagement.wx.memberQrCode.model.MemberQrCode;
import com.iwei.wxmanagement.wx.memberQrCode.service.MemberQrCodeService;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MemberQrCodeServiceImpl implements MemberQrCodeService {
    private static Logger logger = Logger.getLogger(MemberQrCodeServiceImpl.class);

    @Autowired
    private MemberQrCodeMapper MemberQrCodeMapper;

    @Override
    public MemberQrCode getMemberById(MemberQrCode qrCode){
        MemberQrCodeDTO qrCodeDTO = new MemberQrCodeDTO();
        qrCodeDTO.openid = qrCode.openid;
        qrCodeDTO.type = qrCode.type;
        qrCodeDTO = MemberQrCodeMapper.selectBymid(qrCodeDTO);
        if(qrCodeDTO != null){
            qrCode.qrId = qrCodeDTO.qrId;
            qrCode.openid = qrCodeDTO.openid;
            qrCode.type=qrCodeDTO.type;
            qrCode.path = qrCodeDTO.path;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            qrCode.createTime = sdf.format(new Date());
        }
        return  qrCode;
    }

    @Override
    public void getQrCodeByUserIdType(HttpServletResponse response, String userId, int type){
        InputStream is = null;
        OutputStream os = null;
        try {
            if(StringUtils.isEmpty(userId)){
                System.out.println("获取二维码用户openId为空");
            }

            MemberQrCode qrCode = new MemberQrCode();
            qrCode.openid = userId;
            qrCode.type = type;
            qrCode = getMemberById(qrCode);
            response.setContentType("static/image/jpeg");
            File file = new File(qrCode.path);
            response.addHeader("Content-Length", "" + file.length());
            is = new FileInputStream(file);
            os = response.getOutputStream();
            IOUtils.copy(is, os);
        } catch (Exception e) {
        } finally {
            try {
                os.close();
                os.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("关闭流发生异常");
            }

        }
    }
}
