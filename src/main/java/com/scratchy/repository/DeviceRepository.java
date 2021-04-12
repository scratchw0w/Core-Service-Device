package com.scratchy.repository;

import com.scratchy.domain.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, String> {}
