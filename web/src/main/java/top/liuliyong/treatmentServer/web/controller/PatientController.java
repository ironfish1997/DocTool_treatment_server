package top.liuliyong.treatmentServer.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liuliyong.treatmentServer.common.exception.OperationException;
import top.liuliyong.treatmentServer.common.model.Patient;
import top.liuliyong.treatmentServer.common.response.OperationResponse;
import top.liuliyong.treatmentServer.common.status.StatusEnum;
import top.liuliyong.treatmentServer.service.PatientOperationService;

import java.util.List;

/**
 * @Author liyong.liu
 * @Date 2019/4/8
 **/
@RestController
@RequestMapping("/patient")
@Validated
@Api(value = "PatientOperation", description = "病人操作")
@CrossOrigin
public class PatientController {
    private final PatientOperationService patientOperationService;

    public PatientController(PatientOperationService patientOperationService) {
        this.patientOperationService = patientOperationService;
    }

    /**
     * 通过patient_id查询病人信息
     *
     * @param id_number
     * @return
     */
    @ApiOperation(value = "通过身份证号查询病人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "patient_id", value = "病人id（身份证号）", required = true, dataType = "string"),})
    @GetMapping("/findByIDNumber")
    public OperationResponse getPatientInfoByIDNumber(@RequestParam("patient_id") String id_number) {
        //查询mongo
        Patient patient = patientOperationService.findPatientByIdNumber(id_number);
        if (patient == null) {
            throw new OperationException(StatusEnum.NOT_FOUNT_PATINET);
        } else {
            OperationResponse response = new OperationResponse(0, "ok", patient);
            return response;
        }
    }


    /**
     * 通过id查询病人信息
     *
     * @param patient_id
     * @return
     */
    @ApiOperation(value = "通过id查询病人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "病人信息id", required = true, dataType = "string"),})
    @GetMapping
    public OperationResponse getPatientInfo(@RequestParam("id") String patient_id) {
        //查询mongo
        Patient patient = patientOperationService.findPatientById(patient_id);
        if (patient == null) {
            throw new OperationException(StatusEnum.NOT_FOUNT_PATINET);
        } else {
            OperationResponse response = new OperationResponse(0, "ok", patient);
            return response;
        }
    }

    /**
     * 新增病人信息
     *
     * @param patient
     * @return
     */
    @ApiOperation(value = "新增病人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "patient", value = "病人信息实体", required = true, dataType = "Patient"),})
    @PostMapping
    public OperationResponse newPatient(@RequestBody Patient patient) {
        Patient res = patientOperationService.addPatient(patient);
        if (res == null) {
            throw new OperationException(StatusEnum.ADD_PATIENT_FAILED);
        }
        return new OperationResponse(0, "ok", res);
    }

    /**
     * 修改病人信息
     *
     * @param patient
     * @return
     */
    @ApiOperation(value = "修改病人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "patient", value = "病人信息实体", required = true, dataType = "Patient"),})
    @PutMapping
    public OperationResponse updatePatient(@RequestBody Patient patient) {
        Patient res = patientOperationService.updatePatient(patient);
        if (res == null) {
            throw new OperationException(StatusEnum.UPDATE_PATIENT_FAILED);
        }
        return new OperationResponse(0, "ok", res);
    }

    /**
     * 删除单个病人
     *
     * @param patient_id
     * @return
     */
    @ApiOperation(value = "删除单个病人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "patient_id", value = "病人id", required = true, dataType = "string"),})
    @DeleteMapping
    public OperationResponse deleteSinglePatient(@RequestParam("patient_id") String patient_id) {
        Patient res = patientOperationService.deleteOnePatient(patient_id);
        if (res == null) {
            throw new OperationException(StatusEnum.DELETE_PATIENT_FAILED);
        }
        return new OperationResponse(0, "ok", res);
    }

    /**
     * 批量删除病人信息
     *
     * @param patient_ids
     * @return
     */
    @ApiOperation(value = "批量删除病人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "patient_ids", value = "病人id数组", required = true, dataType = "list"),})
    @DeleteMapping("/batch")
    public OperationResponse deleteBatchPatients(@RequestParam("patient_ids") String... patient_ids) {
        List<Patient> res = patientOperationService.deleteManyPatients(patient_ids);
        if (res == null || res.size() == 0) {
            throw new OperationException(StatusEnum.DELETE_PATIENT_FAILED);
        }
        return new OperationResponse(0, "ok", res);
    }

    /**
     * 获取所有病人信息
     *
     * @return
     */
    @ApiOperation(value = "获取所有病人信息")
    @GetMapping("/getAllPatients")
    public OperationResponse getAllPatients() {
        List<Patient> res = patientOperationService.findAll();
        if (res == null || res.size() == 0) {
            throw new OperationException(StatusEnum.FIND_ALL_PATIENT_FAILED);
        }
        return new OperationResponse(0, "ok", res);
    }
}
