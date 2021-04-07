package com.scratchy.repository;

import com.scratchy.domain.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceDao extends CrudRepository<Device, String> {}
