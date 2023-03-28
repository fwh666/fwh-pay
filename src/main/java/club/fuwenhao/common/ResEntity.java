package club.fuwenhao.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ResEntity<T> implements Serializable {
    private static final long serialVersionUID = 89320237558946183L;
    private String code;
    private String message;
    private T data;

//    public RespEntity() {
//        this.code = ResponseCodeEnum.SUCCESS.getCode();
//        this.message = ResponseCodeEnum.SUCCESS.getMessage();
//    }
//
//    public RespEntity(ResponseCodeEnum responseCodeEnum) {
//        this.code = responseCodeEnum.getCode();
//        this.message = responseCodeEnum.getMessage();
//    }
//
//    public RespEntity(String code, String message) {
//        this.code = code;
//        this.message = message;
//    }

//    public static RespEntity success() {
//        return success((Object) null);
//    }
//
//    public static <T> RespEntity success(T data) {
//        RespEntity<T> result = new RespEntity();
//        result.setData(data);
//        return result;
//    }
//
//    public static <T> RespEntity success0(T data) {
//        RespEntity<T> result = new RespEntity(club.fuwenhao.enums.ResponseCodeEnum.SUCCESS0);
//        result.setData(data);
//        return result;
//    }
//
//    public static <T> RespEntity success0() {
//        RespEntity result = new RespEntity(club.fuwenhao.enums.ResponseCodeEnum.SUCCESS0);
//        return result;
//    }
//
//    public static RespEntity failure() {
//        return failure(club.fuwenhao.enums.ResponseCodeEnum.SERVER_ERROR.getCode(), club.fuwenhao.enums.ResponseCodeEnum.SERVER_ERROR.getMessage(), (Object) null);
//    }
//
//    public static RespEntity failure(club.fuwenhao.enums.ResponseCodeEnum responseCode) {
//        return failure(responseCode.getCode(), responseCode.getMessage(), (Object) null);
//    }
//
//    public static <T> RespEntity failure(club.fuwenhao.enums.ResponseCodeEnum responseCode, T data) {
//        return failure(responseCode.getCode(), responseCode.getMessage(), data);
//    }
//
//    public static <T> RespEntity failureparam(club.fuwenhao.enums.ResponseCodeEnum responseCode, String msg, T data) {
//        return failure(responseCode.getCode(), msg, data);
//    }
//
//    public static RespEntity failure(String code, String msg) {
//        return failure(code, msg, (Object) null);
//    }
//
//    public static <T> RespEntity failure(String code, String msg, T data) {
//        RespEntity<T> result = new RespEntity(code, msg);
//        result.setData(data);
//        return result;
//    }
//
//    public String getCode() {
//        return this.code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return this.message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return this.data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public boolean ifSuccess() {
//        return Arrays.asList(club.fuwenhao.enums.ResponseCodeEnum.SUCCESS0.getCode(), club.fuwenhao.enums.ResponseCodeEnum.SUCCESS.getCode()).contains(this.code);
//    }
}
