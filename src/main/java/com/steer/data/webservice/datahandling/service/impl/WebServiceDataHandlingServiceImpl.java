package com.steer.data.webservice.datahandling.service.impl;

import com.steer.data.common.utils.ObjectUtil;
import com.steer.data.common.utils.StringUtil;
import com.steer.data.db.datahandling.model.DataTransView;
import com.steer.data.db.datahandling.service.impl.DbDataHandlingServiceImpl;
import com.steer.data.webservice.client.ClientInvoke;
import com.steer.data.webservice.datahandling.mapper.WebServiceDataHandlingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WebServiceDataHandlingServiceImpl {

    @Autowired
    DbDataHandlingServiceImpl dbDataHandlingService;
    @Autowired
    WebServiceDataHandlingMapper webServiceDataHandlingMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String doWork(List<String> ws_ids) throws Exception {
        String str = null;
        //根据ws_ids查询
        for (String ws_id : ws_ids) {
            if (ws_id.equals("001")) {//表示读取RFID卡数据
                //wsdl的地址
                String wsdlUrl = "http://172.16.21.166:8730/RfidService?wsdl";
                //String wsdlUrl="http://localhost:8080/Ws-Servers/service/mesPlmL2Wgt?wsdl";
                //String wsdlUrl="http://172.16.81.200:8003/Ws-Servers/service/mesPlmL2Wgt?wsdl=MesPlmL2WgtService.wsdl";
                //调用的方法名称
                String methodName = "Get_RFID_Data_Json_String";
                //命名空间       刚进入wsdl看到的targetNamespace名称        切记仔细查看服务端wsdl中targetNamespace是否是/结尾
                String targetNamespace = "http://tempuri.org/";
                //参数集合
                List<Object> paramList = new ArrayList<>();
                try {
                    String reStr = ClientInvoke.dynamicCallWebServiceByCXF(wsdlUrl, targetNamespace, methodName, paramList);
                    List<HashMap<String, String>> list = StringUtil.jsonArrayStrToHashMapList(reStr);
                    for (HashMap<String, String> hashMap : list) {
                        if (ObjectUtil.isEmpty(hashMap.get("GouHao")) || "异常".equals(hashMap.get("GouHao"))
                                || "2345678".equals(hashMap.get("GouHao")) || Integer.valueOf(hashMap.get("RFID_Count")) < 50) {
                            logger.info("钩号：" + hashMap.get("GouHao") + "，或RFID_COUNT【" + hashMap.get("RFID_Count") + "】不入库，不予处理");
                            continue;
                        }
                        DataTransView dtv = new DataTransView();
                        dtv.setTarget_db("datasource_master");
                        if (hashMap.get("RFID_ID").equals("1")) {
                            List<String> strList = webServiceDataHandlingMapper.getGouHao("RFID_1_UPCOIL", 1);
                            if (ObjectUtil.isNotEmpty(strList) && strList.get(0).equals(hashMap.get("GouHao"))) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "和前一个钩号相等，不入库，不予处理");
                                continue;
                            }
                            List<String> gouhao5MinList = webServiceDataHandlingMapper.getGouhao5Min("RFID_1_UPCOIL", hashMap.get("GouHao"));
                            if (ObjectUtil.isNotEmpty(gouhao5MinList)) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "在5分钟以内已出现过，不入库，不予处理");
                                continue;
                            }
                            dtv.setTarget_table_name("RFID_1_UPCOIL");
                            hashMap.put("GOU_HAO", hashMap.get("GouHao"));
                            hashMap.remove("GouHao");
                            hashMap.put("RFID_DESC", hashMap.get("RFID_ChineseDescr"));
                            hashMap.remove("RFID_ChineseDescr");
                            hashMap.put("UPDATE_TIME", "to_date('" + hashMap.get("UpdateTime") + "','yyyy/MM/dd HH24:mi:ss')");
                            hashMap.remove("UpdateTime");
                            dbDataHandlingService.insertTableData2(hashMap, dtv);
                        } else if (hashMap.get("RFID_ID").equals("2")) {
                            List<String> strList = webServiceDataHandlingMapper.getGouHao("RFID_2_QCM", 1);
                            if (ObjectUtil.isNotEmpty(strList) && strList.get(0).equals(hashMap.get("GouHao"))) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "和前一个钩号相等，不入库，不予处理");
                                continue;
                            }
                            List<String> gouhao5MinList = webServiceDataHandlingMapper.getGouhao5Min("RFID_2_QCM", hashMap.get("GouHao"));
                            if (ObjectUtil.isNotEmpty(gouhao5MinList)) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "在5分钟以内已出现过，不入库，不予处理");
                                continue;
                            }
                            dtv.setTarget_table_name("RFID_2_QCM");
                            hashMap.put("GOU_HAO", hashMap.get("GouHao"));
                            hashMap.remove("GouHao");
                            hashMap.put("RFID_DESC", hashMap.get("RFID_ChineseDescr"));
                            hashMap.remove("RFID_ChineseDescr");
                            hashMap.put("UPDATE_TIME", "to_date('" + hashMap.get("UpdateTime") + "','yyyy/MM/dd HH24:mi:ss')");
                            hashMap.remove("UpdateTime");
                            dbDataHandlingService.insertTableData2(hashMap, dtv);
                        } else if (hashMap.get("RFID_ID").equals("3")) {
                            List<String> strList = webServiceDataHandlingMapper.getGouHao("RFID_3_WGT", 1);
                            if (ObjectUtil.isNotEmpty(strList) && strList.get(0).equals(hashMap.get("GouHao"))) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "和前一个钩号相等，不入库，不予处理");
                                continue;
                            }
                            List<String> gouhao5MinList = webServiceDataHandlingMapper.getGouhao5Min("RFID_3_WGT", hashMap.get("GouHao"));
                            if (ObjectUtil.isNotEmpty(gouhao5MinList)) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "在5分钟以内已出现过，不入库，不予处理");
                                continue;
                            }
                            dtv.setTarget_table_name("RFID_3_WGT");
                            hashMap.put("GOU_HAO", hashMap.get("GouHao"));
                            hashMap.remove("GouHao");
                            hashMap.put("RFID_DESC", hashMap.get("RFID_ChineseDescr"));
                            hashMap.remove("RFID_ChineseDescr");
                            hashMap.put("UPDATE_TIME", "to_date('" + hashMap.get("UpdateTime") + "','yyyy/MM/dd HH24:mi:ss')");
                            hashMap.remove("UpdateTime");
                            dbDataHandlingService.insertTableData2(hashMap, dtv);
                        } else if (hashMap.get("RFID_ID").equals("4")) {
                            List<String> strList = webServiceDataHandlingMapper.getGouHao("RFID_4_DOWNCOIL", 1);
                            if (ObjectUtil.isNotEmpty(strList) && strList.get(0).equals(hashMap.get("GouHao"))) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "和前一个钩号相等，不入库，不予处理");
                                continue;
                            }
                            List<String> gouhao5MinList = webServiceDataHandlingMapper.getGouhao5Min("RFID_4_DOWNCOIL", hashMap.get("GouHao"));
                            if (ObjectUtil.isNotEmpty(gouhao5MinList)) {
                                logger.info("钩号：" + hashMap.get("GouHao") + "在5分钟以内已出现过，不入库，不予处理");
                                continue;
                            }
                            dtv.setTarget_table_name("RFID_4_DOWNCOIL");
                            hashMap.put("GOU_HAO", hashMap.get("GouHao"));
                            hashMap.remove("GouHao");
                            hashMap.put("RFID_DESC", hashMap.get("RFID_ChineseDescr"));
                            hashMap.remove("RFID_ChineseDescr");
                            hashMap.put("UPDATE_TIME", "to_date('" + hashMap.get("UpdateTime") + "','yyyy/MM/dd HH24:mi:ss')");
                            hashMap.remove("UpdateTime");
                            dbDataHandlingService.insertTableData2(hashMap, dtv);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }
}
