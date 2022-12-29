package com.mdsujan.restPostgres.response;

import com.mdsujan.restPostgres.entity.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginatedResponse {
    List<Item> items;
    Long maxPages;
    Integer pageNum;
    Integer pageSize;
}
