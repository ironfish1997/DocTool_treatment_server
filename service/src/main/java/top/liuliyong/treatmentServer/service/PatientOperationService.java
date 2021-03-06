package top.liuliyong.treatmentServer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.liuliyong.treatmentServer.common.exception.OperationException;
import top.liuliyong.treatmentServer.common.model.Patient;
import top.liuliyong.treatmentServer.common.status.StatusEnum;
import top.liuliyong.treatmentServer.common.util.NumberSystemUtil;
import top.liuliyong.treatmentServer.repository.dao.PatientDao;
import top.liuliyong.treatmentServer.repository.dao.TreatmentDao;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liyong.liu
 * @Date 2019/4/1
 **/
@Service
@Slf4j
public class PatientOperationService {
    @Resource(name = "patientDaoImpl")
    private PatientDao patientDao;
    @Resource(name = "treatmentDaoImpl")
    private TreatmentDao treatmentRowDao;


    /**
     * 新增病人
     *
     * @param patient
     * @return
     */
    public Patient addPatient(Patient patient) {
        if (patient == null || patient.getId_number() == null) {
            log.warn("新增病人请求：接收到的病人信息不完整");
            throw new OperationException(StatusEnum.LACK_OF_INFORMATION);
        }
        //可传入满足mongo要求的id，也可传入null让mongo自己生成
        if (patient.getId() != null && !NumberSystemUtil.checkHex(patient.getId(), NumberSystemUtil.MONGO_ID_FORMAT)) {
            log.warn("新增病人请求：接收到的病人id格式不正确");
            throw new OperationException(StatusEnum.WRONG_ID_FORMAT);
        }
        //通过身份证号检索当前数据库中有没有已存在的病人
        Patient oriPatient = patientDao.findPatientByIdNumber(patient.getId_number());
        if (oriPatient == null) {
            Patient resPat = patientDao.<Patient>save(patient);
            log.info("新增病人请求：成功新增病人记录===>", resPat);
            return resPat;
        } else {
            log.warn("新增病人请求：该病人已经存在===>", oriPatient);
            throw new OperationException(StatusEnum.ALREADY_REGISTED);
        }
    }

    /**
     * 更新病人信息
     *
     * @param updatedInfo
     * @return
     */
    public Patient updatePatient(Patient updatedInfo) {
        if (updatedInfo == null) {
            log.warn("更新病人请求：接收到的病人信息为空");
            throw new OperationException(StatusEnum.LACK_OF_INFORMATION);
        }
        //查出病人的原始信息
        Patient oriPatient = patientDao.findPatientById(updatedInfo.getId());
        if (oriPatient == null) {
            log.warn("更新病人请求：找不到病人信息,id===>", updatedInfo.getId());
            //找不到病人信息
            throw new OperationException(StatusEnum.NOT_FOUNT_PATINET);
        }
        //更新病人信息
        if (updatedInfo.getSpecial_disease() != null && updatedInfo.getSpecial_disease().length != 0) {
            oriPatient.setSpecial_disease(updatedInfo.getSpecial_disease());
        }
        //不更新就诊记录，若需要更新就诊记录请call TreatmentOperationService
//        if (updatedInfo.getTreatment_record() != null && updatedInfo.getTreatment_record().size() != 0) {
//            oriPatient.setTreatment_record(updatedInfo.getTreatment_record());
//        }
        if (updatedInfo.getArea() != null && updatedInfo.getArea().trim().length() != 0) {
            oriPatient.setArea(updatedInfo.getArea());
        }
        if (updatedInfo.getContacts() != null) {
            oriPatient.setContacts(updatedInfo.getContacts());
        }
        if (updatedInfo.getName() != null && updatedInfo.getName().trim().length() != 0) {
            oriPatient.setName(updatedInfo.getName());
        }
        patientDao.updatePatient(oriPatient);
        Patient resPat = patientDao.findPatientById(oriPatient.getId());
        log.info("更新病人记录请求：成功更新病人记录===>", resPat);
        return resPat;
    }

    /**
     * 通过id删除病人记录
     *
     * @param id
     * @return
     */
    public Patient deleteOnePatient(String id) {
        if (!NumberSystemUtil.checkHex(id, NumberSystemUtil.MONGO_ID_FORMAT)) {
            return null;
        }
        return deleteManyPatients(id).get(0);
    }

    /**
     * 通过ids批量删除病人记录
     *
     * @param ids
     * @return
     */
    public List<Patient> deleteManyPatients(String... ids) {
        if (ids == null || ids.length == 0) {
            throw new OperationException(StatusEnum.LACK_OF_INFORMATION);
        }
        for (String id : ids) {
            if (!NumberSystemUtil.checkHex(id, NumberSystemUtil.MONGO_ID_FORMAT)) {
                return null;
            }
        }
        List<Patient> patientList = patientDao.deleteByPatientId(ids);
        if (patientList == null || patientList.size() == 0) {
            throw new OperationException(StatusEnum.NOT_FOUNT_PATINET);
        }
        //级联删除该病人的就诊记录
        treatmentRowDao.delete(ids);
        return patientList;
    }

    /**
     * 通过id查找病人记录
     *
     * @param id
     * @return
     */
    public Patient findPatientById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        if (!NumberSystemUtil.checkHex(id, NumberSystemUtil.MONGO_ID_FORMAT)) {
            return null;
        }
        Patient resPat = patientDao.findPatientById(id);
        if (resPat == null) {
            return null;
        }
        return resPat;
    }

    /**
     * 通过病人身份证号查找病人记录
     *
     * @param id_number
     * @return
     */
    public Patient findPatientByIdNumber(String id_number) {
        if (StringUtils.isEmpty(id_number)) {
            return null;
        }
        Patient resPat = patientDao.findPatientByIdNumber(id_number);
        if (resPat == null) {
            return null;
        }
        return resPat;
    }


    /**
     * 查询所有病人信息
     *
     * @return
     */
    public List<Patient> findAll() {
        Iterable patientList = patientDao.findAll();
        List<Patient> result = null;
        if (patientList instanceof List) {
            result = (List<Patient>) patientList;
        }
        if (result == null || result.size() == 0) {
            throw new OperationException(StatusEnum.NOT_FOUNT_PATINET);
        }
        return result;
    }

}
