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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
     * Zobrazí všechny klienty s adresou a telefonními čísly. Výs rozdělí na stránky a použije filtr
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
