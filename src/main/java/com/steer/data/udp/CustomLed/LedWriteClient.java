package com.steer.data.udp.CustomLed;

import ch.qos.logback.classic.Logger;
import com.steer.data.common.utils.ObjectUtil;
import com.steer.data.webservice.datahandling.mapper.WebServiceDataHandlingMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LedWriteClient {

    public static Logger logger = (Logger) LoggerFactory.getLogger(LedWriteClient.class);
    @Autowired
    WebServiceDataHandlingMapper webServiceDataHandlingMapper;

    public String doWork(List<String> udp_ids) throws Exception {
        String str = null;
        //根据udp_id查询
        for (String udp_id : udp_ids) {
            if (udp_id.equals("228")) {//质检位置
                Write_LED_IP_228();
            } else if (udp_id.equals("227")) {//上卷位置
                Write_LED_IP_227();
            } else if (udp_id.equals("229")) {//卸卷位置
                Write_LED_IP_229();
            } else if (udp_id.equals("226")) {//精轧位置
                Write_LED_IP_226();
            }

        }
        return null;
    }

    public String Write_LED_IP_228() //质检位置
    {
        try {
            int iRet = 0;
            String resMsg = "";
            String strMsg = "";
            Boolean bSuccess = false;

            List<HashMap> hashList = webServiceDataHandlingMapper.getRfidMsg("RFID_2_QCM", 1);
            if (ObjectUtil.isNotEmpty(hashList)) {
                List<HashMap> listHooksGst = webServiceDataHandlingMapper.getGxHookMsg(hashList.get(0).get("GOU_HAO"));
                if (ObjectUtil.isNotEmpty(listHooksGst)) {
                    String hook_no = listHooksGst.get(0).get("HOOK_NO") == null ? null : listHooksGst.get(0).get("HOOK_NO") + "";
                    String coilno = listHooksGst.get(0).get("COILNO") == null ? null : listHooksGst.get(0).get("COILNO") + "";//截取为轧批号
                    String steelcode = listHooksGst.get(0).get("STEELCODE") == null ? null : listHooksGst.get(0).get("STEELCODE") + "";
                    String aim_dmt = listHooksGst.get(0).get("AIM_DMT") == null ? null : listHooksGst.get(0).get("AIM_DMT") + "";
                    if(listHooksGst.get(0).get("BATCHSEQ")!=null){
                        List<HashMap> next1 =webServiceDataHandlingMapper.getNext1(listHooksGst.get(0).get("BATCHSEQ"));
                        if(ObjectUtil.isNotEmpty(next1)){
                            String zhishu1 = next1.get(0).get("BATCHQUANTITY") == null ? null : next1.get(0).get("BATCHQUANTITY") + "";
                            String coilno1 = next1.get(0).get("BATCHNO") == null ? null : next1.get(0).get("BATCHNO") + "";//截取为轧批号
                            String steelcode1 = next1.get(0).get("STEELCODE") == null ? null : next1.get(0).get("STEELCODE") + "";
                            String aim_dmt1 = next1.get(0).get("AIM_DMT") == null ? null : next1.get(0).get("AIM_DMT") + "";
                            try {
                                if (steelcode != null) {
                                    Write_LED_IP_228_GangZhong(coilno1+"|"+steelcode1+"|"+aim_dmt1+"|"+zhishu1);
                                }
                            } catch (Exception ex) {

                            }
                            if(next1.get(1)!=null && next1.get(1).get("BATCHNO")!=null){
                                String zhishu2 = next1.get(1).get("BATCHQUANTITY") == null ? null : next1.get(1).get("BATCHQUANTITY") + "";
                                String coilno2 = next1.get(1).get("BATCHNO") == null ? null : next1.get(1).get("BATCHNO") + "";//截取为轧批号
                                String steelcode2 = next1.get(1).get("STEELCODE") == null ? null : next1.get(1).get("STEELCODE") + "";
                                String aim_dmt2 = next1.get(1).get("AIM_DMT") == null ? null : next1.get(1).get("AIM_DMT") + "";
                                try {
                                    if (hook_no != null) {
                                        Write_LED_IP_228_Gouhao(coilno2+"|"+steelcode2+"|"+aim_dmt2+"|"+zhishu2);
                                    }
                                } catch (Exception ex) {

                                }
                            }
                        }
                    }

                    try {
                        if (coilno != null) {
                            Write_LED_IP_228_ZhaPiHao(coilno+"|"+steelcode+"|"+aim_dmt+"|"+hook_no);
                        }
                    } catch (Exception ex) {

                    }


                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String Write_LED_IP_227() //上卷位置
    {
        try {
            int iRet = 0;
            String resMsg = "";
            String strMsg = "";
            Boolean bSuccess = false;

            List<HashMap> hashList = webServiceDataHandlingMapper.getRfidMsg("RFID_1_UPCOIL", 1);
            if (ObjectUtil.isNotEmpty(hashList)) {
                List<HashMap> listHooksGst = webServiceDataHandlingMapper.getGxHookMsg(hashList.get(0).get("GOU_HAO"));
                if (ObjectUtil.isNotEmpty(listHooksGst)) {
                    String hook_no = listHooksGst.get(0).get("HOOK_NO") == null ? null : listHooksGst.get(0).get("HOOK_NO") + "";
                    String coilno = listHooksGst.get(0).get("COILNO") == null ? null : listHooksGst.get(0).get("COILNO") + "";//截取为轧批号
                    String steelcode = listHooksGst.get(0).get("STEELCODE") == null ? null : listHooksGst.get(0).get("STEELCODE") + "";
                    try {
                        if (coilno != null) {
                            Write_LED_IP_227_ZhaPiHao(coilno);
                        }
                    } catch (Exception ex) {

                    }
                    try {
                        if (steelcode != null) {
                            Write_LED_IP_227_GangZhong(steelcode);
                        }
                    } catch (Exception ex) {

                    }
                    try {
                        if (hook_no != null) {
                            Write_LED_IP_227_Gouhao(hook_no);
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String Write_LED_IP_229() //卸卷位置
    {
        try {
            int iRet = 0;
            String resMsg = "";
            String strMsg = "";
            Boolean bSuccess = false;

            List<HashMap> hashList = webServiceDataHandlingMapper.getRfidMsg("RFID_4_DOWNCOIL", 1);
            if (ObjectUtil.isNotEmpty(hashList)) {
                List<HashMap> listHooksGst = webServiceDataHandlingMapper.getGxHookMsg(hashList.get(0).get("GOU_HAO"));
                if (ObjectUtil.isNotEmpty(listHooksGst)) {
                    String hook_no = listHooksGst.get(0).get("HOOK_NO") == null ? null : listHooksGst.get(0).get("HOOK_NO") + "";
                    String coilno = listHooksGst.get(0).get("COILNO") == null ? null : listHooksGst.get(0).get("COILNO") + "";//截取为轧批号
                    String steelcode = listHooksGst.get(0).get("STEELCODE") == null ? null : listHooksGst.get(0).get("STEELCODE") + "";
                    try {
                        if (coilno != null) {
                            Write_LED_IP_229_ZhaPiHao(coilno);
                        }
                    } catch (Exception ex) {

                    }
                    try {
                        if (steelcode != null) {
                            Write_LED_IP_229_GangZhong(steelcode);
                        }
                    } catch (Exception ex) {

                    }
                    try {
                        if (hook_no != null) {
                            Write_LED_IP_229_Gouhao(hook_no);
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String Write_LED_IP_226() //精轧位置
    {
        try {
            int iRet = 0;
            String resMsg = "";
            String strMsg = "";
            Boolean bSuccess = false;

            List<HashMap> hashList = webServiceDataHandlingMapper.getJzMsg(1);
            List<HashMap> wenduList = webServiceDataHandlingMapper.getWenDu();
            if (ObjectUtil.isNotEmpty(hashList)) {
                String steelcode = hashList.get(0).get("STEELCODE") == null ? null : hashList.get(0).get("STEELCODE") + "";
                String coilno = hashList.get(0).get("BATCHNO") == null ? null : hashList.get(0).get("BATCHNO") + "";//截取为轧批号
                String shengyzhishu = hashList.get(0).get("SHENGYZHISHU") == null ? null : hashList.get(0).get("SHENGYZHISHU") + "";
                String aim_dmt = hashList.get(0).get("AIM_DMT") == null ? null : hashList.get(0).get("AIM_DMT") + "";
                String batchquantity = hashList.get(0).get("BATCHQUANTITY") == null ? null : hashList.get(0).get("BATCHQUANTITY") + "";
                try {
                    if (coilno != null) {
                        Write_LED_IP_226_ZhaPiHao(coilno);
                    }
                } catch (Exception ex) {

                }
                try {
                    if (steelcode != null) {
                        Write_LED_IP_226_GangZhong(steelcode);
                    }
                } catch (Exception ex) {

                }
                try {
                    if (shengyzhishu != null) {
                        Write_LED_IP_226_ShengYuZhiShu(shengyzhishu);
                    }
                } catch (Exception ex) {

                }
                try {
                    if (batchquantity != null) {
                        Write_LED_IP_226_JiHuaZhiShu(batchquantity);
                    }
                } catch (Exception ex) {

                }
                try {
                    if (aim_dmt != null) {
                        Write_LED_IP_226_GuiGe(aim_dmt);
                    }
                } catch (Exception ex) {

                }
                if (ObjectUtil.isNotEmpty(wenduList)) {
                    String temp_in_fr_a = wenduList.get(0).get("TEMP_IN_FR_A") == null ? null : wenduList.get(0).get("TEMP_IN_FR_A") + "";
                    String temp_spin_a = wenduList.get(0).get("TEMP_SPIN_A") == null ? null : wenduList.get(0).get("TEMP_SPIN_A") + "";

                    try {
                        if (temp_in_fr_a != null) {
                            Write_LED_IP_226_JinZha_WenDu(temp_in_fr_a);
                        }
                    } catch (Exception ex) {

                    }
                    try {
                        if (temp_spin_a != null) {
                            Write_LED_IP_226_TuSi_WenDu(temp_spin_a);
                        }
                    } catch (Exception ex) {

                    }
                }


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public String Write_LED_IP_228_ZhaPiHao(String str_ZhiJian_ZhaPiHao) throws Exception   //写质检位置LED的轧批号  外部用9003端口
    {
        if (str_ZhiJian_ZhaPiHao == null || str_ZhiJian_ZhaPiHao.equals(""))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String_228(str_ZhiJian_ZhaPiHao, "228", LED_Display_Type.ZhaPiHao);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "228", 9001);
            logger.info("方法【Write_LED_IP_228_ZhaPiHao】，参数【" + str_ZhiJian_ZhaPiHao + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";
        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "228" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_228_GangZhong(String str_ZhiJian_GangZhong) throws Exception  //写质检位置LED的钢种  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_ZhiJian_GangZhong))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String_228(str_ZhiJian_GangZhong, "228", LED_Display_Type.GangZhong);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "228", 9001);
            logger.info("方法【Write_LED_IP_228_GangZhong】，参数【" + str_ZhiJian_GangZhong + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "228" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_228_Gouhao(String str_ZhiJian_Gouhao) throws Exception  //写质检位置LED的钩号  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_ZhiJian_Gouhao))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String_228(str_ZhiJian_Gouhao, "228", LED_Display_Type.GouHao);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "228", 9001);
            logger.info("方法【Write_LED_IP_228_Gouhao】，参数【" + str_ZhiJian_Gouhao + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "228" + "有其他错误。。。。请检查";
    }


    public String Write_LED_IP_229_ZhaPiHao(String str_XieJuan_ZhaPiHao) throws Exception  //发货区域 写卸卷位置LED的轧批号  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_XieJuan_ZhaPiHao))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_XieJuan_ZhaPiHao, "229", LED_Display_Type.ZhaPiHao);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "229", 9002);
            logger.info("方法【Write_LED_IP_229_ZhaPiHao】，参数【" + str_XieJuan_ZhaPiHao + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "229" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_229_GangZhong(String str_XieJuan_GangZhong) throws Exception //发货区域 写卸卷位置LED的钢种  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_XieJuan_GangZhong))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_XieJuan_GangZhong, "229", LED_Display_Type.GangZhong);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "229", 9002);
            logger.info("方法【Write_LED_IP_229_GangZhong】，参数【" + str_XieJuan_GangZhong + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "229" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_229_Gouhao(String str_XieJuan_Gouhao) throws Exception  //发货区域 写卸卷位置LED的钩号  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_XieJuan_Gouhao))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_XieJuan_Gouhao, "229", LED_Display_Type.GouHao);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "229", 9002);
            logger.info("方法【Write_LED_IP_229_Gouhao】，参数【" + str_XieJuan_Gouhao + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "229" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_227_ZhaPiHao(String str_ShangJuan_ZhaPiHao) throws Exception  //3CS上卷区域位置LED的轧批号  外部用9003端口
    {
        //str_ShangJuan_ZhaPiHao="E9-06060090";
        if (ObjectUtil.isEmpty(str_ShangJuan_ZhaPiHao))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_ShangJuan_ZhaPiHao, "227", LED_Display_Type.ZhaPiHao);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "227", 9003);
            logger.info("方法【Write_LED_IP_227_ZhaPiHao】，参数【" + str_ShangJuan_ZhaPiHao + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "227" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_227_GangZhong(String str_ShangJuan_GangZhong) throws Exception  //3CS上卷区域位置LED的钢种  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_ShangJuan_GangZhong))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_ShangJuan_GangZhong, "227", LED_Display_Type.GangZhong);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "227", 9003);
            logger.info("方法【Write_LED_IP_227_GangZhong】，参数【" + str_ShangJuan_GangZhong + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "227" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_227_Gouhao(String str_ShangJuan_Gouhao) throws Exception //3CS上卷区域位置LED的钩号  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_ShangJuan_Gouhao))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_ShangJuan_Gouhao, "227", LED_Display_Type.GouHao);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "227", 9003);
            logger.info("方法【Write_LED_IP_227_Gouhao】，参数【" + str_ShangJuan_Gouhao + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "227" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_226_ZhaPiHao(String str_JingZha_ZhaPiHao) throws Exception  // 精轧区域位置LED的轧批号  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_JingZha_ZhaPiHao))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_JingZha_ZhaPiHao, "226", LED_Display_Type.ZhaPiHao);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "226", 9004);
            logger.info("方法【Write_LED_IP_226_ZhaPiHao】，参数【" + str_JingZha_ZhaPiHao + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "226" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_226_GangZhong(String str_JingZha_GangZhong) throws Exception // 精轧区域位置LED的钢种  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_JingZha_GangZhong))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_JingZha_GangZhong, "226", LED_Display_Type.GangZhong);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "226", 9004);
            logger.info("方法【Write_LED_IP_226_GangZhong】，参数【" + str_JingZha_GangZhong + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "226" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_226_GuiGe(String str_JingZha_GuiGe) throws Exception  // 精轧区域位置LED的规格  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_JingZha_GuiGe))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_JingZha_GuiGe, "226", LED_Display_Type.GuiGe);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "226", 9004);
            logger.info("方法【Write_LED_IP_226_GuiGe】，参数【" + str_JingZha_GuiGe + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "226" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_226_JiHuaZhiShu(String str_JingZha_JiHuaZhiShu) throws Exception  // 精轧区域位置LED的精轧支数  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_JingZha_JiHuaZhiShu))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_JingZha_JiHuaZhiShu, "226", LED_Display_Type.JiHuaZhiShu);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "226", 9004);
            logger.info("方法【Write_LED_IP_226_JiHuaZhiShu】，参数【" + str_JingZha_JiHuaZhiShu + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "226" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_226_ShengYuZhiShu(String str_JingZha_ShengYuZhiShu) throws Exception  // 精轧区域位置LED的剩余支数  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_JingZha_ShengYuZhiShu))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_JingZha_ShengYuZhiShu, "226", LED_Display_Type.ShengYuZhiShu);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "226", 9004);
            logger.info("方法【Write_LED_IP_226_ShengYuZhiShu】，参数【" + str_JingZha_ShengYuZhiShu + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "226" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_226_JinZha_WenDu(String str_JingZha_JinZha_WenDu) throws Exception // 精轧区域位置LED的精轧温度  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_JingZha_JinZha_WenDu))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_JingZha_JinZha_WenDu, "226", LED_Display_Type.JinZha_WenDu);
        String str_Send_Return = "";


        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "226", 9004);
            logger.info("方法【Write_LED_IP_226_JinZha_WenDu】，参数【" + str_JingZha_JinZha_WenDu + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "226" + "有其他错误。。。。请检查";
    }

    public String Write_LED_IP_226_TuSi_WenDu(String str_JingZha_TuSi_WenDu) throws Exception  // 精轧区域位置LED的吐丝温度  外部用9003端口
    {
        if (ObjectUtil.isEmpty(str_JingZha_TuSi_WenDu))
            return "01,error,不能发送空字符";
        String str_Send_Text = CLS_UDPAsyncClient.JieXi_String(str_JingZha_TuSi_WenDu, "226", LED_Display_Type.TuSi_WenDu);
        String str_Send_Return = "";

        if (str_Send_Text != "") {
            str_Send_Return = CLS_UDPAsyncClient.UDP_Client_Sent_String(str_Send_Text, "226", 9004);
            logger.info("方法【Write_LED_IP_226_TuSi_WenDu】，参数【" + str_JingZha_TuSi_WenDu + "】,发送信息【" + str_Send_Text + "】，返回值【" + str_Send_Return + "】");
        } else
            return "01,error,不能发送空字符";

        if (str_Send_Return == "OK")
            return "00,OK 写入成功！";
        else
            return "02,error,写LED：<" + "226" + "有其他错误。。。。请检查";
    }


    public static void main(String[] args) throws Exception {
        //C# 中  KLB0001000000570013000000E900700KLE5B
        //java   KLB0001000000570013000000E900700KLE5B
       String str_Send_Text = CLS_UDPAsyncClient.JieXi_String("E900700", "228", LED_Display_Type.ZhaPiHao);
//        //C# 中  KLB0001000000570009010000509KLE8A
//        //java   KLB0001000000570009010000509KLE8A
//        String str_Send_Text2 = CLS_UDPAsyncClient.JieXi_String("509", "228", LED_Display_Type.GangZhong);
//        //C# 中  KLB000100000057000802000012KLE4F
//        //java   KLB000100000057000802000012KLE4F
//        String str_Send_Text3 = CLS_UDPAsyncClient.JieXi_String("12", "228", LED_Display_Type.GouHao);
//        //C# 中  KLB0001000000570013020000HRB400EKLE9D
//        //java   KLB0001000000570013020000HRB400EKLE9D
//        String str_Send_Text4 = CLS_UDPAsyncClient.JieXi_String("HRB400E", "228", LED_Display_Type.GouHao);


//        System.out.println(str_Send_Text);
//        System.out.println(str_Send_Text2);
//        System.out.println(str_Send_Text3);
//        System.out.println(str_Send_Text4);
        LedWriteClient led = new LedWriteClient();
//        //C#                                                            KLB0001000000570014000000E9-07032KLE8E
//        //方法【Write_LED_IP_228_ZhaPiHao】，参数【E9-07032】,发送信息【KLB0001000000570014000000E9-07032KLE8E】，返回值【OK】
//        led.Write_LED_IP_228_ZhaPiHao("E9-07032");
//        //C#                                                           KLB0001000000570012010000HPB300KLE53
//        //方法【Write_LED_IP_228_GangZhong】，参数【HPB300】,发送信息【KLB0001000000570012010000HPB300KLE53】，返回值【OK】
//        led.Write_LED_IP_228_GangZhong("HPB300");
//        //C#                                                   KLB00010000005700070200002KLE1D
//        //方法【Write_LED_IP_228_Gouhao】，参数【2】,发送信息【KLB00010000005700070200002KLE1D】，返回值【OK】
//        led.Write_LED_IP_228_Gouhao("2");
        //C#                                                            KLB0001000000570014000000E9-07032KLE8E
        //方法【Write_LED_IP_228_ZhaPiHao】，参数【E9-07032】,发送信息【KLB0001000000570014000000E9-07032KLE8E】，返回值【OK】
        led.Write_LED_IP_227_ZhaPiHao("E9-07033010");
        //C#                                                           KLB0001000000570012010000HPB300KLE53
        //方法【Write_LED_IP_228_GangZhong】，参数【HPB300】,发送信息【KLB0001000000570012010000HPB300KLE53】，返回值【OK】
//        led.Write_LED_IP_227_GangZhong("HPB300");
//        //C#                                                   KLB00010000005700070200002KLE1D
//        //方法【Write_LED_IP_228_Gouhao】，参数【2】,发送信息【KLB00010000005700070200002KLE1D】，返回值【OK】
//        led.Write_LED_IP_227_Gouhao("55");
//
//        led.Write_LED_IP_226_TuSi_WenDu("334");

//        led.Write_LED_IP_226_ZhaPiHao("E9007000");
//        led.Write_LED_IP_226_JinZha_WenDu("56.2");
//        led.Write_LED_IP_226_TuSi_WenDu("334");
//        led.Write_LED_IP_226_GuiGe("8.1");
//        led.Write_LED_IP_226_GangZhong("HTG");
//        led.Write_LED_IP_226_JiHuaZhiShu("100");
//        led.Write_LED_IP_226_ShengYuZhiShu("13");
    }

}
