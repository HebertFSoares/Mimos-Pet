package io.github.hebertfsoares.ms_produtos.domain.entities;

import io.github.hebertfsoares.ms_produtos.dto.DataClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clients {
    private DataClient client;
}
