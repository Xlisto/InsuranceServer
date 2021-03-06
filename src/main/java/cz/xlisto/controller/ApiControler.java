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

    /**
     * Přidá nového klienta
     * @param clientDTO
     * @return
     */
    @PostMapping("/client")
    public ClientDTO addClient(@RequestBody ClientDTO clientDTO) {
        return myClientService.saveClient(clientDTO);
    }

    /**
     * Přidá telefonní číslo ke klientovi
     * @param clientId
     * @param phoneDTOs
     * @return
     */
    @PostMapping("/phone/{clientId}")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public List<PhoneDTO> addPhone(@PathVariable(value = "clientId") Long clientId,
                                   @RequestBody List<PhoneDTO> phoneDTOs) {
        return myClientService.addPhone(phoneDTOs, clientId);
    }

    /**
     * Vrátí seznam klientů
     * @return
     */
    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        return myClientService.getAllClients();
    }

    /**
     * Vrátí stránkovaný seznam klientů
     * @param page
     * @param size
     * @param uriBuilder
     * @param response
     * @param clientFilter
     * @return
     */
    @GetMapping("/pageclients")
    public List<ClientDTO> getAllPageClients(@RequestParam int page, @RequestParam int size,
                                             UriComponentsBuilder uriBuilder, HttpServletResponse response, ClientFilter clientFilter) {
        List<ClientDTO> clientDTOs = myClientService.getPagesClients(page, size, clientFilter);
        response.addHeader("pages", String.valueOf(myClientService.getMaxPages()));
        return clientDTOs;
    }

    /**
     * Počet klientů
     * @return
     */
    @GetMapping("/countclients")
    public Long getCountClients() {
        return myClientService.getCountClients();
    }

    /**
     * Upraví telefonní číslo
     * @param phoneId
     * @param phoneDTO
     * @return
     */
    @PutMapping("/phone/{phoneId}")
    public PhoneDTO editPhone(@PathVariable(value = "phoneId") Long phoneId,
                              @RequestBody PhoneDTO phoneDTO) {
        return myClientService.editPhone(phoneDTO, phoneId);
    }

    /**
     * Smaže telefonní číslo
     * @param phoneId
     */
    @DeleteMapping("/phone/{phoneId}")
    public void deletePhone(@PathVariable("phoneId") Long phoneId) {
        myClientService.deletePhone(phoneId);
    }

    /**
     * Upraví klienta
     * @param clientId
     * @param clientDTO
     * @return
     */
    @PutMapping("/client/{clientId}")
    public ClientDTO editClient(@PathVariable("clientId") Long clientId, @RequestBody ClientDTO clientDTO) {
        return myClientService.editClient(clientDTO, clientId);
    }

    /**
     * Smaže klienta
     * @param clientId
     */
    @DeleteMapping("/client/{clientId}")
    public void deleteClient(@PathVariable("clientId") Long clientId) {
        myClientService.deleteClient(clientId);
    }

    /**
     * Vrátí klienta podle id
     * @param clientId
     * @return
     */
    @GetMapping("client/{clientId}")
    public ClientDTO getOneClient(@PathVariable("clientId") Long clientId) {
        return myClientService.getClient(clientId);
    }


    /**
     * Kategorie vozidel
     * @return
     */
    @GetMapping("/carcategories")
    public List<InsuranceCarCategoriesDTO> getCarCategories() {
        return myCarService.getCarCategories();
    }

    /**
     * Nová kategorie vozidel
     * @param insuranceCarCategoriesDTO
     * @return
     */
    @PostMapping("/carcategories")
    public InsuranceCarCategoriesDTO addCarCategories(@RequestBody InsuranceCarCategoriesDTO insuranceCarCategoriesDTO) {
        return myCarService.addInsuranceCarCategory(insuranceCarCategoriesDTO);
    }

    /**
     * Upraví kategorii vozidel
     * @param carCategoryId
     * @param insuranceCarCategoriesDTO
     * @return
     */
    @PutMapping("/carcategories/{carTypeId}")
    public InsuranceCarCategoriesDTO editCarCategory(@PathVariable("carTypeId") Long carCategoryId,
                                                     @RequestBody InsuranceCarCategoriesDTO insuranceCarCategoriesDTO) {
        InsuranceCarCategoriesDTO saved = myCarService.editCarCategory(insuranceCarCategoriesDTO, carCategoryId);
        return saved;
    }

    /**
     * Smaže kategorii vozidel
     * @param carCategoryId
     */
    @DeleteMapping("/carcategories/{carTypeId}")
    public void removeCarCategory(@PathVariable("carTypeId") Long carCategoryId) {
        myCarService.removeCarCategory(carCategoryId);
    }

    /**
     * Vloží nové pojištění vozidla
     * @param insuranceCarDTO
     */
    @PostMapping("/car")
    public void addInsuranceCar(@RequestBody InsuranceCarDTO insuranceCarDTO) {
        System.out.println("Date " + insuranceCarDTO.getStart());
        myCarService.addInsuranceCar(insuranceCarDTO);
    }

    /**
     * Upraví pojištění vozidla
     * @param carId
     * @param insuranceCarDTO
     */
    @PutMapping("/car/{carId}")
    public void editInsuranceCar(@PathVariable("carId") Long carId, @RequestBody InsuranceCarDTO insuranceCarDTO) {
        myCarService.editInsuranceCar(insuranceCarDTO, carId);
    }

    /**
     * Smaže pojištění vozidla
     * @param carId
     */
    @DeleteMapping("/car/{carId}")
    public void removeInsuranceCar(@PathVariable("carId") Long carId) {
        myCarService.removeInsuranceCar(carId);
    }

    /**
     * Celkový počet pojištění
     * @return
     */
    @GetMapping("/countinsurances")
    public Long getCountInsurances() {
        return myCarService.getCountInsurances();
    }

    /**
     * Seznam pojištění podle klienta
     * @param clientId
     * @return
     */
    @GetMapping("/insurancescar/{clientId}")
    public List<InsuranceCarDTO> getIncuranceCarByClient(@PathVariable("clientId") Long clientId) {
        return myCarService.getInsuranceCars(clientId);
    }

    /**
     * Cena pojištění podle obsahu válců
     * @param enginePower
     * @return
     */
    @GetMapping("/cost/{enginePower}")
    public InsuranceCarCategoriesDTO getPrice(@PathVariable("enginePower") Long enginePower) {
        return myCarService.getInsurancePrice(enginePower);
    }

    @GetMapping("demo")
    public void createDemoData() {
        myClientService.createDemoUsers();
    }

}
