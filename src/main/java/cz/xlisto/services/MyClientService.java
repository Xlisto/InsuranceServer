package cz.xlisto.services;

import cz.xlisto.dto.ClientDTO;
import cz.xlisto.dto.InsuranceCarCategoriesDTO;
import cz.xlisto.dto.InsuranceCarDTO;
import cz.xlisto.dto.PhoneDTO;
import cz.xlisto.dto.mapper.*;
import cz.xlisto.entity.*;
import cz.xlisto.entity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MyClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PageableClientRepository pageableClientRepository;

    @Autowired
    private AdressRepository addressRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private MyCarService myCarService;

    private int maxPages;

    /**
     * Přidá nového klienta s adresou. Pokud jsou vyplněna telefonní čísla, také je přidá
     *
     * @param clientDTO
     */
    public ClientDTO saveClient(ClientDTO clientDTO) {
        ClientEntity clientEntity = clientMapper.toEntity(clientDTO);
        ClientEntity saved = clientRepository.save(clientEntity);
        for (PhoneDTO phoneDTO : clientDTO.getPhones()) {
            if (!phoneDTO.getPhoneNumber().isEmpty()) {
                List<PhoneDTO> phoneDTOs = new ArrayList<>();
                phoneDTOs.add(phoneDTO);
                addPhone(phoneDTOs, saved.getId());
            }
        }
        System.out.println(clientMapper.toDTO(saved));
        return clientMapper.toDTO(saved);
    }

    /**
     * Upraví klienta, jeho adresu, telefonné čísla
     *
     * @param clientDTO
     * @param clientId
     * @return
     */
    public ClientDTO editClient(ClientDTO clientDTO, Long clientId) {
        clientDTO.setClientId(clientId);
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(clientId);
        Long addressId = clientEntityOptional.get().getId();
        ClientEntity clientEntity = clientMapper.toEntity(clientDTO);
        clientEntity.getAddress().setId(addressId);
        System.out.println(clientEntity.getAddress().getId());
        System.out.println(clientDTO.getPhones());
        for (PhoneDTO phoneDTO : clientDTO.getPhones()) {
            editPhone(phoneDTO, phoneDTO.getPhoneId());
            System.out.println(phoneDTO + " " + phoneDTO.getPhoneId());
        }
        ClientEntity saved = clientRepository.save(clientEntity);
        return clientMapper.toDTO(saved);
    }

    /**
     * Přidá ke klientovy telefonní čísla
     *
     * @param phoneDTOs
     * @param id
     */
    public List<PhoneDTO> addPhone(List<PhoneDTO> phoneDTOs, Long id) {
        List<PhoneDTO> savedPhones = new ArrayList<>();
        for (PhoneDTO phoneDTO : phoneDTOs) {
            if (!phoneDTO.getPhoneNumber().isEmpty()) {
                phoneDTO.setClientId(id);
                PhoneEntity phoneEntity = phoneMapper.toEntity(phoneDTO);
                PhoneEntity saved = phoneRepository.save(phoneEntity);
                savedPhones.add(phoneMapper.toDTO(saved));
            }
        }
        return savedPhones;
    }

    /**
     * Zobrazí všechny klienty s adresou a telefonními čísly
     *
     * @return
     */
    public List<ClientDTO> getAllClients() {
        return convertClientToDTO(clientRepository.findAll());
    }

    /**
     * zobrazí jednoho klienta
     *
     * @param clientId
     * @return
     */
    public ClientDTO getClient(Long clientId) {
        return clientMapper.toDTO(clientRepository.getOne(clientId));
    }

    /**
     * Zobrazí všechny klienty s adresou a telefonními čísly. Výpis rozdělí na stránky a použije filtr
     *
     * @param page
     * @param size
     * @param clientFilter
     * @return
     */
    public List<ClientDTO> getPagesClients(int page, int size, ClientFilter clientFilter) {
        Page<ClientEntity> clientsPages = pageableClientRepository.findFilteredAll(clientFilter, PageRequest.of(page, size, Sort.by("lastName")));
        maxPages = clientsPages.getTotalPages();
        List<ClientEntity> clientsEntity = clientsPages.getContent();
        return convertClientToDTO(clientsEntity);
    }

    /**
     * Převede List ClientEntity na List ClientDTO
     *
     * @param clientsEntity
     * @return
     */
    private List<ClientDTO> convertClientToDTO(List<ClientEntity> clientsEntity) {
        List<ClientDTO> result = new ArrayList<>();
        for (ClientEntity client : clientsEntity) {
            List<PhoneDTO> phoneDTOs = new ArrayList<>();
            List<PhoneEntity> phoneEntityes = phoneRepository.findAllByClientId(client.getId());
            for (PhoneEntity phoneEntity : phoneEntityes) {
                phoneDTOs.add(phoneMapper.toDTO(phoneEntity));
            }
            ClientDTO clientDTO = clientMapper.toDTO(client);
            clientDTO.setPhones(phoneDTOs);
            result.add(clientDTO);
        }
        return result;
    }

    /**
     * Upraví jedno telefonní číslo
     *
     * @param phoneDTO
     * @param phoneId
     * @return
     */
    public PhoneDTO editPhone(PhoneDTO phoneDTO, Long phoneId) {
        phoneDTO.setPhoneId(phoneId);
        PhoneEntity phoneEntity = phoneMapper.toEntity(phoneDTO);
        PhoneEntity saved = phoneRepository.save(phoneEntity);
        return phoneMapper.toDTO(saved);
    }

    /**
     * Smaže jedno telefonní číslo
     *
     * @param phoneId
     */
    public void deletePhone(Long phoneId) {
        phoneRepository.deleteById(phoneId);
    }

    /**
     * Smaže klienta, včetne všech jeho telefonních čísel
     *
     * @param clientId
     */
    public void deleteClient(Long clientId) {
        phoneRepository.deleteByClientId(clientId);
        clientRepository.deleteById(clientId);
        System.out.println(clientId);
    }

    /**
     * Maximální počet stránek
     *
     * @return
     */
    public int getMaxPages() {
        return maxPages;
    }

    /**
     * Počet klientů
     *
     * @return
     */
    public Long getCountClients() {
        return pageableClientRepository.count();
    }

    public void createDemoUsers() {

        List<InsuranceCarCategoriesDTO> insuranceCarCategoriesDTOS = new ArrayList<>();
        insuranceCarCategoriesDTOS.add(new InsuranceCarCategoriesDTO(null, 0L, 893L));
        insuranceCarCategoriesDTOS.add(new InsuranceCarCategoriesDTO(null, 1000L, 1156L));
        insuranceCarCategoriesDTOS.add(new InsuranceCarCategoriesDTO(null, 1400L, 1488L));
        insuranceCarCategoriesDTOS.add(new InsuranceCarCategoriesDTO(null, 1970L, 2569L));
        insuranceCarCategoriesDTOS.add(new InsuranceCarCategoriesDTO(null, 2500L, 3698L));
        insuranceCarCategoriesDTOS.add(new InsuranceCarCategoriesDTO(null, 3000L, 5498L));

        for (InsuranceCarCategoriesDTO insuranceCarCategoriesDTO : insuranceCarCategoriesDTOS) {
            myCarService.addInsuranceCarCategory(insuranceCarCategoriesDTO);
        }

        List<ClientDTO> clientDTOS = new ArrayList<>();
        clientDTOS.add(new ClientDTO(null, "Petr", "Vomáčka", "801112/1234", "Kaštanová",
                "25", "", "30556", "Praha", Arrays.asList(
                new PhoneDTO(null, "+420606909333", null),
                new PhoneDTO(null, "+420606909333", null)
        )));

        clientDTOS.add(new ClientDTO(null, "Jiří", "Králíček", "540125/756", "Růžová",
                "675", "2", "39000", "Brno", Arrays.asList(
                new PhoneDTO(null, "+420603505369", null),
                new PhoneDTO(null, "+420733456987", null)
        )));

        clientDTOS.add(new ClientDTO(null, "Jan", "Kvasnička", "910512/5465", "Lipová",
                "675", "", "42100", "Ostrava", Arrays.asList(
                new PhoneDTO(null, "+420724567913", null),
                new PhoneDTO(null, "+420724567914", null),
                new PhoneDTO(null, "+420909102030", null)

        )));

        clientDTOS.add(new ClientDTO(null, "Jiřina", "Beranová", "915512/1567", "Květnatá",
                "33", "", "11200", "Plzeň", Arrays.asList(
                new PhoneDTO(null, "+420721258951", null)
        )));

        for (int i = 0; i < clientDTOS.size(); i++) {
            clientDTOS.set(i, saveClient(clientDTOS.get(i)));
        }

        List<InsuranceCarDTO> insuranceCarDTOS = new ArrayList<>();

        insuranceCarDTOS.add(new InsuranceCarDTO(null, new Date(), "1A2 4569", "Škoda", 1899L, clientDTOS.get(0).getClientId()));
        insuranceCarDTOS.add(new InsuranceCarDTO(null, new Date(), "1C8 1559", "Ford", 1499L, clientDTOS.get(1).getClientId()));
        insuranceCarDTOS.add(new InsuranceCarDTO(null, new Date(), "8S8 1736", "Trabant", 799L, clientDTOS.get(1).getClientId()));

        for (InsuranceCarDTO insuranceCarDTO : insuranceCarDTOS) {
            myCarService.addInsuranceCar(insuranceCarDTO);
        }


    }



    /*public void saveAddress(AddressDTO addressDTO) {
        AddressEntity addressEntity = addressMapper.toEntity(addressDTO);
        addressRepository.save(addressEntity);
    }*/

    /*public ClientsForm loadClients(ClientsForm clientsForm) {
        List<ClientEntity> clientEntities = clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for(ClientEntity clientEntity : clientEntities) {
            clientDTOList.add(clientMapper.toDTO(clientEntity));
            System.out.println(clientEntity.getAddress().getCity());
        }
        System.out.println("address "+clientDTOList.get(0).getHouse_number());
        clientsForm.setClientDTOList(clientDTOList);
        return clientsForm;
    }*/
}
