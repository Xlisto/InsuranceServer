package cz.xlisto.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.xlisto.dto.*;
import cz.xlisto.entity.ClientFilter;
import cz.xlisto.services.MyCarService;
import cz.xlisto.services.MyClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiControler {

    @Autowired
    private MyClientService myClientService;

    @Autowired
    private MyCarService myCarService;

    @PostMapping("/client")
    public ClientDTO addClient(@RequestBody ClientDTO clientDTO) {
        return myClientService.saveClient(clientDTO);
    }

    @PostMapping("/phone/{clientId}")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public List<PhoneDTO> addPhone(@PathVariable(value = "clientId") Long clientId,
                         @RequestBody List<PhoneDTO> phoneDTOs) {
        return myClientService.addPhone(phoneDTOs, clientId);
    }

    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        return myClientService.getAllClients();
    }

    @GetMapping("/pageclients")
    public List<ClientDTO> getAllPageClients(@RequestParam int page, @RequestParam int size,
                                             UriComponentsBuilder uriBuilder, HttpServletResponse response, ClientFilter clientFilter) {
        List<ClientDTO> clientDTOs = myClientService.getPagesClients(page, size, clientFilter);
        response.addHeader("pages", String.valueOf(myClientService.getMaxPages()));
        return clientDTOs;
    }

    @GetMapping("/countclients")
    public Long getCountClients() {
        return myClientService.getCountClients();
    }

    @PutMapping("/phone/{phoneId}")
    public PhoneDTO editPhone(@PathVariable(value = "phoneId") Long phoneId,
                          @RequestBody PhoneDTO phoneDTO) {
        return myClientService.editPhone(phoneDTO, phoneId);
    }

    @DeleteMapping("/phone/{phoneId}")
    public void deletePhone(@PathVariable("phoneId") Long phoneId) {
        myClientService.deletePhone(phoneId);
    }

    @PutMapping("/client/{clientId}")
    public ClientDTO editClient(@PathVariable("clientId") Long clientId, @RequestBody ClientDTO clientDTO) {
        return myClientService.editClient(clientDTO, clientId);
    }

    @DeleteMapping("/client/{clientId}")
    public void deleteClient(@PathVariable("clientId") Long clientId) {
        myClientService.deleteClient(clientId);
    }




    @GetMapping("/carcategories")
    public List<InsuranceCarCategoriesDTO> getCarCategories() {
        return myCarService.getCarCategories();
    }

    @PostMapping("/carcategories")
    public InsuranceCarCategoriesDTO addCarCategories(@RequestBody InsuranceCarCategoriesDTO insuranceCarCategoriesDTO) {
        return myCarService.addInsuranceCarCategory(insuranceCarCategoriesDTO);
    }

    @PutMapping("/carcategories/{carTypeId}")
    public InsuranceCarCategoriesDTO editCarCategory(@PathVariable("carTypeId") Long carCategoryId,
                                @RequestBody InsuranceCarCategoriesDTO insuranceCarCategoriesDTO) {
        InsuranceCarCategoriesDTO saved = myCarService.editCarCategory(insuranceCarCategoriesDTO, carCategoryId);
        return saved;
    }

    @PostMapping("/car")
    public void addInsuranceCar(@RequestBody InsuranceCarDTO insuranceCarDTO) {
        System.out.println("Date "+insuranceCarDTO.getStart());
        myCarService.addInsuranceCar(insuranceCarDTO);
    }

    @PutMapping("/car/{carId}")
    public void editInsuranceCar(@PathVariable("carId") Long carId, @RequestBody InsuranceCarDTO insuranceCarDTO) {
        myCarService.editInsuranceCar(insuranceCarDTO, carId);
    }

    @DeleteMapping("/car/{carId}")
    public void removeInsuranceCar(@PathVariable("carId") Long carId) {
        myCarService.removeInsuranceCar(carId);
    }

    @GetMapping("/countinsurances")
    public Long getCountInsurances() {
        return myCarService.getCountInsurances();
    }

}
