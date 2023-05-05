package com.safra.InventaryEntryAndExitService.repositories;

import com.safra.InventaryEntryAndExitService.entities.InventoryExitAndEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryExitAndEntryRepository extends MongoRepository<InventoryExitAndEntry,String> {
}
