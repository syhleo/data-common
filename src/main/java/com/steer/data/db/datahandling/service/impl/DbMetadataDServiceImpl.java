package com.steer.data.db.datahandling.service.impl;

import com.steer.data.common.utils.ObjectUtil;
import com.steer.data.db.datahandling.mapper.DbMetadataDMapper;
import com.steer.data.db.datahandling.model.DbMetadataD;
import com.steer.data.db.datahandling.service.DbMetadataDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 数据库-元数据表明细信息，面向于整个Map移除、新增、交互数据 服务实现类
 * </p>
 *
 * @author syhleo
 * @since 2019-09-16
 */
@Service
public class DbMetadataDServiceImpl implements DbMetadataDService {

    @Autowired
    private DbMetadataDMapper dbMetadataDMapper;

    public Map<String, Object> changMapData(Map<String, Object> map,
                                            String db_relate) {
        if (ObjectUtil.isEmpty(map)) {
            return map;
        }
        if (ObjectUtil.isEmpty(db_relate)) {
            return map;
        }
        //对需要替换的字段和禁用的字段进行处理。
        List<DbMetadataD> dmdList = dbMetadataDMapper.findByDbrela(db_relate);
        if (ObjectUtil.isNotEmpty(dmdList)) {
            //遍历key-value
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (DbMetadataD dbMeta : dmdList) {
                    if (entry.getKey().equals(dbMeta.getSourceTableColumn())) {
                        String new_key = dbMeta.getTargetTableColumn();
                        Object value = entry.getValue();
                        if (dbMeta.getInvalidFlg() != null && "1".equals(dbMeta.getInvalidFlg())) {//禁用字段
                            map.remove(entry.getKey());
                        } else {//替换接口对于的字段名称，替换名称
                            map.remove(entry.getKey());
                            map.put(new_key, value);
                        }
                    }
                }
            }
        }
        return map;
    }

}
