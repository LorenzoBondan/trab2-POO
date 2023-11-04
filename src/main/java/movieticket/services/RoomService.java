package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.RoomDTO;
import movieticket.entities.Room;
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
		Room entity = new Room();
		copyDtoToEntity(dto, entity);
		repository.insert(entity);
	}
	
	public void update(Long id, RoomDTO dto) {
		Room entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		copyDtoToEntity(dto, entity);
		repository.update(entity);
	}
	
	public void delete(Long id) {
		repository.delete(id);
	}
	
	private void copyDtoToEntity(RoomDTO dto, Room entity) {
		entity.setId(dto.getId());
		entity.setNumber(dto.getNumber());
		entity.setCinema(cinemaRepository.findById(dto.getCinemaId()).get());
	}
}
