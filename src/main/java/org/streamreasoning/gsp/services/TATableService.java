package org.streamreasoning.gsp.services;

import org.streamreasoning.gsp.data.TATable;
import org.streamreasoning.gsp.data.TATableRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TATableService {

    private final TATableRepository repository;

    public TATableService(TATableRepository repository) {
        this.repository = repository;
    }

    public Optional<TATable> get(Long id) {
        return repository.findById(id);
    }

    public TATable update(TATable entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<TATable> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<TATable> list(Pageable pageable, Specification<TATable> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
