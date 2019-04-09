package top.liuliyong.common.model.builder;


import top.liuliyong.common.exception.OperationException;
import top.liuliyong.common.model.Contacts;
import top.liuliyong.common.model.Patient;
import top.liuliyong.common.status.StatusEnum;

/**
 * @Author liyong.liu
 * @Date 2019/3/12
 **/
public class PatientBuilder {
    private String id;
    private String name;
    private String area;
    private Contacts contacts;
    private Object extra_meta;
    private String id_number;


    public PatientBuilder(String id_number, String name, String area, String phone_number) {
        this.id_number = id_number;
        this.name = name;
        this.area = area;
        this.setContacts(null, null, phone_number, null);
    }


    public Patient build() {
        if (name == null || id_number == null || contacts == null || contacts.getPhone_number() == null) {
            throw new OperationException(StatusEnum.LACK_OF_INFORMATION);
        }
        return new Patient(null, id_number, null, name, area, contacts, extra_meta);
    }

    public String getName() {
        return name;
    }

    public PatientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String getArea() {
        return area;
    }

    public PatientBuilder setArea(String area) {
        this.area = area;
        return this;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public PatientBuilder setContacts(String wechat, String qq, String phone_number, String email) {
        Contacts contacts = new Contacts(wechat, qq, phone_number, email);
        this.contacts = contacts;
        return this;
    }


    public Object getExtra_meta() {
        return extra_meta;
    }

    public PatientBuilder setExtra_meta(Object extra_meta) {
        this.extra_meta = extra_meta;
        return this;
    }
}
