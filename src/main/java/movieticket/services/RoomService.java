package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.RoomDTO;
import movieticket.entities.Room;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.CinemaRepository;
import movieticket.repositories.RoomRepository;

public class RoomService {

	private RoomRepository repository = new RoomRepository();
	private CinemaRepository cinemaRepository = new CinemaRepository();
	
	public List<RoomDTO> findAll(){
		List<Room> list = repository.findAll();
		return list.stream().map(obj -> new RoomDTO(obj)).collect(Collectors.toList());
	}
	
	public RoomDTO findById(Long id) {
		Room entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new RoomDTO(entity);
	}
	
	public void insert(RoomDTO dto) {
		try {
			Room entity = new Room();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Sala inserida com sucesso: " + dto);
		} catch(Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, RoomDTO dto) {
		try{
			Room entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
		} catch(Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		RoomDTO dto = findById(id);
		if(dto != null) {
			repository.delete(id);
			System.out.println("Sala deletada com sucesso: " + id);
		}
	}
	
	private void copyDtoToEntity(RoomDTO dto, Room entity) {
		entity.setId(dto.getId());
		entity.setNumber(dto.getNumber());
		entity.setCinema(cinemaRepository.findById(dto.getCinemaId()).get());
	}
}
