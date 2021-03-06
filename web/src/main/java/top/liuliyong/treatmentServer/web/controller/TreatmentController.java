package top.liuliyong.treatmentServer.web.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liuliyong.treatmentServer.common.model.TreatmentRow;
import top.liuliyong.treatmentServer.common.response.OperationResponse;
import top.liuliyong.treatmentServer.common.status.StatusEnum;
import top.liuliyong.treatmentServer.service.TreatmentRecordOperationService;

import java.util.List;

/**
 * @Author liyong.liu
 * @Date 2019/4/8
 **/
@RestController
@RequestMapping("/treatment")
@Validated
@Api(value = "TreatmentOperation", description = "就诊记录操作")
@CrossOrigin
public class TreatmentController {
    @Autowired
    private TreatmentRecordOperationService treatmentRecordOperationService;

    /**
     * 根据treatment_id查找相应的就诊记录
     *
     * @param treatment_id
     * @return
     */
    @GetMapping
    public OperationResponse getTreatmentRecordByTreatmentId(@RequestParam("id") String treatment_id) {
        TreatmentRow res = treatmentRecordOperationService.findTreatmentRowByTreatmentRowId(treatment_id);
        return getOperationResponse(res, StatusEnum.FIND_TREATMENT_FAILED);
    }

    /**
     * 新增就诊记录
     *
     * @param treatmentRow
     * @return
     */
    @PostMapping
    public OperationResponse addTreatmentRecord(@RequestBody TreatmentRow treatmentRow) {
        TreatmentRow res = treatmentRecordOperationService.addTreatmentRow(treatmentRow);
        return getOperationResponse(res, StatusEnum.ADD_TREATMENTRECORD_FAILED);
    }

    /**
     * 修改就诊记录
     *
     * @param treatmentRow
     * @return
     */
    @PutMapping
    public OperationResponse updateTreatmentRecord(@RequestBody TreatmentRow treatmentRow) {
        TreatmentRow res = treatmentRecordOperationService.updateTreatmentRow(treatmentRow);
        return getOperationResponse(res, StatusEnum.UPDATE_TREATMENTRECORD_FAILED);
    }

    /**
     * 删除就诊记录
     *
     * @param treatment_id
     * @return
     */
    @DeleteMapping
    public OperationResponse deleteTreatmentRecord(@RequestParam("treatment_id") String treatment_id) {
        TreatmentRow res = treatmentRecordOperationService.deleteTreatmentRow(treatment_id);
        return getOperationResponse(res, StatusEnum.DELETE_TREATMENTRECORD_FAILED);
    }

    /**
     * 批量删除就诊记录
     *
     * @param treatment_ids
     * @return
     */
    @DeleteMapping("/batch")
    public OperationResponse batchDeleteTreatmentRecords(@RequestParam("Treatment_ids") String... treatment_ids) {
        List<TreatmentRow> res = treatmentRecordOperationService.batchDeleteTreatmentRows(treatment_ids);
        return getOperationResponse(res, StatusEnum.DELETE_TREATMENTRECORD_FAILED);
    }

    /**
     * 根据patient_id删除其所有就诊记录
     *
     * @param patient_id
     * @return
     */
    @DeleteMapping("/deleteByPatientId")
    public OperationResponse deleteTreatmentRecordsByPatientId(@RequestParam("patient_id") String patient_id) {
        List<TreatmentRow> res = treatmentRecordOperationService.deleteTreatmentRowsByPatientId(patient_id);
        return getOperationResponse(res, StatusEnum.DELETE_TREATMENTRECORD_FAILED);
    }

    /**
     * 通过身份证查询就诊记录
     *
     * @param patient_id_number
     * @return
     */
    @GetMapping("/getTreatmentsByPatientId")
    public OperationResponse getTreatmentsByPatientIdNumber(@RequestParam("patient_id_number") String patient_id_number) {
        List<TreatmentRow> res = treatmentRecordOperationService.findTreatmentRowsByPatientIdNumber(patient_id_number);
        return getOperationResponse(res, StatusEnum.FIND_TREATMENT_FAILED);
    }


    private OperationResponse getOperationResponse(Object res, StatusEnum treatmentrecordFailed) {
//        if (res == null) {
//            throw new OperationException(treatmentrecordFailed);
//        }
//        if (res instanceof List) {
//            if (((List) res).size() == 0) {
//                throw new OperationException(treatmentrecordFailed);
//            }
//        }
        return new OperationResponse(0, "ok", res);
    }
}
