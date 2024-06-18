package io.github.hebertfsoares.ms_produtos.domain.infra.clients;

import io.github.hebertfsoares.ms_produtos.dto.DataClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "msclientes", path = "/clients")
public interface ClientResource {
    @GetMapping("/{cpf}")
    ResponseEntity<DataClient> getClientByCpf(@PathVariable String cpf);

}
