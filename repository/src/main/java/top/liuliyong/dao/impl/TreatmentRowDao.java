package top.liuliyong.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.stereotype.Repository;
import top.liuliyong.common.model.TreatmentRow;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @Author liyong.liu
 * @Date 2019/4/1
 **/
@Repository
public class TreatmentRowDao extends AbstractUserDao<TreatmentRow> {
    @Override
    protected Class<TreatmentRow> getEntityClass() {
        return TreatmentRow.class;
    }

    @Override
    protected String getCollection() {
        return "treatment_record";
    }

    /**
     * 查找所有就诊记录
     *
     * @return
     */
    public List<TreatmentRow> findAllTreatRow() {
        return IterableConverter.toList(super.findAll());
    }

    /**
     * 通过就诊记录id删除就诊记录
     *
     * @param ids
     * @return
     */
    @Override
    public List<TreatmentRow> delete(String... ids) {
        //删除就诊记录
        return mongoTemplate.findAllAndRemove(query(where("id").in((Object[]) ids)), getEntityClass());
    }

    /**
     * 更新就诊记录
     *
     * @param entity
     * @param <S>
     * @return
     */
    @Override
    public <S extends TreatmentRow> S update(S entity) {
        mongoTemplate.findAndReplace(query(where("_id").is(new ObjectId(entity.getId()))), entity, getCollection());
        return entity;
    }

    /**
     * 根据就诊记录的id查询就诊记录信息
     *
     * @param id
     * @return
     */
    public TreatmentRow findTreatmentRowById(String id) {
        Optional<TreatmentRow> res_opt = findById(id);
        return res_opt.orElse(null);
    }

    /**
     * 根据病人id查询其所有就诊记录
     *
     * @param patientId
     * @return
     */
    public List<TreatmentRow> findTreatmentRowsByPatientId(String patientId) {
        return mongoTemplate.find(query(where("patient_id_number").is(patientId)), getEntityClass(), getCollection());
    }

    /**
     * 根据病人id删除其所有就诊记录
     *
     * @param patientId
     * @return
     */
    public List<TreatmentRow> deleteByPatientId(String patientId) {
        return mongoTemplate.findAllAndRemove(query(where("patient_id").is(patientId)), getEntityClass(), getCollection());
    }
}
