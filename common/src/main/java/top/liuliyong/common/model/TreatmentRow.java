package top.liuliyong.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 治疗记录
 *
 * @Author liyong.liu
 * @Date 2019/4/2
 **/
@Data
@Document(collection = "treatment_record")
public class TreatmentRow implements Serializable {
    @Id
    private String id;//id
    private String disease_name;//病症名称
    @Indexed
    private String patient_id;//病人id
    private long start_time;//就诊开始日期
    @Indexed
    private long end_time;//就诊结束日期
    private String medicines_record;//用药记录
    private Object extra_meta;//其他信息

    public TreatmentRow(String id, String disease_name, String patient_id, long start_time, long end_time, String medicines_record, Object extra_meta) {
        this.id = id;
        this.disease_name = disease_name;
        this.patient_id = patient_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.medicines_record = medicines_record;
        this.extra_meta = extra_meta;
    }

    public TreatmentRow() {
    }

    @Override
    public String toString() {
        return "TreatmentRow{" + "id='" + id + '\'' + ", disease_name='" + disease_name + '\'' + ", patient_id='" + patient_id + '\'' + ", start_time=" + start_time + ", end_time=" + end_time + ", medicines_record='" + medicines_record + '\'' + ", extra_meta=" + extra_meta + '}';
    }
}