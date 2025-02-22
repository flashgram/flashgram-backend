package com.app.flashgram.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 페이지네이션 처리를 위한 기본 클래스
 * 페이지 번호와 페이지당 항목 수를 관리하며, 데이터베이스 쿼리에 필요한 offset과 limit 값 계산
 */
@Getter
@Setter
public class Pageable {

    private int pageIndex;
    private int pageSize;

    public Pageable() {
        this.pageIndex = 1;
        this.pageSize = 10;
    }

    public Pageable(int pageIndex, int pageSize) {
        if (pageIndex <1) {
            throw new IllegalArgumentException("페이지 인덱스는 1 이상이어야 합니다.");
        }

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (pageIndex -1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}
