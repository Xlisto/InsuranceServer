package cz.xlisto.services;

import cz.xlisto.dto.InsuranceCarCategoriesDTO;
import cz.xlisto.dto.InsuranceCarDTO;
import cz.xlisto.dto.mapper.InsuranceCarCategoriesMapper;
import cz.xlisto.dto.mapper.InsuranceCarMapper;
import cz.xlisto.entity.InsuranceCarCategoriesEntity;
import cz.xlisto.entity.InsuranceCarEntity;
import cz.xlisto.entity.repository.InsuranceCarCategoriesRepository;
import cz.xlisto.entity.repository.InsuranceCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyCarService {
    @Autowired
    InsuranceCarCategoriesRepository insuranceCarCategoriesRepository;

    @Autowired
    InsuranceCarRepository insuranceCarRepository;


    @Autowired
    private InsuranceCarCategoriesMapper insuranceCarCategoriesMapper;

    @Autowired
    private InsuranceCarMapper insuranceCarMapper;

    /**
     * Upraví kategorie vozidel
     * @param insuranceCarCategoriesDTO
     * @param catTypeId
     * @return
     */
    public InsuranceCarCategoriesDTO editCarCategory(InsuranceCarCategoriesDTO insuranceCarCategoriesDTO, Long catTypeId) {
        insuranceCarCategoriesDTO.setId(catTypeId);
        InsuranceCarCategoriesEntity insuranceCarCategoriesEntity = insuranceCarCategoriesMapper.toEntity(insuranceCarCategoriesDTO);
        InsuranceCarCategoriesEntity saved = insuranceCarCategoriesRepository.save(insuranceCarCategoriesEntity);
        return insuranceCarCategoriesMapper.toDTO(saved);
    }

    /**
     * Seznam kategorií vozidel
     * @return
     */
    public List<InsuranceCarCategoriesDTO> getCarCategories() {
        List<InsuranceCarCategoriesDTO> result = new ArrayList<>();
        for (InsuranceCarCategoriesEntity carCategories : insuranceCarCategoriesRepository.findAll()) {
            result.add(insuranceCarCategoriesMapper.toDTO(carCategories));
        }
        return result;
    }

    /**
     * Přidá novou kategorii vozidel
     * @param insuranceCarCategoriesDTO
     * @return
     */
    public InsuranceCarCategoriesDTO addInsuranceCarCategory(InsuranceCarCategoriesDTO insuranceCarCategoriesDTO) {
        InsuranceCarCategoriesEntity insuranceCarCategoriesEntity = insuranceCarCategoriesMapper.toEntity(insuranceCarCategoriesDTO);
        InsuranceCarCategoriesEntity saved = insuranceCarCategoriesRepository.save(insuranceCarCategoriesEntity);
        return insuranceCarCategoriesMapper.toDTO(saved);
    }

    /**
     * přidá nové ručení
     * @param insuranceCarDTO
     * @return
     */
    public InsuranceCarDTO addInsuranceCar(InsuranceCarDTO insuranceCarDTO) {
        InsuranceCarEntity insuranceCarEntity = insuranceCarMapper.toEntity(insuranceCarDTO);
        InsuranceCarEntity saved = insuranceCarRepository.save(insuranceCarEntity);
        return insuranceCarMapper.toDTO(saved);
    }

    /**
     * Upraví stávající ručení
     * @param insuranceCarDTO
     * @param insuranceCarId
     * @return
     */
    public InsuranceCarDTO editInsuranceCar(InsuranceCarDTO insuranceCarDTO, Long insuranceCarId) {
        insuranceCarDTO.setId(insuranceCarId);
        InsuranceCarEntity insuranceCarEntity = insuranceCarMapper.toEntity(insuranceCarDTO);
        InsuranceCarEntity saved = insuranceCarRepository.save(insuranceCarEntity);
        return  insuranceCarMapper.toDTO(saved);
    }

    /**
     * Odstraní ručení
     * @param insuranceCar
     */
    public void removeInsuranceCar(Long insuranceCar) {
        insuranceCarRepository.deleteById(insuranceCar);
    }

    /**
     * Zobrazí pojištění patřící klientovi
     * @param clientId
     * @return
     */
    public List<InsuranceCarDTO> getInsuranceCars(Long clientId)  {
        List<InsuranceCarDTO> insuranceCarDTOS = new ArrayList<>();
        List<InsuranceCarEntity> insuranceCarEntites = insuranceCarRepository.findAllByClientId(clientId);
        for(InsuranceCarEntity insuranceCarEntity: insuranceCarEntites) {
            insuranceCarDTOS.add(insuranceCarMapper.toDTO(insuranceCarEntity));
        }
        return insuranceCarDTOS;
    }

    public Long getCountInsurances() {
        return insuranceCarRepository.count();
    }

    public InsuranceCarCategoriesDTO getInsurancePrice(Long enginePower) {
        return insuranceCarCategoriesMapper.toDTO(insuranceCarCategoriesRepository.findPriceByEnginePower(enginePower));
    }
}
