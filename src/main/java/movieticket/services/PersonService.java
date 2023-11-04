package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.PersonDTO;
import movieticket.entities.Person;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.PersonRepository;

public class PersonService {

	private PersonRepository repository = new PersonRepository();
	
	public List<PersonDTO> findAll(){
		List<Person> list = repository.findAll();
		return list.stream().map(obj -> new PersonDTO(obj)).collect(Collectors.toList());
	}
	
	public PersonDTO findById(Long id) {
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new PersonDTO(entity);
	}
	
	public void insert(PersonDTO dto) {
		Person entity = new Person();
		copyDtoToEntity(dto, entity);
		repository.insert(entity);
	}
	
	public void update(Long id, PersonDTO dto) {
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		copyDtoToEntity(dto, entity);
		repository.update(entity);
	}
	
	public void delete(Long id) {
		repository.delete(id);
	}
	
	private void copyDtoToEntity(PersonDTO dto, Person entity) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setRole(dto.getRole());
		entity.setMarried(repository.findById(dto.getMarriedId()).get());
	}
}
