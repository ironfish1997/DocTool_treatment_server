package top.liuliyong.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import top.liuliyong.common.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @Author liyong.liu
 * @Date 2019/3/12
 **/
@Repository
public class PatientUserDao extends AbstractUserDao<Patient> {

    @Override
    protected Class getEntityClass() {
        return Patient.class;
    }

    @Override
    protected String getCollection() {
        return "patients";
    }

    @Override
    public List delete(String... ids) {
        if (ids == null || ids.length == 0) {
            return null;
        }
        //删除病人记录
        List<ObjectId> ids_obj = new ArrayList<>();
        for (String id : ids) {
            ids_obj.add(new ObjectId(id));
        }
        List<Patient> patient = mongoTemplate.findAllAndRemove(query(where("_id").in(ids_obj)), getEntityClass());
        assert patient != null;
        return patient;
    }

    @Override
    public <S extends Patient> S update(S entity) {
        if (entity == null) {
            return null;
        }
        return mongoTemplate.findAndReplace(query(where("_id").is(new ObjectId(entity.getId()))), entity, getCollection());
    }


    /**
     * 根据病人id删除病人信息
     *
     * @param ids
     * @return
     */
    public List deletePatient(String... ids) {
        return delete(ids);
    }

    /**
     * 更新病人信息
     *
     * @param renewPatient
     * @return
     */
    public Patient updatePatient(Patient renewPatient) {
        return update(renewPatient);
    }

    /**
     * 根据id查询病人信息
     *
     * @param id
     * @return
     */
    public Patient findOnePatientById(String id) {
        Optional<Patient> resOpt = findById(id);
        return resOpt.orElse(null);
    }

    /**
     * 根据病人身份证号查询病人信息
     */
    public Patient findOnePatientByIdNumber(String id_number) {
        return (Patient) mongoTemplate.findOne(query(where("id_number").is(id_number)), getEntityClass(), getCollection());
    }


}
