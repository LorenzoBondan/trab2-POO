package movieticket.controllers;

import java.util.List;

import movieticket.dtos.ActorDTO;
import movieticket.dtos.DirectorDTO;
import movieticket.dtos.PersonDTO;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.PersonService;

public class PersonController {

	private PersonService service = new PersonService();
	
	public void findAll() {
		List<PersonDTO> list = service.findAll();
        for(PersonDTO personDto : list) {
        	System.out.println(personDto);
        }
	}
	
	public void findAllActors() {
		List<ActorDTO> list = service.findAllActors();
        for(ActorDTO actorDto : list) {
        	System.out.println(actorDto);
        }
	}
	
	public void findAllDirectors() {
		List<DirectorDTO> list = service.findAllDirectors();
        for(DirectorDTO directorDto : list) {
        	System.out.println(directorDto);
        }
	}
	
	public void findAllActorsByMovieId(Long movieId) {
		List<ActorDTO> list = service.findAllActorsByMovieId(movieId);
        for(ActorDTO actorDto : list) {
        	System.out.println(actorDto);
        }
	}
	
	public void findAllDirectorsByMovieId(Long movieId) {
		List<DirectorDTO> list = service.findAllDirectorsByMovieId(movieId);
        for(DirectorDTO directorDto : list) {
        	System.out.println(directorDto);
        }
	}
	
	public void findById(Long id) {
		try {
			PersonDTO dto = service.findById(id);
			System.out.println(dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Pessoa não encontrada");
		}
	}
	
	public void findActorById(Long id) {
		try {
			ActorDTO dto = service.findActorById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println("Ator não encontrado");
		}
	}
	
	public void findDirectorById(Long id) {
		try {
			DirectorDTO dto = service.findDirectorById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println("Diretor não encontrado");
		}
	}
	
	public void insert(PersonDTO dto) {
		try {
			service.insert(dto);
		} catch(InvalidDataException e) {
			System.out.println("Dados inválidos.");
		}
	}
	
	public void update(Long id, PersonDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Pessoa não encontrada");
		} catch(InvalidDataException e) {
			System.out.println("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Pessoa não encontrada");
		}
	}
}
