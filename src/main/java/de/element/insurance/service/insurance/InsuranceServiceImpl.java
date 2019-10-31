package de.element.insurance.service.insurance;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import de.element.insurance.domainobject.InsuranceModule;
import de.element.insurance.exception.ConstraintsViolationException;
import de.element.insurance.exception.EntityNotFoundException;
import de.element.insurance.repository.InsuranceModuleRepository;

@Service
public class InsuranceServiceImpl implements InsuranceService {
	
	private static final Logger LOG = LoggerFactory.getLogger(InsuranceServiceImpl.class);
	private InsuranceModuleRepository insuranceRepository;

	@Autowired
	public InsuranceServiceImpl(InsuranceModuleRepository moduleRepository) {
		this.insuranceRepository = moduleRepository;
	}

	@Override
	public List<InsuranceModule> findAll(Pageable pageable) {
		return insuranceRepository.findAllByDeletedFalse(pageable);
	}

	@Override
	@Transactional
	public InsuranceModule save(InsuranceModule insuranceToCreate)  throws ConstraintsViolationException {
		InsuranceModule insuranceModule = null;
        try {
        	insuranceModule = insuranceRepository.save(insuranceToCreate);
        }
        catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a insurance module: {}", insuranceModule, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return insuranceModule;	
	}

	@Override
	@Transactional
	public InsuranceModule update(Long id, InsuranceModule insuranceToUpdate) throws EntityNotFoundException {
		return insuranceRepository.findById(id).map(insurance -> {
			insurance.setName(insuranceToUpdate.getName());
			insurance.setMinimumCoverage(insuranceToUpdate.getMinimumCoverage());
			insurance.setMaximumCoverage(insuranceToUpdate.getMaximumCoverage());
			insurance.setRisk(insuranceToUpdate.getRisk());
			return insuranceRepository.save(insurance);
		}).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
	}
	
	@Override
	@Transactional
	public void delete(Long id) throws EntityNotFoundException {
		InsuranceModule insurance = insuranceRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
		insurance.setDeleted(true);
		insuranceRepository.save(insurance);
	}

	@Override
	@Transactional
	public InsuranceModule partialUpdate(Long id, Map<String, Object> fields)
			throws EntityNotFoundException {
		InsuranceModule insuranceModule = insuranceRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
		fields.forEach((k, v) -> {
	       // use reflection to get field k on InsuranceModule and set it to value k
			if(v != null) {
		        Field field = ReflectionUtils.findField(InsuranceModule.class, k);
		        ReflectionUtils.setField(field, insuranceModule, v);
			}
	    });
		
		insuranceRepository.save(insuranceModule);
		return null;
	}
}