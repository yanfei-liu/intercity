package com.ld.intercity.business.address.service.serviceImpl;

import com.ld.intercity.business.address.mapper.AddressMapper;
import com.ld.intercity.business.address.model.AddressModel;
import com.ld.intercity.business.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public int save(AddressModel addressModel) {
        addressModel.setUuid(UUID.randomUUID().toString());
        return addressMapper.save(addressModel);
    }

    @Override
    public int del(String uuid) {
        return addressMapper.del(uuid);
    }

    @Override
    public int update(AddressModel addressModel) {
        return addressMapper.update(addressModel);
    }

    @Override
    public AddressModel getByUuid(String uuid) {
        return addressMapper.getByUuid(uuid);
    }

    @Override
    public List<AddressModel> findByCreateBy(String uuid) {
        return addressMapper.findByCreateBy(uuid);
    }
}
