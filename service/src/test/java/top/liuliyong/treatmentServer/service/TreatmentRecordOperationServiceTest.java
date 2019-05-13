package top.liuliyong.treatmentServer.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.liuliyong.treatmentServer.common.model.TreatmentRow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreatmentRecordOperationServiceTest {
    @Autowired
    private TreatmentRecordOperationService treatmentRecordOperationService;


    @Test
    public void addTreatmentRow() {
        TreatmentRow treatmentRow = new TreatmentRow(null, "感冒", "5ca32c2d0bee6b192178dc2e", 1554048000, 1554049000, "用了阿莫西林，感冒灵", null);
        TreatmentRow result = treatmentRecordOperationService.addTreatmentRow(treatmentRow);
        Assert.assertEquals(result, treatmentRow);
    }

    @Test
    public void updateTreatmentRow() {
        TreatmentRow treatmentRow = treatmentRecordOperationService.findTreatmentRowsByPatientIdNumber("5ca32c2d0bee6b192178dc2e").get(0);
        treatmentRow.setMedicines_record("修改了药物记录");
        TreatmentRow updatedRow = treatmentRecordOperationService.updateTreatmentRow(treatmentRow);
        Assert.assertEquals(updatedRow, treatmentRow);
    }

    @Test
    public void deleteTreatmentRow() {
    }

    @Test
    public void batchDeleteTreatmentRows() {
    }

    @Test
    public void findTreatmentRowsByPatientId() {
    }
}