package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Item;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginatedResponse {
    List<Item> items;
    Integer maxPages;
    Integer pageNum;
    Integer pageSize;
}
