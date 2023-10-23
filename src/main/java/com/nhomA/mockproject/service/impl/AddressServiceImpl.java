package com.nhomA.mockproject.service.impl;

import com.nhomA.mockproject.dto.AddressDTO;
import com.nhomA.mockproject.entity.Address;
import com.nhomA.mockproject.entity.User;
import com.nhomA.mockproject.mapper.AddressMapper;
import com.nhomA.mockproject.repository.AddressRepository;
import com.nhomA.mockproject.repository.UserRepository;
import com.nhomA.mockproject.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(UserRepository userRepository, AddressMapper addressMapper, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }
    @Transactional
    @Override
    public List<AddressDTO> getAllAddress(String username) {
        Optional<User> existedUser = userRepository.findByUsername(username);
        List<Address> addresses = existedUser.get().getAddresses();
        return addressMapper.toDTOs(addresses);
    }
    @Transactional
    @Override
    public AddressDTO createAddress(String username, AddressDTO addressDTO) {
        Optional<User> existedUser = userRepository.findByUsername(username);
        User user = existedUser.get();
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        user.getAddresses().add(address);
        address.setUser(user);
        User saveUser = userRepository.save(user);
        return addressMapper.toDTO(address);
    }
    @Transactional
    @Override
    public AddressDTO updateAddressById(Long id, AddressDTO addressDTO) {
        Optional<Address> existedAddress = addressRepository.findById(id);
        Address address = existedAddress.get();
        address.setAddress(addressDTO.getAddress());
        Address saveAddress = addressRepository.save(address);
        return addressMapper.toDTO(address);
    }
    @Transactional
    @Override
    public boolean deleteAddressById(Long id) {
        try {
            addressRepository.deleteById(id);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

}
