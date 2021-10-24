package cz.xlisto.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientsForm {

    private List<ClientDTO> clientDTOList;
}
