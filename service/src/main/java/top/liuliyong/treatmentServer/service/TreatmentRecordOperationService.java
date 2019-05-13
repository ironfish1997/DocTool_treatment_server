package top.liuliyong.treatmentServer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.liuliyong.treatmentServer.common.model.TreatmentRow;
import top.liuliyong.treatmentServer.common.util.NumberSystemUtil;
import top.liuliyong.treatmentServer.repository.dao.impl.TreatmentDaoImpl;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author liyong.liu
 * @Date 2019/4/2
 **/
@Service
@Slf4j
public class TreatmentRecordOperationService {

    private final TreatmentDaoImpl treatmentRowDao;

    @Autowired
    private PatientOperationService tempPatientService;

    @Autowired
    public TreatmentRecordOperationService(TreatmentDaoImpl treatmentRowDao) {
        this.treatmentRowDao = treatmentRowDao;
    }

    /**
     * 新增就诊记录
     *
     * @param treatmentRow
     * @return
     */
    public TreatmentRow addTreatmentRow(TreatmentRow treatmentRow) {
        //1.判断就诊记录是否合法
        if (treatmentRow == null) {
            return null;
        }
        if (treatmentRow.getId() != null && !NumberSystemUtil.checkHex(treatmentRow.getId(), NumberSystemUtil.MONGO_ID_FORMAT)) {
            return null;
        }
        if (treatmentRow.getDisease_name() == null) {
            return null;
        }
        //如果当前病人记录里没有这个病人id，不插入该就诊记录
        String patient_id_number = treatmentRow.getPatient_id_number();
        if (tempPatientService.findPatientByIdNumber(patient_id_number) == null) {
            return null;
        }
        //计算时间
        {
            long startTime = treatmentRow.getStart_time();
            long endTime = treatmentRow.getEnd_time();
            try {
                Timestamp start_timestamp = new Timestamp(startTime);
                Timestamp end_timestamp = new Timestamp(endTime);
                Timestamp now_time = new Timestamp(System.currentTimeMillis());
                if (end_timestamp.before(start_timestamp) || start_timestamp.after(now_time)) {
                    return null;
                }
            } catch (Exception e) {
                log.warn("时间格式有误：", e);
                return null;
            }
        }
        return treatmentRowDao.save(treatmentRow);
    }

    /**
     * 更新就诊记录
     *
     * @param updateTreatmentRow
     * @return
     */
    public TreatmentRow updateTreatmentRow(TreatmentRow updateTreatmentRow) {
        TreatmentRow ori_treatmentRow = treatmentRowDao.findRowById(updateTreatmentRow.getId());
        if (ori_treatmentRow == null) {
            return null;
        }
        if (updateTreatmentRow.getPatient_id_number() != null) {
            ori_treatmentRow.setPatient_id_number(updateTreatmentRow.getPatient_id_number());
        }
        if (updateTreatmentRow.getDisease_name() != null) {
            ori_treatmentRow.setDisease_name(updateTreatmentRow.getDisease_name());
        }
        if (updateTreatmentRow.getStart_time() != 0) {
            ori_treatmentRow.setStart_time(updateTreatmentRow.getStart_time());
        }
        if (updateTreatmentRow.getEnd_time() != 0) {
            ori_treatmentRow.setEnd_time(updateTreatmentRow.getEnd_time());
        }
        if (updateTreatmentRow.getMedicines_record() != null) {
            ori_treatmentRow.setMedicines_record(updateTreatmentRow.getMedicines_record());
        }
        if (updateTreatmentRow.getExtra_meta() != null) {
            ori_treatmentRow.setExtra_meta(updateTreatmentRow.getExtra_meta());
        }
        return treatmentRowDao.update(ori_treatmentRow);
    }

    /**
     * 根据就诊记录id删除就诊记录信息
     *
     * @param targetTreatmentId
     * @return
     */
    public TreatmentRow deleteTreatmentRow(String targetTreatmentId) {
        if (targetTreatmentId == null || targetTreatmentId.trim().length() == 0) {
            return null;
        }
        if (!NumberSystemUtil.checkHex(targetTreatmentId, NumberSystemUtil.MONGO_ID_FORMAT)) {
            return null;
        }
        List<TreatmentRow> treatmentRows = treatmentRowDao.delete(targetTreatmentId);
        if (treatmentRows == null || treatmentRows.size() == 0) {
            return null;
        }
        return treatmentRows.get(0);
    }

    /**
     * 根据就诊记录id批量删除就诊记录
     *
     * @param targetTreatmentIds
     * @return
     */
    public List<TreatmentRow> batchDeleteTreatmentRows(String... targetTreatmentIds) {
        if (targetTreatmentIds == null || targetTreatmentIds.length == 0) {
            return null;
        }
        for (String id : targetTreatmentIds) {
            if (!NumberSystemUtil.checkHex(id, NumberSystemUtil.MONGO_ID_FORMAT)) {
                return null;
            }
        }
        return treatmentRowDao.delete(targetTreatmentIds);
    }

    /**
     * 根据patientId查询该病人的所有就诊记录
     *
     * @param patientId
     * @return
     */
    public List<TreatmentRow> findTreatmentRowsByPatientIdNumber(String patientId) {
        if (patientId == null || patientId.trim().length() == 0) {
            return null;
        }
        return treatmentRowDao.findRowsByPatientId(patientId);
    }

    /**
     * 根据treatmentId查询单个就诊记录
     *
     * @param treatmentId
     * @return
     */
    public TreatmentRow findTreatmentRowByTreatmentRowId(String treatmentId) {
        if (treatmentId == null) {
            return null;
        }
        if (!NumberSystemUtil.checkHex(treatmentId, NumberSystemUtil.MONGO_ID_FORMAT)) {
            return null;
        }
        return treatmentRowDao.findById(treatmentId).orElse(null);
    }

    /**
     * 根据patient_id删除该病人所有就诊记录
     */
    public List<TreatmentRow> deleteTreatmentRowsByPatientId(String patientId) {
        if (patientId == null || patientId.trim().length() == 0) {
            return null;
        }
        return treatmentRowDao.deleteByPatientId(patientId);
    }
}
