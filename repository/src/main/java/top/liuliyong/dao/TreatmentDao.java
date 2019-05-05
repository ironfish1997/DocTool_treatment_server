package top.liuliyong.dao;

import top.liuliyong.common.model.TreatmentRow;

import java.util.List;

/**
 * @Author liyong.liu
 * @Date 2019-05-05
 **/
public interface TreatmentDao extends IMongoService<TreatmentRow> {

    /**
     * 根据就诊记录的id查询就诊记录信息
     *
     * @param id
     * @return
     */
    TreatmentRow findRowById(String id);

    /**
     * 根据病人id查询其所有就诊记录
     *
     * @param patientId
     * @return
     */
    List<TreatmentRow> findRowsByPatientId(String... patientId);

    /**
     * 根据病人id删除其所有就诊记录
     *
     * @param patientId
     * @return
     */
    List<TreatmentRow> deleteByPatientId(String... patientId);
}
