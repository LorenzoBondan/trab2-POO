package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.RoomDTO;
import movieticket.entities.Room;
import movieticket.entities.Schedule;
import movieticket.entities.Seat;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.IntegrityViolationException;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.CinemaRepository;
import movieticket.repositories.RoomRepository;
import movieticket.repositories.ScheduleRepository;
import movieticket.repositories.SeatRepository;

public class RoomService {

	private RoomRepository repository = new RoomRepository();
	private CinemaRepository cinemaRepository = new CinemaRepository();
	private ScheduleRepository scheduleRepository = new ScheduleRepository();
	private SeatRepository seatRepository = new SeatRepository();
	
	public List<RoomDTO> findAll(){
		List<Room> list = repository.findAll();
		return list.stream().map(obj -> new RoomDTO(obj)).collect(Collectors.toList());
	}
	
	public RoomDTO findById(Long id) {
		Room entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada com o ID: " + id));
		List<Schedule> scheduleList = scheduleRepository.findAllByRoomId(id);
		entity.setSchedules(scheduleList);
		List<Seat> seatList = seatRepository.findAllByRoomId(id);
		entity.setSeats(seatList);
		return new RoomDTO(entity);
	}
	
	public void insert(RoomDTO dto) {
		try {
			Room entity = new Room();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Sala inserida com sucesso: " + dto);
		} catch (DuplicateResourceException e) {
			throw new DuplicateResourceException(e.getMessage());
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, RoomDTO dto) {
		try{
			Room entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada com o ID: " + id));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
		} catch (ResourceNotFoundException ex){
			throw new ResourceNotFoundException("Sala não encontrada com o ID: " + id);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		RoomDTO dto = findById(id);
		if(dto != null) {
			if(dto.getSchedulesIds().isEmpty() && dto.getSeatsIds().isEmpty()){
				repository.delete(id);
				System.out.println("Sala deletada com sucesso: " + id);
			} else{
				throw new IntegrityViolationException("Não é possível deletar pois há dependências relacionadas a esse objeto");
			}
		} else{
			throw new ResourceNotFoundException("Sala não encontrada com o ID: " + id);
		}
	}
	
	private void copyDtoToEntity(RoomDTO dto, Room entity) {
		entity.setId(dto.getId());
		entity.setNumber(dto.getNumber());
		entity.setCinema(cinemaRepository.findById(dto.getCinemaId()).get());
	}
}
