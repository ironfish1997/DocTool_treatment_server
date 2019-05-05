package top.liuliyong.dao;

import top.liuliyong.common.model.Patient;

import java.util.List;

/**
 * @Author liyong.liu
 * @Date 2019-05-05
 **/
public interface PatientDao extends IMongoService<Patient> {
    Patient findPatientById(String id);

    /**
     * 更新病人信息
     */
    Patient updatePatient(Patient renewPatient);

    /**
     * 根据病人id删除病人信息
     */
    List deleteByPatientId(String... ids);

    /**
     * 根据病人身份证号查询病人信息
     */
    Patient findPatientByIdNumber(String id_number);
}
