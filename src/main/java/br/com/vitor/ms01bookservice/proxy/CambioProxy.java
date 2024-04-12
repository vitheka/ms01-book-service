package br.com.vitor.ms01bookservice.proxy;


import br.com.vitor.ms01bookservice.response.CambioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms01-cambio-service", url = "localhost:8000")
public interface CambioProxy {

   @GetMapping("v1/cambio-service/{amount}/{from}/{to}")
   public CambioResponse getCambio(@PathVariable("amount") Double amount,
                                   @PathVariable("from") String from,
                                   @PathVariable("to") String to
   );
}
