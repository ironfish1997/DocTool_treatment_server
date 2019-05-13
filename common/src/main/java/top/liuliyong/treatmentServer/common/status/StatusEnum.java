package top.liuliyong.treatmentServer.common.status;

import lombok.Getter;

@Getter
public enum StatusEnum {
    LACK_OF_INFORMATION(-10030, "lack of information"),
    FROZEN_ACCOUNT(-10031, "invalid status"),
    NOT_ONLINE(-10032, "this account is not online"),
    DELETE_ACCOUNT_FAILED(-10033, "delete account failed"),
    ALREADY_REGISTED(-10034, "already registed"),
    WRONG_ACCOUNT_OR_PASSWORD(-10035, "wrong account_id or password"),
    SESSION_ID_OUTOFDATA(-10036, "session_id is out of date"),
    NO_AUTH(-10037, "you have no permission"),
    ADD_PATIENT_FAILED(-10038, "add patient failed"),
    UPDATE_PATIENT_FAILED(-10039, "update patient failed"),
    DELETE_PATIENT_FAILED(-10040, "delete patient failed"),
    NOT_FOUNT_PATINET(-10041, "patient not found"),
    FIND_ALL_PATIENT_FAILED(-10042, "find all patients failed"),
    FIND_TREATMENT_FAILED(-10043,"find treatment record failed"),
    ADD_TREATMENTRECORD_FAILED(-10044,"add treatment record failed"),
    UPDATE_TREATMENTRECORD_FAILED(-10045,"update treatment record failed"),
    DELETE_TREATMENTRECORD_FAILED(-10046,"delete treatment record failed"),
    FORMAT_ERROR(-10047,"format error"),
    WRONG_ID_FORMAT(-10048,"wrong id format"),
    ;

    private Integer code;
    private String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
