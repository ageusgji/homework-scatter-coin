package com.ageusgji.kakaopay.coinScatter.util.enums;

import com.ageusgji.kakaopay.coinScatter.util.exception.BaseExceptionType;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public enum CoinScatterExceptionType implements BaseExceptionType {

    // TODO: throw exception!!! - 토큰 오래 만듬! 타임아웃!!
    TIMEOUT_GEN_TOKEN(1001, 200, "뿌리기에 시간이 너무 오래걸립니다.\n잠시 후 다시 시도해주세요."),
    // TODO: throw exception!!! - 해당 토큰이 없다!!
    NOT_FOUND_TOKEN(1011, 200, "요청한 뿌리기를 찾을 수 없습니다."),
    // TODO: throw exception!!! - 토큰이 너무 많다~!! 생성시 문제가 있는것으로 판단!!
    TOO_MANY_TOKEN_FATAL(1912, 200, "요청한 뿌리기를 구분할 수 없습니다."),
    // TODO: throw logical exception!!! - 뿌린사람이 또 받아?
    NOT_SCATTER_OWNER(1013, 200, "뿌리기한 사람은 받을 수 없습니다."),
    // TODO: throw logical exception!!! - 모든 사람이 다 받았다!!
    NO_COIN_LEFT(1014, 200, "남은 금액이 없습니다."),
    // TODO: throw logical exception!!! - 한번 받은사람이 또 받을 수 없습니다.
    NOT_RECEIVE_AGAIN(1015, 200, "이미 받았습니다."),
    // TODO: throw error exception!!! - 찾았는데 없음.
    NOT_FOUND_SCATTER(1021, 200, "해당 뿌리기를 찾을 수 없습니다."),

    NO_ARG_ROOM_ID(1101, 200, "대화방 식별자가 없습니다."),
    NO_ARG_HEAD_CNT(1102, 200, "받을 인원이 없습니다."),
    NO_ARG_USER_NO(1103, 200, "뿌리는 사람 값이 없습니다."),
    NO_ARG_COINS(1104, 200, "뿌리는 금액이 없습니다."),
    NO_ARG_TOKEN(1105, 200, "토큰값이 없습니다."),

    OTHER_EXCEPTION(0000, 200, "기타 오류");


    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    CoinScatterExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
