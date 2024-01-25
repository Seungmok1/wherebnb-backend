package goorm.wherebnb.domain.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PagedResponse <T>{
    private List<T> content;
    private int page;
    private int size;
    private boolean hasNext;

    public PagedResponse(List<T> content, int page, int size, boolean hasNext) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
    }
}
