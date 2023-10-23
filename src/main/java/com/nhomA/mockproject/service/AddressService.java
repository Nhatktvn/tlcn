package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAllAddress (String username);
    AddressDTO createAddress (String username, AddressDTO addressDTO);
    AddressDTO updateAddressById (Long id, AddressDTO addressDTO);
    boolean deleteAddressById (Long id);
}
