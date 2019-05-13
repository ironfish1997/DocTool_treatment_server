package top.liuliyong.treatmentServer.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.liuliyong.treatmentServer.common.model.Patient;
import top.liuliyong.treatmentServer.common.model.builder.PatientBuilder;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientOperationServiceTest {
    @Autowired
    private PatientOperationService patientOperationService;

    @Test
    public void addPatient() {
        Patient patient = new PatientBuilder("123456789","wangwu", "china,hunan,changde,lixian", "18587391090").build();
        Patient patient1 = patientOperationService.addPatient(patient);
        Assert.assertEquals(patient1, patient);
    }

    @Test
    public void updatePatient() {
        Patient ori = patientOperationService.findPatientById("5ca319b50bee6b135adac3d7");
        ori.setArea("china,hunan,changde,wulin");
        Patient result = patientOperationService.updatePatient(ori);
        Assert.assertEquals(result, ori);
    }

    @Test
    public void deleteOnePatient() {
        Patient ori = patientOperationService.findPatientById("5ca319b50bee6b135adac3d7");
        Patient patient = patientOperationService.deleteOnePatient("5ca319b50bee6b135adac3d7");
        Assert.assertEquals(ori, patient);
    }

    @Test
    public void deleteManyPatients() {
        List<Patient> result = patientOperationService.deleteManyPatients("5ca31afc10dd3e5b545fd8e4", "5ca31a980bee6b13a7367dd3", "5ca31abf0bee6b13b436988e");
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void findPatientByPatientId() {
        Patient ori = patientOperationService.findPatientById("5ca319b50bee6b135adac3d7");
    }

    @Test
    public void findAll() {
        List<Patient> patients = patientOperationService.findAll();
        Assert.assertNotEquals(0, patients.size());
    }
}